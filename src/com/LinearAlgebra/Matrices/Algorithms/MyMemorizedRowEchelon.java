package com.LinearAlgebra.Matrices.Algorithms;

import com.LinearAlgebra.Rings.Fields.ComplexField.ComplexScalar;
import com.LinearAlgebra.Matrices.MyComplexMatrix;
import com.LinearAlgebra.Matrices.ComplexMatrix;
import com.LinearAlgebra.Matrices.SquareMatrices.ElementryMatrix;
import com.LinearAlgebra.Matrices.VectorSets.ComplexVector;

import java.util.ArrayList;
import java.util.List;

public class MyMemorizedRowEchelon implements MemorizedRowEchelon {

    private final ComplexMatrix matrix;
    private final List<ElementryMatrix> operations;

    public MyMemorizedRowEchelon(ComplexMatrix matrix) {
        this.matrix = matrix;
        operations = new ArrayList<>();
    }

    private static int[] getLeadingRow(List<ComplexVector> rowEchelon, int row) {
        ComplexMatrix mat = new MyComplexMatrix(rowEchelon);
        List<ComplexVector> cols = mat.colVectors();
        for (int j = 0; j < cols.size(); j++) {
            ComplexVector v = cols.get(j);
            if (!v.isZero()) {
                List<ComplexScalar> entries = v.entries();
                for (int i = row; i < entries.size(); i++) {
                    if (!entries.get(i).equals(ComplexScalar.ZERO)) {
                        return new int[]{i, j};
                    }
                }
            }
        }

        // should reach here if and only if row is zero
        return new int[]{-1, -1};
    }

    private static void switchRows(List<ComplexVector> rowEchelon, int row1, int row2) {
        ComplexVector v1 = rowEchelon.get(row1);
        ComplexVector v2 = rowEchelon.get(row2);
        rowEchelon.set(row1, v2);
        rowEchelon.set(row2, v1);
    }

    public ComplexMatrix rowEchelon() {
        List<ComplexVector> rowEchelon = matrix.rowVectors();
        int size = Math.max(matrix.n(), matrix.m());
        for (int row = 0; row < rowEchelon.size(); row++) {
            // get row with leading entry.
            int[] co = getLeadingRow(rowEchelon, row);
            if (co[0] == -1) continue;
            ComplexScalar leading = rowEchelon.get(co[0]).entries().get(co[1]);
            rowEchelon.set(co[0], rowEchelon.get(co[0]).mul(leading.inverse()));
            operations.add(new ElementryMatrix(size, co[0], leading.inverse()));
            // switch that row with the first row.
            switchRows(rowEchelon, co[0], row);
            if (row != co[0])
                operations.add(new ElementryMatrix(size, co[0], row));
            for (int i = 0; i < rowEchelon.size(); i++) {
                ComplexScalar aboveOrBelow = rowEchelon.get(i).entries().get(co[1]);
                if (!aboveOrBelow.equals(ComplexScalar.ZERO) && i != row) {
                    rowEchelon.set(i, rowEchelon.get(i).sub(rowEchelon.get(row).mul(aboveOrBelow)));
                    operations.add(new ElementryMatrix(size, i, row, aboveOrBelow.minus()));
                }
            }
        }
        return new MyComplexMatrix(rowEchelon);
    }

    @Override
    public List<ElementryMatrix> getOperationsOfRowEchelon() {
        return List.copyOf(operations);
    }

}
