/**
 * Created by supatel on 1/28/2016.
 */


import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] numSitestoPerc;
    private int numTimes = 0;
    private int nSq = 0;
    int size = 0;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException("Argument must be greater than 0");
        } else {
            numSitestoPerc = new double [T];
            size = N;
            int nSq = size*size;
            numTimes = T;

            for (int num = 0; num < T; ++num){
                Percolation perc = new Percolation(size);
                int numSites = 0;
                while (!perc.percolates()){
                    int i = StdRandom.uniform(1, N + 1);
                    int j = StdRandom.uniform(1, N + 1);
                    if (!perc.isOpen(i, j)) {
                        perc.open(i, j);
                        perc.isOpen(i, j);
                        numSites = ++numSites;
                    }
                }
                numSitestoPerc[num] = (double) numSites/nSq;
            }
        }
    }

    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(numSitestoPerc);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return (StdStats.stddev(numSitestoPerc));
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo(){
        return  (mean() - ((1.96 * Math.sqrt(stddev()))/Math.sqrt(numTimes)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean() + ((1.96 * Math.sqrt(stddev())) / Math.sqrt(numTimes)));
    }

    // test client (described below)
    public static void main(String[] args){
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats percstats = new PercolationStats(n, t);

       for (int num = 0; num < t; ++num){
            Percolation perc = new Percolation(n);
            int numSites = 0;
            while (!perc.percolates()){
                int i = StdRandom.uniform(1, n+1);
                int j = StdRandom.uniform(1, n+1);
                if (!perc.isOpen(i, j)) {
                    perc.open(i, j);
                    perc.isOpen(i, j);
                    numSites = ++numSites;
                }
            }
           percstats.numSitestoPerc[num] = numSites;
        }
        System.out.println("mean                    = " + percstats.mean());
        System.out.println("stddev                  = " + percstats.stddev());
        System.out.println("95% confidence interval = " + percstats.confidenceLo() + ", " + percstats.confidenceHi());
    }
}