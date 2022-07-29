package com.LinearAlgebra.ComplexMath.FieldScalars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComplexScalar implements Scalar {

    private final BigRational real;
    private final BigRational imaginary;

    public ComplexScalar(BigRational real, BigRational imaginary) {
        this.real = new BigRational(real.toString());
        this.imaginary = new BigRational(imaginary.toString());
    }

    public ComplexScalar(String real, String imaginary) throws IllegalArgumentException{
        this.real = new BigRational(real);
        this.imaginary = new BigRational(imaginary);
    }

    public ComplexScalar(Scalar scalar) {
        this.real = scalar.getReal();
        this.imaginary = scalar.getImaginary();
    }

    @Override
    public Scalar add(Scalar other) {
        return new ComplexScalar(this.real.plus(other.getReal()).toString(), this.imaginary.plus(other.getImaginary()).toString());
    }

    @Override
    public Scalar mul(Scalar other) {
        BigRational real = this.real.times(other.getReal()).minus(this.imaginary.times(other.getImaginary()));
        BigRational imaginary = this.real.times(other.getImaginary()).plus(this.imaginary.times(other.getReal()));
        return new ComplexScalar(real.toString(), imaginary.toString());
    }

    @Override
    public Scalar getInverse() throws ScalarDivisionByZeroException {
        if (this.equals(Scalar.getZero())) throw new ScalarDivisionByZeroException();
        return new ComplexScalar(real.times(new BigRational(getRadiusSquared().toString()).reciprocal()), imaginary.times(new BigRational(getRadiusSquared().toString()).reciprocal()).negate());
    }

    @Override
    public Scalar getMinus() {
        return new ComplexScalar(real.negate(), imaginary.negate());
    }

    /**
     * @param n
     * @return the n-th power of the scalar.
     * @pre: n >= 0, n is an integer.
     */
    @Override
    public Scalar pow(int n) {
        Scalar ans = new RealScalar("1");
        for (int i = 0; i < n; i++) {
            ans = ans.mul(this);
        }
        return ans;
    }

    private BigRational getRadiusSquared() {
        return real.times(real).plus(imaginary.times(imaginary));
    }

    @Override
    public BigRational getReal() {
        return real;
    }

    @Override
    public BigRational getImaginary() {
        return imaginary;
    }

    @Override
    public String toString() {
        if (isReal()) return real.toString();
        if (isImaginary()) return imaginary + "i";
        if (imaginary.isInteger()) {
            if (imaginary.compareTo(BigRational.ZERO) >= 0) {
                return "(" + real + " + " + imaginary + "i)";
            }
            return "(" + real + " " + imaginary + "i)";
        } else {
            if (imaginary.compareTo(BigRational.ZERO) >= 0) {
                return "(" + real + " + " + imaginary.getNumerator() + "i/" + imaginary.getDenominator() + ")";
            }
            return "(" + real + " " + imaginary.getNumerator() + "i/" + imaginary.getDenominator() + ")";
        }
    }

}

