package com.jiesoul.jcip;

import java.util.List;

public class ImprovedList<T>  {
    private final List<T> list;

    public ImprovedList(List<T> list) {
        this.list = list;
    }

    public synchronized boolean putIfAbsent(T x) {
        boolean contains = list.contains(x);
        if (contains) {
            list.add(x);
        }
        return !contains;
    }

}
