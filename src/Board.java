import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final int n;
    private final int[][] tiles;
    private int[] flattenTiles;
    private int inversionCount = 0;

    // create a board from an n-by-n array of tiles
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        this.tiles = tiles;
        flattenTiles = new int[n * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                flattenTiles[i * n + j] = tiles[j][i];
            }
        }

        for (int i = 0; i < n * n; i++) {
            for (int j = i + 1; j < n * n; j++) {
                // StdOut.println(i + ", " + j);
                if (flattenTiles[i] > 0 && flattenTiles[j] > 0 && flattenTiles[i] > flattenTiles[j]) {
                    inversionCount++;
                }
            }
        }

        StdOut.println("intersions: " + inversionCount);
    }

    public boolean isSolvable() {
        return inversionCount % 2 != 0;
    }

    // string represention of this board
    public String toString() {
        String r = n + "\n";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                r += (tiles[i][j] + "  ");
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
        for (int i = 0; i < n * n; i++) {
            if (flattenTiles[i] != i + 1) {
                outOfPlaceNum++;
            }
        }

        return outOfPlaceNum;
    }

    // sum of Manhattan distance between tiles and goal
    public int manhattan() {

        int distances = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int current = tiles[i][j];
                // calculate the correct position for the current value
                if (current == 0) {
                    continue;
                }
                if (current == i * n + j + 1) {
                    continue;
                }

                int ox = current % n;

                int x = ox == 0 ? n - 1 : ox - 1;
                int y = (current - x) / n;

                int distance = Math.abs(x - j) + Math.abs(y - i);

                distances += distance;
            }
        }

        return distances;
    }

    // is this board the goal board?
    public boolean isGoal() {

        for (int i = 0; i < n * n; i++) {
            if (flattenTiles[i] != i + 1) {
                return false;
            }
        }

        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        return toString().equals(y.toString());
    }

    private Board createNewBoardBySwitchingTiles(int row, int col, int nextRow, int nextCol) {
        int[][] newTiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newTiles[i][j] = tiles[i][j];
            }
        }

        newTiles[row][col] = newTiles[nextRow][nextCol];
        newTiles[nextRow][nextCol] = 0;

        return new Board(newTiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<>();

        // find zero
        int row = 0;
        int col = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    row = i;
                    col = j;
                }
            }
        }

        // top
        if (row > 0) {
            neighbors.add(createNewBoardBySwitchingTiles(row, col, row - 1, col));
        }

        // bottom
        if (row < n - 1) {
            neighbors.add(createNewBoardBySwitchingTiles(row, col, row + 1, col));
        }

        // left
        if (col > 0) {
            neighbors.add(createNewBoardBySwitchingTiles(row, col, row, col - 1));
        }

        // right
        if (col < n - 1) {
            neighbors.add(createNewBoardBySwitchingTiles(row, col, row, col + 1));
        }

        return neighbors;
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

        int[][] tiles = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }

        Board board = new Board(tiles);
        int moves = board.manhattan();
        String r = board.toString();
        StdOut.println("Original board: \n" + r);


        for(Board neighbor: board.neighbors()) {
            StdOut.println("Neighbor board: \n" + neighbor.toString());
        }


    }

}
