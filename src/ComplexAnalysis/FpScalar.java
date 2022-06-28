package ComplexAnalysis;


/**
 * A class representing a scalar in the Fp field.
 * All methods support the case of the other scalar being in another field,
 * and the result is complex scalar.
 */
public class FpScalar implements Scalar{

    private int p; /** Must be prime number **/

    private int value;

    public FpScalar(int p, int value){
        this.p = p;
        this.value = value % p;
    }

    public FpScalar(FpScalar other){
        this.p = other.p;
        this.value = other.value % p;
    }

    /**
     * @pre: other scalar is a Fp field scalar && this.p == other.p
     * @param other
     * @return a Fp scalar of the sum.
     */
    @Override
    public Scalar add(Scalar other) {
        // Assumes the other scalar has the same p as a mod.
        if (other instanceof FpScalar) {
            return new FpScalar(p, modp(value + ((FpScalar) other).value));
        }
        // Should never get here, just in case.
        return new ComplexScalar(value + other.getReal(), other.getImaginary());
    }

    /**
     * @pre: other scalar is a Fp field scalar && this.p == other.p
     * @param other
     * @return a Fp scalar that is resulted from the subtraction.
     */
    @Override
    public Scalar sub(Scalar other) {
        // Assumes the other scalar has the same p as a mod.
        if (other instanceof FpScalar) {
            return new FpScalar(p, modp(value - ((FpScalar) other).value));
        }
        // Should never get here, just in case.
        return new ComplexScalar(value - other.getReal(), other.getImaginary());
    }

    /**
     * @pre: other scalar is a Fp field scalar && this.p == other.p
     * @param other
     * @return a Fp scalar that is resulted from the multiplication.
     */
    @Override
    public Scalar mul(Scalar other) {
        // Assumes the other scalar has the same p as a mod.
        if (other instanceof FpScalar) {
            return new FpScalar(p, modp(value * ((FpScalar) other).value));
        }
        // Should never get here, just in case.
        return new ComplexScalar(value * other.getReal(),value * other.getImaginary());
    }

    /**
     * @pre: other scalar is a Fp field scalar && this.p == other.p
     * @param other
     * @return a Fp scalar that is resulted from the division.
     */
    @Override
    public Scalar divide(Scalar other) {
        // Assumes the other scalar has the same p as a mod
        // && other.value != 0 mod p.
        if (other instanceof FpScalar) {
            // Derived from Fermat's last theorem.
            int inverseOfOther = modp((int) Math.pow((((FpScalar) other).value), p-2));
            return new FpScalar(p, modp(value * inverseOfOther));
        }
        // Should never get here, just in case.
        ComplexScalar alpha = new ComplexScalar(value, 0);
        return alpha.divide(other);

    }

    @Override
    public Scalar conjugate() {
        return new FpScalar(this);
    }

    @Override
    public double getReal() {
        return value;
    }

    @Override
    public double getImaginary() {
        return 0;
    }

    /**
     *
     * @param n
     * @return n mod p
     */
    private int modp(int n){
        int mod =  n % p;
        if (mod >= 0) return mod;
        return mod + p;
    }

    @Override
    public String toString() {
        return "value: " + value + ", p: " + p;
    }
}
