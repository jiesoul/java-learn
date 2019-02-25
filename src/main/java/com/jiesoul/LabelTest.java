package com.jiesoul;

public class LabelTest {

    public static void main(String[] args) {
        first: for (int i=1; i < 10; i++) {
            second: for (int j =1; j <= i; j++) {
                System.out.print(j + "*" + i + "=" + i*j + " ");
            }
            System.out.print("\n");
        }
    }
}
