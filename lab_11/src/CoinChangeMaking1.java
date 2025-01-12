import java.util.Arrays;

/**
 *
 * @author Hooman
 */

public class CoinChangeMaking1 {
    public static int[] trackCoins;

    // This time we assume n = number of coins and m = amount
    // Recursive equation can be as:
    // C(n, m) = min C(n - 1, m - C[n] * i) + i and 0 <= i <= m / n
    // C(0, m) = inf
    // C(n, 0) = 0
    static int minCoinsDC(int[] coins, int n, int amount) {
        // base case
        if (amount == 0)
            return 0;
        if (n < 0 && amount > 0)
            return 1000000000;
        int minCount = 1000000000;
        // For any possible number of coins[n]
        for (int i = 0; i <= (amount / coins[n]); i++) {
            int remainingAmount = amount - coins[n] * i;
            if (remainingAmount >= 0) {
                int count = minCoinsDC(coins, n - 1, remainingAmount) + i;
                if (count < minCount) {
                    minCount = count;
                    if (i > 0) {
                        trackCoins[amount] = coins[n];
                    }
                }
            }
        }
        return minCount;
    }

    // static int [] counts = new int[100]; or send it to the method
    static int minCoinsDPMem(int[] coins, int[][] counts, int n, int amount) {
        // base cases
        if (amount == 0)
            return 0;
        if (n < 0 && amount > 0)
            return 1000000000;
        if (counts[amount][n] > 0)
            return counts[amount][n];
        // is it solved already? Retrieve it from the DS
        int minCount = 1000000000;
        // For any possible number of coins[n]
        for (int i = 0; i <= (amount / coins[n]); i++) {
            int remainingAmount = amount - coins[n] * i;
            if (remainingAmount >= 0) {
                int count = minCoinsDPMem(coins, counts, n - 1, remainingAmount) + i;
                if (count < minCount) {
                    minCount = count;
                    if (i > 0) {
                        trackCoins[amount] = coins[n];
                    }
                }
            }
        }
        counts[amount][n] = minCount;
        return minCount; // or counts[amount][n]
    }

    static int minCoinsDPTab(int coins[], int amount) {
        int[][] counts = new int[amount + 1][coins.length + 1];
        trackCoins = new int[amount + 1];
        // Base case amount = 0
        for (int i = 0; i <= coins.length; i++)
            counts[0][i] = 0;
        // Base case no coins
        for (int i = 1; i <= amount; i++)
            counts[i][0] = 1000000000;
        // Calculate minimum coins required for all values from 1 to amounts
        for (int i = 1; i <= coins.length; i++) {
            for (int j = 1; j <= amount; j++) {
                int minCount = 1000000000;
                // For any possible number of coins[n]
                for (int k = 0; k <= (j / coins[i - 1]); k++) {
                    int remainingAmount = j - coins[i - 1] * k;
                    int newCount = counts[remainingAmount][i - 1] + k;
                    if (newCount < minCount) {
                        minCount = newCount;
                        if (k > 0) {
                            trackCoins[j] = coins[i - 1];
                        }
                    }
                }
                counts[j][i] = minCount;
                // System.out.print(counts[j][i] + ",");
            }
            // System.out.println();
        }
        return counts[amount][coins.length];
    }

    static void printSelectedCoins(int[] coins, int amount) {
        System.out.println("Coins used to make amount " + amount + ":");
        int[] coinCount = new int[coins.length];
        while (amount > 0) {
            int coin = trackCoins[amount];
            for (int i = 0; i < coins.length; i++) {
                if (coins[i] == coin) {
                    coinCount[i]++;
                    break;
                }
            }
            amount -= coin;
        }
        for (int i = 0; i < coins.length; i++) {
            if (coinCount[i] > 0)
                System.out.println("Coin " + coins[i] + ": " + coinCount[i] + " times");
        }
    }

    public static void main(String args[]) {
        // int coins[] = { 9, 6, 5, 4, 3, 1 };
        // int amount = 23;

        int coins[] = { 24, 22, 9, 6, 5, 4, 3 };
        int amount = 23;

        // int coins[] = { 25, 10, 5 };
        // int amount = 30;

        // int coins[] = { 9, 6, 5, 1 };
        // int amount = 11;

        trackCoins = new int[amount + 1];
        Long time = System.nanoTime();
        System.out.println("Minimum coins required (DC Algorithm): " + minCoinsDC(coins, coins.length - 1, amount));
        System.out.println("DC time: " + (System.nanoTime() - time));
        printSelectedCoins(coins, amount);

        int[][] counts = new int[amount + 1][coins.length]; // 0 to amount
        Arrays.fill(trackCoins, 0);
        time = System.nanoTime();
        System.out.println("\nMinimum coins required (DP Mem Algorithm): "
                + minCoinsDPMem(coins, counts, coins.length - 1, amount));
        System.out.println("DP Mem time: " + (System.nanoTime() - time));
        printSelectedCoins(coins, amount);

        Arrays.fill(trackCoins, 0);
        time = System.nanoTime();
        System.out.println("\nMinimum coins required (DP Tab/Itr Algorithm): " + minCoinsDPTab(coins, amount));
        System.out.println("DP Tab/Itr time: " + (System.nanoTime() - time));
        printSelectedCoins(coins, amount);
    }
}
