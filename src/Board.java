import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Board {

    private final int n;
    private int[][] square;

    // create a board from an n-by-n array of tiles
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        square = tiles;
    }

    // string represention of this board
    public String toString() {

        String r = "";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                r += (square[i][j] + "  ");
            }
            r += "\n";
        }

        return r;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int outOfPlaceNum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (square[i][j] != i * n + j) {
                    outOfPlaceNum++;
                }
            }
        }

        return outOfPlaceNum;
    }

    // sum of Manhattan distance between tiles and goal
    public int manhattan() {

        int distances = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int current = square[i][j];
                // calculate the correct position for the current value
                if (current == 0) {
                    continue;
                }
                if (current == i * n + j + 1) {
                    StdOut.println("distance: (" + j + ", " + i + ") " + current + " -> 0");
                    continue;
                }

                int ox = current % n;

                int x = ox == 0 ? n - 1 : ox - 1;
                int y = (current - x) / n;

                int df = Math.abs(x - j) + Math.abs(y - i);

                distances += df;
                StdOut.println("distance: (" + j + ", " + i + ") " + current + " -> " + df);
            }
        }

        return distances;
    }

    // is this board the goal board?
    public boolean isGoal() {

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (square[i][j] != i * n + j + 1) {
                    return false;
                }
            }
        }

        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                return null;
            }
        };
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return null;
    }

    // unit testing
    public static void main(String[] args) {

        In in = new In(args[0]);

        int n = 0;

        if (!in.isEmpty()) {
            n = in.readInt();
        }

        int row = 0;
        int col = 0;

        int[][] tiles = new int[n][n];

        while (!in.isEmpty()) {
            tiles[row][col] = in.readInt();

            if (col == n - 1) {
                row++;
                col = 0;
            } else {
                col++;
            }
        }

        Board board = new Board(tiles);
        int moves = board.manhattan();
        String r = board.toString();
        StdOut.println(r);

    }

}
