package com.jiesoul.jcip;

import net.jcip.annotations.GuardedBy;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class LogService {
    private static final int CAPACTITY = 100;
    private final BlockingQueue<String> queue;
    private final LoggerThread loggerThread;
    private final PrintWriter writer;

    @GuardedBy("this") private boolean isShutdown;
    @GuardedBy("this") private int reservations;

    public LogService(PrintWriter writer) {
        this.writer = writer;
        this.queue = new LinkedBlockingDeque<>(CAPACTITY);
        this.loggerThread = new LogService.LoggerThread(writer);
    }

    public void start() {
        loggerThread.start();
    }

    public void stop() {
        synchronized (this) {
            isShutdown = true;
        }
        loggerThread.interrupt();
    }

    public void log(String msg) throws InterruptedException {
        synchronized (this) {
            if (isShutdown) {
                throw new IllegalStateException("");
            }
            ++reservations;
        }
        queue.put(msg);
    }

    private class LoggerThread extends Thread {
        public LoggerThread(PrintWriter writer) {

        }

        @Override
        public void run() {
            try {
                while (true) {
                    try {
                        synchronized (LogService.this) {
                            if (isShutdown && reservations == 0) {
                                break;
                            }
                        }
                        String msg = queue.take();
                        synchronized (LogService.this) {
                            --reservations;
                        }
                        writer.println(msg);
                    } catch (InterruptedException e) {

                    }
                }
            } finally {
                writer.close();
            }
        }
    }
}
