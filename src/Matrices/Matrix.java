package Matrices;

import ComplexMath.ComplexScalar;
import ComplexMath.Scalar;

import java.util.List;

public interface Matrix {

    Matrix add(Matrix other);

    Matrix mul(Matrix other);

    Matrix mulByScalar(Scalar alpha);

    Matrix transpose();

    Matrix rowEchelon();

    Vector solve(Vector b);

    static Matrix getZeroMatrix(int m, int n){
        ComplexScalar[][] matrix = new ComplexScalar[m][n];
        for (int i = 0; i < m; i++){
            for (int j = 0; j < n; j++){
                matrix[i][j] = (ComplexScalar) Scalar.getZero();
            }
        }
        return new ComplexMatrix(matrix);
    }

    List<Vector> getRowVectors();

    List<Vector> getColVectors();

    Scalar[][] getMatrix();

    int getM();

    int getN();

    int getRank();


}
