package com.LinearAlgebra.Matrices.MatrixAlgorithms;

import com.LinearAlgebra.ComplexMath.Scalars.Scalar;
import com.LinearAlgebra.Matrices.ComplexMatrix;
import com.LinearAlgebra.Matrices.Matrix;
import com.LinearAlgebra.Matrices.SquareMatrices.ElementryMatrix;
import com.LinearAlgebra.Matrices.VectorSets.ComplexVector;
import com.LinearAlgebra.Matrices.VectorSets.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyMemorizedRowEchelon implements MemorizedRowEchelon {

    private Matrix matrix;
    private List<ElementryMatrix> operations;

    public MyMemorizedRowEchelon(Matrix matrix){
        this.matrix = matrix;
        operations = new ArrayList<>();
    }

    public Matrix rowEchelon() {
        List<Vector> rowEchelon = matrix.getRowVectors();
        for (int row = 0; row < rowEchelon.size(); row++) {
            // get row with leading entry.
            int[] co = getLeadingRow(rowEchelon, row);
            if (co[0] == -1) continue;
            Scalar leading = rowEchelon.get(co[0]).getEntries().get(co[1]);
            rowEchelon.set(co[0], rowEchelon.get(co[0]).mul(leading.getInverse()));
            operations.add(new ElementryMatrix(matrix.getN(), co[0], leading.getInverse()));
            // switch that row with the first row.
            switchRows(rowEchelon, co[0], row);
            if (row != co[0])
                operations.add(new ElementryMatrix(matrix.getN(), co[0], row));
            for (int i = 0; i < rowEchelon.size(); i++) {
                Scalar aboveOrBelow = rowEchelon.get(i).getEntries().get(co[1]);
                if (!aboveOrBelow.isZero() && (i < row || i > row)) {
                    rowEchelon.set(i, rowEchelon.get(i).sub(rowEchelon.get(row).mul(aboveOrBelow)));
                    operations.add(new ElementryMatrix(matrix.getN(), i, row, aboveOrBelow.getMinus()));
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

    @Override
    public List<ElementryMatrix> getOperationsOfRowEchelon() {
        return List.copyOf(operations);
    }

    @Override
    public Matrix getMatrix() {
        return new ComplexMatrix(matrix.getMatrix());
    }
}
