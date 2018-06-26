package algojava;

public class CoinFlip {
    static boolean heads() {
        return Math.random() < 0.5;
    }

    public static void main(String[] args) {
        int i, j, cnt;
        int N = 32;
        int M = 1000;

        int[] f = new int[N+1];
        for (j = 0; j <= N; j++) {
            f[j] = 0;
        }

        for (i = 0; i < M; i++, f[cnt]++) {
            for (j = 0, cnt = 0; j <= N; j++) {
                if (heads()) {
                    cnt++;
                }
            }
        }

        for(j = 0; j <= N; j++) {
            if (f[j] == 0) {
                Out.print(".");
            }
            for (i = 0; i < f[j]; i+=10) {
                Out.print("*");
            }
            Out.println();
        }

    }
}
