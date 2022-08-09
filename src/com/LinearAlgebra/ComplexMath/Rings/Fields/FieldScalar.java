package com.LinearAlgebra.ComplexMath.Rings.Fields;

import com.LinearAlgebra.ComplexMath.Rings.RingScalar;
import com.LinearAlgebra.ComplexMath.Scalars.DivisionByZeroException;

public interface FieldScalar extends RingScalar {

    default FieldScalar div(FieldScalar other) throws DivisionByZeroException, DifferentFieldException{
        return this.mul(other.inverse());
    }

    FieldScalar inverse() throws DivisionByZeroException;

    @Override
    FieldScalar add(RingScalar other) throws DifferentFieldException;

    @Override
    default FieldScalar sub(RingScalar other) throws DifferentFieldException {
        return add(other.minus());
    }

    @Override
    FieldScalar mul(RingScalar other) throws DifferentFieldException ;

    @Override
    FieldScalar minus();
}
