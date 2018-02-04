/**
 * Created by supatel on 1/27/2016.
 */


import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    private int n;
    private WeightedQuickUnionUF wquf;
    private int[] openSites, fullSites;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        n = ((N * N) + 1);
        validate(N);
        wquf = new WeightedQuickUnionUF(n);
        openSites = new int[n];
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        int x = convert2dTo1d(i, j);
        int y = (int) Math.sqrt(n - 1);

        if (!isOpen(i, j)) {
            openSites[x] = 1;
        }

        for (int row = 1; row <= y; row++) {
            for (int col = 1; col <= y; col++) {
                if (isOpen(row, col)) {
                    if ((row - 1) > 0 && isFull(row - 1, col)) {
                        wquf.union(convert2dTo1d(row, col), convert2dTo1d(row - 1, col));
                    } else if ((col - 1) > 0 && isFull(row, col - 1)) {
                        wquf.union(convert2dTo1d(row, col), convert2dTo1d(row, col - 1));
                    } else if ((col + 1) <= y && isFull(row, col + 1)) {
                        wquf.union(convert2dTo1d(row, col), convert2dTo1d(row, col + 1));
                    } else if ((row + 1) <= y && isFull(row + 1, col)) {
                        wquf.union(convert2dTo1d(row, col), convert2dTo1d(row + 1, col));
                    }
                }
            }
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        int x = convert2dTo1d(i, j);

        if (openSites[x] == 1) {
            return true;
        } else {
            return false;
        }
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        int N = (int) Math.sqrt(n - 1);
        int x = convert2dTo1d(i, j);
        boolean y = false;

        if (isOpen(i, j)) {
            if (i == 1) {
                y = true;
            } else if (i > 1) {
                for (int a = 1; a <= N; a++) {
                    if (wquf.connected(x, a)) {
                        y = true;
                        break;
                    } else {
                        y = false;
                    }
                }
            }
        }

        return y;
    }

    // does the system percolate?
    public boolean percolates() {
        int N = (int) Math.sqrt(n - 1);
        boolean y = false;

        for (int k = N ; k >= 1 ; k--) {
            if (isFull(N, k)) {
                y = true;
                break;
            } else {
                y = false;
            }
        }

        return y;
    }

    private int convert2dTo1d(int i, int j) {
        validate(i);
        validate(j);
        int x = (int) Math.sqrt(n - 1);
        int y = ((x * i) - (x-j));

        return y;
    }


    private void validate(int i) {
        if (i <= 0 || i > n) {
            throw new IndexOutOfBoundsException("index is out of bounds" + " " + i);
        }
    }

    // test client (optional)
    public static void main(String[] args) {

        int n = 10;

        Percolation perc = new Percolation(n);

        while (!perc.percolates()) {
            int i = StdRandom.uniform(1, n+1);
            int j = StdRandom.uniform(1, n+1);

            //System.out.println("is Open " + perc.isOpen(i, j));
            //System.out.println("is Full " + perc.isFull(i, j));
            //System.out.println("percs? " + perc.percolates());

            if (!perc.isOpen(i, j)) {
                System.out.println(i + " "+ j);
                perc.open(i, j);
                perc.isOpen(i, j);
            }
        }
    }
}