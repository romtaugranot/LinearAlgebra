package Matrix;

public interface Matrix {

    Matrix add(Matrix other);

    Matrix scalarMul(double alpha);

    Matrix multiply(Matrix other);

    Matrix power(int n);

    Matrix transpose();

    int rank();

    Matrix gaussianElimination();

    Matrix canonicalGaussianElimination();

    double trace();

    double determinant();

}
