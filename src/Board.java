import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

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
        return "";
    }

    // board dimension n (可以是二维、三维、甚至多维？)
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
                int x = current % n;
                int y = (current - x) / n;
                distances += Math.abs(x - i);
                distances += Math.abs(y - i);
                StdOut.println("distance: " + (Math.abs(x - i) + Math.abs(y - i)));
            }
        }

        return distances;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        return true;
    }

    // all neighboring boards(how to comprehend this? why boards instead of tiles?)
//    public Iterable<Board> neighbors() {
//    }

    // a board that is obtained by exchanging any pair of tiles
    // public Board twin() {
    //     return new Board();
    // }

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
        board.manhattan();

        // for (int i = 0; i < n; i++) {
        //     for (int j = 0; j < n; j++) {
        //         StdOut.println(tiles[i][j]);
        //     }
        // }

    }

}
