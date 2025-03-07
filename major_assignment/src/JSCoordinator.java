import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JSCoordinator {
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
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
        for (int num = 5; num <= n; num += 10) {
            for (int rep = 0; rep < maxRep; rep++) {
                System.out.println("Testing n= " + num);
                int[] deadlines = generateArray(num, 1, num * 2);
                int[] executionTimes = generateArray(num, 1, num);
                JobScheduling jobScheduling = new JobScheduling();
                JobScheduling1 jobScheduling1 = new JobScheduling1(deadlines, executionTimes);
                JobScheduling1Opt jobScheduling1Opt = new JobScheduling1Opt(deadlines, executionTimes);
                JobScheduling2 jobScheduling2 = new JobScheduling2(deadlines, executionTimes);

                ArrayList<BackTracking> jobSchedulers = new ArrayList<>();
                jobSchedulers.add(jobScheduling1);
                jobSchedulers.add(jobScheduling1Opt);
                jobSchedulers.add(jobScheduling2);
                writer.write(num + ",");

                for (BackTracking jobScheduler : jobSchedulers) {
                    long begin = getTime();
                    jobScheduler.solve();
                    long finish = getTime();
                    writer.write((finish - begin) + ",");
                }

                long startGreedy = getTime();
                jobScheduling.jobSchedulingGreedyEDF(deadlines, executionTimes);
                long endGreedy = getTime();
                writer.write((endGreedy - startGreedy) + ",");

                if (num <= 5) {
                    long startBlind = getTime();
                    jobScheduling.jobSchedulingBlindOpt(deadlines, executionTimes);
                    long endBlind = getTime();
                    writer.write((endBlind - startBlind) + ",");
                }
                writer.write("\n");
            }
        }
        writer.close();
    }
}
