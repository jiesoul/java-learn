package jcip;

import java.awt.*;
import java.util.EventListener;

public class SafeListener {
    private final EventListener listener;

    private SafeListener() {
        listener = new EventListener() {

        };
    }

    public static SafeListener newInstance(EventListener source) {
        SafeListener safe = new SafeListener();
        return safe;
    }

    private void doSomething(Event e) {

    }
}
