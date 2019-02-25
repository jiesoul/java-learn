package com.jiesoul.jcip;

public class LoggingWidget extends Widget {

    public synchronized void doSomething() {
        System.out.println(toString() + ": calling doSomething");
        super.doSomething();
    }
}
