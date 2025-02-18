import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Coordinator {
    private String generateString(int length, boolean alphabetsOnly) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c;
            if (alphabetsOnly) {
                c = (char) ('a' + (int) (Math.random() * 26));
            } else {
                c = (char) ((int) (Math.random() * 128));
            }
            builder.append(c);
        }
        return builder.toString();
    }

    private long getTime() {
        return System.nanoTime();
    }

    public void experiment(int n, int maxRep, boolean alphabetsOnly) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
        for (int num = 5; num <= n; num += 10) {
            for (int rep = 0; rep < maxRep; rep++) {
                StringEditingDPMem.cost = null;
                StringEditingDPMem.selected = null;
                System.out.println("Testing n= " + num);
                StringEditingDPMem stringEditingDPMem = new StringEditingDPMem();
                StringEditingDPItr stringEditingDPItr = new StringEditingDPItr();
                StringEditingDQ stringEditingDQ = new StringEditingDQ();

                ArrayList<Compute> methods = new ArrayList<Compute>();
                methods.add(stringEditingDPMem);
                methods.add(stringEditingDPItr);
                methods.add(stringEditingDQ);

                if (num >= 25) {
                    methods.remove(stringEditingDQ);
                }

                writer.write(num + ",");
                String X = generateString(num, alphabetsOnly);
                String Y = generateString(num / 2, alphabetsOnly);

                for (Compute method : methods) {
                    long begin = getTime();
                    int val = method.computeTransformation(X, Y, X.length(), Y.length(), 1, 2, 1);
                    long finish = getTime();
                    writer.write((finish - begin) + ",");
                    // writer.write(val + ",");
                    System.out.println(method.toString() + " " + val);
                }
                writer.write("\n");
            }
        }
        writer.close();
    }
}
