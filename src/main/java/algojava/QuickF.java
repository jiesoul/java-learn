package algojava;

public class QuickF {
    public static void main(String[] args) {
        int N = 10;
        int[] id = new int[N];

        for (int i = 0; i < N; i++) {
            id[i] = i;
        }

        for(In.init(); !In.empty();) {
            int p = In.getInt();
            int q = In.getInt();
            int t = id[p];
            if (t == id[q]) {
                continue;
            }
            for (int i = 0; i < N; i++) {
                if (id[i] == t) {
                    id[i] = id[q];
                }
            }
            Out.println(" " + p + " q");
        }
    }
}
