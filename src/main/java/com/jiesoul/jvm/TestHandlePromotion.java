package com.jiesoul.jvm;

/**
 * VM args: -Xms20M -Xmx20M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:-HandlePromotionFailure
 */
public class TestHandlePromotion {

    public static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        byte[] allocation1 = new byte[2 * _1MB];
        byte[] allocation2 = new byte[2 * _1MB];
        byte[] allocation3 = new byte[2 * _1MB];
        allocation1 = null;
        byte[] allocation4 = new byte[2 * _1MB];
        byte[] allocation5 = new byte[2 * _1MB];
        byte[] allocation6 = new byte[2 * _1MB];
        allocation4 = null;
        allocation5 = null;
        allocation6 = null;
        byte[] allocation7 = new byte[2 * _1MB];
    }
}
