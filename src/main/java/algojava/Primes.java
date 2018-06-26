package algojava;

public class Primes {
    public static void main(String[] args) {
        int N = 32;
        boolean[] a = new boolean[N];
        for (int i = 2; i < N; i++) {
            a[i] = true;
        }

        for (int i = 2; i < N; i++) {
            if (a[i] != false) {
                for (int j = i; j * i < N; j++) {
                    a[i*j] = false;
                }
            }
        }

        for (int i = 2; i < N; i++) {
            if (i > N - 100) {
                if (a[i]) {
                    Out.print(" " + i);
                }
                Out.println();
            }
        }


    }
}
