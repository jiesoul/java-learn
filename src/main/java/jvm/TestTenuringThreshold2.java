package jvm;

/**
 * VM参数 -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:+PrintTenuringDistribution
 */

public class TestTenuringThreshold2 {
    public static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        byte[] allocation1 = new byte[_1MB/4];
        byte[] allocation2 = new byte[_1MB/4];
        byte[] allocation3 = new byte[4*_1MB];
        byte[] allocation4 = new byte[4*_1MB];
        allocation3 = null;
        allocation3 = new byte[4*_1MB];
    }
}
