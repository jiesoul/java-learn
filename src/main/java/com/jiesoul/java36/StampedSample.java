package com.jiesoul.java36;

import java.util.concurrent.locks.StampedLock;

public class StampedSample {

    private final StampedLock s1 = new StampedLock();

    void mutate() {
        long stamp = s1.writeLock();
        try {
//            write();
        } finally {
            s1.unlockWrite(stamp);
        }
    }


}
