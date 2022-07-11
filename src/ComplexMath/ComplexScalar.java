package ComplexMath;

public class ComplexScalar implements Scalar{

    private double real, imaginary;

    public ComplexScalar(double real, double imaginary){
        this.real = real;
        this.imaginary = imaginary;
    }

    public ComplexScalar(Scalar scalar){
        this.real = scalar.getReal();
        this.imaginary = scalar.getImaginary();
    }


    @Override
    public Scalar add(Scalar other) {
        return new ComplexScalar(this.real + other.getReal(), this.imaginary + other.getImaginary());
    }

    @Override
    public Scalar mul(Scalar other) {
        double real = this.real * other.getReal() - this.imaginary * other.getImaginary();
        double imaginary = this.real * other.getImaginary() + this.imaginary * other.getReal();
        return new ComplexScalar(real, imaginary);
    }

    @Override
    public Scalar getInverse() throws ScalarDivisionByZeroException{
        if (this.equals(Scalar.getZero())) throw new ScalarDivisionByZeroException();
        return new ComplexScalar( real / getRadiusSquared(), -imaginary / getRadiusSquared());
    }

    @Override
    public Scalar getMinus() {
        return new ComplexScalar(-real, -imaginary);
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
        if (isReal()) return Double.toString(real);
        if (imaginary >= 0) return real + " + " + imaginary + "i";
        return real + " - " + -imaginary + "i";
    }
}

