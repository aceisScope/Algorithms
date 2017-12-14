/**
 * Binghui Liu, 12. 2017
 *
 * Coursera Algorithms, Part 1, Assignment 1 Percolation
 *
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] fraction;
    private int T = 1;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials){
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        T = trials;
        fraction = new double[trials];

        for (int i = 0; i < trials; i ++) {
            fraction[i] = findFraction(n);
        }
    }

    /**
     *
     * @return sample mean of percolation threshold
     */
    public double mean() {
        return StdStats.mean(fraction);
    }

    /**
     *
     * @return sample standard deviation of percolation threshold
     */
    public double stddev() {
        return StdStats.stddev(fraction);
    }

    /**
     *
     * @return low  endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - (1.96*stddev())/(Math.sqrt(T));
    }

    /**
     *
     * @return high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return mean() + (1.96*stddev())/(Math.sqrt(T));
    }

    /**
     *
     * @param n
     * @return the fraction of open sites for perculation of a n*n system
     */
    private double findFraction(int n) {
        int row, col;
        Percolation percolation = new Percolation(n);
        while (!percolation.percolates()) {
            row = StdRandom.uniform(1, n + 1);
            col = StdRandom.uniform(1, n + 1);
            if (!percolation.isOpen(row, col)) {
                percolation.open(row, col);
            }
        }
        return (double)percolation.numberOfOpenSites() / (n * n);
    }

    // test client
    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(2, 10000);
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo() +
                ", " + stats.confidenceHi() + "]");
    }
}
