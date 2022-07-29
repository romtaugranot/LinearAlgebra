package com.LinearAlgebra.ComplexMath.Polynomials;

import com.LinearAlgebra.ComplexMath.FieldScalars.Scalar;

import java.util.ArrayList;
import java.util.List;

public interface Polynomial {

    static Polynomial getZeroPolynomial(int degree) {
        List<Scalar> coefficients = new ArrayList<>();
        for (int i = 0; i < degree; i++) {
            coefficients.add(Scalar.getZero());
        }
        return new ComplexPolynomial(coefficients);
    }

    int getDegree();

    List<Scalar> getCoefficients();

    Polynomial add(Polynomial other);

    Polynomial mul(Polynomial other);

    Polynomial mulByScalar(Scalar other);

    Scalar calculate(Scalar alpha);

}
