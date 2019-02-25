package com.jiesoul;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class Percolation {

    int[] sites;
    int[] opens;
    int n;

    public Percolation (int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("error argument");
        }
        this.n = n;
        int N = n*n + 1;
        sites = new int[N];
        opens = new int[N];
        sites[0] = 0;
        opens[0] = 1;
        for (int i = 1; i < N-1; i++) {
            sites[i] = i;
            opens[i] = 0;
        }
        sites[N-1] = 0;
        opens[N-1] = 1;
    }

    public void open(int row, int col) {
        if (row > n || col > n) {
            throw new IllegalArgumentException("error row or error col");
        }
    }

    public boolean isOpen(int row, int col) {

        return false;
    }

    public boolean isFull(int row, int col) {

        return false;
    }

    public int numberOfOpenSites() {
        return 0;
    }

    public boolean percolates() {

        return false;
    }

    public static void main(String[] args) {

    }
}
