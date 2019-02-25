package com.jiesoul.java8inaction.ch03;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by zhangyunjie on 2017/4/18.
 */
@FunctionalInterface
public interface BufferedReaderProcessor {
    String process(BufferedReader b) throws IOException;
}
