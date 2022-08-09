package com.LinearAlgebra.ComplexMath.Rings.Fields.ComplexField;

import com.LinearAlgebra.ComplexMath.Rings.Fields.FieldScalar;
import com.LinearAlgebra.ComplexMath.Rings.RingScalar;
import com.LinearAlgebra.ComplexMath.Scalars.BigRational;

public interface ComplexScalar extends FieldScalar {

    MyComplexScalar ZERO = new MyComplexScalar(BigRational.ZERO,BigRational.ZERO);
    MyComplexScalar ONE = new MyComplexScalar(BigRational.ONE, BigRational.ZERO);

    String getReal();

    String getImaginary();

    boolean isReal();

    boolean isImaginary();

    @Override
    ComplexScalar add(RingScalar other) throws NotComplexScalarException;

    @Override
    default ComplexScalar sub(RingScalar other) throws NotComplexScalarException{
        return add(other.minus());
    }

    @Override
    ComplexScalar mul(RingScalar other) throws NotComplexScalarException;

    @Override
    default ComplexScalar div(FieldScalar other) throws NotComplexScalarException{
        return mul(other.inverse());
    }

    @Override
    ComplexScalar minus();

    @Override
    ComplexScalar inverse();

    @Override
    default ComplexScalar pow(int n){
        ComplexScalar alpha = ONE;
        for (int i = 0; i < n; i++)
            alpha = alpha.mul(alpha);
        return alpha;
    }

    String absValueSquared();

}
