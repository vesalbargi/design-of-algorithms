package bank;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Hooman
 */

public class Program {
    public static void main(String args[]) throws IOException {
        test();
        // Coordinator coordinator = new Coordinator();
        // coordinator.experiment(1000, 50);
    }

    public static void test() {
        int val[] = new int[] { 6, 10, 18, 43, 34, 32, 12, 6, 10, 18, 43, 34, 32, 12, 6, 10, 18, 43, 34, 32, 12 };
        int key[] = new int[] { 9, 967, 27, 24, 5, 23, 143, 90, 3, 20, 247, 50, 13, 430, 900, 22, 29, 14, 15, 19, 43 };

        MyHashMap<Integer, Integer> myHashMap = new MyHashMap<>();
        JavaHashMap<Integer, Integer> javaHashMap = new JavaHashMap<>();

        ArrayList<SearchStructure<Integer, Integer>> structs = new ArrayList<SearchStructure<Integer, Integer>>();
        structs.add(myHashMap);
        structs.add(javaHashMap);

        for (SearchStructure<Integer, Integer> struct : structs) {
            System.out.println(struct);
            for (int i = 0; i < key.length; i++) {
                if (!struct.insert(key[i], val[i]))
                    System.out.println(struct.toString() + " : error in insert");
                else
                    struct.print();
            }

            for (int i = 0; i < key.length; i++) {
                Integer temp = struct.search(key[i]);
                if ((temp == null) || (temp != val[i])) // test & validation
                    System.out.println(struct.toString() + " : error in search. search result=" + temp
                            + " but searched for key: " + key[i] + " expected val: " + val[i]);
                // else
                // System.out.println("good");
            }
            // insert duplicate
            if (!struct.insert(9, 95))
                System.out.println("error in insert");
            else
                struct.print();
            // search not in the list
            Integer temp = struct.search(76);
            if ((temp == null) || (temp != 76)) // test & validation
                System.out.println("error in search. search result=" + temp + " but searched for key: 76");
            // delete not in the strcuture
            if (!struct.delete(789)) {
                System.out.println("error in delete");
            }

            for (int i = 0; i < key.length; i++) {
                if (!struct.delete(key[i])) {
                    System.out.println("error in delete");
                }
                struct.print();
            }
        }
    }
}
