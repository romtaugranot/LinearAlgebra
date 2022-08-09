package com.LinearAlgebra.ComplexMath.Scalars;

public interface Scalar {

    static Scalar getZero() {
        return new RealScalar(BigRational.ZERO);
    }

    static Scalar getOne() {return new RealScalar(BigRational.ONE);}

    Scalar add(Scalar other);

    default Scalar sub(Scalar other) {
        return this.add(other.getMinus());
    }

    Scalar mul(Scalar other);

    default Scalar div(Scalar other) throws DivisionByZeroException {
        if (isZero()) throw new DivisionByZeroException();
        return this.mul(other.getInverse());
    }

    Scalar getInverse() throws DivisionByZeroException;

    Scalar getMinus();

    Scalar pow(int n);

    RealScalar getReal();

    RealScalar getImaginary();

    default boolean isZero() {
        return this.equals(getZero());
    }


    default boolean isReal() {
        return getImaginary().equals(ComplexScalar.getZero());
    }

    default boolean isImaginary() {
        return getReal().equals(ComplexScalar.getZero());
    }

}