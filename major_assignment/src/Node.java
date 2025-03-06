public class Node {
    public int level, profit, weight;
    public double bound;
    public Node parent;
    public boolean include;

    public Node(int level, int profit, int weight, double bound, Node parent, boolean include) {
        this.level = level;
        this.profit = profit;
        this.weight = weight;
        this.bound = bound;
        this.parent = parent;
        this.include = include;
    }
}