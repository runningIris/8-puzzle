import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final int[][] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        int n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        int n = dimension();
        StringBuilder result = new StringBuilder();
        result.append(n);
        result.append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result.append(this.tiles[i][j]);
                result.append(this.tiles[i][j] < 10 ? "  " : " ");
            }
            result.append("\n");
        }
        return result.toString();
    }

    // board dimension n
    public int dimension() {
        return this.tiles.length;
    }

    // number of tiles out of place
    public  int hamming() {

        int count = 0;
        int n = dimension();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // 0 不计数
                if (this.tiles[i][j] != 0) {
                    if (this.tiles[i][j] != i * n + j + 1) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {

        int n = dimension();
        int distance = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int result = this.tiles[i][j];

                // 0 不计数
                if (result != 0) {
                    int row = (result - 1) / n;
                    int col = (result - 1) % n;

                    distance += Math.abs(row - i);
                    distance += Math.abs(col - j);
                }
            }
        }

        return distance;
    }

    // is this board the goal board
    public boolean isGoal() {
        return hamming() == 0;
    }

    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }

        if (y.getClass() != Board.class) {
            return false;
        }

        Board x = (Board) y;

        int n = dimension();

        if (n != x.dimension()) {
            return false;
        }


        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != x.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private Board exchange(int row, int col, int nextRow, int nextCol) {
        int n = dimension();
        int[][] newTiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newTiles[i][j] = this.tiles[i][j];
            }
        }

        int tmp = newTiles[row][col];
        newTiles[row][col] = newTiles[nextRow][nextCol];
        newTiles[nextRow][nextCol] = tmp;
        return new Board(newTiles);
    }

    private Board neighborWithDirection(int row, int col, String direction) {
        int n = dimension();

        switch (direction) {
            case "up":
                if (row > 0) {
                    return exchange(row, col, row - 1, col);
                }
                break;
            case "down":
                if (row + 1 < n) {
                    return exchange(row, col, row + 1, col);
                }
                break;
            case "left":
                if (col > 0) {
                    return exchange(row, col, row, col - 1);
                }
                break;
            case "right":
                if (col + 1 < n) {
                    return exchange(row, col, row, col + 1);
                }
                break;
        }
        return null;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        int n = dimension();

        // 木得办法，java 叫我写个默认值，勉强写个 -1 吧
        int zeroRow = -1;
        int zeroCol = -1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                }
            }
        }

        List<Board> neighbors = new ArrayList<>();

        // top
        Board top = neighborWithDirection(zeroRow, zeroCol, "up");
        if (top != null) {
            neighbors.add(top);
        }

        // bottom
        Board bottom = neighborWithDirection(zeroRow, zeroCol, "down");
        if (bottom != null) {
            neighbors.add(bottom);
        }

        // left
        Board left = neighborWithDirection(zeroRow, zeroCol, "left");
        if (left != null) {
            neighbors.add(left);
        }


        // right
        Board right = neighborWithDirection(zeroRow, zeroCol, "right");
        if (right != null) {
            neighbors.add(right);
        }

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

        int n = dimension();

        int row1 = 0;
        int col1 = 0;
        int row2 = n - 1;
        int col2 = 0;

        while (this.tiles[row1][col1] == 0) {
            col1++;
        }
        while (this.tiles[row2][col2] == 0) {
            col2++;
        }

        return exchange(row1, col1, row2, col2);
    }

    public static void main(String[] args) {
        In in = new In(args[0]);

        int n = in.readInt();

        int[][] tiles = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }

        Board board1 = new Board(tiles);
        Board board2 = new Board(tiles);

        StdOut.println(board1.equals(board2));
    }
}
