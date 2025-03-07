import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Hooman
 */

// A template for backtracking strategy
// Any backtracking algorithm needs to override the abstract methods
public abstract class BackTracking<T> {
    protected T[] x; // Tuple
    int numberOfNodes; // to calculate the complexity in term of number of nodes

    public BackTracking(T[] x) {
        this.x = x;
        numberOfNodes = 0;
    }

    protected abstract T[] nodeValues(int k);

    // returns all the values for node k children
    // or all the possible values for x[k + 1]. See the T in book page 348
    // general template for backtracking algoirthms
    protected abstract double bound(int k); // returning 0 means bounding or blocking

    protected abstract boolean isSolution(int k); // return true if a solution is found

    protected abstract void solutionFound(int k); // print the solution or add it to the solution set

    // Backtracking template
    protected void solve(int k) {
        for (T t : nodeValues(k - 1)) {
            x[k] = t;
            numberOfNodes++; // just to count the number of nodes generated
            if (bound(k) != 0) {
                if (isSolution(k)) {
                    solutionFound(k);
                }
                if (k < x.length - 1)
                    solve(k + 1);
            }
        }
    }

    protected void itrSolve(int startingK) {
        int k = startingK;
        // structure to keep values/set for each node in tuple
        ArrayList<ArrayList<T>> nodeNexts = new ArrayList<ArrayList<T>>();
        for (int i = 0; i < x.length; i++) {
            nodeNexts.add(null); // initially all are null
        }
        while (k != startingK - 1) // -1 if we start from 0 or 0 if we start from 1
        {
            if (nodeNexts.get(k) == null) {
                nodeNexts.set(k, new ArrayList<T>(Arrays.asList(nodeValues(k - 1)))); // array to list
            }
            if (!nodeNexts.get(k).isEmpty()) {
                x[k] = nodeNexts.get(k).remove(0);
                numberOfNodes++; // just to count the number of nodes generated
                if (bound(k) != 0) {
                    if (isSolution(k)) {
                        solutionFound(k);
                    }
                    if (k < x.length - 1)
                        k++; // next node/set
                }
            } else {
                nodeNexts.set(k, null);
                k--;
            }
        }

    }

    public void solve() {
        solve(0);
    }
}
