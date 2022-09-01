package com.LinearAlgebra.Rings.Fields.ComplexField;

import com.LinearAlgebra.Rings.Fields.ComplexField.RealField.MyRealScalar;
import com.LinearAlgebra.Rings.Fields.ComplexField.RealField.RealScalar;
import com.LinearAlgebra.Rings.Fields.FieldScalar;
import com.LinearAlgebra.Rings.RingScalar;
import com.LinearAlgebra.Rings.BigRational;

public interface ComplexScalar extends FieldScalar {

    RealScalar ZERO = new MyRealScalar(BigRational.ZERO);
    RealScalar ONE = new MyRealScalar(BigRational.ONE);

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
            alpha = this.mul(alpha);
        return alpha;
    }

    String absValueSquared();

}
