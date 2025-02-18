public class StringEditingDQ implements Compute {
    @Override
    public int computeTransformation(String X, String Y, int i, int j, int deleteCost, int changeCost, int insertCost) {
        if (i == 0)
            return j * insertCost;
        if (j == 0)
            return i * deleteCost;
        int delete = computeTransformation(X, Y, i - 1, j, deleteCost, changeCost, insertCost) + deleteCost;
        int change = computeTransformation(X, Y, i - 1, j - 1, deleteCost, changeCost, insertCost) +
                (X.charAt(i - 1) == Y.charAt(j - 1) ? 0 : changeCost);
        int insert = computeTransformation(X, Y, i, j - 1, deleteCost, changeCost, insertCost) + insertCost;
        return Math.min(delete, Math.min(change, insert));
    }
}
