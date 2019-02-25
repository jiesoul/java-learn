package com.jiesoul.jcip;

import net.jcip.annotations.GuardedBy;

public class PriateLock {
    private final Object myLocak = new Object();
    @GuardedBy("myLock") Widget widget;

    void someMethod() {
        synchronized (myLocak) {

        }
    }
}
