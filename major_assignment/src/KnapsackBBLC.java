import java.util.Comparator;
import java.util.PriorityQueue;

public class KnapsackBBLC extends KnapsackBB {
    public KnapsackBBLC(int[] weights, int[] profits, int capacity, int n) {
        super(weights, profits, capacity, n);
    }

    @Override
    public int solve() {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(n1 -> -n1.bound));
        Node root = new Node(-1, 0, 0, 0, null, false);
        root.bound = bound(root);
        pq.add(root);
        int maxProfit = 0;
        while (!pq.isEmpty()) {
            Node node = pq.poll();
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
                    pq.add(left);
                }
            }
            Node right = new Node(nextLevel, node.profit, node.weight, 0, node, false);
            right.bound = bound(right);
            if (right.bound > maxProfit) {
                pq.add(right);
            }
        }
        return maxProfit;
    }
}
