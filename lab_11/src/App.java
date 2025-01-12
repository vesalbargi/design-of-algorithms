public class App {
    public static void main(String[] args) throws Exception {
        int val[] = new int[] { 6, 10, 18, 43, 34, 32, 12, 6, 10, 18, 43, 34, 32, 12, 6, 10, 18, 43, 34, 32, 12, 6, 10,
                18, 43, 34, 32, 12, 6, 10, 18, 43, 34, 32, 12, 6, 10, 18, 43, 34, 32, 12 };
        int wt[] = new int[] { 9, 22, 20, 24, 5, 23, 43, 9, 22, 20, 24, 5, 23, 43, 9, 22, 20, 24, 5, 23, 43, 9, 22, 20,
                24, 5, 23, 43, 9, 22, 20, 24, 5, 23, 43, 9, 22, 20, 24, 5, 23, 43 };

        int W = 70;
        int n = val.length;

        KnapsackDPMem knapsackDPMem = new KnapsackDPMem();
        KnapsackDPItr knapsackDPItr = new KnapsackDPItr();

        System.out.println("Dynamic Programming Recursive / Memoized: " + knapsackDPMem.solve(W, wt, val, n));
        System.out.println("Dynamic Programming Iterative: " + knapsackDPItr.solve(W, wt, val, n));

        // Dynamic programmig tables
        knapsackDPMem.print();
        knapsackDPMem.printSelectedItems(W, wt, val, n);
        knapsackDPItr.print();
        knapsackDPItr.printSelectedItems(W, wt, val, n);
    }
}
