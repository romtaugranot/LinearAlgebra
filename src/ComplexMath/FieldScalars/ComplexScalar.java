package ComplexMath.FieldScalars;

public class ComplexScalar implements Scalar {

    private BigRational real, imaginary;

    public ComplexScalar(BigRational real, BigRational imaginary){
        this.real = new BigRational(real.toString());
        this.imaginary = new BigRational(imaginary.toString());
    }

    public ComplexScalar(String real, String imaginary){
        this.real = new BigRational(real);
        this.imaginary = new BigRational(imaginary);
    }

    public ComplexScalar(Scalar scalar){
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
        return new ComplexScalar( real.times(new BigRational(getRadiusSquared().toString()).reciprocal()), imaginary.times(new BigRational(getRadiusSquared().toString()).reciprocal()).negate());
    }

    @Override
    public Scalar getMinus() {
        return new ComplexScalar(real.negate(), imaginary.negate());
    }

    private BigRational getRadiusSquared() {
        return real.times(real).plus(imaginary.times(imaginary));
    }

    public boolean isReal(){
        return imaginary.equals(BigRational.ZERO);
    }

    public boolean isImaginary(){
        return real.equals(BigRational.ZERO);
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
        if (imaginary.compareTo(BigRational.ZERO) >= 0) return real + " + " + imaginary + " i";
        return real + " " + imaginary + " i";
    }
}

