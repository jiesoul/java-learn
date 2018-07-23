package java36;

public class ThreadSafeSample {

    public int sharedState;
    public void nonSafeAction() {
        while (sharedState < 100000) {
            int former = sharedState++;
            int latter = sharedState;
            if (former != latter - 1) {
                System.out.printf("Observed data race, former is " + former + ", latter is " + latter);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadSafeSample sample = new ThreadSafeSample();
        Thread ta = new Thread() {
            public void run() {
                sample.nonSafeAction();
            }
        };

        Thread tb = new Thread() {
            public void run() {
                sample.nonSafeAction();
            }
        };

        ta.start();
        tb.start();
        ta.join();
        tb.join();
    }
}
