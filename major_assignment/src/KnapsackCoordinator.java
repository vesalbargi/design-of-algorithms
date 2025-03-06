import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class KnapsackCoordinator {
    private int[] generateArray(int n, int min, int max) {
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = (int) (Math.random() * (max - min) + min);
        }
        return array;
    }

    private long getTime() {
        return System.nanoTime();
    }

    public void experiment(int n, int maxRep) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("output1.txt"));
        for (int num = 5; num <= n; num += 10) {
            for (int rep = 0; rep < maxRep; rep++) {
                System.out.println("Testing n= " + num);
                int[] weights = generateArray(num, 1, 10 * num);
                int[] profits = generateArray(num, 100, 500);
                KnapsackBBLC knapsackBBLC = new KnapsackBBLC(weights, profits, num * 2, num);
                KnapsackBBFIFO knapsackBBFIFO = new KnapsackBBFIFO(weights, profits, num * 2, num);
                KnapsackDPItr knapsackDPItr = new KnapsackDPItr(weights, profits, num * 2, num);

                ArrayList<Solve> methods = new ArrayList<>();
                methods.add(knapsackBBLC);
                methods.add(knapsackBBFIFO);
                methods.add(knapsackDPItr);
                writer.write(num + ",");

                for (Solve method : methods) {
                    long begin = getTime();
                    int val = method.solve();
                    long finish = getTime();
                    writer.write((finish - begin) + ",");
                    writer.write(val + ",");
                    System.out.println(method.toString() + " " + val);
                }
                writer.write("\n");
            }
        }
        writer.close();
    }
}
