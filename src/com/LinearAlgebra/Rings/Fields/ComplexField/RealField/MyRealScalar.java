package com.LinearAlgebra.Rings.Fields.ComplexField.RealField;

import com.LinearAlgebra.Rings.Fields.ComplexField.MyComplexScalar;
import com.LinearAlgebra.Rings.BigRational;

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
