public class StringEditingDPItr implements Compute {
    public static int[][] cost;
    public static int[][] selected;

    @Override
    public int computeTransformation(String X, String Y, int i, int j, int deleteCost, int changeCost, int insertCost) {
        cost = new int[i + 1][j + 1];
        selected = new int[i + 1][j + 1];
        for (int p = 0; p <= i; p++) {
            cost[p][0] = p * deleteCost;
            selected[p][0] = 1;
        }
        for (int q = 0; q <= j; q++) {
            cost[0][q] = q * insertCost;
            selected[0][q] = 3;
        }
        for (int p = 1; p <= i; p++) {
            for (int q = 1; q <= j; q++) {
                int delete = cost[p - 1][q] + deleteCost;
                int change = cost[p - 1][q - 1] +
                        (X.charAt(p - 1) == Y.charAt(q - 1) ? 0 : changeCost);
                int insert = cost[p][q - 1] + insertCost;

                if (delete <= change && delete <= insert) {
                    selected[p][q] = 1;
                    cost[p][q] = delete;
                } else if (change <= delete && change <= insert) {
                    selected[p][q] = 2;
                    cost[p][q] = change;
                } else {
                    selected[p][q] = 3;
                    cost[p][q] = insert;
                }
            }
        }
        return cost[i][j];
    }

    public void printOperations(String X, String Y, int i, int j) {
        StringBuilder steps = new StringBuilder();
        StringBuilder transformed = new StringBuilder(X);
        System.out.println("Steps (Iterative DP):");
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
        System.out.println("Iterative array");
        for (int i = 0; i < selected.length; i++) {
            for (int j = 0; j < selected[0].length; j++) {
                System.out.print(selected[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
