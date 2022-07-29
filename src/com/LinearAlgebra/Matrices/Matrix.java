package com.LinearAlgebra.Matrices;

import com.LinearAlgebra.ComplexMath.FieldScalars.ComplexScalar;
import com.LinearAlgebra.ComplexMath.FieldScalars.Scalar;
import com.LinearAlgebra.Matrices.VectorSets.Vector;
import com.LinearAlgebra.Matrices.VectorSets.VectorSet;
import com.LinearAlgebra.Matrices.VectorSets.VectorSpaces.VectorSpace;

import java.util.List;

public interface Matrix {

    static Matrix getZeroMatrix(int m, int n) {
        ComplexScalar[][] matrix = new ComplexScalar[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = (ComplexScalar) Scalar.getZero();
            }
        }
        return new ComplexMatrix(matrix);
    }

    Matrix add(Matrix other);

    Matrix getMinus();

    default Matrix sub(Matrix other){
        return this.add(other.getMinus());
    }

    Matrix mul(Matrix other);

    Matrix mulByScalar(Scalar alpha);

    Matrix transpose();

    Matrix rowEchelon();

    Matrix canonicalRowEchelon();

    VectorSet solve(Vector b);

    VectorSpace getNullSpace();

    Vector mulByVector(Vector v);

    List<Vector> getRowVectors();

    List<Vector> getColVectors();

    Scalar[][] getMatrix();

    int getM();

    int getN();

    int getRank();

    Scalar getDeterminant();


}
