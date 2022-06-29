package ComplexAnalysis;

/**
 * A class representing a scalar in the real numbers' field.
 */
public class RealScalar extends ComplexScalar {

    public RealScalar(double value) {
        super(value, 0);
    }

    public RealScalar(RealScalar other) {
        super(other);
    }

    @Override
    public String toString() {
        return "" + real;
    }
}
