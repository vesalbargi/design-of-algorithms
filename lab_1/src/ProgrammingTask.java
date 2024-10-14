import java.util.ArrayList;
import java.util.HashMap;

class Main {
    public static void main(String[] args) {
        ProgrammingTask programmingTask = new ProgrammingTask();
        programmingTask.test();
    }
}

public class ProgrammingTask {
    public void test() {
        // Test Part A
        System.out.println("Part A");
        float[] arrayA = { 1.3f, 2.5f, 3.7f, 4.2f, 1.3f, 5.3f };
        Float duplicate = findDuplicate(arrayA);
        if (duplicate != null) {
            System.out.println("Duplicate: " + duplicate);
        } else {
            System.out.println("No duplicate");
        }

        // Test Part B
        System.out.println("\nPart B");
        float[] arrayB = { 1.3f, 2.5f, 3.7f, 2.5f, 4.2f, 1.3f, 5.8f, 1.3f };
        HashMap<Float, Integer> duplicatesWithFrequency = findDuplicatesWithFrequency(arrayB);
        System.out.println("Duplicates with frequencies: " + duplicatesWithFrequency);

        // Test Part C
        System.out.println("\nPart C");
        int[] arrayC = { 3, 2, 6, 0, 3, 6, 0, 7, 7 };
        int m = 6;
        ArrayList<Integer> duplicatesInRange = findDuplicatesInRange(arrayC, m);
        System.out.println("Duplicates in range 0 to " + m + ": " + duplicatesInRange);
    }

    // Part A
    public Float findDuplicate(float[] array) {
        ArrayList<Float> encountered = new ArrayList<>();
        for (float num : array) {
            if (encountered.contains(num)) {
                return num;
            }
            encountered.add(num);
        }
        return null;
    }

    // Part B
    public HashMap<Float, Integer> findDuplicatesWithFrequency(float[] array) {
        HashMap<Float, Integer> frequency = new HashMap<>();
        for (float num : array) {
            if (frequency.containsKey(num)) {
                frequency.put(num, frequency.get(num) + 1);
            } else {
                frequency.put(num, 1);
            }
        }
        HashMap<Float, Integer> duplicates = new HashMap<>();
        for (float num : frequency.keySet()) {
            int count = frequency.get(num);
            if (count > 1) {
                duplicates.put(num, count);
            }
        }
        return duplicates;
    }

    // Part C
    public ArrayList<Integer> findDuplicatesInRange(int[] array, int m) {
        int[] count = new int[m + 1];
        ArrayList<Integer> duplicates = new ArrayList<>();
        for (int num : array) {
            if (num >= 0 && num <= m) {
                count[num]++;
            }
        }
        for (int i = 0; i < count.length; i++) {
            if (count[i] > 1) {
                duplicates.add(i);
            }
        }
        return duplicates;
    }
}
