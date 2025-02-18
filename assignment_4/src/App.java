public class App {
    public static void main(String[] args) throws Exception {
        test();
        // Coordinator coordinator = new Coordinator();
        // coordinator.experiment(1000, 5, true);
    }

    public static void test() {
        String X1 = "aabab";
        String Y1 = "babb";

        String X2 = "abcdbdc";
        String Y2 = "bddb";

        String X3 = "programming";
        String Y3 = "prgrmmng";

        String X4 = "12345";
        String Y4 = "54321";

        runTest(X1, Y1);
        runTest(X2, Y2);
        runTest(X3, Y3);
        runTest(X4, Y4);
    }

    public static void runTest(String X, String Y) {
        int i = X.length();
        int j = Y.length();

        int deleteCost = 1;
        int changeCost = 2;
        int insertCost = 1;

        StringEditingDQ stringEditingDQ = new StringEditingDQ();
        StringEditingDPMem stringEditingDPMem = new StringEditingDPMem();
        StringEditingDPItr stringEditingDPItr = new StringEditingDPItr();

        System.out.println("Testing with X = \"" + X + "\" and Y = \"" + Y + "\"");
        System.out.println("\nRecursive D&Q: "
                + stringEditingDQ.computeTransformation(X, Y, i, j, deleteCost, changeCost, insertCost));

        StringEditingDPMem.cost = null;
        StringEditingDPMem.selected = null;
        System.out.println("\nDynamic Programming Recursive / Memoized: "
                + stringEditingDPMem.computeTransformation(X, Y, i, j, deleteCost, changeCost, insertCost));
        stringEditingDPMem.printOperations(X, Y, i, j);

        StringEditingDPItr.cost = null;
        StringEditingDPItr.selected = null;
        System.out.println("\nDynamic Programming Iterative: "
                + stringEditingDPItr.computeTransformation(X, Y, i, j, deleteCost, changeCost, insertCost));
        stringEditingDPItr.printOperations(X, Y, i, j);
        System.out.println("==============================");

        stringEditingDPMem.print();
        stringEditingDPItr.print();
    }
}
