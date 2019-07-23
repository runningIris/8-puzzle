import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    private boolean solvable;
    private int leastMoves;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        solvable = initial.isSolvable();
        leastMoves = 0;

        if (!solvable) {
            return;
        }


        List<Board> solutions = new ArrayList<>();

        MinPQ<Board> pq = new MinPQ<>(1, manhattanComparator());

        Board prev = initial;

        pq.insert(prev);

        while(!prev.isGoal() && leastMoves < 100) {
            leastMoves++;
            prev = pq.delMin();
            StdOut.println(prev.toString());
            for(Board neighbor: prev.neighbors()) {
                pq.insert(neighbor);
            }
        }
    }

    private Comparator<Board> manhattanComparator() {
       return new Comparator<Board>() {
           @Override
           public int compare(Board o1, Board o2) {
               return o1.manhattan() - o2.manhattan();
           }
       };
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board
    public int moves() {
        return leastMoves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        return new ArrayList<Board>();
    }

    public static void main(String[] args) {
        In in = new In(args[0]);

        int n = 0;

        if (!in.isEmpty()) {
            n = in.readInt();
        }

        int[][] tiles = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }

        Board board = new Board(tiles);

        Solver s = new Solver(board);

        StdOut.println(s.leastMoves);
    }
}
