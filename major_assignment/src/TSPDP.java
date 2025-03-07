import java.util.Arrays;

public class TSPDP {
    private static final int INF = Integer.MAX_VALUE / 2;
    private int[][] dp;
    private int[][] cost;
    private int n;

    public TSPDP(int[][] costMatrix) {
        this.n = costMatrix.length;
        this.cost = costMatrix;
        this.dp = new int[n][1 << n];
        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }
    }

    public int tsp(int i, int mask) {
        if (mask == (1 << n) - 1) {
            return cost[i][0] == 0 ? INF : cost[i][0];
        }
        if (dp[i][mask] != -1) {
            return dp[i][mask];
        }
        int minCost = INF;
        for (int j = 0; j < n; j++) {
            if ((mask & (1 << j)) == 0 && cost[i][j] > 0) {
                int newCost = cost[i][j] + tsp(j, mask | (1 << j));
                minCost = Math.min(minCost, newCost);
            }
        }
        return dp[i][mask] = minCost;
    }

    public int findMinCost() {
        return tsp(0, 1);
    }
}
