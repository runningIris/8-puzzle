import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


public class Solver {
    private SearchNode result;
    private boolean isSolvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("the constructor argument initial should not be null");
        }

        // initial board
        MinPQ<SearchNode> minPQ = new MinPQ<SearchNode>();
        SearchNode init = new SearchNode();
        init.board = initial;
        init.moves = 0;
        init.prev = null;
        minPQ.insert(init);

        // twin board
        Board initialTwin = initial.twin();

        MinPQ<SearchNode> twinMinPQ = new MinPQ<SearchNode>();
        SearchNode twin = new SearchNode();
        twin.board = initialTwin;
        twin.moves = 0;
        init.prev = null;
        twinMinPQ.insert(twin);


        while (!minPQ.isEmpty() || !twinMinPQ.isEmpty()) {

            SearchNode sn = minPQ.delMin();

            // solved
            if (sn.board.isGoal()) {
                isSolvable = true;
                result = sn;
                break;
            }

            for (Board board: sn.board.neighbors()) {
                if (sn.prev == null || !board.equals(sn.prev.board)) {
                    SearchNode neighbor = new SearchNode();
                    neighbor.board = board;
                    neighbor.moves = sn.moves + 1;
                    neighbor.prev = sn;
                    minPQ.insert(neighbor);
                }
            }

            SearchNode twinSN = twinMinPQ.delMin();

            // not solvable
            if (twinSN.board.isGoal()) {
                isSolvable = false;
                break;
            }

            for (Board board: twinSN.board.neighbors()) {

                if (twinSN.prev == null || !board.equals(twinSN.prev.board)) {
                    SearchNode neighbor = new SearchNode();
                    neighbor.board = board;
                    neighbor.moves = twinSN.moves + 1;
                    neighbor.prev = sn;
                    twinMinPQ.insert(neighbor);
                }
            }
        }
    }

    private static class SearchNode implements Comparable<SearchNode> {
        private SearchNode prev;
        private Board board;
        private int moves;
        public int compareTo(Solver.SearchNode that) {
            return Integer.compare(board.manhattan() + moves, that.board.manhattan() + that.moves);
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board
    public int moves() {
        if (!isSolvable) {
            return -1;
        }
        return result.moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (!isSolvable) {
            return null;
        }

        int len = result.moves + 1;
        SearchNode current = result;

        Board[] steps = new Board[len];

        for (int i = 0; i < len; i++) {
            steps[len - i - 1] = current.board;
            current = current.prev;
        }

        List<Board> all = new ArrayList<>();

        Collections.addAll(all, steps);

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
