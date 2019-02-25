package com.jiesoul.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * Vm args: -XX:PermSize=10M -XX:MaxPermSize=10M
 * 从 JDK8 开始 PermGen space 已被移除，由 Measpace 代替， 相应的上面的两个参数被忽略
 */

public class RuntimeConstantPoolOOM {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        int i = 0;
        while (true) {
            list.add(String.valueOf(i++).intern());
        }
    }
}
