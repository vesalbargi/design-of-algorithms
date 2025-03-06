import java.util.LinkedList;
import java.util.Queue;

public class KnapsackBBFIFO extends KnapsackBB {
    public KnapsackBBFIFO(int[] weights, int[] profits, int capacity, int n) {
        super(weights, profits, capacity, n);
    }

    @Override
    public int solve() {
        Queue<Node> queue = new LinkedList<>();
        Node root = new Node(-1, 0, 0, 0, null, false);
        root.bound = bound(root);
        queue.add(root);
        int maxProfit = 0;
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node.weight <= capacity && node.profit > maxProfit) {
                maxProfit = node.profit;
            }
            if (node.bound <= maxProfit) {
                continue;
            }
            int nextLevel = node.level + 1;
            if (nextLevel >= n) {
                continue;
            }
            Node left = new Node(nextLevel, node.profit + profits[nextLevel], node.weight + weights[nextLevel], 0, node,
                    true);
            left.bound = bound(left);
            if (left.weight <= capacity) {
                if (left.profit > maxProfit) {
                    maxProfit = left.profit;
                }
                if (left.bound > maxProfit) {
                    queue.add(left);
                }
            }
            Node right = new Node(nextLevel, node.profit, node.weight, 0, node, false);
            right.bound = bound(right);
            if (right.bound > maxProfit) {
                queue.add(right);
            }
        }
        return maxProfit;
    }
}
