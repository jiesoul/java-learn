package algo4;

public class WeightedQuickUnionUF {

    private int[] id;
    private int[] sz;
    private int count;

    public WeightedQuickUnionUF(int N) {
        count = N;
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
        sz = new int[N];
        for (int i = 0; i < N; i++) {
            sz[i] = i;
        }
    }

    public int count() {
        return count;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public int find(int p) {
        while (p != id[p]) {
            p = id[p];
        }

        return p;
    }

    public void union(int p, int q) {
        int i = find(p);
        int j = find(q);
        if (i == j) {
            return;
        }

        if (sz[i] < sz[j]) {
            id[i] = j;
            sz[j] += sz[i];
        } else {
            id[j] = i;
            sz[i] += sz[j];
        }

        count--;
    }

    public void printId() {
        for (int i = 0; i < id.length; i++) {
            System.out.print(id[i] + ",");

        }

        System.out.print(" ; ");

        for (int i = 0; i < sz.length; i++) {
            System.out.print(sz[i] + ",");
        }

        System.out.print("\n");
    }

    public static void main(String[] args) {
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(10);
        uf.printId();

        uf.union(4,3);
        uf.printId();

        uf.union(3,8);
        uf.printId();

        uf.union(6,5);
        uf.printId();

        uf.union(9,4);
        uf.printId();

        uf.union(2,1);
        uf.printId();

        uf.union(8,9);
        uf.printId();

        uf.union(5,0);
        uf.printId();

        uf.union(7,2);
        uf.printId();

        uf.union(6,1);
        uf.printId();

        uf.union(1,0);
        uf.printId();

        uf.union(6,7);
        uf.printId();

    }
}
