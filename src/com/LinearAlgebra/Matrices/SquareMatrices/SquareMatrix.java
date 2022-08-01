package com.LinearAlgebra.Matrices.SquareMatrices;

import com.LinearAlgebra.ComplexMath.Scalars.Scalar;
import com.LinearAlgebra.Matrices.ComplexMatrix;
import com.LinearAlgebra.Matrices.Matrix;

/**
 * @inv: getM() == getN()
 */
public class SquareMatrix extends ComplexMatrix {

    /**
     * @pre: matrix.getN() == matrix.getM()
     * @param matrix
     */
    public SquareMatrix(Matrix matrix){
        super(matrix.getMatrix());
    }

    public Scalar getTrace(){
        Scalar[][] matrix = getMatrix();
        Scalar trace = Scalar.getZero();
        for (int i = 0; i < getM(); i++)
            trace = trace.add(matrix[i][i]);
        return trace;
    }

    public Scalar getDeterminant() {
        Matrix canonical = canonicalRowEchelon();
        if (!canonical.equals(Matrix.getOneMatrix(n)))
            return Scalar.getZero();
        return (new NonSingularMatrix(this)).getDeterminant();
    }

}
