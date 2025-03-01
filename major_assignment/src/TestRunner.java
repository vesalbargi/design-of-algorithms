import java.util.Arrays;

public class TestRunner {
    private int[][] costMatrix = {
            { 0, 10, 15, 20 },
            { 5, 0, 9, 10 },
            { 6, 13, 0, 12 },
            { 8, 8, 9, 0 }
    };

    public void runTSPDP() {
        TSPDP tspdp = new TSPDP(costMatrix);
        System.out.println("\nTSP Problem Details:");
        System.out.println("---------------------");
        System.out.println("Distance Matrix:");
        printMatrix(costMatrix);
        System.out.println("\nSolution:");
        System.out.println("----------");
        System.out.println("Minimum cost of TSP tour: " + tspdp.findMinCost());
    }

    public void runJSBT() {
        int[] deadlines = { 9, 5, 3, 14, 22, 25, 12 };
        int[] executionTimes = { 2, 3, 1, 3, 1, 7, 3 };

        System.out.println("\nJob Scheduling Problem Details:");
        System.out.println("--------------------------------");
        System.out.println("Deadlines: " + Arrays.toString(deadlines));
        System.out.println("Execution Times: " + Arrays.toString(executionTimes));

        JobScheduling jobScheduling = new JobScheduling();
        System.out.println("\nJobScheduling Results:");
        System.out.println("-----------------------");
        System.out.println("Greedy:");
        jobScheduling.jobSchedulingGreedyEDF(deadlines, executionTimes);
        System.out.println("\nBlind:");
        jobScheduling.jobSchedulingBlindOpt(deadlines, executionTimes);
        jobScheduling.reportBlindOpt();

        JobScheduling1 jobScheduling1 = new JobScheduling1(deadlines, executionTimes);
        System.out.println("\nJobScheduling1 Results:");
        System.out.println("------------------------");
        jobScheduling1.solve();
        jobScheduling1.report();

        JobScheduling1Opt jobScheduling1Opt = new JobScheduling1Opt(deadlines, executionTimes);
        System.out.println("\nJobScheduling1Opt Results:");
        System.out.println("---------------------------");
        jobScheduling1Opt.solve();
        jobScheduling1Opt.report();

        JobScheduling2 jobScheduling2 = new JobScheduling2(deadlines, executionTimes);
        System.out.println("\nJobScheduling2 Results:");
        System.out.println("------------------------");
        jobScheduling2.solve();
        jobScheduling2.report();
    }

    public void runCoordinator() throws Exception {
        Coordinator coordinator = new Coordinator();
        coordinator.experiment(50, 5);
    }

    private void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%4d", matrix[i][j]);
            }
            System.out.println();
        }
    }
}
