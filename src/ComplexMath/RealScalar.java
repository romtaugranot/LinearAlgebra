package ComplexMath;

/**
 * @imp_inv: imaginary == 0
 * @inv: getImaginary() == 0
 */
public class RealScalar extends ComplexNumber{

    public RealScalar(double real) {
        super(real, 0);
    }

    public RealScalar(RealScalar scalar) {
        super(scalar);
    }

    @Override
    public String toString() {
        return Double.toString(real);
    }
}
