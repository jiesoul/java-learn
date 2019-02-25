package com.jiesoul.jvm;

/**
 * Vm args: -XX:PermSize=10M -XX:MaxPermSize=10M
 */

public class RuntimeConstantPoolOOM1 {
    public static void main(String[] args) {
        String str1 = new StringBuilder("computer").append("software").toString();
        System.out.println(str1.intern() == str1);

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);
    }
}
