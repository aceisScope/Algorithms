/**
 * Binghui Liu, 12. 2017
 *
 * Coursera Algorithms, Part 1, Assignment 1 Percolation
 *
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int sizeN;
    private int numberOfOpenSites;
    private boolean [][] sites;
    private WeightedQuickUnionUF weightedUF; // this UF has a top and a bottom to determine percolation
    private WeightedQuickUnionUF backwashFixUF; // this UF only has a top and no bottom to fix backwash

    // virtual top and bottom site. they are always open.
    private int top;
    private int bottom;

    /**
     * create n-by-n grid, with all sites blocked
     * @param n
     */
    public Percolation(int n){
        if (n <= 0) {
            throw new IndexOutOfBoundsException("input" + n + "is illegal");
        }
        sizeN = n;
        weightedUF = new WeightedQuickUnionUF(n * n + 2);
        backwashFixUF = new WeightedQuickUnionUF(n * n + 1);
        sites = new boolean[n][n];
        top = n * n;
        bottom = n * n + 1;
    }

    /**
     * open site (row, col) if it is not open already; connect it to all of its adjacent open sites.
     * @param row
     * @param col
     */
    public void open(int row, int col) {
        validate(row, col);
        if (isOpen(row, col)) {
            return;
        }
        sites[row - 1][col - 1] = true;
        numberOfOpenSites ++;

        int convert = convert(row, col);
        if (row == 1) { // if site is in the top most row, connect it to top
            weightedUF.union(convert, top);
            backwashFixUF.union(convert, top);
        }
        if (row == sizeN) { // if site is in the bottom most row, connect it to bottom
            weightedUF.union(convert, bottom);
        }
        if (row > 1 && isOpen(row - 1, col)) { // if site's upper neighbour is open, connect them
            weightedUF.union(convert, convert(row - 1, col));
            backwashFixUF.union(convert, convert(row - 1, col));
        }
        if (row < sizeN && isOpen(row + 1, col)) { // if site's lower neighbour is open, connect them
            weightedUF.union(convert, convert(row + 1, col));
            backwashFixUF.union(convert, convert(row + 1, col));
        }
        if (col > 1 && isOpen(row, col - 1)) { // if site's left neighbour is open, connect them
            weightedUF.union(convert, convert(row, col - 1));
            backwashFixUF.union(convert, convert(row, col - 1));
        }
        if (col < sizeN && isOpen(row, col + 1)) { // if site's right neighbour is open, connect them
            weightedUF.union(convert, convert(row, col + 1));
            backwashFixUF.union(convert, convert(row, col + 1));
        }
    }

    /**
     *
     * @param row
     * @param col
     * @return is site (row, col) open
     */
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return sites[row - 1][col - 1];
    }

    /**
     *
     * @param row
     * @param col
     * @return is site (row, col) full, i.e open and connected to top
     */
    public boolean isFull(int row, int col) {
        validate(row, col);
        if (isOpen(row, col) && backwashFixUF.connected(convert(row, col), top)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @return number of open sites
     */
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    /**
     *
     * @return does the system percolate
     */
    public boolean percolates() {
        return weightedUF.connected(top, bottom);
    }

    private void validate(int row, int col) {
        if (row <= 0 || col <= 0 || row > sizeN || col > sizeN) {
            throw new IndexOutOfBoundsException("input (" + row + ", " + col + ") is illegal");
        }
    }

    private int convert(int row, int col) {
        return (row - 1) * sizeN + (col - 1);
    }

    // test client (optional)
    public static void main(String[] args) {
    }
}
