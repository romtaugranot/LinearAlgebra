package com.LinearAlgebra.Matrices.SquareMatrices;

import com.LinearAlgebra.Rings.Fields.ComplexField.ComplexScalar;
import com.LinearAlgebra.Rings.Fields.ComplexField.RealField.RealScalar;
import com.LinearAlgebra.Matrices.ComplexMatrix;
import com.LinearAlgebra.Matrices.Algorithms.MemorizedRowEchelon;
import com.LinearAlgebra.Matrices.Algorithms.MyMemorizedRowEchelon;

public class NonSingularMatrix extends SquareMatrix {

    MemorizedRowEchelon rowEcheloner;

    /**
     * @param matrix
     * @pre: Matrix is non-singular.
     */
    public NonSingularMatrix(ComplexMatrix matrix) {
        super(matrix);
        rowEcheloner = new MyMemorizedRowEchelon(this);
    }

    public ComplexMatrix getInvertible() {
        ComplexMatrix inverse = ComplexMatrix.oneMatrix(m);
        rowEcheloner.rowEchelon();
        for (int i = rowEcheloner.getOperationsOfRowEchelon().size() - 1; i >= 0; i--) {
            inverse = inverse.mul(rowEcheloner.getOperationsOfRowEchelon().get(i));
        }
        return inverse;
    }

    @Override
    public ComplexScalar getDeterminant() {
        ComplexScalar det = RealScalar.ONE;
        rowEcheloner.rowEchelon();
        for (ElementryMatrix elementry : rowEcheloner.getOperationsOfRowEchelon()) {
            det = det.mul(elementry.getDeterminant());
        }
        return det;
    }


}
