package ComplexMath;

/**
 * @imp_inv: imaginary == 0
 * @inv: getImaginary() == 0
 */
public class RealScalar extends ComplexScalar {

    public RealScalar(String real){ super(real, "0"); }

    public RealScalar(BigRational real) {
        super(real, BigRational.ZERO);
    }

    public RealScalar(RealScalar scalar) {
        super(scalar);
    }

}
