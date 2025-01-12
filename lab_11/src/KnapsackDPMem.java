/**
 *
 * @author Hooman
 */

// recursive memoized Dynamic Programming for Knapsack 0/1
public class KnapsackDPMem {
    public int K[][];
    public int answer[][];

    // W is the knapsack capacity, w is an array for items and v is the
    // corresponding array of values and n is the number of items
    public int solve(int W, int w[], int v[], int n) {
        if (K == null) {
            K = new int[n + 1][W + 1];
            answer = new int[n + 1][W + 1];
        }
        // Base Case
        if (n == 0 || W == 0)
            return 0;
        // check the global array of solutions to see if it has been already solve to
        // avoid redundancy
        if (K[n][W] != 0)
            return K[n][W];
        // If weight of the nth item is more than Knapsack capacity W, then this item
        // cannot be included in the optimal solution
        if (w[n - 1] > W) {
            K[n][W] = solve(W, w, v, n - 1); // store the current solution for future
            answer[n][W] = 0;
            // Return the maximum of two cases (1) nth item included, (2) not included
        } else {
            int include = v[n - 1] + solve(W - w[n - 1], w, v, n - 1);
            int exclude = solve(W, w, v, n - 1);
            if (include > exclude) {
                K[n][W] = include;
                answer[n][W] = 1;
            } else {
                K[n][W] = exclude;
                answer[n][W] = 0;
            }
        }
        return K[n][W]; // this is the solution
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
        System.out.println("Memoized array");
        // static array
        for (int i = 0; i < K.length; i++) {
            for (int j = 0; j < K[0].length; j++) {
                System.out.print(K[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
