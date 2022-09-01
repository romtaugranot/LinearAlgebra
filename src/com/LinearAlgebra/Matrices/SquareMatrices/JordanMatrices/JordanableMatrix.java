package com.LinearAlgebra.Matrices.SquareMatrices.JordanMatrices;

import com.LinearAlgebra.Matrices.ComplexMatrix;
import com.LinearAlgebra.Matrices.Algorithms.MyJordanAlgorithm;
import com.LinearAlgebra.Matrices.MyComplexMatrix;
import com.LinearAlgebra.Matrices.SquareMatrices.NonSingularMatrix;
import com.LinearAlgebra.Matrices.SquareMatrices.SquareMatrix;
import com.LinearAlgebra.Matrices.VectorSets.VectorSpaces.VectorSpace;

public class JordanableMatrix extends SquareMatrix {

    /**
     * @param matrix
     * @pre: matrix.getN() == matrix.getM()
     */
    public JordanableMatrix(ComplexMatrix matrix) {
        super(matrix);
    }

    public SquareMatrix getJordanMatrix(){
        VectorSpace jordanBase = getJordanBasis();
        NonSingularMatrix jordanBaseMatrix = new NonSingularMatrix(new MyComplexMatrix(jordanBase.getBase()).transpose());
        return new SquareMatrix(jordanBaseMatrix.getInvertible().mul(this).mul(jordanBaseMatrix));
    }

    public VectorSpace getJordanBasis(){
        return new MyJordanAlgorithm().jordan(this);
    }

}
