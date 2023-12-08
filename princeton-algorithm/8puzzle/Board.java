/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class Board {
    private int[][] tiles;
    private int blankCol = 0;
    private int blankRow = 0;
    private int hamming = 0;
    private int manhattan = 0;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = new int[tiles.length][tiles[0].length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                this.tiles[i][j] = tiles[i][j];

                if (tiles[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                }
            }
        }

        updateHamming();
        updateManhattan();
    }

    // string representation of this board
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(dimension());
        builder.append("\n");
        for (int i = 0; i < tiles.length; i++) {
            String split = "";
            for (int j = 0; j < tiles[i].length; j++) {
                builder.append(split);
                builder.append(tiles[i][j]);
                split = " ";
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    // board dimension n
    public int dimension() {
        return this.tiles.length;
    }

    public int hamming() {
        return hamming;
    }

    public int manhattan() {
        return manhattan;
    }

    // number of tiles out of place
    private int updateHamming() {
        int outOfPlaceCount = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (isBlank(i, j)) {
                    continue;
                }
                if (this.tiles[i][j] != getGoalTile(i, j)) {
                    outOfPlaceCount++;
                }
            }
        }

        hamming = outOfPlaceCount;
        return outOfPlaceCount;
    }

    // sum of Manhattan distances between tiles and goal
    private int updateManhattan() {
        int outOfPlaceDis = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                int targetTile = getGoalTile(i, j);
                if (isBlank(i, j)) {
                    continue;
                }
                if (this.tiles[i][j] == targetTile) {
                    continue;
                }
                int row = getTileGoalRow(this.tiles[i][j]);
                int col = getTileGoalCol(this.tiles[i][j]);
                int distance = calCulateDis(i, j, row, col);
                // StdOut.printf("i=%d, j=%d, targetrow=%d, targetcol=%d, distance=%d, curr_tile=%d, target_tile=%d\n", i, j, row, col, distance, tiles[i][j], targetTile);
                outOfPlaceDis += distance;
            }
        }

        manhattan = outOfPlaceDis;
        return outOfPlaceDis;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                int targetTile = getGoalTile(i, j);
                // StdOut.printf("i=%d, j=%d, curr=%d, target=%d\n", i, j, tiles[i][j], targetTile);
                if (isBlank(i, j)) {
                    continue;
                }
                if (tiles[i][j] != targetTile) {
                    return false;
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (y.getClass() == getClass()) {
            Board board = (Board) y;
            return Arrays.deepEquals(tiles, board.tiles);
        }
        return false;
    }

    // public int hashCode() {
    //     int result = Objects.hash(blankCol, blankRow);
    //     result = 31 * result + Arrays.deepHashCode(tiles);
    //     return result;
    // }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> neighs = new Queue<Board>();
        int[][] neighLoop = {
                {-1, 0},
                {0, 1},
                {1, 0},
                {0, -1}
        };

        // StdOut.println("calculate neigh start:\n" + this);
        for (int loop = 0; loop < neighLoop.length; loop++) {
            int newRow = blankRow + neighLoop[loop][0];
            int newCol = blankCol + neighLoop[loop][1];
            if (!isValidPos(newRow, newCol)) {
                continue;
            }
            Board neighBoard = new Board(tiles);
            // StdOut.printf("blankRow=%d, blankCol=%d, newRow=%d, newCol=%d\n", blankRow, blankCol, newRow, newCol);
            swapTiles(neighBoard, blankRow, blankCol, newRow, newCol);
            neighBoard.blankRow = newRow;
            neighBoard.blankCol = newCol;
            neighs.enqueue(neighBoard);
        }
        // StdOut.println("calculate neigh end");
        return neighs;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board twinBoard = new Board(tiles);
        while (true) {
            int newRow1 = StdRandom.uniformInt(dimension());
            int newCol1 = StdRandom.uniformInt(dimension());

            int newRow2 = StdRandom.uniformInt(dimension());
            int newCol2 = StdRandom.uniformInt(dimension());
            swapTiles(twinBoard, newRow1, newCol1, newRow2, newCol2);

            if (newRow1 != newRow2 || newCol1 != newCol2) {
                break;
            }
        }
        return twinBoard;
    }

    private int getGoalTile(int row, int col) {
        return row * dimension() + col + 1;
    }

    private int getTileGoalRow(int tile) {
        return (tile - 1) / dimension();
    }
    private int getTileGoalCol(int tile) {
        return tile - getTileGoalRow(tile) * dimension() - 1;
    }
    private int calCulateDis(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
    private boolean isValidPos(int row, int col) {
        return isValidDimension(row) && isValidDimension(col);
    }
    private boolean isValidDimension(int pos) {
        return pos >= 0 && pos < dimension();
    }
    private void swapTiles(Board board, int row1, int col1, int row2, int col2) {
        int temp = board.tiles[row1][col1];
        board.tiles[row1][col1] = board.tiles[row2][col2];
        board.tiles[row2][col2] = temp;
        board.updateManhattan();
        board.updateManhattan();
    }

    private boolean isBlank(int row, int col) {
        return tiles[row][col] == 0;
    }

    private static void test2() {
        // Test Board constructor and toString method
        int[][] tiles = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        Board board = new Board(tiles);
        String expectedBoardString = "3\n1 2 3\n4 5 6\n7 8 0\n";
        assert board.toString().equals(expectedBoardString) : "Board constructor or toString failed";
        // Test dimension method
        assert board.dimension() == 3 : "Dimension failed";
        // Test hamming method
        StdOut.println("board.hamming: " + Integer.toString(board.hamming()));
        assert board.hamming() == 0 : "Hamming failed";
        // Test manhattan method
        assert board.manhattan() == 0 : "Manhattan failed";
        // Test isGoal method
        assert board.isGoal() : "IsGoal failed";


        int[][] tilesHamming = new int[][]{
                {1, 2, 3},
                {4, 6, 5},
                {7, 8, 0}
        };
        Board boardHamming = new Board(tilesHamming);
        StdOut.println("boardHamming.hamming: " + Integer.toString(boardHamming.hamming()));
        assert boardHamming.hamming() == 2 : "Hamming failed";



        int[][] tilesHamming2 = new int[][]{
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5}
        };
        Board boardHamming2 = new Board(tilesHamming2);
        StdOut.println("boardHamming2.hamming: " + Integer.toString(boardHamming2.hamming()));
        assert boardHamming2.hamming() == 5 : "Hamming failed";


        int[][] tilesManhattan = new int[][]{
                {1, 2, 3},
                {4, 6, 5},
                {7, 8, 0}
        };
        Board boardManhattan = new Board(tilesManhattan);
        assert boardManhattan.manhattan() == 2 : "Manhattan failed";

        int[][] tilesManhattan2 = new int[][]{
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5}
        };
        Board boardManhattan2 = new Board(tilesManhattan2);
        assert boardManhattan2.manhattan() == 10 : "Manhattan failed";


        // Test equals method
        Board anotherBoard = new Board(tiles);
        assert board.equals(anotherBoard) : "Equals failed";


        for (Board neighbor : board.neighbors()) {
            StdOut.println(neighbor.toString());
        }

        // Test twin method
        int[][] twinTiles = new int[][]{
                {1, 2, 3},
                {4, 6, 5},
                {7, 8, 0}
        };
        Board twinBoard = new Board(twinTiles);
        StdOut.println(twinBoard.toString());

        System.out.println("All tests passed!");
    }

    private static void neighTest1() {
        int[][] tiles1 = new int[][]{
                {1, 2, 3},
                {4, 6, 0},
                {7, 8, 5}
        };

        Board board = new Board(tiles1);
        for (Board neighbor : board.neighbors()) {
            StdOut.println(neighbor.toString());
        }
        System.out.println("All tests passed!");
    }

    private static void neighTest2() {
        int[][] tiles1 = new int[][]{
                {1, 2, 3},
                {4, 6, 5},
                {7, 8, 0}
        };

        Board board = new Board(tiles1);
        StdOut.println("base: \n" + board);
        for (Board neighbor : board.neighbors()) {
            StdOut.println(neighbor.toString());
        }
        System.out.println("All tests passed!");
    }

    private static void testBase() {
        // Test Board constructor and toString method
        int[][] tiles = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        Board board = new Board(tiles);
        String expectedBoardString = "3\n1 2 3\n4 5 6\n7 8 0\n";
        StdOut.println(board.toString());
        assert board.toString().equals(expectedBoardString) : "Board constructor or toString failed";

        // Test dimension method
        StdOut.println("dimension: " + Integer.toString(board.dimension()));
        assert board.dimension() == 3 : "Dimension failed";

        // Test hamming method
        StdOut.println("hamming: " + Integer.toString(board.hamming()));
        assert board.hamming() == 0 : "Hamming failed";

        // Test manhattan method
        StdOut.println("manhattan: " + Integer.toString(board.manhattan()));
        assert board.manhattan() == 0 : "Manhattan failed";

        // Test isGoal method
        StdOut.println("isGoal: " + Boolean.toString(board.isGoal()));
        assert board.isGoal() : "IsGoal failed";

        // Test equals method
        Board anotherBoard = new Board(tiles);
        assert board.equals(anotherBoard) : "Equals failed";
        System.out.println("All tests passed!");
    }

    public static void main(String[] args) {
        test2();
        testBase();
        neighTest1();
        neighTest2();
        System.out.println("All tests passed!");
    }
}
