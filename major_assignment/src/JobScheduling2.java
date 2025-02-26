import java.util.Arrays;

public class JobScheduling2 extends BackTrackingOptimization<Integer> {
    private int n; // Number of jobs
    private int[] deadlines;
    private int[] executionTimes;
    private int currentTime;

    public JobScheduling2(int[] deadlines, int[] executionTimes) {
        super(new Integer[deadlines.length], new Integer[deadlines.length]);
        this.n = deadlines.length;
        this.deadlines = deadlines;
        this.executionTimes = executionTimes;
        this.currentTime = 0;
        sortJobs();
    }

    private void sortJobs() {
        int[][] jobs = new int[n][3]; // [deadline, execution time, index]
        for (int i = 0; i < n; i++) {
            jobs[i][0] = deadlines[i];
            jobs[i][1] = executionTimes[i];
            jobs[i][2] = i;
        }
        Arrays.sort(jobs, (a, b) -> Integer.compare(a[0], b[0])); // Sort by earliest deadline
        for (int i = 0; i < n; i++) {
            deadlines[i] = jobs[i][0];
            executionTimes[i] = jobs[i][1];
        }
    }

    @Override
    protected double bound(int k) {
        int time = currentTime;
        int potentialProfit = 0;

        for (int i = k; i < n; i++) {
            if (time + executionTimes[i] <= deadlines[i]) {
                time += executionTimes[i];
                potentialProfit++;
            } else {
                break;
            }
        }
        return potentialProfit > finalProfit ? 1 : 0;
    }

    @Override
    protected Integer[] nodeValues(int k) {
        if (k == -1)
            return new Integer[] { 0 };

        Integer[] nextValues = new Integer[n - k - 1];
        for (int i = k + 1; i < n; i++) {
            nextValues[i - k - 1] = i;
        }
        return nextValues;
    }

    @Override
    protected boolean isSolution(int k) {
        return true; // Any node is a potential solution
    }

    @Override
    protected double cost(int k) {
        return Arrays.stream(finalX).filter(i -> i != null).count();
    }

    public void report() {
        System.out.println("Selected Jobs:");
        for (int i = 0; i < n; i++) {
            if (finalX[i] != null) {
                System.out.println("Job " + (finalX[i] + 1) + " with execution time " + executionTimes[finalX[i]]
                        + " and deadline " + deadlines[finalX[i]]);
            }
        }
        System.out.println("Total Scheduled Jobs: " + finalProfit);
        System.out.println("Number of nodes generated: " + numberOfNodes);
    }
}
