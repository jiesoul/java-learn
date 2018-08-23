package jvm;

/**
 * VM args: -Xss2M
 */

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
        //会造成 window 假死，要运行先做好强制重启准备
        oom.stackLeakByThread();
    }
}
