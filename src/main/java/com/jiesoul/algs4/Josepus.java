package com.jiesoul.algs4;

import com.jiesoul.algojava.Out;

public class Josepus {

    static class Node {
        int val;
        Node next;

        Node(int v) {
            val = v;
        }
    }

    public static void main(String[] args) {
        int N = 9;
        int M = 5;
        Node t = new Node(1);
        Node x = t;
        for (int i = 2; i <= N; i++) {
            x = (x.next = new Node(i));
        }

        x.next = t;
        while (x != x.next) {
            for (int i = 1; i < M; i++) {
                x = x.next;
            }
            x.next = x.next.next;
        }
        Out.println("Survivor is " + x.next);
    }
}
