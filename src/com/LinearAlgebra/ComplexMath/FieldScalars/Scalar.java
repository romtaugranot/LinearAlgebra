package com.LinearAlgebra.ComplexMath.FieldScalars;

public interface Scalar {

    static Scalar getZero() {
        return new ComplexScalar(BigRational.ZERO, BigRational.ZERO);
    }

    Scalar add(Scalar other);

    default Scalar sub(Scalar other) {
        return this.add(other.getMinus());
    }

    Scalar mul(Scalar other);

    default Scalar div(Scalar other) throws ScalarDivisionByZeroException {
        if (other.equal(getZero())) throw new ScalarDivisionByZeroException();
        return this.mul(other.getInverse());
    }

    Scalar getInverse() throws ScalarDivisionByZeroException;

    Scalar getMinus();

    Scalar pow(int n);

    BigRational getReal();

    BigRational getImaginary();

    default boolean equal(Scalar other) {
        return this.getReal().equals(other.getReal())
                && this.getImaginary().equals(other.getImaginary());
    }

    default boolean isZero() {
        return this.equal(getZero());
    }


    default boolean isReal() {
        return getImaginary().equals(BigRational.ZERO);
    }

    default boolean isImaginary() {
        return getReal().equals(BigRational.ZERO);
    }

}
