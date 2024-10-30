package bank;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Hooman
 */

public class Coordinator {
    private int[] generateArray(int n, int min, int max) {
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = (int) (Math.random() * (double) (max - min) + min);
        }
        return array;
    }

    private long getTime() {
        return System.nanoTime();
    }

    public void experiment(int n, int maxRep) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
        // all sizes
        for (int num = 100; num <= n; num += 50) {
            for (int rep = 0; rep < maxRep; rep++) {
                System.out.println("Testing n= " + num);
                MyHashMap<Integer, Integer> myHashMap = new MyHashMap<>();
                JavaHashMap<Integer, Integer> javaHashMap = new JavaHashMap<>();

                ArrayList<SearchStructure<Integer, Integer>> structs = new ArrayList<SearchStructure<Integer, Integer>>();
                structs.add(myHashMap);
                structs.add(javaHashMap);

                writer.write(num + ",");
                int[] keys = generateArray(num, 1, 2000000000); // key 1 to max
                int[] vals = generateArray(num, 1000, 300000000); // value 1000 to 3000000

                for (SearchStructure<Integer, Integer> struct : structs) {
                    long begin = getTime();
                    for (int i = 0; i < num; i++) {
                        // struct.insert(keys[i],vals[i]);
                        if (!struct.insert(keys[i], vals[i]))
                            System.out.println(struct.toString() + " : error in insert");
                    }
                    long finish = getTime();
                    writer.write(/* struct.toString()+","+ */(finish - begin) / num + ",");
                    // struct.print();

                    begin = getTime();
                    for (int i = 0; i < num; i++) {
                        int temp = struct.search(keys[i]);
                        if (temp != vals[i]) // test & validation
                            System.out.println(struct.toString() + " : error in search. search result=" + temp
                                    + " but searched for key: " + keys[i] + " expected val: " + vals[i]);
                        // else
                        // System.out.println("good");
                    }
                    finish = getTime();
                    writer.write(/* struct.toString()+","+ */(finish - begin) / num + ",");
                    // System.out.println(struct.toString()+" "+(finish-begin)/num);

                    begin = getTime();
                    for (int i = 0; i < num; i++) {
                        if (!struct.delete(keys[i])) {
                            System.out.println(struct.toString() + " : error in delete");
                        }
                    }
                    finish = getTime();
                    writer.write(/* struct.toString()+","+ */(finish - begin) / num + ",");
                }
                writer.write("\n");
            }
        }
        writer.close();
    }
}
