package com.LinearAlgebra.Matrices;

import com.LinearAlgebra.ComplexMath.Scalars.RealScalar;
import com.LinearAlgebra.ComplexMath.Scalars.Scalar;
import com.LinearAlgebra.Matrices.VectorSets.Vector;
import com.LinearAlgebra.Matrices.VectorSets.VectorSet;
import com.LinearAlgebra.Matrices.VectorSets.VectorSpaces.VectorSpace;

import java.util.List;

public interface Matrix {

    static Matrix getZeroMatrix(int m, int n) {
        Scalar[][] matrix = new Scalar[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Scalar.getZero();
            }
        }
        return new ComplexMatrix(matrix);
    }

    static Matrix getOneMatrix(int size) {
        Scalar[][] matrix = new Scalar[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j)
                    matrix[i][j] = new RealScalar("1");
                else
                    matrix[i][j] = Scalar.getZero();
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

    Matrix canonicalRowEchelon();

    VectorSet solve(Vector b) throws ContradictionLineException;

    VectorSpace getNullSpace();

    Vector mulByVector(Vector v);

    List<Vector> getRowVectors();

    List<Vector> getColVectors();

    Scalar[][] getMatrix();

    int getM();

    int getN();

    int getRank();

}
