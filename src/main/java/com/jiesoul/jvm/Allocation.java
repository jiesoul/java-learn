package com.jiesoul.jvm;

public class Allocation {

    public static final int _1MB = 1024 * 1024;

    /**
     * VM参数 -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     */

    public static void main(String[] args) {
        byte[] allocation1 = new byte[2*_1MB];
        byte[] allocation2 = new byte[3*_1MB];
        byte[] allocation3 = new byte[2*_1MB];
        byte[] allocation4 = new byte[4*_1MB];
    }

}
