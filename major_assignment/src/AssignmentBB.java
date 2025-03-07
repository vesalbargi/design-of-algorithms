import java.util.ArrayList;

public class AssignmentBB extends BranchAndBound<AssignmentNode> {
    private int[][] costMatrix;
    private int n;

    public AssignmentBB(int[][] costMatrix) {
        this.costMatrix = costMatrix;
        this.n = costMatrix.length;
    }

    @Override
    public AssignmentNode[] nodeValues(Node<AssignmentNode> parent) {
        if (parent.depth == n) {
            return new AssignmentNode[0];
        }

        ArrayList<AssignmentNode> nextValues = new ArrayList<>();
        for (int job = 0; job < n; job++) {
            if (!parent.x.assigned[job]) {
                boolean[] newAssigned = parent.x.assigned.clone();
                newAssigned[job] = true;
                nextValues.add(new AssignmentNode(job, parent.x.cost + costMatrix[parent.depth][job], newAssigned));
            }
        }
        return nextValues.toArray(new AssignmentNode[0]);
    }

    @Override
    public boolean isSolution(Node<AssignmentNode> node) {
        return node.depth == n - 1;
    }

    @Override
    public double calcCX(int depth, AssignmentNode t) {
        return t.cost;
    }

    @Override
    public double calcUX(int depth, AssignmentNode t) {
        return t.cost + estimateLowerBound(depth, t.assigned);
    }

    private double estimateLowerBound(int depth, boolean[] assigned) {
        double bound = 0;
        for (int i = depth; i < n; i++) {
            int minCost = Integer.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                if (!assigned[j]) {
                    minCost = Math.min(minCost, costMatrix[i][j]);
                }
            }
            bound += minCost;
        }
        return bound;
    }

    public void report(ArrayList<Node<AssignmentNode>> answer) {
        System.out.println("\nTotal Cost: " + answerVal);
        System.out.println("Number of nodes generated: " + numberOfNodes);
    }

    public void solve() {
        boolean[] assigned = new boolean[n];
        AssignmentNode root = new AssignmentNode(-1, 0, assigned);
        ArrayList<Node<AssignmentNode>> answer = solve(Strategy.LC, root);
        report(answer);
    }
}

class AssignmentNode {
    public int person;
    public double cost;
    public boolean[] assigned;

    public AssignmentNode(int person, double cost, boolean[] assigned) {
        this.person = person;
        this.cost = cost;
        this.assigned = assigned.clone();
    }

    public String toString() {
        return "Person: " + person + " Cost: " + cost;
    }
}
