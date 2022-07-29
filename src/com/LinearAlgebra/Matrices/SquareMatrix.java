package com.LinearAlgebra.Matrices;

import com.LinearAlgebra.ComplexMath.FieldScalars.Scalar;
import com.LinearAlgebra.Matrices.ComplexMatrix;
import com.LinearAlgebra.Matrices.Matrix;

/**
 * @inv: getM() == getN()
 */
public class SquareMatrix extends ComplexMatrix {

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

}
