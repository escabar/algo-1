/**
 * Created by supatel on 1/27/2016.
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private WeightedQuickUnionUF wquf;
    private int[] openSites, fullSites;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        if (N <= 0) throw new java.lang.IllegalArgumentException("Please use n > 0");
        n = ((N * N) + 1);
        wquf = new WeightedQuickUnionUF(n);
        openSites = new int[n];
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        int x = convert2dTo1d(i, j);
        int y = (int) Math.sqrt(n - 1);

        if (!isOpen(i, j)) {
            openSites[x] = 1;
            fillSites (i, j);
        }

    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        validate(i);
        validate(j);
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

        if (isOpen(i, j)){
            if (i == 1) y = true;
            else {
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
            if (isFull(N, k)){
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

        return  ((x * i) - (x-j));
    }


    private void validate(int i) {
        int x = (int) Math.sqrt(n);

        if (i <= 0 || i > x) {
            throw new IndexOutOfBoundsException("index is out of bounds" + " " + i);
        }
    }

    private void fillSites (int i, int j) {
        validate(i);
        validate(j);
        int y = (int) Math.sqrt(n - 1);

        if (i == 1){
            if (i + 1 <= y)
                 if (isOpen (i + 1, j)){
                     fillSites(i + 1, j);
                     wquf.union(convert2dTo1d(i, j), convert2dTo1d(i + 1, j));
                 }
        }

         if(isOpen(i, j)  && !isFull(i, j)) {
             if ((i - 1) > 0 && isFull(i - 1, j)) {
                wquf.union(convert2dTo1d(i, j), convert2dTo1d(i - 1, j));
                if (j - 1 > 0) fillSites(i, j - 1);
                if (j + 1 <= y) fillSites(i, j + 1);
                if (i + 1 <= y) fillSites(i + 1, j);
            } else if ((j - 1) > 0 && isFull(i, j - 1)) {
                wquf.union(convert2dTo1d(i, j), convert2dTo1d(i, j - 1));
                if (i - 1 > 0) fillSites(i - 1, j);
                if (j + 1 <= y) fillSites(i, j + 1);
                if (i + 1 <= y) fillSites(i + 1, j);
            }else if ((j + 1) <= y && isFull(i, j + 1)) {
                wquf.union(convert2dTo1d(i, j), convert2dTo1d(i, j + 1));
                if (i - 1 > 0) fillSites(i - 1, j);
                if (j - 1 > 0) fillSites(i, j - 1);
                if (i + 1 <= y) fillSites(i + 1, j);
            }else if ((i + 1) <= y && isFull(i + 1, j)) {
                wquf.union(convert2dTo1d(i, j), convert2dTo1d(i + 1, j));
                if (i - 1 > 0) fillSites(i - 1, j);
                if (j - 1 > 0) fillSites(i, j - 1);
                if (j + 1 <= y) fillSites(i, j + 1);
            }

        }
    }


    // test client (optional)
    public static void main(String[] args) {
        int n = 10;
        Percolation perc = new Percolation(n);
        Percolation perc2 = new Percolation(n);

        /*
        while (!perc.percolates()) {
            int i = StdRandom.uniform(1, n+1);
            int j = StdRandom.uniform(1, n+1);

            if (!perc.isOpen(i, j)) {
                System.out.println(i + " "+ j);
                perc.open(i, j);
                System.out.println(perc.isOpen(i, j));
                System.out.println(perc.isFull(i, j));
            }

        }*/

        perc.open(4,4);
        perc.open(1,1);
        perc.open(2,1);
        System.out.println(perc.isFull(2,1));
    }
}