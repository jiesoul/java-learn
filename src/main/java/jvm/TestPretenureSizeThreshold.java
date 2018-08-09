package jvm;

/**
 * VM参数 -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728
 */

public class TestPretenureSizeThreshold {

    public static final int _1MB = 1025 * 1024;

    public static void main(String[] args) {
        byte[] allocation = new byte[4*_1MB];
    }
}
