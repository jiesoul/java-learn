package com.jiesoul.algojava;

public class ClosePoints {
    public static void main(String[] args) {
        int cnt = 0;
        int N = 100;
        double d = 1.0;
        Point[] a = new Point[N];
        for (int i = 0; i < N; i++) {
            a[i] = new Point();
        }

        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                if (a[i].distance(a[j]) < d) {
                    cnt++;
                }
            }
        }

        Out.println(cnt + " pairs ");
        Out.println("closer than " + d);
    }
}
