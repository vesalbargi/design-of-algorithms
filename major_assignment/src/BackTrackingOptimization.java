/**
 *
 * @author Hooman
 */

public abstract class BackTrackingOptimization<T> extends BackTracking<T> {
    protected T[] finalX; // Best Solution Tuple
    protected double finalProfit; // final solution after running backtrackign optimization

    public BackTrackingOptimization(T[] x, T[] finalX) {
        super(x);
        this.finalX = finalX;
        finalProfit = -100000000;
    }

    protected abstract double cost(int k);

    @Override
    protected void solutionFound(int k) {
        // calculate current profit and weight
        double currentProfit = cost(k);
        if ((currentProfit > finalProfit)) {
            finalProfit = currentProfit;
            for (int j = 0; j <= k; j++)
                finalX[j] = x[j]; // copy the current best solution to finalX tuple
        }
    }
}
