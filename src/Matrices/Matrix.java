package Matrices;

import ComplexMath.FieldScalars.ComplexScalar;
import ComplexMath.FieldScalars.Scalar;
import Matrices.VectorSets.Vector;
import Matrices.VectorSets.VectorSet;
import Matrices.VectorSets.VectorSpaces.VectorSpace;

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
