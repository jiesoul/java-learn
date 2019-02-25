package com.jiesoul.jcip;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CellularAutomata {
    private final Board maianBoard;
    private final CyclicBarrier barrier;
    private final Worker[] workers;

    public CellularAutomata(Board board) {
        this.maianBoard = board;
        int count = Runtime.getRuntime().availableProcessors();
        this.barrier = new CyclicBarrier(count, () -> maianBoard.commitNewValues());
        this.workers = new Worker[count];
        for (int i = 0; i < count; i++) {
            workers[i] = new Worker(maianBoard.getSubBoard(count, i));
        }
    }

    private class Worker implements Runnable {
        private final Board board;
        public Worker(Board board) {
            this.board = board;
        }

        @Override
        public void run() {
            while (!board.hasConvrged()) {
                for (int x = 0; x < board.getMaxX(); x++) {
                    for (int y = 0; y < board.getMaxY(); y++) {
                        board.setNewValue(x, y, computeValue(x, y));
                    }
                }
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    return;
                } catch (BrokenBarrierException e) {
                    return;
                }
            }
        }
    }

    private Object computeValue(int x, int y) {
        return null;
    }
}

class Board {

    public void commitNewValues() {

    }

    public Board getSubBoard(int count, int i) {
        return null;
    }

    public boolean hasConvrged() {
        return false;
    }

    public double getMaxX() {
        return 0;
    }

    public double getMaxY() {
        return 0;
    }

    public void setNewValue(int x, int y, Object o) {
        
    }
}
