public class Percolation {

    boolean[][] sites;

    public Percolation (int n) {
        sites = new boolean[n][n];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                sites[i][j] = false;
            }
        }
    }

    public void open(int row, int col) {
        if (!sites[row][col]) {
            sites[row][col] = true;
        }
    }

    public boolean isOpen(int row, int col) {

        return sites[row][col];
    }

    public boolean isFull(int row, int col) {

        return false;
    }

    public boolean percolates() {

        return false;
    }

    public static void main(String[] args) {

    }
}
