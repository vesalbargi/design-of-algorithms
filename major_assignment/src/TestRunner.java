import java.util.Arrays;

public class TestRunner {
    public void runTSPDP() {
        int[][] distanceMatrix = {
                { 0, 10, 15, 20 },
                { 5, 0, 9, 10 },
                { 6, 13, 0, 12 },
                { 8, 8, 9, 0 }
        };
        TSPDP tspdp = new TSPDP(distanceMatrix);
        System.out.println("\nTSP Problem Details:");
        System.out.println("---------------------");
        System.out.println("Distance Matrix:");
        printMatrix(distanceMatrix);
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

    public void runAssignmentBT() {
        int[][] costMatrix = {
                { 9, 2, 7, 8 },
                { 6, 4, 3, 7 },
                { 5, 8, 1, 8 },
                { 7, 6, 9, 4 }
        };
        AssignmentBT assignmentBT = new AssignmentBT(costMatrix);
        System.out.println("\nAssignment Problem Details:");
        System.out.println("----------------------------");
        System.out.println("Cost Matrix:");
        printMatrix(costMatrix);
        System.out.println("\nSolution:");
        System.out.println("----------");
        assignmentBT.findOptimalAssignment();
    }

    public void runKnapsackBB() {
        int[] profits = { 6, 10, 18, 43, 34, 32, 12, 6, 10, 18, 43, 34, 32, 12, 6,
                10, 18, 43, 34, 32, 12, 6, 10, 18,
                43, 34, 32, 12, 6, 10, 18, 43, 34, 32, 12, 6, 10, 18, 43, 34, 32, 12 };
        int[] weights = { 9, 22, 20, 24, 5, 23, 43, 9, 22, 20, 24, 5, 23, 43, 9, 22,
                20, 24, 5, 23, 43, 9, 22, 20, 24,
                5, 23, 43, 9, 22, 20, 24, 5, 23, 43, 9, 22, 20, 24, 5, 23, 43 };
        int capacity = 70;
        System.out.println("\nKnapsack Problem Details:");
        System.out.println("--------------------------");
        System.out.println("Profits: " + Arrays.toString(profits));
        System.out.println("Weights: " + Arrays.toString(weights));
        System.out.println("Capacity: " + capacity);
        System.out.println("\nKnapsack Results:");
        System.out.println("------------------");
        checkKnapsackAlgorithms(profits, weights, capacity);

        int[] profits1 = { 15, 25, 35, 50 };
        int[] weights1 = { 4, 8, 10, 15 };
        int capacity1 = 18;
        System.out.println("\nKnapsack Problem Details:");
        System.out.println("--------------------------");
        System.out.println("Profits: " + Arrays.toString(profits1));
        System.out.println("Weights: " + Arrays.toString(weights1));
        System.out.println("Capacity: " + capacity1);
        System.out.println("\nKnapsack Results:");
        System.out.println("------------------");
        checkKnapsackAlgorithms(profits1, weights1, capacity1);

        int[] profits2 = { 10, 50, 100, 200 };
        int[] weights2 = { 1, 5, 10, 20 };
        int capacity2 = 10;
        System.out.println("\nKnapsack Problem Details:");
        System.out.println("--------------------------");
        System.out.println("Profits: " + Arrays.toString(profits2));
        System.out.println("Weights: " + Arrays.toString(weights2));
        System.out.println("Capacity: " + capacity2);
        System.out.println("\nKnapsack Results:");
        System.out.println("------------------");
        checkKnapsackAlgorithms(profits2, weights2, capacity2);
    }

    private void checkKnapsackAlgorithms(int[] profits, int[] weights, int capacity) {
        int n = profits.length;
        KnapsackBBLC knapsackBBLC = new KnapsackBBLC(weights, profits, capacity, n);
        KnapsackBBFIFO knapsackBBFIFO = new KnapsackBBFIFO(weights, profits, capacity, n);
        KnapsackDPItr knapsackDPItr = new KnapsackDPItr(weights, profits, capacity, n);
        int lcBBResult = knapsackBBLC.solve();
        int fifoBBResult = knapsackBBFIFO.solve();
        int ItrDPResult = knapsackDPItr.solve();
        System.out.println("Branch and Bound (LC): " + lcBBResult);
        System.out.println("Branch and Bound (FIFO): " + fifoBBResult);
        System.out.println("Dynamic Programming (Iterative): " + ItrDPResult);
        if (lcBBResult == ItrDPResult && fifoBBResult == ItrDPResult) {
            System.out.println("Validation Successful: Both Branch and Bound methods match Dynamic Programming.");
        } else {
            System.out.println("Validation Failed: Branch and Bound methods do not match Dynamic Programming.");
        }
    }

    public void runCoordinator(String algorithm) throws Exception {
        switch (algorithm) {
            case "JS":
                JSCoordinator jsCoordinator = new JSCoordinator();
                jsCoordinator.experiment(50, 5);
                break;
            case "Knapsack":
                KnapsackCoordinator knapsackCoordinator = new KnapsackCoordinator();
                knapsackCoordinator.experiment(1000, 5);
                break;
            default:
                break;
        }
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
