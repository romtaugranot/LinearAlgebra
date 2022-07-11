package Matrices;

import ComplexMath.Scalar;

public interface Matrix {

    Matrix add(Matrix other);

    Matrix mul(Matrix other);

    Matrix mulByScalar(Scalar alpha);

}
