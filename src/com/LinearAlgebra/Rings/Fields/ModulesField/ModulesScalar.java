package com.LinearAlgebra.Rings.Fields.ModulesField;

import com.LinearAlgebra.Rings.DivisionByZeroException;
import com.LinearAlgebra.Rings.Fields.DifferentFieldException;
import com.LinearAlgebra.Rings.Fields.FieldScalar;
import com.LinearAlgebra.Rings.RingScalar;

public interface ModulesScalar extends FieldScalar {

    @Override
    ModulesScalar add(RingScalar other) throws DifferentFieldException, NotSamePrimeException;

    @Override
    default ModulesScalar sub(RingScalar other) throws DifferentFieldException, NotSamePrimeException {
        return add(other.minus());
    }

    @Override
    ModulesScalar mul(RingScalar other) throws DifferentFieldException, NotSamePrimeException;

    @Override
    default ModulesScalar div(FieldScalar other) throws DivisionByZeroException,NotSamePrimeException, DifferentFieldException {
        return mul(other.inverse());
    }

    @Override
    ModulesScalar minus();

    @Override
    ModulesScalar inverse() throws DivisionByZeroException;

    @Override
    ModulesScalar pow(int n);

    int p();
    int val();
}
