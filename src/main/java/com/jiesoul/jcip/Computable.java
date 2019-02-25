package com.jiesoul.jcip;

public interface Computable<A, V> {
    V compute(A arg) throws InterruptedException;
}
