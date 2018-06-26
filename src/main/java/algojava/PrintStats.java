package algojava;

public class PrintStats {

    public static void main(String[] args) {
        int N = 1000;
        double m = 0.0, s = 0.0;
        for (int i = 0; i < N; i++) {
            int x = (int) (Math.random() * 100000);
            double d = (double)x;
            m += d/N;
            s += (d*d)/N;
        }

        s = Math.sqrt(s - m*m);
        Out.println("    Avg.: " + m);
        Out.println("Std. dev.: " + s);
    }
}
