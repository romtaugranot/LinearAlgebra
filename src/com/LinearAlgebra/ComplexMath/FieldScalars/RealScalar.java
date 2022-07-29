package com.LinearAlgebra.ComplexMath.FieldScalars;

/**
 * @inv: getImaginary() == 0
 */
public class RealScalar extends ComplexScalar {

    public RealScalar(String real) {
        super(real, "0");
    }

    public RealScalar(BigRational real) {
        super(real, BigRational.ZERO);
    }

    public RealScalar(RealScalar scalar) {
        super(scalar);
    }

}
