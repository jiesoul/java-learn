package com.jiesoul.corejava.pair1;

import com.jiesoul.corejava.Pair;

public class PairTest1 {

    public static void main(String[] args) {
        String[] words = {"Mary", "had", "a", "litter", "lamb"};
        Pair<String> mm = ArrayAlg.minmax(words);
        System.out.println(mm.getFirst());
        System.out.println(mm.getSecond());
    }
}

class ArrayAlg {
    public static Pair<String> minmax(String[] a) {
        if (a == null && a.length == 0) {
            return null;
        }
        String min = a[0];
        String max = a[0];
        for (int i = 1; i < a.length; i++) {
            if (min.compareTo(a[i]) > 0) {
                min = a[i];
            }
            if (min.compareTo(a[i]) < 0) {
                max = a[i];
            }
        }
        return new Pair<>(min, max);
    }
}
