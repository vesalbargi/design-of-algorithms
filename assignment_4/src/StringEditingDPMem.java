public class StringEditingDPMem implements Compute {
    public static int[][] cost;
    public static int[][] selected;

    @Override
    public int computeTransformation(String X, String Y, int i, int j, int deleteCost, int changeCost, int insertCost) {
        if (cost == null) {
            cost = new int[i + 1][j + 1];
            selected = new int[i + 1][j + 1];
        }
        if (i == 0) {
            selected[i][j] = 3;
            return cost[i][j] = j * insertCost;
        }
        if (j == 0) {
            selected[i][j] = 1;
            return cost[i][j] = i * deleteCost;
        }
        if (cost[i][j] != 0)
            return cost[i][j];
        int delete = computeTransformation(X, Y, i - 1, j, deleteCost, changeCost, insertCost) + deleteCost;
        int change = computeTransformation(X, Y, i - 1, j - 1, deleteCost, changeCost, insertCost) +
                (X.charAt(i - 1) == Y.charAt(j - 1) ? 0 : changeCost);
        int insert = computeTransformation(X, Y, i, j - 1, deleteCost, changeCost, insertCost) + insertCost;
        if (delete <= change && delete <= insert) {
            selected[i][j] = 1;
            cost[i][j] = delete;
        } else if (change <= delete && change <= insert) {
            selected[i][j] = 2;
            cost[i][j] = change;
        } else {
            selected[i][j] = 3;
            cost[i][j] = insert;
        }
        return cost[i][j];
    }

    public void printOperations(String X, String Y, int i, int j) {
        StringBuilder steps = new StringBuilder();
        StringBuilder transformed = new StringBuilder(X);
        System.out.println("Steps (Memoized DP):");
        System.out.println("Initial String: " + X);
        while (i > 0 || j > 0) {
            if (i > 0 && j > 0 && selected[i][j] == 2) {
                if (X.charAt(i - 1) != Y.charAt(j - 1)) {
                    steps.insert(0, "C(X" + i + ", Y" + j + ") -> ");
                    transformed.setCharAt(i - 1, Y.charAt(j - 1));
                }
                i--;
                j--;
            } else if (i > 0 && selected[i][j] == 1) {
                steps.insert(0, "D(X" + i + ") -> ");
                transformed.deleteCharAt(i - 1);
                i--;
            } else if (j > 0 && selected[i][j] == 3) {
                steps.insert(0, "I(Y" + j + ") -> ");
                transformed.insert(i, Y.charAt(j - 1));
                j--;
            } else {
                break;
            }
        }
        System.out.println("Steps: " + steps.toString());
        System.out.println("Final Transformation: " + transformed);
    }

    public void print() {
        System.out.println("Memoized array");
        for (int i = 0; i < selected.length; i++) {
            for (int j = 0; j < selected[0].length; j++) {
                System.out.print(selected[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
