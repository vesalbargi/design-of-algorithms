import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * @author Hooman
 */

// A template for branch and bound strategy
// Any branch and bound algorithm needs to override the abstract methods
enum Strategy {
    FIFO,
    LIFO,
    LC
}

class Node<T> implements Comparable<Node<T>> {
    public int depth; // in case if node depth is important
    public T x; // the value of node
    public double cX; // C^(x)
    public double uX; // U(x)
    public Node parent;

    public Node(int depth, T x, double cX, double uX, Node parent) {
        this.depth = depth;
        this.x = x;
        this.cX = cX;
        this.uX = uX;
        this.parent = parent;
    }

    public int compareTo(Node<T> other) {
        return Double.compare(cX, other.cX);
    }
}

public abstract class BranchAndBound<T> {
    // protected Deque<Node<T>> nodesFIFOqueue=new LinkedList<Node<T>>();
    // ArrayDeque is more efficient
    // Deque is more efficient than Stack
    // We use this for both FIFO and LIFO
    protected Deque<Node<T>> nodesQueue = new ArrayDeque<Node<T>>();
    // for LC
    protected Queue<Node<T>> nodesLCqueue = new PriorityQueue<>();
    ArrayList<Node<T>> answer;
    double answerVal = 1000000000; // again most of the time this is the same as upper
    double upper = 1000000000;
    int numberOfNodes; // to calculate the complexity in term of number of nodes

    public BranchAndBound() {

    }

    abstract public T[] nodeValues(Node<T> parent);

    // returns all the possible values for node's children
    // input : a node of search tree - return true if a solution is found
    abstract public boolean isSolution(Node<T> node);

    abstract public double calcCX(int depth, T t);

    abstract public double calcUX(int depth, T t);

    // input : root value
    public ArrayList<Node<T>> branchPath(Node<T> node) {
        ArrayList<Node<T>> path = new ArrayList<Node<T>>();
        while (node != null) {
            path.add(node);
            node = node.parent;
        }
        return path; // return a path from a node to the root as a list
    }

    // check whether a node a alive or not
    public boolean isKilled(Node<T> node) {
        // child can overide this
        return node.cX > upper;
    }

    // input : a branch of search tree - print the solution or add it to the
    // solution set
    protected void solutionFound(ArrayList<Node<T>> branch) {
        // child can call super.solutionFound and adds its own code
        double branchVal = cost(branch);
        if (branchVal < answerVal) {
            answerVal = branchVal; // update best answer
            answer = branch;
        }
    }

    public double cost(ArrayList<Node<T>> branch) {
        // we assume that cost is the same as U(x) which most of the time is true
        // chlid can override this if needed
        return branch.get(0).uX;
    }

    // Branch and Bound template
    public ArrayList<Node<T>> solveFIFO(T root) {
        upper = 1000000000;
        answer = null;
        answerVal = 1000000000; // init
        numberOfNodes = 0;
        Node<T> node = new Node<T>(0, root, calcCX(0, root), calcUX(0, root), null);
        nodesQueue.addLast(node); // FIFO
        // while there is any live nodes
        while (!nodesQueue.isEmpty()) {
            node = nodesQueue.removeFirst(); // FIFO
            System.out.println("Depth: " + node.depth + " > " + node.x + " U(x)= " + String.format("%.2f", node.uX)
                    + " C(x)= " + String.format("%.2f", node.cX)); // test
            // if node is alive
            if (!isKilled(node)) {
                numberOfNodes++; // if the node is aliave
                if (node.uX < upper)
                    upper = node.uX; // update uX
                if (isSolution(node)) {
                    solutionFound(branchPath(node));
                }
                int depth = node.depth;
                // node's children
                for (T t : nodeValues(node)) {
                    // node is the parent, depth is incremented, CX and UX should be calculated
                    Node<T> child = new Node<T>(depth + 1, t, calcCX(depth + 1, t), calcUX(depth + 1, t), node);
                    // if not killed
                    if (!isKilled(child)) {
                        if (child.uX < upper)
                            upper = child.uX; // update uX
                        nodesQueue.addLast(child); // add child to the FIFO queue if child is alive
                    }
                }
            }
        }
        return answer; // best answer
    }

    // Branch and Bound template
    public ArrayList<Node<T>> solveLIFO(T root) {
        upper = 1000000000;
        answer = null;
        answerVal = 1000000000; // init
        numberOfNodes = 0;
        Node<T> node = new Node<T>(0, root, calcCX(0, root), calcUX(0, root), null);
        nodesQueue.addFirst(node); // LIFO
        // while there is any live nodes
        while (!nodesQueue.isEmpty()) {
            node = nodesQueue.removeFirst(); // LIFO
            System.out.println("Depth: " + node.depth + " > " + node.x + " U(x)= " + String.format("%.2f", node.uX)
                    + " C(x)= " + String.format("%.2f", node.cX)); // test
            // if node is alive
            if (!isKilled(node)) {
                numberOfNodes++; // if the node is aliave
                if (node.uX < upper)
                    upper = node.uX; // update uX
                if (isSolution(node)) {
                    solutionFound(branchPath(node));
                }
                int depth = node.depth;
                // node's children
                for (T t : nodeValues(node)) {
                    // node is the parent, depth is incremented, CX and UX should be calculated
                    Node<T> child = new Node<T>(depth + 1, t, calcCX(depth + 1, t), calcUX(depth + 1, t), node);
                    // if not killed
                    if (!isKilled(child)) {
                        if (child.uX < upper)
                            upper = child.uX; // update uX
                        nodesQueue.addFirst(child); // add child to the LIFO queue if child is alive
                    }
                }
            }
        }
        return answer; // best answer
    }

    // Branch and Bound template
    public ArrayList<Node<T>> solveLC(T root) {
        upper = 1000000000;
        answer = null;
        answerVal = 1000000000; // init
        numberOfNodes = 0;
        Node<T> node = new Node<T>(0, root, calcCX(0, root), calcUX(0, root), null);
        nodesLCqueue.add(node); // LC priority queue
        // while there is any live nodes
        while (!nodesLCqueue.isEmpty()) {
            node = nodesLCqueue.remove(); // LIFO
            System.out.println("Depth: " + node.depth + " > " + node.x + " U(x)= " + String.format("%.2f", node.uX)
                    + " C(x)= " + String.format("%.2f", node.cX)); // test
            // if node is alive
            if (!isKilled(node)) {
                numberOfNodes++; // if the node is aliave
                if (node.uX < upper)
                    upper = node.uX; // update uX
                if (isSolution(node)) {
                    solutionFound(branchPath(node));
                }
                int depth = node.depth;
                // node's children
                for (T t : nodeValues(node)) {
                    // node is the parent, depth is incremented, CX and UX should be calculated
                    Node<T> child = new Node<T>(depth + 1, t, calcCX(depth + 1, t), calcUX(depth + 1, t), node);
                    // if not killed
                    if (!isKilled(child)) {
                        if (child.uX < upper)
                            upper = child.uX; // update uX
                        nodesLCqueue.add(child); // add child to the LIFO queue if child is alive
                    }
                }
            }
        }
        return answer; // best answer
    }

    public ArrayList<Node<T>> solve(Strategy strategy, T root) {
        switch (strategy) {
            case FIFO:
                return solveFIFO(root);
            case LIFO:
                return solveFIFO(root);
            case LC:
                return solveLC(root);
        }
        return null;
    }
}
