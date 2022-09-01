package com.LinearAlgebra.Matrices;

import com.LinearAlgebra.Rings.Fields.ComplexField.ComplexScalar;
import com.LinearAlgebra.Rings.Fields.ComplexField.RealField.RealScalar;
import com.LinearAlgebra.Matrices.Algorithms.MyMemorizedRowEchelon;
import com.LinearAlgebra.Matrices.VectorSets.MyComplexVector;
import com.LinearAlgebra.Matrices.VectorSets.MyVectorSet;
import com.LinearAlgebra.Matrices.VectorSets.ComplexVector;
import com.LinearAlgebra.Matrices.VectorSets.VectorSet;
import com.LinearAlgebra.Matrices.VectorSets.VectorSpaces.MyVectorSpace;
import com.LinearAlgebra.Matrices.VectorSets.VectorSpaces.VectorSpace;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MatrixMathUtils {

    public static ComplexMatrix canonicalRowEchelon(ComplexMatrix matrix) {
        return new MyMemorizedRowEchelon(matrix).rowEchelon();
    }

    /****************************Start Of Equation Solver************************************/
    public static VectorSet solve(ComplexMatrix matrix, ComplexVector b) throws ContradictionLineException {
        if (b.equals(ComplexVector.zeroVector(b.size()))) {
            if (matrix.equals(ComplexMatrix.zeroMatrix(matrix.m(), matrix.n())))
                return VectorSpace.fnSpan(b.size());
            else return solveNullSpace(matrix);
        }

        //expand matrix.
        ComplexMatrix expandedMatrix = expandMatrix(matrix, b);
        //row echelon the expanded matrix.
        expandedMatrix = canonicalRowEchelon(expandedMatrix);
        // remove row vectors that are zeros.
        expandedMatrix = removeZeroRows(expandedMatrix);

        //check for contradiction rows (rows that express the equation 0 = c where c != 0).
        if (checkForContradictionLines(expandedMatrix)) throw new ContradictionLineException();

        List<Integer> indicesOfFreeVar = indexOfFreeVar(matrix.canonicalRowEchelon());

        ComplexVector solToFixedVar = calculateVector(indicesOfFreeVar, expandedMatrix.colVectors());
        int j = 0;
        ComplexScalar[] co = new ComplexScalar[matrix.n()];
        for (int i = 0; i < matrix.n(); i++) {
            if (!indicesOfFreeVar.contains(i))
                co[i] = solToFixedVar.entries().get(j++);
            else co[i] = ComplexScalar.ZERO;;
        }

        return new MyVectorSet(matrix.getNullSpace(), new MyComplexVector(co));
    }

    private static ComplexVector calculateVector(List<Integer> indicesOfFreeVar, List<ComplexVector> cols) {
        List<ComplexVector> newCols = new ArrayList<>();
        for (int i = 0; i < cols.size(); i++) {
            if (!indicesOfFreeVar.contains(i)) {
                newCols.add(cols.get(i));
            }
        }
        ComplexVector b = newCols.remove(newCols.size() - 1);
        return solveNoFreeVar(new MyComplexMatrix(newCols).transpose(), b);
    }

    /**
     * @param v1
     * @param v2
     * @return $ret == true implies for all 0 <= i,j < v1.getSize(),
     * v1.entries().get(i).divide(v2.entries().get(i)).equals(v1.entries().get(j).divide(v2.entries().get(j))
     * @pre: v1.getSize() == v2.getSize()
     */
    public static boolean divide(ComplexVector v1, ComplexVector v2) {
        List<ComplexScalar> entries1 = v1.entries();
        List<ComplexScalar> entries2 = v2.entries();
        ComplexScalar s = null;
        int size = v1.size();
        int i = 0;
        do {
            ComplexScalar one = entries1.get(i);
            ComplexScalar second = entries2.get(i);
            if (one.equals(ComplexScalar.ZERO)) {
                if (!second.equals(ComplexScalar.ZERO)) return false;
            } else {
                if (second.equals(ComplexScalar.ZERO)) return false;
                else {
                    if (i == 0) {
                        s = one.div(second);
                    } else {
                        if (!one.div(second).equals(s)) return false;
                    }
                }
            }
            i++;
        } while (i < size);
        return true;
    }

    private static boolean checkForContradictionLines(ComplexMatrix matrix) {
        List<ComplexVector> rows = matrix.rowVectors();
        for (ComplexVector v : rows) {
            List<ComplexScalar> entries = v.entries();
            if (!entries.get(entries.size() - 1).equals(ComplexScalar.ZERO)) {
                boolean flag = false;
                for (int i = 0; i < entries.size() - 1; i++) {
                    if (!entries.get(i).equals(ComplexScalar.ZERO)) {
                        flag = true;
                    }
                }
                if (flag) continue;
                return true;
            }
        }
        return false;
    }

    private static ComplexMatrix removeZeroRows(ComplexMatrix matrix) {
        return new MyComplexMatrix(matrix.rowVectors().stream().filter(v -> !v.isZero()).collect(Collectors.toList()));
    }

    private static ComplexMatrix expandMatrix(ComplexMatrix matrix, ComplexVector b) {
        List<ComplexVector> cols = matrix.colVectors();
        cols.add(b);
        return new MyComplexMatrix(cols).transpose();
    }

    public static ComplexVector solveNoFreeVar(ComplexMatrix matrix, ComplexVector b) {
        //expand matrix.
        ComplexMatrix expandedMatrix = expandMatrix(matrix, b);
        //row echelon the expanded matrix.
        expandedMatrix = canonicalRowEchelon(expandedMatrix);

        return expandedMatrix.colVectors().get(expandedMatrix.colVectors().size() - 1);
    }

    public static VectorSpace solveNullSpace(ComplexMatrix matrix) throws ContradictionLineException {
        int m = matrix.m();
        int n = matrix.n();

        if (matrix.equals(ComplexMatrix.zeroMatrix(m, n)))
            return VectorSpace.fnSpan(n);

        int numOfFreeVariables = n - matrix.getRank();
        // if matrix is non-singular return the zero vector space.
        if (numOfFreeVariables == 0)
            return VectorSpace.zeroSpace(n);

        //row echelon the expanded matrix.
        matrix = canonicalRowEchelon(matrix);

        List<Integer> indicesOfFreeVar = indexOfFreeVar(matrix);

        VectorSpace vs = new MyVectorSpace();

        //if A is in RREF, then a basis for the null-space of A can be built up by doing the
        // following: For each free variable, set it to 1 and the rest of the free variables
        // to zero and solve for the pivot variables. The resulting solution will give a
        // vector to be included in the basis.
        calculateVectorsNullSpace(vs, indicesOfFreeVar, matrix);

        return vs;
    }

    private static void calculateVectorsNullSpace(VectorSpace vs, List<Integer> indicesOfFreeVar, ComplexMatrix matrix) {
        for (int i : indicesOfFreeVar) {

            List<ComplexVector> newCols = matrix.colVectors();
            ComplexScalar[] co = new ComplexScalar[matrix.n()];
            for (int col : indicesOfFreeVar) {
                if (col != i) {
                    newCols.set(col, ComplexVector.zeroVector(matrix.m()));
                    co[col] = ComplexScalar.ZERO;
                } else {
                    co[col] = ComplexScalar.ONE;
                }
            }

            List<ComplexVector> rowVec = new MyComplexMatrix(newCols).transpose().rowVectors();

            int j = 0;
            for (int col = 0; col < matrix.n(); col++) {
                if (!indicesOfFreeVar.contains(col)) {
                    ComplexScalar sum = sumEntries(rowVec.get(j++));
                    co[col] = sum.sub(RealScalar.ONE).minus();
                }
            }

            vs.add(new MyComplexVector(co));
        }
    }

    private static ComplexScalar sumEntries(ComplexVector vector) {
        ComplexScalar alpha = ComplexScalar.ZERO;
        for (int i = 0; i < vector.entries().size(); i++) {
            alpha = alpha.add(vector.entries().get(i));
        }
        return alpha;
    }

    private static List<Integer> indexOfFreeVar(ComplexMatrix matrix) {
        List<Integer> freeVar = new ArrayList<>();
        int row = 0;
        for (int col = 0; col < matrix.n(); col++) {
            if (row == matrix.m())
                freeVar.add(col);
            else if (matrix.getMatrix()[row][col].equals(ComplexScalar.ZERO)) {
                freeVar.add(col);
            } else row++;
        }
        return freeVar;
    }
    /****************************End Of Equation Solver************************************/

}
