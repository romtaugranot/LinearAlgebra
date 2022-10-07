package com.LinearAlgebra.Matrices.SquareMatrices.JordanMatrices;

import com.LinearAlgebra.Matrices.ComplexMatrix;
import com.LinearAlgebra.Matrices.Algorithms.MyJordanAlgorithm;
import com.LinearAlgebra.Matrices.MyComplexMatrix;
import com.LinearAlgebra.Matrices.SquareMatrices.NonSingularMatrix;
import com.LinearAlgebra.Matrices.SquareMatrices.SquareMatrix;
import com.LinearAlgebra.Matrices.VectorSets.VectorSpaces.VectorSpace;
import com.LinearAlgebra.Rings.Fields.ComplexField.ComplexScalar;
import com.LinearAlgebra.Rings.Polynomials.ComplexPolynomial;
import com.LinearAlgebra.Rings.Polynomials.Polynomial;

import java.util.*;

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

    /**
     *
     * @return minimal polynomial of matrix using the theorem which states that for any eigen value c, the power of (x - c)
     * in the minimal polynomial is the size of the largest block corresponding to c.
     */
    public Polynomial getMinimalPolynomial() {
        SquareMatrix mat = getJordanMatrix();
        Set<ComplexScalar> eigenValuesNoRep = new HashSet<>(List.of(getEigenValues()));
        Polynomial p = Polynomial.ONE;
        for (ComplexScalar s : eigenValuesNoRep){
            int n = getBiggestBlockOfScalarFromJordanMatrix(mat, s);
            p = p.mul(new ComplexPolynomial(s.minus(), ComplexScalar.ONE).pow(n));
        }
        return p;
    }

    /**
     * @pre: mat != null && mat in jordan form.
     * @param mat
     * @param s
     * @return Biggest size of s*I block in mat.
     */
    private int getBiggestBlockOfScalarFromJordanMatrix(SquareMatrix mat, ComplexScalar s) {
        int max = 1;
        int curr = 1;
        for (int i = 0; i < mat.n() - 1; i++){
            if (!mat.getMatrix()[i][i].equals(s)) curr = 1;
            else{
                if (mat.getMatrix()[i + 1][i].equals(ComplexScalar.ZERO)) curr = 1;
                else curr++;
            }
            max = Math.max(max, curr);
        }
        return max;
    }
}
