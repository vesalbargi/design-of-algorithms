import java.util.Arrays;
import java.util.Comparator;

public abstract class KnapsackBB implements Solve {
    protected int[] weights;
    protected int[] profits;
    protected int capacity;
    protected int n;

    public KnapsackBB(int[] weights, int[] profits, int capacity, int n) {
        this.capacity = capacity;
        this.n = n;
        sortItemsByProfitWeightRatio(weights, profits);
    }

    private void sortItemsByProfitWeightRatio(int[] weights, int[] profits) {
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++)
            indices[i] = i;
        Arrays.sort(indices, Comparator.comparingDouble(i -> -((double) profits[i] / weights[i])));
        this.weights = new int[n];
        this.profits = new int[n];
        for (int i = 0; i < n; i++) {
            this.weights[i] = weights[indices[i]];
            this.profits[i] = profits[indices[i]];
        }
    }

    protected double bound(Node u) {
        if (u.weight >= capacity)
            return 0;
        double profitBound = u.profit;
        int j = u.level + 1, totalWeight = u.weight;
        while (j < n && totalWeight + weights[j] <= capacity) {
            totalWeight += weights[j];
            profitBound += profits[j];
            j++;
        }
        if (j < n)
            profitBound += (capacity - totalWeight) * ((double) profits[j] / weights[j]);
        return profitBound;
    }
}
