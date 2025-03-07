import java.util.Arrays;

/**
 *
 * @author Hooman
 */

public class JobScheduling {
    private int[] bestAnswer;
    private double minAnswer = 1000000000;

    public void jobSchedulingBlind(int[] deadlines, int[] executionTimes, int k) {
        if (k == deadlines.length)
            if (valid(deadlines, executionTimes))
                System.out.println("Blind -> waiting time= " + String.format("%.2f", waitingTime(executionTimes))
                        + " jobs deadlines: " + Arrays.toString(deadlines) + " Execution Times: "
                        + Arrays.toString(executionTimes));
        for (int i = k; i < deadlines.length; i++) {
            swap(deadlines, k, i);
            swap(executionTimes, k, i);
            jobSchedulingBlind(deadlines, executionTimes, k + 1);
            swap(deadlines, i, k);
            swap(executionTimes, i, k);
        }
    }

    public double jobSchedulingBlindOpt(int[] deadlines, int[] executionTimes) {
        bestAnswer = new int[deadlines.length];
        minAnswer = 1000000000;
        return jobSchedulingBlindOpt(deadlines, executionTimes, 0);
    }

    private double jobSchedulingBlindOpt(int[] deadlines, int[] executionTimes, int k) {
        if (k == deadlines.length)
            if (valid(deadlines, executionTimes)) {
                double curWaitingTime = waitingTime(executionTimes);
                if (curWaitingTime < minAnswer) {
                    minAnswer = curWaitingTime;
                    bestAnswer = Arrays.copyOf(executionTimes, executionTimes.length);
                }
            }
        for (int i = k; i < deadlines.length; i++) {
            swap(deadlines, k, i);
            swap(executionTimes, k, i);
            jobSchedulingBlindOpt(deadlines, executionTimes, k + 1);
            swap(deadlines, i, k);
            swap(executionTimes, i, k);
        }
        return minAnswer;
    }

    public void reportBlindOpt() {
        System.out.println("Min asnwer returned from the method: " + minAnswer);
        System.out.println("Best asnwer execution times: " + Arrays.toString(bestAnswer));
    }

    private boolean valid(int[] deadlines, int[] executionTimes) {
        int clock = 0;
        for (int i = 0; i < deadlines.length; i++) {
            clock += executionTimes[i];
            if (clock > deadlines[i])
                return false;
        }
        return true;
    }

    private double waitingTime(int[] executionTimes) {
        int clock = 0;
        double totalWaitingTime = 0;
        for (int i = 0; i < executionTimes.length; i++) {
            totalWaitingTime += clock;
            clock += executionTimes[i];
        }
        return totalWaitingTime / executionTimes.length;
    }

    private void swap(int[] sequence, int i, int j) {
        int temp = sequence[i];
        sequence[i] = sequence[j];
        sequence[j] = temp;
    }

    public void jobSchedulingGreedyEDF(int[] deadlines, int[] executionTimes) {
        int[][] data = new int[deadlines.length][2];
        for (int i = 0; i < deadlines.length; i++) {
            data[i][0] = deadlines[i];
            data[i][1] = executionTimes[i];
        }
        Arrays.sort(data, (a, b) -> Double.compare(a[0], b[0]));

        int clock = 0;
        for (int i = 0; i < deadlines.length; i++) {
            clock += data[i][1];
            if (clock > data[i][0])
                return;
        }
        System.out.print("Deadlines: ");
        for (int i = 0; i < deadlines.length; i++) {
            System.out.print(data[i][0] + ", ");
        }
        System.out.print("Execution Times: ");
        for (int i = 0; i < deadlines.length; i++) {
            System.out.print(data[i][1] + ", ");
        }
        System.out.println();
    }
}
