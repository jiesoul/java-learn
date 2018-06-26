package algo4;

public class SortCompare {

    public static double time(String alg, Double[] a) {
        return 0;
    }

    public static double timeRandomInput(String alg, int N, int T) {
        double total = 0.0;
        Double[] a = new Double[N];

        for (int t = 0; t < N; t++) {
            for (int i = 0; i < N; i++) {

            }

            total += time(alg, a);
        }

        return total;
    }

    public static void main(String[] args) {

    }
}
