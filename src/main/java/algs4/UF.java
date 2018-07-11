package algs4;

public class UF {
    private int[] id;
    private int count;

    public  UF(int N) {
        count = N;
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    public int count() {
        return count;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public int find(int p) {
        return id[p];
    }

    public void union(int p, int q) {
        int pID = find(p);
        int qID = find(q);

        if (pID == qID) {
            return;
        }

        for (int i = 0; i < id.length; i++) {
            if (id[i] == pID) {
                id[i] = qID;
            }
        }
        count --;
    }

    public void printId() {
        for (int i = 0; i < id.length; i++) {
            System.out.print(id[i] + ",");
            if (i == id.length-1) {
                System.out.print("\n");
            }
        }
    }

    public static void main(String[] args) {
        UF uf = new UF(10);
        uf.union(4,3);
        uf.printId();

        uf.union(3,8);
        uf.printId();

        uf.union(9,4);
        uf.printId();

        uf.union(2,1);
        uf.printId();

        uf.union(8,9);
        uf.printId();

        uf.union(5, 0);
        uf.printId();

        uf.union(7,2);
        uf.printId();

        uf.union(6,1);
        uf.printId();

        uf.union(1,0);
        uf.printId();

        uf.union(6, 7);
        uf.printId();

    }
}
