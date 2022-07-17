package Matrices;

import ComplexMath.FieldScalars.ComplexScalar;
import ComplexMath.FieldScalars.Scalar;
import VectorSpaces.Vector;
import VectorSpaces.VectorSpace;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MatrixMathUtils {

    /**********************Row Echelon Code*********************************/
    public static Matrix rowEchelon(Matrix matrix){
        List<Vector> rowEchelon = matrix.getRowVectors();
        for (int row = 0; row < rowEchelon.size(); row++){
            // get row with leading entry.
            int[] co = getLeadingRow(rowEchelon,row);
            if (co[0] == -1) continue;
            rowEchelon.set(co[0], rowEchelon.get(co[0]).mul(rowEchelon.get(co[0]).getEntries().get(co[1]).getInverse()));
            // switch that row with the first row.
            switchRows(rowEchelon, co[0], row);
            Scalar leading = rowEchelon.get(row).getEntries().get(co[1]);
            for (int i = row + 1; i < rowEchelon.size(); i++){
                Scalar belowLeading = rowEchelon.get(i).getEntries().get(co[1]);
                if (!belowLeading.isZero()) {
                    Scalar div = belowLeading.div(leading);
                    rowEchelon.set(i, rowEchelon.get(i).sub(rowEchelon.get(row).mul(div)));
                }
            }
        }
        return new ComplexMatrix(rowEchelon);
    }

    public static Matrix canonicalRowEchelon(Matrix matrix) {
        List<Vector> rowEchelon = matrix.rowEchelon().getRowVectors();
        for (int row = 0; row < rowEchelon.size(); row++){
            // get row with leading entry.
            int[] co = getLeadingRow(rowEchelon,row);
            if (co[0] == -1) break;
            Scalar leading = rowEchelon.get(co[0]).getEntries().get(co[1]);
            for (int i = 0; i < co[0]; i++){
                Scalar aboveLeading = rowEchelon.get(i).getEntries().get(co[1]);
                if (!aboveLeading.isZero()) {
                    Scalar div = aboveLeading.div(leading);
                    rowEchelon.set(i, rowEchelon.get(i).sub(rowEchelon.get(row).mul(div)));
                }
            }
        }
        return new ComplexMatrix(rowEchelon);
    }

    private static int[] getLeadingRow(List<Vector> rowEchelon,int row) {
        Matrix mat = new ComplexMatrix(rowEchelon);
        List<Vector> cols = mat.getColVectors();
        for(int j = 0; j < cols.size(); j++){
            Vector v = cols.get(j);
            if (!v.isZero()){
                List<Scalar> entries = v.getEntries();
                for (int i = row; i < entries.size(); i++){
                    if (!entries.get(i).equal(Scalar.getZero())){
                        return new int[]{i,j};}
                }
            }
        }

        // should reach here if and only if row is zero
        return new int[]{-1,-1};
    }

    private static void switchRows(List<Vector> rowEchelon, int row1, int row2) {
        Vector v1 = rowEchelon.get(row1);
        Vector v2 = rowEchelon.get(row2);
        rowEchelon.set(row1, v2);
        rowEchelon.set(row2, v1);
    }

    /**********************End Of Row Echelon Code*********************************/

    /**********************Linear Equations Solver*********************************/

    public static VectorSpace solve(Matrix matrix, Vector b) throws ContradictionLineException{
        //expand matrix.
        Matrix expandedMatrix = expandMatrix(matrix, b);
        //row echelon the expanded matrix.
        expandedMatrix = canonicalRowEchelon(expandedMatrix);
        Scalar[] variables = new ComplexScalar[expandedMatrix.getM()];
        // remove row vectors that are zeros.
        expandedMatrix = removeZeroRows(expandedMatrix);

        //check for contradiction rows (rows that express the equation 0 = c where c != 0).
        if (checkForContradictionLines(removeZeroRows(expandedMatrix))) throw new ContradictionLineException();

        System.out.println(expandedMatrix);

        int m = expandedMatrix.getM();
        int n = expandedMatrix.getN();
        for (int row = 0 ; row < m; row++){
            if(onlyOneVarInRow(expandedMatrix, row))
                variables[row] = expandedMatrix.getRowVectors().get(row).getEntries().get(n-1);
        }
        System.out.println(Arrays.toString(variables));


        return null;
    }

    private static boolean onlyOneVarInRow(Matrix expandedMatrix, int row) {
        List<Scalar> entries = expandedMatrix.getRowVectors().get(row).getEntries();
        int count = 0;
        for (int i = 0 ; i < expandedMatrix.getN(); i++){
            if (!entries.get(i).equal(Scalar.getZero()))
                count++;
            if (count > 2)
                return false;
        }
        return true;
    }

    private static boolean checkForContradictionLines(Matrix matrix) {
        List<Vector> rows = matrix.getRowVectors();
        for (Vector v : rows){
            List<Scalar> entries = v.getEntries();
            if (!entries.get(entries.size() - 1).equal(Scalar.getZero())){
                boolean flag = false;
                for (int i = 0; i < entries.size() - 1; i++){
                    if (!entries.get(i).equal(Scalar.getZero())){
                        flag = true;
                    }
                }
                if (flag) continue;
                return true;
            }
        }
        return false;
    }

    private static Matrix removeZeroRows(Matrix matrix) {
        return new ComplexMatrix(matrix.getRowVectors().stream().filter(v -> !v.isZero()).collect(Collectors.toList()));
    }

    private static Matrix expandMatrix(Matrix matrix, Vector b) {
        int n = matrix.getN();
        int m = matrix.getM();
        ComplexScalar[][] mat = new ComplexScalar[m][n+1];
        for (int i = 0; i < m; i++){
            for (int j = 0; j < n; j++){
                mat[i][j] = (ComplexScalar) matrix.getMatrix()[i][j];
            }
            mat[i][n] = (ComplexScalar) b.getEntries().get(i);
        }
        return new ComplexMatrix(mat);
    }

}
