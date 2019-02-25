package com.jiesoul;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyunjie on 2017/5/11.
 */
public class HeapOOM {

    static class OOMObject {

    }

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();
        while (true) {
            list.add(new OOMObject());
        }
    }
}
