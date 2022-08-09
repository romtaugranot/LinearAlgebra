package com.LinearAlgebra.ComplexMath.Rings.Fields.ComplexField.RealField;

import com.LinearAlgebra.ComplexMath.Rings.Fields.ComplexField.ComplexScalar;
import com.LinearAlgebra.ComplexMath.Rings.Fields.ComplexField.MyComplexScalar;
import com.LinearAlgebra.ComplexMath.Scalars.BigRational;

public class MyRealScalar extends MyComplexScalar implements RealScalar {

    public MyRealScalar(BigRational val){
        super(val, BigRational.ZERO);
    }

    public MyRealScalar(String val){
        super(val, BigRational.ZERO.toString());
    }

    @Override
    public RealScalar minus() {
        return new MyRealScalar(real.negate());
    }

    @Override
    public RealScalar inverse() {
        return new MyRealScalar(real.reciprocal());
    }
}
