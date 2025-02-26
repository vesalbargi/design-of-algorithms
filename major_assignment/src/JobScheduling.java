import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Hooman
 */

// This is to find if a feasible solution for a list of jobs with given
// deadlines and execution times. We know that Greedy method EDF works
// perfectly. But still we solve it with blind/Brute Force method as well
// note that if the purpose of scheduling in addition to no job to miss its
// deadline is to minimize the waiting time of all jobs then EDF is not optimum
// . This is demonstrated that how the optimum (found by blind SSS)
// is different from the EDF. Note that if there is no feasible solution by EDF
// definitely no feasible solution will be found by Blind method
public class JobScheduling {
    public static void main(String[] args) {
        Integer[] deadlines = { 9, 5, 3, 14, 22, 25, 12 }; // Deadlines for a list of jobs
        Integer[] executionTimes = { 2, 3, 1, 3, 1, 7, 3 }; // Execution times for a list of jobs.

        // Use greedy EDF algorithm to find and print the feasible solution as a
        // sequence ( the order of jobs scheduled) if there is any
        jobSchedulingGreedyEDF(deadlines, executionTimes);
        // Modify the second blind method to generate all the permutations ( The one
        // which returns them )
        jobSchedulingBlind(deadlines, executionTimes, 0);
    }

    // The same template/ pattern as permutation method with no return but for each
    // permutation/sequence checks if it is a valid
    // permutation ( No jobs missed the deadline). If the purpose of search is to
    // minimize the waiting time as well, you can check the waiting time and only
    // accept the solution with minimum waiting time.
    public static void jobSchedulingBlind(Integer[] deadlines, Integer[] executionTimes, int k) {
        if (k == deadlines.length)
            // check if the permutation is valid
            if (valid(deadlines, executionTimes))
                System.out.println("Blind -> waiting time= " + String.format("%.2f", waitingTime(executionTimes))
                        + " jobs deadlines: " + Arrays.asList(deadlines) + " Execution Times: "
                        + Arrays.asList(executionTimes));
        // for any found sequence/permutation which is valid print the scheduled
        // sequence as well as the waiting time
        // Same pattern as before to generate all the permutations
        for (int i = k; i < deadlines.length; i++) {
            swap(deadlines, k, i);
            swap(executionTimes, k, i);
            jobSchedulingBlind(deadlines, executionTimes, k + 1);
            swap(deadlines, i, k);
            swap(executionTimes, i, k);
        }
    }

    // see if the schedule if valid/feasible. It assumes that jobs are already in
    // the correct order of schedule in both deadlines and executionTimes arrays.
    public static boolean valid(Integer[] deadlines, Integer[] executionTimes) {
        int clock = 0; // This is the clock
        for (int i = 0; i < deadlines.length; i++) {
            clock += executionTimes[i];
            // if the current clock is larger than deadline then one job is missed the
            // deadline and schedule is not valid/feasible
            if (clock > deadlines[i])
                return false;
        }
        return true;
    }

    // This one is the same as above but also accepts the third parameter a
    // permutation of job indexes.
    // The deadlines and executionTimes are in the original order and the third
    // parameter has the correct order of scheduling/execution
    public static boolean valid(Integer[] deadlines, Integer[] executionTimes, ArrayList<Integer> sequences) {
        int clock = 0;
        for (Integer jobIndex : sequences) {
            clock += executionTimes[jobIndex];
            if (clock > deadlines[jobIndex])
                return false;
        }
        return true;
    }

    // Calculates the waiting time for a given schedule. Assumes that arrays already
    // have the correct order of the schedule
    public static double waitingTime(Integer[] executionTimes) {
        int clock = 0;
        double totalWaitingTime = 0;
        for (int i = 0; i < executionTimes.length; i++) {
            totalWaitingTime += clock;
            clock += executionTimes[i];
        }
        return totalWaitingTime / executionTimes.length;
    }

    // basic method to swap
    public static void swap(Integer[] sequence, int i, int j) {
        Integer temp = sequence[i];
        sequence[i] = sequence[j];
        sequence[j] = temp;
    }

    // Greedy EDF algorithm to do the schedule. If the purpose is not to find the
    // minimum waiting time this always finds one feasible schedule.
    public static void jobSchedulingGreedyEDF(Integer[] deadlines, Integer[] executionTimes) {
        Integer[][] data = new Integer[deadlines.length][2];
        for (int i = 0; i < deadlines.length; i++) {
            data[i][0] = deadlines[i];
            data[i][1] = executionTimes[i];
        }
        Arrays.sort(data, (a, b) -> Double.compare(a[0], b[0])); // use lambda to sort them based on deadline

        int clock = 0;
        for (int i = 0; i < deadlines.length; i++) {
            clock += data[i][1];
            if (clock > data[i][0])
                return; // if schedule is not feasible return
        }
        System.out.print("Greedy -> deadlines: "); // printing the schedule if possible
        for (int i = 0; i < deadlines.length; i++) {
            System.out.print(data[i][0] + ", ");
        }
        System.out.print(" Execution Times: ");
        for (int i = 0; i < deadlines.length; i++) {
            System.out.print(data[i][1] + ", ");
        }
        System.out.println();
    }
}
