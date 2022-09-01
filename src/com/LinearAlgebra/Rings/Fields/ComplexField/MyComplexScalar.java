package com.LinearAlgebra.Rings.Fields.ComplexField;

import com.LinearAlgebra.Rings.RingScalar;
import com.LinearAlgebra.Rings.BigRational;
import com.LinearAlgebra.Rings.DivisionByZeroException;

import java.util.Objects;

public class MyComplexScalar implements ComplexScalar {

    protected BigRational real, imaginary;

    public MyComplexScalar(BigRational real, BigRational imaginary){
        this.real = real;
        this.imaginary = imaginary;
    }

    public MyComplexScalar(String real, String imaginary){
        this.real = new BigRational(real);
        this.imaginary = new BigRational(imaginary);
    }

    public MyComplexScalar(ComplexScalar scalar) {
        this.real = new BigRational(scalar.getReal());
        this.imaginary = new BigRational(scalar.getImaginary());
    }


    @Override
    public String getReal() {
        return real.toString();
    }

    @Override
    public String getImaginary() {
        return imaginary.toString();
    }

    @Override
    public boolean isReal() {
        return imaginary.equals(BigRational.ZERO);
    }

    @Override
    public boolean isImaginary() {
        return real.equals(BigRational.ZERO);
    }

    @Override
    public ComplexScalar add(RingScalar other) throws NotComplexScalarException {
        if (!(other instanceof MyComplexScalar o)) throw new NotComplexScalarException();
        return new MyComplexScalar(this.real.plus(o.real), this.imaginary.plus(o.imaginary));
    }

    @Override
    public ComplexScalar mul(RingScalar other) throws NotComplexScalarException {
        if (!(other instanceof MyComplexScalar o)) throw new NotComplexScalarException();
        BigRational real = this.real.times(o.real).minus(this.imaginary.times(o.imaginary));
        BigRational imaginary = this.imaginary.times(o.real).plus(this.real.times(o.imaginary));
        return new MyComplexScalar(real, imaginary);
    }

    @Override
    public ComplexScalar minus() {
        return new MyComplexScalar(real.negate(), imaginary.negate());
    }

    @Override
    public ComplexScalar inverse() throws DivisionByZeroException {
        if (equals(ZERO)) throw new DivisionByZeroException();
        BigRational real = this.real.divides(new BigRational(absValueSquared()));
        BigRational imaginary = this.imaginary.divides(new BigRational(absValueSquared())).negate();
        return new MyComplexScalar(real, imaginary);
    }

    @Override
    public String absValueSquared() {
        return real.times(real).plus(imaginary.times(imaginary)).toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MyComplexScalar o)) return false;
        return o.real.equals(real) && o.imaginary.equals(imaginary);
    }

    @Override
    public String toString() {
        if (isReal()) return real.toString();
        if (isImaginary()) return "(" + imaginary.toString() + ")i";
        if (imaginary.compareTo(BigRational.ZERO) >= 0) {
            return real + " + (" + imaginary + ")i";
        }
        return "(" + real + " - (" + imaginary.negate() + ")i )";
    }

    @Override
    public int hashCode() {
        return Objects.hash(real, imaginary);
    }
}
