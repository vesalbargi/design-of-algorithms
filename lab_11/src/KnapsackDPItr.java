/**
 *
 * @author Hooman
 */

// Iterative Dynamic Programming for Knapsack 0/1
public class KnapsackDPItr {
    public int K[][];
    public int answer[][];

    public int solve(int W, int wt[], int v[], int n) {
        int i, w;
        K = new int[n + 1][W + 1]; // the array to solve the problem iteratively
        answer = new int[n + 1][W + 1];
        // fill the array
        for (i = 0; i <= n; i++) {
            for (w = 0; w <= W; w++) {
                if (i == 0 || w == 0) {
                    K[i][w] = 0; // basic problems
                    answer[i][w] = 0;
                } else if (wt[i - 1] <= w) {
                    if (v[i - 1] + K[i - 1][w - wt[i - 1]] > K[i - 1][w]) {
                        K[i][w] = v[i - 1] + K[i - 1][w - wt[i - 1]];
                        answer[i][w] = 1;
                    } else {
                        K[i][w] = K[i - 1][w];
                        answer[i][w] = 0;
                    }
                } else {
                    K[i][w] = K[i - 1][w];
                    answer[i][w] = 0;
                }
            }
        }
        return K[n][W];
    }

    public void printSelectedItems(int W, int[] w, int[] v, int n) {
        System.out.println("\nSelected Items:");
        for (int i = n; i > 0 && W > 0; i--) {
            if (answer[i][W] == 1) {
                System.out.println("Item " + i + " [Weight: " + w[i - 1] + ", Value: " + v[i - 1] + "]");
                W -= w[i - 1];
            }
        }
    }

    public void print() {
        System.out.println("Iterative array");
        // static array
        for (int i = 0; i < K.length; i++) {
            for (int j = 0; j < K[0].length; j++) {
                System.out.print(K[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
