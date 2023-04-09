import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */
public class Percolation {
    private boolean[][] sites;
    private int openNumber = 0;
    private WeightedQuickUnionUF quickUnionUF;
    private WeightedQuickUnionUF quickUnionUFForFull;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        sites = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sites[i][j] = false;
            }
        }

        quickUnionUF = new WeightedQuickUnionUF(n * n + 2);
        quickUnionUFForFull = new WeightedQuickUnionUF(n * n + 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isInvalidArgument(row) || isInvalidArgument(col)) {
            throw new IllegalArgumentException();
        }
        if (isOpen(row, col)) {
            return;
        }

        if (((row - 1) > 0) && isOpen(row - 1, col)) {
            quickUnionUF.union(gridToIndex(row, col), gridToIndex(row - 1, col));
            quickUnionUFForFull.union(gridToIndex(row, col), gridToIndex(row - 1, col));
        }

        if (((row + 1) <= sites.length) && isOpen(row + 1, col)) {
            quickUnionUF.union(gridToIndex(row, col), gridToIndex(row + 1, col));
            quickUnionUFForFull.union(gridToIndex(row, col), gridToIndex(row + 1, col));
        }

        if (((col - 1) > 0) && isOpen(row, col - 1)) {
            quickUnionUF.union(gridToIndex(row, col), gridToIndex(row, col - 1));
            quickUnionUFForFull.union(gridToIndex(row, col), gridToIndex(row, col - 1));
        }

        if (((col + 1) <= sites.length) && isOpen(row, col + 1)) {
            quickUnionUF.union(gridToIndex(row, col), gridToIndex(row, col + 1));
            quickUnionUFForFull.union(gridToIndex(row, col), gridToIndex(row, col + 1));
        }

        if (row == 1) {
            quickUnionUF.union(0, gridToIndex(row, col));
            quickUnionUFForFull.union(0, gridToIndex(row, col));
        }

        if (row == sites.length) {
            quickUnionUF.union(sites.length * sites.length + 1, gridToIndex(row, col));
        }

        openNumber++;
        sites[row - 1][col - 1] = true;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (isInvalidArgument(row) || isInvalidArgument(col)) {
            throw new IllegalArgumentException();
        }
        return sites[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (isInvalidArgument(row) || isInvalidArgument(col)) {
            throw new IllegalArgumentException();
        }
        return isOpen(row, col) &&
              (quickUnionUFForFull.find(0) == quickUnionUFForFull.find(gridToIndex(row, col)));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openNumber;
    }

    // does the system percolate?
    public boolean percolates() {
        return quickUnionUF.find(0) == quickUnionUF.find(sites.length * sites.length + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        Percolation p = new Percolation(n);
        while (!p.percolates()) {
            int row = StdRandom.uniformInt(n) + 1;
            int col = StdRandom.uniformInt(n) + 1;
            if (p.isOpen(row, col)) {
                continue;
            }
            p.open(row, col);
            System.out.println("row: " + row + " col: " + col
                                       + " isOpen: " + p.isOpen(row, col)
                                       + " isFull: " + p.isFull(row, col)
                                       + " numberOfOpenSites: " + p.numberOfOpenSites()
                                       + " percolates: " + p.percolates());
        }
    }

    private int gridToIndex(int row, int col) {
        return sites.length * (row - 1) + col;
    }

    private boolean isInvalidArgument(int index) {
        return (index <= 0) || (index > sites.length);
    }
}
