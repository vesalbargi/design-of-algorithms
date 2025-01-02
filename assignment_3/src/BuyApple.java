import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *
 * @author Hooman
 */

 public class BuyApple {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[] cost = { 1, 3, 10, 10, 5, 10, 10, 5, 10, 5, 4, 4, 6, 3, 10, 5, 3, 4, 2, 3 };
        int exp = 1;
        System.out.println("Cost per day: " + Arrays.toString(cost) + "\n");
        for (exp = 1; exp <= 20; exp++) {
            int[] buyQnt = buyApple(cost, exp);
            System.out.println("Expiry as Number of Days: " + exp); // + " Cost per day: " + Arrays.toString(cost));
            System.out.println("Number of apples per day: " + Arrays.toString(buyQnt) + "\n");
        }
    }

    public static int[] buyApple(int[] cost, int exp) {
        int[] result = new int[cost.length];
        boolean[] bought = new boolean[cost.length];
        if (exp > 1) {
            PriorityQueue<int[]> heap = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
            for (int i = 0; i < cost.length; i++) {
                heap.offer(new int[] { cost[i], i });
            }
            while (!heap.isEmpty()) {
                int[] current = heap.poll();
                int currentIndex = current[1];
                if (bought[currentIndex]) {
                    continue;
                }
                bought[currentIndex] = true;
                result[currentIndex]++;
                for (int j = 1; j < exp && currentIndex + j < cost.length; j++) {
                    if (cost[currentIndex] <= cost[currentIndex + j] && !bought[currentIndex + j]) {
                        bought[currentIndex + j] = true;
                        result[currentIndex]++;
                    } else {
                        break;
                    }
                }
            }
        } else {
            for (int i = 0; i <= cost.length - exp; i++) {
                result[i] = 1;
            }
        }
        return result;
    }
}
