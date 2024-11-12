import java.util.ArrayList;
import java.util.Arrays;

public class CoinRowing {
    private static int[] coins = { 5, 6, 4, 3, 2, 7, 12, 9, 6, 11, 4, 13, 6, 11, 12, 13, 9 };
    private static int[] bitString = new int[coins.length];

    public static void main(String[] args) {
        int maxValue = maxCoinValue(coins, coins.length, bitString);
        System.out.println("Max sum: " + maxValue);
        System.out.println("Bit string: " + Arrays.toString(bitString));
        System.out.print("Selected coins: " + getSelectedCoins());
    }

    private static int maxCoinValue(int[] coins, int n, int[] selection) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            selection[0] = 1;
            return coins[0];
        }

        int[] withoutLastCoin = new int[n];
        int option1 = maxCoinValue(coins, n - 1, withoutLastCoin);

        int[] withLastCoin = new int[n];
        withLastCoin[n - 1] = 1;
        int option2 = coins[n - 1] + maxCoinValue(coins, n - 2, withLastCoin);

        if (option1 > option2) {
            for (int i = 0; i < n; i++) {
                selection[i] = withoutLastCoin[i];
            }
            return option1;
        } else {
            for (int i = 0; i < n; i++) {
                selection[i] = withLastCoin[i];
            }
            return option2;
        }
    }

    private static ArrayList<Integer> getSelectedCoins() {
        ArrayList<Integer> selectedCoins = new ArrayList<>();
        for (int i = 0; i < bitString.length; i++) {
            if (bitString[i] == 1) {
                selectedCoins.add(coins[i]);
            }
        }
        return selectedCoins;
    }
}
