package Matrices;

import ComplexMath.Scalar;

import java.util.List;

public class MatrixMathUtils {

    /**********************Row Echelon Code*********************************/
    public static Matrix rowEchelon(Matrix matrix) {
        List<Vector> rowEchelon = matrix.getRowVectors();
        for (int col = 0; col < matrix.getN(); col++){
            Scalar leading = rowEchelon.get(col).getEntries().get(col);
            if (!leading.isZero()){
                for (int row = col + 1; row < matrix.getM(); row++){
                    Scalar belowLeading = rowEchelon.get(row).getEntries().get(col);
                    if (!belowLeading.isZero()){
                        Scalar div = belowLeading.div(leading);
                        rowEchelon.set(row, rowEchelon.get(row).sub(rowEchelon.get(col).mul(div)));
                    }
                }
            } else{
                if (checkBelowRowsForNonZeroAndSwitch(rowEchelon, col))
                    col--;
            }
        }
        return new ComplexMatrix(rowEchelon);
    }

    private static boolean checkBelowRowsForNonZeroAndSwitch(List<Vector> rowEchelon, int row) {
        for (int i = row + 1; i < rowEchelon.size(); i++){
            if (!rowEchelon.get(i).getEntries().get(row).isZero()){
                switchRows(rowEchelon, row, i);
                return true;
            }
        }
        return false;
    }

    private static void switchRows(List<Vector> rowEchelon, int row1, int row2) {
        Vector v1 = rowEchelon.get(row1);
        Vector v2 = rowEchelon.get(row2);
        rowEchelon.set(row1, v2);
        rowEchelon.set(row2, v1);
    }
    /**********************End Of Row Echelon Code*********************************/

    /**********************Linear Equations Solver*********************************/

}
