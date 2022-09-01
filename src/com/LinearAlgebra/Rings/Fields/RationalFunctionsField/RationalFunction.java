package com.LinearAlgebra.Rings.Fields.RationalFunctionsField;

import com.LinearAlgebra.Rings.DivisionByZeroException;
import com.LinearAlgebra.Rings.Fields.DifferentFieldException;
import com.LinearAlgebra.Rings.Fields.FieldScalar;
import com.LinearAlgebra.Rings.Polynomials.Polynomial;
import com.LinearAlgebra.Rings.Fields.ComplexField.ComplexScalar;
import com.LinearAlgebra.Rings.RingScalar;

public interface RationalFunction extends FieldScalar {

    RationalFunction ZERO = new MyRationalFunction(Polynomial.ZERO);
    RationalFunction ONE = new MyRationalFunction(Polynomial.ONE);

    ComplexScalar calculate(ComplexScalar x) throws DivisionByZeroException;

    Polynomial getNumerator();

    Polynomial getDenominator();

    @Override
    RationalFunction add(RingScalar other) throws DifferentFieldException;

    @Override
    default RationalFunction sub(RingScalar other) throws DifferentFieldException {
        return this.add(other.minus());
    }

    @Override
    RationalFunction mul(RingScalar other) throws DifferentFieldException;

    @Override
    default RationalFunction div(FieldScalar other) throws DivisionByZeroException, DifferentFieldException {
        return this.mul(other.inverse());
    }

    @Override
    RationalFunction minus();

    @Override
    RationalFunction inverse() throws DivisionByZeroException;

    @Override
    default RationalFunction pow(int n){
        RationalFunction alpha = ONE;
        for (int i = 0; i < n; i++)
            alpha = this.mul(alpha);
        return alpha;
    }
}
