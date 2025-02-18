package SumSubsetandMaxSubset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Hooman
 */

// simmilar to Binpacking for one bin
class MaxSubsets {
    public static HashSet<ArrayList<Integer>>[][] array; // dynamic array to find distinct subsets fitting in a bin

    public static void main(String args[]) {
        Integer[] items = { 1, 2, 2, 5, 3, 7, 1 };
        // Integer[] items = { 9, 10, 11, 12, 8, 9, 4, 7, 6, 5, 4, 6, 7, 5, 2, 3 };
        // Integer[] items = { 1, 2, 3, 3, 4, 2, 2, 2, 2, 1, 4 };
        int targetSum = 13;
        List<Integer> I = Arrays.stream(items).collect(Collectors.toList());
        System.out.println("Number of items: n = " + items.length + " target sum is: " + targetSum
                + " \n The items are " + I + "\n");

        long start = System.nanoTime();

        HashSet<ArrayList<Integer>> q = maxSubset(items, items.length, targetSum);

        System.out.printf("The algorithm running time : %.2f Seconds\n", (System.nanoTime() - start) / 1000000000.0);
        System.out.println("List of all subsets with the exact sum " + targetSum + " : ");
        display(q);
    }

    public static HashSet<ArrayList<Integer>> maxSubset(Integer[] items, int n, int sum) {
        if (array == null) {
            array = new HashSet[n + 1][sum + 1];
            Arrays.sort(items, Collections.reverseOrder());
        }
        // Q5: Updated to compute subsets that sum to an exact target value.
        if ((n <= 0) || (sum < 0)) {
            ArrayList<Integer> emptyList = new ArrayList<Integer>();
            HashSet<ArrayList<Integer>> emptySet = new HashSet<ArrayList<Integer>>();
            if (sum == 0) {
                emptySet.add(emptyList);
            }
            return emptySet;
        }
        // already solved
        if (array[n][sum] != null)
            return array[n][sum];
        // find all subsets fitting binS- nth item
        HashSet<ArrayList<Integer>> parts1 = clone(maxSubset(items, n - 1, sum - items[n - 1]));

        for (ArrayList<Integer> part : parts1) {
            part.add(items[n - 1]); // add nth item to the sets
        }
        HashSet<ArrayList<Integer>> parts2 = clone(maxSubset(items, n - 1, sum)); // not choosing n'th item
        array[n][sum] = parts1; // store the answer in the current answers
        array[n][sum].addAll(parts2); // union of both sets
        return array[n][sum];
    }

    // dispaly a set of subsets (set partitions)
    public static void display(HashSet<ArrayList<Integer>> parts) {
        System.out.println("\n Subsets with the exact given sum are:");
        for (ArrayList<Integer> part : parts) {
            System.out.println(part);
            // for (Integer item : part) {
            // System.out.print(item + ",");
            // }
            // System.out.println();
        }
    }

    // cloning a set of subsets
    public static HashSet<ArrayList<Integer>> clone(HashSet<ArrayList<Integer>> parts) {
        HashSet<ArrayList<Integer>> ret = new HashSet<ArrayList<Integer>>();
        for (ArrayList<Integer> part : parts) {
            ArrayList<Integer> list = new ArrayList<Integer>();
            for (Integer item : part) {
                list.add(item);
            }
            ret.add(list);
        }
        return ret;
    }
}
