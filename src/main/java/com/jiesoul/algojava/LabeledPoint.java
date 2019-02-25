package com.jiesoul.algojava;

public class LabeledPoint extends Point {
    String id;
    void label(String name) {
        id = name;
    }

    @Override
    public String toString() {
        return id + "(" + x + ", " + y + ")";
    }
}
