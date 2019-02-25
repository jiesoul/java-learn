package com.jiesoul.algs4;

import java.util.Comparator;
import java.util.Date;

public class Transaction {

    private final String who;
    private final Date when;
    private final double amount;

    public Transaction(String who, Date when, double amount) {
        this.who = who;
        this.when = when;
        this.amount = amount;
    }

    public static void sort(Object[] a, Comparator c) {
        int N = a.length;
        for (int i = 1; i < N; i++) {
            for (int j = i; j > 0 && less(c, a[j], a[j-1]); j--) {
                exch(a, j, j-1);
            }
        }
    }

    private static boolean less(Comparator c, Object v, Object w) {
        return c.compare(v, w) < 0;
    }

    private static void exch(Object[] a, int i, int j) {
        Object t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static class WhoOrder implements Comparator<Transaction> {

        @Override
        public int compare(Transaction v, Transaction w) {
            return v.who.compareTo(w.who);
        }
    }

    public static class WhenOrder implements Comparator<Transaction> {
        @Override
        public int compare(Transaction o1, Transaction o2) {
            return o1.when.compareTo(o2.when);
        }
    }

    public static class HowMuchOrder implements Comparator<Transaction> {
        @Override
        public int compare(Transaction o1, Transaction o2) {
            if (o1.amount < o2.amount) return -1;
            if (o1.amount > o2.amount) return 1;
            return 0;
        }
    }
}
