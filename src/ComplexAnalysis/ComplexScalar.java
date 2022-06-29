package ComplexAnalysis;


/**
 * A class representing a scalar in the complex numbers' field.
 */
public class ComplexScalar implements Scalar{

    protected double real;

    protected double imaginary;

    public ComplexScalar(double real, double imaginary){
        this.real = real;
        this.imaginary = imaginary;
    }

    public ComplexScalar(ComplexScalar other) {
        this.real = other.real;
        this.imaginary = other.imaginary;
    }

    @Override
    public Scalar add(Scalar other) {
        return new ComplexScalar(other.getReal() + real,
                other.getImaginary() + imaginary);
    }

    @Override
    public Scalar sub(Scalar other) {
        return new ComplexScalar(real - other.getReal(),
                imaginary - other.getImaginary());
    }

    @Override
    public Scalar mul(Scalar other) {
        double realPart = real * other.getReal() - imaginary * other.getImaginary();
        double imaginaryPart = real * other.getImaginary() + imaginary * other.getReal();
        return new ComplexScalar(realPart, imaginaryPart);
    }

    @Override
    public Scalar divide(Scalar other) {
        ComplexScalar numerator = new ComplexScalar((ComplexScalar)  this.mul(other.conjugate()));
        double denominator = other.getReal() * other.getReal() +
                other.getImaginary() * other.getImaginary();
        return new ComplexScalar(numerator.real / denominator, numerator.imaginary / denominator);
    }

    @Override
    public Scalar conjugate(){
        return new ComplexScalar(real, -imaginary);
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
        if (imaginary >= 0)
            return real + " + " + imaginary + "i";
        return real + " - " + -imaginary + "i";
    }
}
