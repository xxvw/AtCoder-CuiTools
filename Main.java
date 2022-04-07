import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static int inf = 1000000007;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int t = sc.nextInt();
        int l = sc.nextInt();
        int[][] a = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(a[i], inf);
        }
        for (int i = 1; i <= t; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            a[u][v] = i;
        }
        sc.close();

        int[][] b = matrixPow(a, l);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (b[0][i] == inf) {
                b[0][i] = -1;
            }
            sb.append(b[0][i]).append(' ');
        }
        sb.deleteCharAt(sb.length() - 1);
        System.out.println(sb.toString());
    }

    static int[][] matrixPow(int[][] a, int k) {
        if (k == 1) {
            return a;
        }
        int[][] ret = matrixPow(a, k / 2);
        ret = matrixMul(ret, ret);
        if (k % 2 == 1) {
            ret = matrixMul(ret, a);
        }
        return ret;
    }

    static int[][] matrixMul(int[][] a, int[][] b) {
        int h = a.length;
        int w = b[0].length;
        int n = a[0].length;
        int[][] c = new int[h][w];
        for (int i = 0; i < h; i++) {
            Arrays.fill(c[i], inf);
            for (int j = 0; j < w; j++) {
                for (int x = 0; x < n; x++) {
                    c[i][j] = Math.min(c[i][j], Math.max(a[i][x], b[x][j]));
                }
            }
        }
        return c;
    }
}
