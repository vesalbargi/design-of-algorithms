import java.util.Arrays;

/**
 *
 * @author Hooman
 */

public class CoinChangeMaking {
    public static int[] trackCoins;

    static int minCoinsDC(int[] coins, int amount) {
        // base case
        if (amount == 0)
            return 0;
        int minCount = 1000000000;
        int selectedCoin = -1;
        // For all coins < amount
        for (int i = 0; i < coins.length; i++) {
            if (coins[i] <= amount) {
                int count = minCoinsDC(coins, amount - coins[i]) + 1;
                if (count < minCount) {
                    minCount = count;
                    selectedCoin = coins[i];
                }
            }
        }
        if (selectedCoin != -1) {
            trackCoins[amount] = selectedCoin;
        }
        return minCount;
    }

    // static int [] counts=new int[100]; or send it to the method
    static int minCoinsDPMem(int[] coins, int[] counts, int amount) {
        // base case
        if (amount == 0)
            return 0;
        if (counts[amount] > 0)
            return counts[amount]; // Check solved sub problems
        int minCount = 1000000000;
        int selectedCoin = -1;
        // For all coins < amount
        for (int i = 0; i < coins.length; i++) {
            if (coins[i] <= amount) {
                int count = minCoinsDPMem(coins, counts, amount - coins[i]) + 1;
                if (count < minCount) {
                    minCount = count;
                    selectedCoin = coins[i];
                }
            }
        }
        counts[amount] = minCount; // Update DP DS
        if (selectedCoin != -1)
            trackCoins[amount] = selectedCoin;
        return minCount;
    }

    static int minCoinsDPTab(int coins[], int amount) {
        int counts[] = new int[amount + 1];
        // Base case (If given value V is 0)
        counts[0] = 0; // 0 amount -> no coin is needed

        // Calculate minimum coins required for all values from 1 to amounts
        for (int i = 1; i <= amount; i++) {
            int minCount = 1000000000;
            int selectedCoin = -1;
            // Go through all coins smaller than i
            for (int j = 0; j < coins.length; j++) {
                if (coins[j] <= i) {
                    if (counts[i - coins[j]] + 1 < minCount) {
                        minCount = counts[i - coins[j]] + 1;
                        selectedCoin = coins[j];
                    }
                }
            }
            counts[i] = minCount;
            if (selectedCoin != -1)
                trackCoins[i] = selectedCoin;
        }
        return counts[amount];
    }

    public static int minCoinsGreedy(int[] coins, int amount) {
        Arrays.sort(coins);
        for (int i = 0; i < coins.length / 2; i++) {
            int temp = coins[i];
            coins[i] = coins[coins.length - 1 - i];
            coins[coins.length - 1 - i] = temp;
        }
        int minCount = 0;
        for (int i = 0; i < coins.length && amount > 0; i++) {
            if (coins[i] <= amount) {
                int numCoins = amount / coins[i];
                minCount += numCoins;
                amount -= numCoins * coins[i];
                trackCoins[coins[i]] += numCoins;
            }
        }
        if (amount > 0) {
            return -1;
        }
        return minCount;
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
        System.out.println("Minimum coins required (DC Algorithm): " + minCoinsDC(coins, amount));
        System.out.println("DC time: " + (System.nanoTime() - time));
        printSelectedCoins(coins, amount);

        int[] counts = new int[amount + 1]; // 0 to amount
        Arrays.fill(trackCoins, 0);
        time = System.nanoTime();
        System.out.println("\nMinimum coins required (DP Mem Algorithm): " + minCoinsDPMem(coins, counts, amount));
        System.out.println("DP Mem time: " + (System.nanoTime() - time));
        printSelectedCoins(coins, amount);

        Arrays.fill(trackCoins, 0);
        time = System.nanoTime();
        System.out.println("\nMinimum coins required (DP Tab/Itr Algorithm): " + minCoinsDPTab(coins, amount));
        System.out.println("DP Tab/Itr time: " + (System.nanoTime() - time));
        printSelectedCoins(coins, amount);

        Arrays.fill(trackCoins, 0);
        time = System.nanoTime();
        System.out.println("\nMinimum coins required (Greedy Algorithm): " + minCoinsGreedy(coins, amount));
        System.out.println("Greedy time: " + (System.nanoTime() - time));
    }
}
