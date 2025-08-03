/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private double[] a;
    private double statMean = 0;
    private double statStddev = 0;
    private double statConfidenceLo = 0;
    private double statConfidenceHi = 0;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        a = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniformInt(n) + 1;
                int col = StdRandom.uniformInt(n) + 1;
                if (p.isOpen(row, col)) {
                    continue;
                }
                p.open(row, col);
            }
            a[i] = p.numberOfOpenSites() / (double) (n * n);
        }
        statMean = StdStats.mean(a);
        statStddev = StdStats.stddev(a);
        statConfidenceLo = statMean - statStddev * CONFIDENCE_95 / Math.sqrt(trials);
        statConfidenceHi = statMean + statStddev * CONFIDENCE_95 / Math.sqrt(trials);
    }

    // sample mean of percolation threshold
    public double mean() {
        return statMean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return statStddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return statConfidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return statConfidenceHi;
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats pStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.printf("mean=%f\n", pStats.mean());
        StdOut.printf("stddev=%f\n", pStats.stddev());
        StdOut.printf("95%s confidence interval=[%f, %f]\n", "%", pStats.confidenceLo(), pStats.confidenceHi());
    }
}
