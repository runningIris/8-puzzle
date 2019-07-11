import edu.princeton.cs.algs4.StdOut;

public class Board {

    private final int n;
    private int[][] square;

    // create a board from an n-by-n array of tiles
    // where tiles[row][col] = tile at (row, col)
    // public Board(int[][] tiles) {
    public Board() {
        int[][] tiles = {{2, 2}, {2, 1}, {3, 2}, {3, 1}, {1, 2}, {3, 3}, {2, 3}, {1, 3}, {1, 1}};

        n = tiles.length;

        square = new int[n][n];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                square[i][j] = tiles[i][j];
            }
        }

    }

    // string represention of this board
    public String toString() {
    }

    // board dimension n (可以是二维、三维、甚至多维？)
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int outOfPlaceNum = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {

                if (square[i][j] != i * n + j) {
                    outOfPlaceNum++;
                }
            }
        }

        return outOfPlaceNum;
    }

    // sum of Manhattan distance between tiles and goal
    public int manhattan() {

        int[][] distances = new int[n][n];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                int current = square[i][j];
                int x = current % n;
                int y = (current - x) / n;
                distances[i][j] = Math.abs(x - i) + Math.abs(y - i);
                StdOut.println("distance: " + distances[i][j]);
            }
        }
    }

    // is this board the goal board?
    public boolean isGoal() {
    }

    // does this board equal y?
    public boolean equals(Object y) {

    }

    // all neighboring boards(how to comprehend this? why boards instead of tiles?)
    public Iterable<Board> neighbors() {
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
    }

    // unit testing
    public static void main(String[] args) {
        Board board = new Board();
        board.manhattan();
    }

}
