import java.util.Arrays;

public class AssignmentBT {
    private int n;
    private int[][] costMatrix;
    private boolean[] assigned;
    private int[] assignment;
    private int minCost;

    public AssignmentBT(int[][] costMatrix) {
        this.n = costMatrix.length;
        this.costMatrix = costMatrix;
        this.assigned = new boolean[n];
        this.assignment = new int[n];
        Arrays.fill(assignment, -1);
        this.minCost = Integer.MAX_VALUE;
    }

    private void solve(int person, int currentCost) {
        if (person == n) {
            if (currentCost < minCost) {
                minCost = currentCost;
                System.out.println("New optimal assignment found with cost: " + minCost);
                System.out.println("Assignment: " + Arrays.toString(assignment));
            }
            return;
        }

        for (int job = 0; job < n; job++) {
            if (!assigned[job]) {
                int newCost = currentCost + costMatrix[person][job];
                if (newCost >= minCost)
                    continue;
                assigned[job] = true;
                assignment[person] = job;
                solve(person + 1, newCost);
                assigned[job] = false;
            }
        }
    }

    public void findOptimalAssignment() {
        solve(0, 0);
        System.out.println("Optimal cost: " + minCost);
    }
}
