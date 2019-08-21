import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class Board {

    public int[][] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = tiles;
    }

    // string representation of this board
    public String toString() {
        int n = dimension();
        String result = n + "\n";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result += this.tiles[i][j];
                result += this.tiles[i][j] < 10 ? "  " : " ";
            }
            result += "\n";
        }
        return result;
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
                // 取余是为了 0 的情况
                if (this.tiles[i][j] != (i * 3 + j + 1) % (n * n)) {
                    count++;
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

                // 0 放在 n * n 的格子里
                if (result == 0) {
                    result = n * n;
                }

                int row = (result - 1) / n;
                int col = (result - 1) % n;

                distance += Math.abs(row - i);
                distance += Math.abs(col - j);
            }
        }

        return distance;
    }

    // is this board the goal board
    public boolean isGoal() {
        return hamming() == 0;
    }

    private int[][] copyTiles() {
        int n = dimension();
        int[][] newTiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                newTiles[i][j] = this.tiles[i][j];
            }
        }
        return newTiles;
    }

    private Board exchange(int row, int col, int nextRow, int nextCol) {
        int n = dimension();
        int[][] newTiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                newTiles[i][j] = this.tiles[i][j];
            }
        }

        int tmp = newTiles[row][col];
        newTiles[row][col] = newTiles[nextRow][nextCol];
        newTiles[nextRow][nextCol] = tmp;
        return new Board(newTiles);
    }

    private Board neighborWithDirection(int row, int col, String direction) {
        switch (direction) {
            case "up":
                if (row - 1 > 0) {
                    return exchange(row, col, row - 1, col);
                }
                break;
            case "down":
                if (row + 1 < 3) {
                    return exchange(row, col, row + 1, col);
                }
                break;
            case "left":
                if (col - 1 > 0) {
                    return exchange(row, col, row, col - 1);
                }
                break;
            case "right":
                if (col + 1 < 3) {
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
        return null;
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

        Board board = new Board(tiles);

        StdOut.println(board.manhattan());
        StdOut.println(board.toString());
    }
}
