package com.jiesoul.jvm;

public class FinalizeEscapeGC {

    public static FinalizeEscapeGC SAVE_HOKK = null;
    public void isAlive() {
        System.out.println("yes, i am still alive.");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed!");
        FinalizeEscapeGC.SAVE_HOKK = this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOKK = new FinalizeEscapeGC();
        SAVE_HOKK = null;
        System.gc();
        Thread.sleep(500);
        if (SAVE_HOKK != null) {
            SAVE_HOKK.isAlive();
        } else {
            System.out.println("no, i am dead.");
        }
        
        SAVE_HOKK = null;
        System.gc();
        Thread.sleep(500);
        if (SAVE_HOKK != null) {
            SAVE_HOKK.isAlive();
        } else {
            System.out.println("no, i am dead.");
        }
    }
}
