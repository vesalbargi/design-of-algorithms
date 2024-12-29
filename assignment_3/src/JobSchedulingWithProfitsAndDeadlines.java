import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * Greedy Assignment II Template
 */

public class JobSchedulingWithProfitsAndDeadlines {
    // any static fields you may need
    private static Integer[] deadlines = { 2, 1, 2, 1, 3 };
    private static Integer[] profits = { 100, 19, 27, 25, 15 };
    private static Integer[] deadlinesET = { 4, 2, 3, 3 };
    private static Integer[] profitsET = { 20, 15, 10, 5 };
    private static Integer[] executionTimes = { 2, 1, 1, 2 };

    public static void main(String[] args) {
        // Your test code
        System.out.println("====================");
        ArrayList<Integer> answerJSGreedy = new ArrayList<>();
        int maxProfitJSGreedy = JSGreedy(deadlines, profits, answerJSGreedy);
        System.out.println("JSGreedy Max Profit: " + maxProfitJSGreedy);
        System.out.println("Selected Jobs: " + answerJSGreedy);
        System.out.println("====================");
        ArrayList<Integer> answerFJSGreedy = new ArrayList<>();
        int maxProfitFJSGreedy = FJSGreedy(deadlines, profits, answerFJSGreedy);
        System.out.println("FJSGreedy Max Profit: " + maxProfitFJSGreedy);
        System.out.println("Selected Jobs: " + answerFJSGreedy);
        System.out.println("====================");
        ArrayList<Integer> answerJSGreedyET = new ArrayList<>();
        int maxProfitJSGreedyET = JSGreedy(deadlinesET, profitsET, executionTimes, answerJSGreedyET);
        System.out.println("JSGreedyET Max Profit: " + maxProfitJSGreedyET);
        System.out.println("Selected Jobs: " + answerJSGreedyET);
        System.out.println("====================");
        ArrayList<Integer> answerJSBlindIterative = new ArrayList<>();
        int maxProfitJSBlindIterative = JSBlindIterative(deadlinesET, profitsET, executionTimes,
                answerJSBlindIterative);
        System.out.println("JSBlindIterative Max Profit: " + maxProfitJSBlindIterative);
        System.out.println("Selected Jobs: " + answerJSBlindIterative);
        System.out.println("====================");
        ArrayList<Integer> answerJSBlindRec = new ArrayList<>();
        int maxProfitJSBlindRec = JSBlindRec(deadlinesET, profitsET, executionTimes,
                answerJSBlindRec);
        System.out.println("JSBlindRec Max Profit: " + maxProfitJSBlindRec);
        System.out.println("Selected Jobs: " + answerJSBlindRec);
        System.out.println("====================");
        ArrayList<Integer> answerJSDC = new ArrayList<>();
        int maxProfitJSDC = JSDC(deadlinesET, profitsET, executionTimes,
                answerJSDC);
        System.out.println("JSDC Max Profit: " + maxProfitJSDC);
        System.out.println("Selected Jobs: " + answerJSDC);
        System.out.println("====================");
    }

    // Greedy job schedualing assuming execution time = 1 (book implementation)
    public static int JSGreedy(Integer[] deadlines, Integer[] profits, ArrayList<Integer> answer) {
        // This method can call another private method. return is the total profit and
        // answer is the subset returned as call by reference
        int n = deadlines.length;
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }
        Arrays.sort(indices, (i, j) -> profits[j] - profits[i]);
        int[] J = new int[n + 1];
        J[0] = 0;
        int k = 1;
        J[k] = indices[0];
        for (int i = 1; i < n; i++) {
            int jobIndex = indices[i];
            int r = k;
            while (r > 0 && deadlines[J[r]] > deadlines[jobIndex]) {
                r--;
            }
            if (deadlines[jobIndex] > r) {
                for (int q = k; q > r; q--) {
                    J[q + 1] = J[q];
                }
                J[r + 1] = jobIndex;
                k++;
            }
        }
        int totalProfit = 0;
        for (int i = 1; i <= k; i++) {
            int jobIndex = J[i];
            answer.add(jobIndex);
            totalProfit += profits[jobIndex];
        }
        answer.sort((i, j) -> profits[j] - profits[i]);
        return totalProfit;
    }

    // Greedy fast job schedualing assuming execution time = 1 (book implementation)
    public static int FJSGreedy(Integer[] deadlines, Integer[] profits, ArrayList<Integer> answer) {
        // This method can call another private method. return is the total profit and
        // answer is the subset returned as call by reference
        int n = deadlines.length;
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }
        Arrays.sort(indices, (i, j) -> profits[j].compareTo(profits[i]));
        int b = Math.min(n, Arrays.stream(deadlines).max(Integer::compare).orElse(0));
        int[] f = new int[b + 1];
        for (int i = 0; i <= b; i++) {
            f[i] = i;
        }
        int totalProfit = 0;
        for (int idx : indices) {
            int q = collapsingFind(Math.min(n, deadlines[idx]), f);
            if (f[q] != 0) {
                answer.add(idx);
                totalProfit += profits[idx];
                int m = collapsingFind(f[q] - 1, f);
                weightedUnion(m, q, f);
                f[q] = f[m];
            }
        }
        answer.sort((i, j) -> profits[j] - profits[i]);
        return totalProfit;
    }

    // Greedy fast job schedualing arbitary execution time . Approximation method
    public static int JSGreedy(Integer[] deadlines, Integer[] profits, Integer[] executionTimes,
            ArrayList<Integer> answer) {
        // This method can call another private method. return is the total profit and
        // answer is the subset returned as call by reference
        int n = deadlines.length;
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }
        Arrays.sort(indices, (i, j) -> {
            double ratioI = (double) profits[i] / executionTimes[i];
            double ratioJ = (double) profits[j] / executionTimes[j];
            return Double.compare(ratioJ, ratioI);
        });
        int maxDeadline = Arrays.stream(deadlines).max(Comparator.naturalOrder()).orElse(0);
        int[] timeSlots = new int[maxDeadline + 1];
        Arrays.fill(timeSlots, -1);
        int totalProfit = 0;
        for (int idx : indices) {
            int deadline = deadlines[idx];
            int executionTime = executionTimes[idx];
            for (int t = deadline; t >= executionTime; t--) {
                boolean canSchedule = true;
                for (int slot = t; slot > t - executionTime; slot--) {
                    if (timeSlots[slot] != -1) {
                        canSchedule = false;
                        break;
                    }
                }
                if (canSchedule) {
                    for (int slot = t; slot > t - executionTime; slot--) {
                        timeSlots[slot] = idx;
                    }
                    answer.add(idx);
                    totalProfit += profits[idx];
                    break;
                }
            }
        }
        answer.sort((i, j) -> profits[j] - profits[i]);
        return totalProfit;
    }

    // The same as iterative blind method with the same template / pattern by using
    // bitstream
    public static int JSBlindIterative(Integer[] deadlines, Integer[] profits, Integer[] executionTimes,
            ArrayList<Integer> answer) {
        // This method can call another private method. return is the total profit and
        // answer is the subset returned as call by reference
        // Use bitstring the same as lecture code subset, sum of subset, and knapsack
        // implementations
        int maxProfit = -1;
        long bitString = 0;
        long one = 1;
        int n = deadlines.length;
        do {
            ArrayList<Integer> selectedTasks = new ArrayList<>();
            ArrayList<Integer> selectedExecutionTimes = new ArrayList<>();
            ArrayList<Integer> selectedProfits = new ArrayList<>();
            long bitStringCopy = bitString;
            for (int i = 0; i < n; i++) {
                if ((bitStringCopy & one) == one) {
                    selectedTasks.add(i); // Track the index
                    selectedExecutionTimes.add(executionTimes[i]);
                    selectedProfits.add(profits[i]);
                }
                bitStringCopy = bitStringCopy >> 1;
            }
            if (isFeasible(selectedTasks, deadlines, selectedExecutionTimes)) {
                int totalProfit = calculateTotal(selectedProfits);
                if (totalProfit > maxProfit) {
                    maxProfit = totalProfit;
                    answer.clear();
                    answer.addAll(selectedTasks);
                }
            }
            bitString++;
        } while (bitString < Math.pow(2, n));
        answer.sort((i, j) -> profits[j] - profits[i]);
        return maxProfit;
    }

    // The same as iterative blind method with the same template / pattern by using
    // bitstream
    public static int JSBlindRec(Integer[] deadlines, Integer[] profits, Integer[] executionTimes,
            ArrayList<Integer> answer) {
        // This method can call another private method. return is the total profit and
        // answer is the subset returned as call by reference
        // This method calls the recursive subset methods inside Subset class and then
        // iterate over them all to find the optimum valid solution
        // see the knapsack problem
        int maxProfit = -1;
        ArrayList<Integer> tasks = new ArrayList<>();
        for (int i = 0; i < deadlines.length; i++) {
            tasks.add(i);
        }
        ArrayList<ArrayList<Integer>> allSubsets = subsetsRec(tasks);
        for (ArrayList<Integer> subset : allSubsets) {
            ArrayList<Integer> selectedExecutionTimes = new ArrayList<>();
            ArrayList<Integer> selectedProfits = new ArrayList<>();
            for (int task : subset) {
                selectedExecutionTimes.add(executionTimes[task]);
                selectedProfits.add(profits[task]);
            }
            if (isFeasible(subset, deadlines, selectedExecutionTimes)) {
                int totalProfit = calculateTotal(selectedProfits);
                if (totalProfit > maxProfit) {
                    maxProfit = totalProfit;
                    answer.clear();
                    answer.addAll(subset);
                }
            }
        }
        answer.sort((i, j) -> profits[j] - profits[i]);
        return maxProfit;
    }

    // Divide and Conquer. See knapsack to come up with recursive equation
    public static int JSDC(Integer[] deadlines, Integer[] profits, Integer[] executionTimes,
            ArrayList<Integer> answer) {
        // This method can call another private method. return is the total profit and
        // answer is the subset returned as call by reference
        // Divide and Conquer. See knapsack to come up with recursive equation
        int n = deadlines.length;
        ArrayList<Integer> bestSubset = new ArrayList<>();
        int maxProfit = JSDCHelper(deadlines, profits, executionTimes, n, 0, 0, bestSubset);
        answer.clear();
        answer.addAll(bestSubset);
        return maxProfit;
    }

    private static int JSDCHelper(Integer[] deadlines, Integer[] profits, Integer[] executionTimes, int n,
            int currentTime,
            int currentIndex, ArrayList<Integer> bestSubset) {
        if (currentIndex == n) {
            return 0;
        }
        ArrayList<Integer> includeSubset = new ArrayList<>(bestSubset);
        ArrayList<Integer> excludeSubset = new ArrayList<>(bestSubset);
        int excludeProfit = JSDCHelper(deadlines, profits, executionTimes, n, currentTime, currentIndex + 1,
                excludeSubset);
        int includeProfit = 0;
        if (currentTime + executionTimes[currentIndex] <= deadlines[currentIndex]) {
            includeSubset.add(currentIndex);
            includeProfit = profits[currentIndex] + JSDCHelper(deadlines, profits, executionTimes, n,
                    currentTime + executionTimes[currentIndex], currentIndex + 1, includeSubset);
        }
        if (includeProfit > excludeProfit) {
            bestSubset.clear();
            bestSubset.addAll(includeSubset);
            return includeProfit;
        } else {
            bestSubset.clear();
            bestSubset.addAll(excludeSubset);
            return excludeProfit;
        }
    }

    public static ArrayList<ArrayList<Integer>> subsetsRec(ArrayList<Integer> set) {
        if (set.size() == 0) {
            ArrayList<Integer> emptySet = new ArrayList<Integer>();
            ArrayList<ArrayList<Integer>> solutions = new ArrayList<>();
            solutions.add(emptySet);
            return solutions;
        }
        int data = set.remove(0);
        ArrayList<ArrayList<Integer>> solutions = subsetsRec(set);
        ArrayList<ArrayList<Integer>> solutions1 = clone(solutions);
        for (ArrayList<Integer> solution : solutions1) {
            solution.add(data);
        }
        solutions1.addAll(solutions);
        return solutions1;
    }

    public static ArrayList<ArrayList<Integer>> clone(ArrayList<ArrayList<Integer>> subsets) {
        ArrayList<ArrayList<Integer>> subsetsCopy = new ArrayList<ArrayList<Integer>>();
        for (ArrayList<Integer> subset : subsets) {
            ArrayList<Integer> newSubset = new ArrayList<Integer>();
            for (Integer item : subset) {
                newSubset.add(item);
            }
            subsetsCopy.add(newSubset);
        }
        return subsetsCopy;
    }

    private static boolean isFeasible(ArrayList<Integer> tasks, Integer[] deadlines,
            ArrayList<Integer> executionTimes) {
        int currentTime = 0;
        for (int i = 0; i < tasks.size(); i++) {
            currentTime += executionTimes.get(i);
            if (currentTime > deadlines[tasks.get(i)]) {
                return false;
            }
        }
        return true;
    }

    private static int calculateTotal(ArrayList<Integer> profits) {
        int total = 0;
        for (int profit : profits) {
            total += profit;
        }
        return total;
    }

    private static int collapsingFind(int i, int[] f) {
        int root = i;
        while (root != f[root]) {
            root = f[root];
        }
        while (i != root) {
            int parent = f[i];
            f[i] = root;
            i = parent;
        }
        return root;
    }

    private static void weightedUnion(int i, int j, int[] f) {
        f[j] = i;
    }
}
