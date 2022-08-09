package com.LinearAlgebra.Matrices.SquareMatrices;

import com.LinearAlgebra.ComplexMath.Scalars.RealScalar;
import com.LinearAlgebra.ComplexMath.Scalars.Scalar;
import com.LinearAlgebra.Matrices.Matrix;
import com.LinearAlgebra.Matrices.MatrixAlgorithms.MemorizedRowEchelon;
import com.LinearAlgebra.Matrices.MatrixAlgorithms.MyMemorizedRowEchelon;

public class NonSingularMatrix extends SquareMatrix {

    MemorizedRowEchelon rowEcheloner;

    /**
     * @param matrix
     * @pre: Matrix is non-singular.
     */
    public NonSingularMatrix(Matrix matrix) {
        super(matrix);
        rowEcheloner = new MyMemorizedRowEchelon(this);
    }

    public Matrix getInvertible() {
        Matrix inverse = Matrix.getOneMatrix(m);
        rowEcheloner.rowEchelon();
        for (int i = rowEcheloner.getOperationsOfRowEchelon().size() - 1; i >= 0; i--) {
            inverse = inverse.mul(rowEcheloner.getOperationsOfRowEchelon().get(i));
        }
        return inverse;
    }

    @Override
    public Scalar getDeterminant() {
        Scalar det = new RealScalar("1");
        rowEcheloner.rowEchelon();
        for (ElementryMatrix elementry : rowEcheloner.getOperationsOfRowEchelon()) {
            det = det.mul(elementry.getDeterminant());
        }
        return det;
    }


}
