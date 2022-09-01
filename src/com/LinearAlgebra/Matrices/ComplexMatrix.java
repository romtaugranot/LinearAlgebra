package com.LinearAlgebra.Matrices;

import com.LinearAlgebra.Rings.Fields.ComplexField.ComplexScalar;
import com.LinearAlgebra.Rings.Fields.ComplexField.RealField.RealScalar;
import com.LinearAlgebra.Matrices.VectorSets.ComplexVector;
import com.LinearAlgebra.Matrices.VectorSets.VectorSet;
import com.LinearAlgebra.Matrices.VectorSets.VectorSpaces.VectorSpace;

import java.util.List;

public interface ComplexMatrix {

    ComplexMatrix add(ComplexMatrix other);

    ComplexMatrix minus();

    default ComplexMatrix sub(ComplexMatrix other) {
        return this.add(other.minus());
    }

    ComplexMatrix mul(ComplexMatrix other);

    ComplexMatrix mulByScalar(ComplexScalar alpha);

    ComplexMatrix transpose();

    ComplexMatrix canonicalRowEchelon();

    VectorSet solve(ComplexVector b) throws ContradictionLineException;

    VectorSpace getNullSpace();

    ComplexVector mulByVector(ComplexVector v);

    List<ComplexVector> rowVectors();

    List<ComplexVector> colVectors();

    ComplexScalar[][] getMatrix();

    int m();

    int n();

    int getRank();

    static ComplexMatrix zeroMatrix(int m, int n) {
        ComplexScalar[][] matrix = new RealScalar[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = RealScalar.ZERO;
            }
        }
        return new MyComplexMatrix(matrix);
    }

    static ComplexMatrix oneMatrix(int size) {
        ComplexScalar[][] matrix = new RealScalar[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j)
                    matrix[i][j] = RealScalar.ONE;
                else
                    matrix[i][j] = RealScalar.ZERO;
            }
        }
        return new MyComplexMatrix(matrix);
    }

}
