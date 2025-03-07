import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class JobSchedulingBB extends BranchAndBound<JSNode> {
    private int n;
    private int[] deadlines;
    private int[] executionTimes;

    public JobSchedulingBB(int[] deadlines, int[] executionTimes) {
        this.deadlines = deadlines.clone();
        this.executionTimes = executionTimes.clone();
        this.n = deadlines.length;
        sortJobs();
    }

    private void sortJobs() {
        int[][] jobs = new int[n][3];
        for (int i = 0; i < n; i++) {
            jobs[i][0] = deadlines[i];
            jobs[i][1] = executionTimes[i];
            jobs[i][2] = i;
        }
        Arrays.sort(jobs, (a, b) -> Integer.compare(a[0], b[0]));
        for (int i = 0; i < n; i++) {
            deadlines[i] = jobs[i][0];
            executionTimes[i] = jobs[i][1];
        }
    }

    @Override
    public JSNode[] nodeValues(Node<JSNode> parent) {
        int depth = parent.depth;
        if (depth >= n) {
            return new JSNode[0];
        }
        ArrayList<JSNode> children = new ArrayList<>();
        JSNode excludeChild = new JSNode(0, parent.x.currentCount, parent.x.currentTime);
        children.add(excludeChild);
        int newTime = parent.x.currentTime + executionTimes[depth];
        int newCount = parent.x.currentCount + 1;
        if (newTime <= deadlines[depth]) {
            JSNode includeChild = new JSNode(1, newCount, newTime);
            children.add(includeChild);
        }
        return children.toArray(new JSNode[0]);
    }

    @Override
    public boolean isSolution(Node<JSNode> node) {
        return node.depth == n;
    }

    @Override
    public double calcCX(int depth, JSNode t) {
        return calcUX(depth, t);
    }

    @Override
    public double calcUX(int depth, JSNode t) {
        int currentCount = t.currentCount;
        int currentTime = t.currentTime;
        int tempTime = currentTime;
        int maxJobs = currentCount;
        for (int i = depth; i < n; i++) {
            if (tempTime + executionTimes[i] <= deadlines[i]) {
                tempTime += executionTimes[i];
                maxJobs++;
            }
        }
        return -maxJobs;
    }

    @Override
    public double cost(ArrayList<Node<JSNode>> branch) {
        return -branch.get(0).x.currentCount;
    }

    public void report(ArrayList<Node<JSNode>> answer) {
        if (answer == null) {
            System.out.println("No solution found.");
            return;
        }
        ArrayList<Integer> bestAnswer = new ArrayList<>();
        Node<JSNode> currentNode = answer.get(0);
        while (currentNode.parent != null) {
            if (currentNode.x.val == 1) {
                int jobIndex = currentNode.depth - 1;
                bestAnswer.add(executionTimes[jobIndex]);
            }
            currentNode = currentNode.parent;
        }
        Collections.reverse(bestAnswer);
        System.out.println("\nBest answer execution times: " + bestAnswer);
        System.out.println("Total Scheduled Jobs: " + (-answerVal));
        System.out.println("Number of Nodes Generated: " + numberOfNodes);
    }

    public void solve() {
        JSNode root = new JSNode(-1, 0, 0);
        ArrayList<Node<JSNode>> answer = solve(Strategy.LC, root);
        report(answer);
    }
}

class JSNode {
    public int val;
    public int currentCount;
    public int currentTime;

    public JSNode(int val, int currentCount, int currentTime) {
        this.val = val;
        this.currentCount = currentCount;
        this.currentTime = currentTime;
    }

    @Override
    public String toString() {
        return "val: " + val + " Count: " + currentCount + " Time: " + currentTime;
    }
}
