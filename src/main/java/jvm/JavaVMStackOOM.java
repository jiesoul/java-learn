package jvm;

public class JavaVMStackOOM {
    private void donStop() {
        while (true) {

        }
    }

    public void stackLeakByThread() {
        while (true) {
            Thread t = new Thread(() -> donStop());
            t.start();
        }
    }

    public static void main(String[] args) {
        JavaVMStackOOM oom = new JavaVMStackOOM();
//        oom.stackLeakByThread();
    }
}
