import java.util.Arrays;

public class JobScheduling1Opt extends BackTrackingOptimization<Integer> {
    private int n;
    private int[] deadlines;
    private int[] executionTimes;
    private int[] bestAnswer;

    public JobScheduling1Opt(int[] deadlines, int[] executionTimes) {
        super(new Integer[deadlines.length], new Integer[deadlines.length]);
        this.n = deadlines.length;
        this.deadlines = deadlines;
        this.executionTimes = executionTimes;
        this.bestAnswer = new int[n];
        sortJobs();
    }

    private void sortJobs() {
        int[][] jobs = new int[n][3];
        for (int i = 0; i < n; i++) {
            jobs[i][0] = deadlines[i];
            jobs[i][1] = executionTimes[i];
            jobs[i][2] = i;
        }
        Arrays.sort(jobs, (a, b) -> Integer.compare(a[0], b[0]));
        for (int i = 0; i < n; i++) {
            deadlines[i] = jobs[i][0];
            executionTimes[i] = jobs[i][1];
        }
    }

    @Override
    protected Integer[] nodeValues(int k) {
        return new Integer[] { 1, 0 };
    }

    @Override
    protected double bound(int k) {
        int clock = 0;
        for (int i = 0; i <= k; i++) {
            if (x[i] == 1) {
                clock += executionTimes[i];
                if (clock > deadlines[i]) {
                    return 0;
                }
            }
        }
        return cost(k);
    }

    @Override
    protected double cost(int k) {
        int clock = 0;
        int currentProfit = 0;
        int[] tempBest = new int[n];
        int index = 0;
        for (int i = 0; i <= k; i++) {
            if (x[i] == 1) {
                clock += executionTimes[i];
                if (clock <= deadlines[i]) {
                    currentProfit++;
                    tempBest[index++] = executionTimes[i];
                }
            }
        }
        if (currentProfit > finalProfit) {
            finalProfit = currentProfit;
            bestAnswer = Arrays.copyOf(tempBest, index);
        }

        return currentProfit;
    }

    @Override
    protected boolean isSolution(int k) {
        return k == n - 1;
    }

    public void report() {
        System.out.println("Best answer execution times: " + Arrays.toString(bestAnswer));
        System.out.println("Total Scheduled Jobs: " + finalProfit);
        System.out.println("Number of nodes generated: " + numberOfNodes);
    }
}
