package com.LinearAlgebra.ComplexMath.RationalFunctions;

import com.LinearAlgebra.ComplexMath.Polynomials.Polynomial;
import com.LinearAlgebra.ComplexMath.Scalars.ComplexScalar;
import com.LinearAlgebra.ComplexMath.Scalars.Scalar;

public interface RationalFunction extends Scalar {

    ComplexScalar calculate(ComplexScalar x);

    Polynomial getNumerator();

    Polynomial getDenominator();

}
