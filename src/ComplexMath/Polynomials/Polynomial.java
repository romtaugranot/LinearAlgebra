package ComplexMath.Polynomials;

import ComplexMath.FieldScalars.Scalar;

import java.util.List;

public interface Polynomial {

    int getDegree();

    List<Scalar> getCoefficients();

    Polynomial add(Polynomial other);

    Polynomial mul(Polynomial other);

    Polynomial mulByScalar(Scalar other);

}
