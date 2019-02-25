package com.jiesoul.unit;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class DisabledTestDemo {

    @Disabled
    @Test
    void  testWillBeSkipped() {

    }

    @Test
    void testWillBeExceeded() {

    }
}
