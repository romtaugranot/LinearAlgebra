package com.LinearAlgebra.ComplexMath.Scalars;

public class ComplexScalar implements Scalar {

    private final BigRational real;
    private final BigRational imaginary;

    public ComplexScalar(BigRational real, BigRational imaginary) {
        this.real = new BigRational(real.toString());
        this.imaginary = new BigRational(imaginary.toString());
    }

    public ComplexScalar(RealScalar real, RealScalar imaginary){
        this(real.getRationalReal(), imaginary.getRationalReal());
    }

    public ComplexScalar(String real, String imaginary) throws IllegalArgumentException {
        this.real = new BigRational(real);
        this.imaginary = new BigRational(imaginary);
    }

    public ComplexScalar(Scalar scalar) {
        this.real = scalar.getReal().getRationalReal();
        this.imaginary = scalar.getImaginary().getRationalReal();
    }

    public static ComplexScalar getZero() {
        return new RealScalar(BigRational.ZERO);
    }

    public static ComplexScalar getOne() {
        return new RealScalar(BigRational.ONE);
    }

    @Override
    public Scalar add(Scalar other) {
        return new ComplexScalar(this.real.plus(other.getReal().getRationalReal()).toString(), this.imaginary.plus(other.getImaginary().getRationalReal()).toString());
    }

    @Override
    public Scalar mul(Scalar other) {
        BigRational real = this.real.times(other.getReal().getRationalReal()).minus(this.imaginary.times(other.getImaginary().getRationalReal()));
        BigRational imaginary = this.real.times(other.getImaginary().getRationalReal()).plus(this.imaginary.times(other.getReal().getRationalReal()));
        return new ComplexScalar(real.toString(), imaginary.toString());
    }

    @Override
    public Scalar getInverse() throws DivisionByZeroException {
        if (this.equals(Scalar.getZero())) throw new DivisionByZeroException();
        return new ComplexScalar(real.times(new BigRational(getAbsoluteValueSquared().toString()).reciprocal()), imaginary.times(new BigRational(getAbsoluteValueSquared().toString()).reciprocal()).negate());
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
        Scalar ans = new RealScalar(BigRational.ONE);
        for (int i = 0; i < n; i++) {
            ans = ans.mul(this);
        }
        return ans;
    }


    public BigRational getAbsoluteValueSquared() {
        return real.times(real).plus(imaginary.times(imaginary));
    }

    public BigRational getRationalReal() {
        return real;
    }

    public BigRational getRationalImaginary() {
        return imaginary;
    }

    @Override
    public RealScalar getReal() {
        return new RealScalar(real);
    }

    @Override
    public RealScalar getImaginary() {
        return new RealScalar(imaginary);
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ComplexScalar s)) return false;
        return s.real.equals(real) && s.imaginary.equals(imaginary);
    }
}
