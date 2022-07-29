package com.LinearAlgebra.Matrices;

import com.LinearAlgebra.ComplexMath.FieldScalars.ComplexScalar;
import com.LinearAlgebra.ComplexMath.FieldScalars.RealScalar;
import com.LinearAlgebra.ComplexMath.FieldScalars.Scalar;
import com.LinearAlgebra.Matrices.VectorSets.ComplexVector;
import com.LinearAlgebra.Matrices.VectorSets.MyVectorSet;
import com.LinearAlgebra.Matrices.VectorSets.Vector;
import com.LinearAlgebra.Matrices.VectorSets.VectorSet;
import com.LinearAlgebra.Matrices.VectorSets.VectorSpaces.MyVectorSpace;
import com.LinearAlgebra.Matrices.VectorSets.VectorSpaces.VectorSpace;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MatrixMathUtils {

    /**********************Row Echelon Code*********************************/
    public static Matrix rowEchelon(Matrix matrix) {
        List<Vector> rowEchelon = matrix.getRowVectors();
        for (int row = 0; row < rowEchelon.size(); row++) {
            // get row with leading entry.
            int[] co = getLeadingRow(rowEchelon, row);
            if (co[0] == -1) continue;
            rowEchelon.set(co[0], rowEchelon.get(co[0]).mul(rowEchelon.get(co[0]).getEntries().get(co[1]).getInverse()));
            // switch that row with the first row.
            switchRows(rowEchelon, co[0], row);
            Scalar leading = rowEchelon.get(row).getEntries().get(co[1]);
            for (int i = row + 1; i < rowEchelon.size(); i++) {
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
        for (int row = 0; row < rowEchelon.size(); row++) {
            // get row with leading entry.
            int[] co = getLeadingRow(rowEchelon, row);
            if (co[0] == -1) break;
            Scalar leading = rowEchelon.get(co[0]).getEntries().get(co[1]);
            for (int i = 0; i < co[0]; i++) {
                Scalar aboveLeading = rowEchelon.get(i).getEntries().get(co[1]);
                if (!aboveLeading.isZero()) {
                    Scalar div = aboveLeading.div(leading);
                    rowEchelon.set(i, rowEchelon.get(i).sub(rowEchelon.get(row).mul(div)));
                }
            }
        }
        return new ComplexMatrix(rowEchelon);
    }

    private static int[] getLeadingRow(List<Vector> rowEchelon, int row) {
        Matrix mat = new ComplexMatrix(rowEchelon);
        List<Vector> cols = mat.getColVectors();
        for (int j = 0; j < cols.size(); j++) {
            Vector v = cols.get(j);
            if (!v.isZero()) {
                List<Scalar> entries = v.getEntries();
                for (int i = row; i < entries.size(); i++) {
                    if (!entries.get(i).equal(Scalar.getZero())) {
                        return new int[]{i, j};
                    }
                }
            }
        }

        // should reach here if and only if row is zero
        return new int[]{-1, -1};
    }

    private static void switchRows(List<Vector> rowEchelon, int row1, int row2) {
        Vector v1 = rowEchelon.get(row1);
        Vector v2 = rowEchelon.get(row2);
        rowEchelon.set(row1, v2);
        rowEchelon.set(row2, v1);
    }

    /**********************End Of Row Echelon Code*********************************/

    /**********************Linear Equations Solver*********************************/

    public static VectorSet solve(Matrix matrix, Vector b) throws ContradictionLineException {
        //expand matrix.
        Matrix expandedMatrix = expandMatrix(matrix, b);
        //row echelon the expanded matrix.
        expandedMatrix = canonicalRowEchelon(expandedMatrix);
        // remove row vectors that are zeros.
        expandedMatrix = removeZeroRows(expandedMatrix);

        //check for contradiction rows (rows that express the equation 0 = c where c != 0).
        if (checkForContradictionLines(matrix)) throw new ContradictionLineException();

        List<Integer> indicesOfFreeVar = indexOfFreeVar(matrix.canonicalRowEchelon());

        System.out.println(expandedMatrix);
        System.out.println(matrix.canonicalRowEchelon());
        System.out.println(indicesOfFreeVar);
        System.out.println();

        Vector solToFixedVar = calculateVector(indicesOfFreeVar, expandedMatrix.getColVectors());
        int j = 0;
        Scalar[] co = new ComplexScalar[matrix.getN()];
        for (int i = 0; i < matrix.getN(); i++) {
            if (!indicesOfFreeVar.contains(i))
                co[i] = solToFixedVar.getEntries().get(j++);
            else co[i] = Scalar.getZero();
        }
        System.out.println(new ComplexVector(co));

        return new MyVectorSet(matrix.getNullSpace(), new ComplexVector(co));
    }

    private static Vector calculateVector(List<Integer> indicesOfFreeVar, List<Vector> cols) {
        List<Vector> newCols = new ArrayList<>();
        for (int i = 0; i < cols.size(); i++) {
            if (!indicesOfFreeVar.contains(i)) {
                newCols.add(cols.get(i));
            }
        }

        Vector b = newCols.remove(newCols.size() - 1);

        return solveNoFreeVar(new ComplexMatrix(newCols).transpose(), b);
    }

    /**
     * @param v1
     * @param v2
     * @return $ret == true implies for all 0 <= i,j < v1.getSize(),
     * v1.getEntries().get(i).divide(v2.getEntries().get(i)).equals(v1.getEntries().get(j).divide(v2.getEntries().get(j))
     * @pre: v1.getSize() == v2.getSize()
     */
    public static boolean divide(Vector v1, Vector v2) {
        List<Scalar> entries1 = v1.getEntries();
        List<Scalar> entries2 = v2.getEntries();
        Scalar s = null;
        int size = v1.getSize();
        int i = 0;
        do {
            Scalar one = entries1.get(i);
            Scalar second = entries2.get(i);
            if (one.equal(Scalar.getZero())) {
                if (!second.equal(Scalar.getZero())) return false;
            } else {
                if (second.equal(Scalar.getZero())) return false;
                else {
                    if (i == 0) {
                        s = one.div(second);
                    } else {
                        if (!one.div(second).equal(s)) return false;
                    }
                }
            }
            i++;
        } while (i < size);
        return true;
    }

    private static boolean checkForContradictionLines(Matrix matrix) {
        List<Vector> rows = matrix.getRowVectors();
        for (Vector v : rows) {
            List<Scalar> entries = v.getEntries();
            if (!entries.get(entries.size() - 1).equal(Scalar.getZero())) {
                boolean flag = false;
                for (int i = 0; i < entries.size() - 1; i++) {
                    if (!entries.get(i).equal(Scalar.getZero())) {
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
        List<Vector> cols = matrix.getColVectors();
        cols.add(b);
        return new ComplexMatrix(cols).transpose();
    }

    public static Vector solveNoFreeVar(Matrix matrix, Vector b) {
        //expand matrix.
        Matrix expandedMatrix = expandMatrix(matrix, b);
        //row echelon the expanded matrix.
        expandedMatrix = canonicalRowEchelon(expandedMatrix);

        return expandedMatrix.getColVectors().get(expandedMatrix.getColVectors().size() - 1);
    }

    public static VectorSpace solveNullSpace(Matrix matrix) throws ContradictionLineException {
        int m = matrix.getM();
        int n = matrix.getN();

        if (matrix.equals(Matrix.getZeroMatrix(m, n)))
            return (VectorSpace) VectorSet.getZeroSet(m);

        int numOfFreeVariables = n - matrix.getRank();

        // if matrix is non-singular return the zero vector space.
        if (numOfFreeVariables == 0)
            return VectorSpace.getZeroSpace(n);

        //row echelon the expanded matrix.
        matrix = canonicalRowEchelon(matrix);

        System.out.println(matrix);

        List<Integer> indicesOfFreeVar = indexOfFreeVar(matrix);

        VectorSpace vs = new MyVectorSpace();

        //if A is in RREF, then a basis for the null-space of A can be built up by doing the
        // following: For each free variable, set it to 1 and the rest of the free variables
        // to zero and solve for the pivot variables. The resulting solution will give a
        // vector to be included in the basis.
        calculateVectorsNullSpace(vs, indicesOfFreeVar, matrix);

        return vs;
    }

    private static void calculateVectorsNullSpace(VectorSpace vs, List<Integer> indicesOfFreeVar, Matrix matrix) {
        for (int i : indicesOfFreeVar) {

            List<Vector> newCols = matrix.getColVectors();
            Scalar[] co = new ComplexScalar[matrix.getN()];
            for (int col : indicesOfFreeVar) {
                if (col != i) {
                    newCols.set(col, Vector.getZeroVector(matrix.getM()));
                    co[col] = Scalar.getZero();
                } else {
                    co[col] = new RealScalar("1");
                }
            }

            List<Vector> rowVec = new ComplexMatrix(newCols).transpose().getRowVectors();

            int j = 0;
            for (int col = 0; col < matrix.getN(); col++) {
                if (!indicesOfFreeVar.contains(col)) {
                    Scalar sum = sumEntries(rowVec.get(j++));
                    co[col] = sum.sub(new RealScalar("1")).getMinus();
                }
            }

            vs.add(new ComplexVector(co));
        }
    }

    private static Scalar sumEntries(Vector vector) {
        Scalar alpha = Scalar.getZero();
        for (int i = 0; i < vector.getEntries().size(); i++) {
            alpha = alpha.add(vector.getEntries().get(i));
        }
        return alpha;
    }

    private static List<Integer> indexOfFreeVar(Matrix matrix) {
        List<Integer> freeVar = new ArrayList<>();
        int row = 0;
        for (int col = 0; col < matrix.getN(); col++) {
            if (row == matrix.getM())
                freeVar.add(col);
            else if (matrix.getMatrix()[row][col].isZero()) {
                freeVar.add(col);
            } else row++;
        }
        return freeVar;
    }
    /****************************End Of Equation Solver************************************/

}
