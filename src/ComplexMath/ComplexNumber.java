package ComplexMath;

public class ComplexNumber implements Scalar{

    protected double real, imaginary;

    public ComplexNumber(double real, double imaginary){
        this.real = real;
        this.imaginary = imaginary;
    }

    public ComplexNumber(Scalar scalar){
        this.real = scalar.getReal();
        this.imaginary = scalar.getImaginary();
    }


    @Override
    public Scalar add(Scalar other) {
        return new ComplexNumber(this.real + other.getReal(), this.imaginary + other.getImaginary());
    }

    @Override
    public Scalar mul(Scalar other) {
        double real = this.real * other.getReal() - this.imaginary * other.getImaginary();
        double imaginary = this.real * other.getImaginary() + this.imaginary * other.getReal();
        return new ComplexNumber(real, imaginary);
    }

    @Override
    public Scalar getInverse() throws ScalarDivisionByZeroException{
        if (this.equals(Scalar.getZero())) throw new ScalarDivisionByZeroException();
        return new ComplexNumber( real / getRadiusSquared(), -imaginary / getRadiusSquared());
    }

    @Override
    public Scalar getMinus() {
        return new ComplexNumber(-real, -imaginary);
    }

    private double getRadiusSquared() {
        return real * real + imaginary * imaginary;
    }

    @Override
    public double getReal() {
        return real;
    }

    @Override
    public double getImaginary() {
        return imaginary;
    }

    @Override
    public String toString() {
        if (imaginary >= 0) return real + " + " + imaginary + "i";
        return real + " - " + -imaginary + "i";
    }
}

