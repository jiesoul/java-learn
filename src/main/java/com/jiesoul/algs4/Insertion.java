package com.jiesoul.algs4;

public class Insertion {

    public static void sort(Comparable[] a) {
        int N = a.length;
        for (int i = 1; i < N; i++) {
            for (int j = i; j > 0 && Example.less(a[j], a[j-1]); j--) {
                Example.exch(a, j, j-1);
            }
        }
    }

    public static void main(String[] args) {
        String[] s = new String[]{"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        Example.show(s);
        Insertion.sort(s);
        Example.show(s);
    }
}
