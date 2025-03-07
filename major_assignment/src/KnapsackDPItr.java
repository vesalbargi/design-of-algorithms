/**
 *
 * @author Hooman
 */

// Iterative Dynamic Programming for Knapsack 0/1
public class KnapsackDPItr implements Solve {
    private int[] weights;
    private int[] profits;
    private int capacity;
    private int n;
    private int[][] K;

    public KnapsackDPItr(int[] weights, int[] profits, int capacity, int n) {
        this.weights = weights;
        this.profits = profits;
        this.capacity = capacity;
        this.n = n;
    }

    @Override
    public int solve() {
        int i, w;
        K = new int[n + 1][capacity + 1]; // the array to solve the problem iteratively
        // fill the array
        for (i = 0; i <= n; i++) {
            for (w = 0; w <= capacity; w++) {
                if (i == 0 || w == 0)
                    K[i][w] = 0; // basic problems
                else if (weights[i - 1] <= w)
                    K[i][w] = Math.max(profits[i - 1] + K[i - 1][w - weights[i - 1]], K[i - 1][w]);
                else
                    K[i][w] = K[i - 1][w];
            }
        }
        return K[n][capacity];
    }

    public void print() {
        System.out.println("Iterative array:");
        // static array
        for (int i = 0; i < K.length; i++) {
            for (int j = 0; j < K[0].length; j++) {
                System.out.print(K[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
