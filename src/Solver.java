import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class Solver {
    private Board initial;
    private int moves;
    private MinPQ<SearchNode> minPQ;
    private List<SearchNode> solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("the constructor argument initial should not be null");
        }

        this.initial = initial;
        if (!isSolvable()) {
            throw new IllegalArgumentException("This board is not solvable.");
        }

        minPQ = new MinPQ<SearchNode>();
        solution = new ArrayList<SearchNode>();

        SearchNode init = new SearchNode();
        init.board = initial;
        init.moves = 0;

        minPQ.insert(init);

        while(!minPQ.isEmpty()) {
            SearchNode sn = minPQ.delMin();
            solution.add(sn);

            if (sn.board.isGoal()) {
                break;
            }

            for (Board board: sn.board.neighbors()) {
                SearchNode neighbor = new SearchNode();
                neighbor.board = board;
                neighbor.moves = sn.moves + 1;
                minPQ.insert(neighbor);
            }
        }

        // solved!

    }

    private static class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int moves;
        public int compareTo(Solver.SearchNode that) {
            return Integer.compare(board.manhattan() + moves, that.board.manhattan() + that.moves);
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        int n = initial.dimension();
        int[] flattenTiles = new int[n * n];
        int inversionCount = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                flattenTiles[i * n + j] = initial.tiles[j][i];
            }
        }

        // use tiles' inversion to figure out whether the board is solvable.
        for (int i = 0; i < n * n; i++) {
            for (int j = i + 1; j < n * n; j++) {
                // StdOut.println(i + ", " + j);
                if (flattenTiles[i] > 0 && flattenTiles[j] > 0 && flattenTiles[i] > flattenTiles[j]) {
                    inversionCount++;
                }
            }
        }
        return inversionCount % 2 != 0;
    }

    // min number of moves to solve initial board
    public int moves() {
        return solution.get(solution.size() - 1).moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        List<Board> all = new ArrayList<>();
        for (SearchNode s: solution) {
            all.add(s.board);
        }
        return all;
    }

    // test client
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
