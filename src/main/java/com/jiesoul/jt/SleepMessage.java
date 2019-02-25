package com.jiesoul.jt;

public class SleepMessage {

    public static void main(String[] args) throws InterruptedException {
        String[] immportantInfo = {
                "Mares eat oats",
                "Does eat oats",
                "Little lambs eat ivy",
                "A kid will eat ivy too"
        };

        for (int i = 0; i < immportantInfo.length; i++) {
            Thread.sleep(4000);
            System.out.println(immportantInfo[i]);
        }
    }
}
