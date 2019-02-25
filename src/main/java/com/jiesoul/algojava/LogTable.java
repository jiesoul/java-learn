package com.jiesoul.algojava;

public class LogTable {

    static int lg(int N) {
        int i = 0;
        while (N > 0) {
            i++;
            N/= 2;
        }
        return i;
    }

    public static void main(String[] args) {
        for (int N = 1000; N <= 10000000; N++) {
            Out.println(lg(N) + " " + N);
        }
    }
}
