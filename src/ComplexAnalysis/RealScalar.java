package ComplexAnalysis;

/**
 * A class representing a scalar in the real numbers' field.
 * All methods support the case of the other scalar being in another field,
 * and the result is complex scalar (except if the other scalar is in Fp field,
 * than it is a real scalar.
 */
public class RealScalar implements Scalar {

    private double value;

    public RealScalar(double value) {
        this.value = value;
    }

    public RealScalar(RealScalar other) {
        this.value = other.value;
    }

    @Override
    public Scalar add(Scalar other) {
        if (other instanceof RealScalar || other instanceof FpScalar)
            return new RealScalar(value + other.getReal());
        return new ComplexScalar(other.getReal() + value, other.getImaginary());
    }

    @Override
    public Scalar sub(Scalar other) {
        if (other instanceof RealScalar || other instanceof FpScalar)
            return new RealScalar(value - other.getReal());
        return new ComplexScalar(value - other.getReal(), other.getImaginary());
    }

    @Override
    public Scalar mul(Scalar other) {
        if (other instanceof RealScalar || other instanceof FpScalar)
            return new RealScalar(value * other.getReal());
        return new ComplexScalar(value * other.getReal(), value * other.getImaginary());
    }

    @Override
    public Scalar divide(Scalar other) {
        if (other instanceof  RealScalar || other instanceof FpScalar)
            return new RealScalar(value / other.getReal());
        ComplexScalar alpha = new ComplexScalar(value, 0);
        return alpha.divide(other);
    }

    @Override
    public Scalar conjugate() {
        return new RealScalar(this);
    }

    @Override
    public double getReal() {
        return value;
    }

    @Override
    public double getImaginary() {
        return 0;
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
