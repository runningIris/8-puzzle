public class Board {
    // create a board from an n-by-n array of tiles
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {

    }

    // string represention of this board
    public String toString() {
    }

    // board dimension n (可以是二维、三维、甚至多维？)
    public int dimension() {
    }

    // number of tiles out of place
    public int hamming() {
    }

    // sum of Manhattan distance between tiles and goal
    public int manhattan() {
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
    }

}
