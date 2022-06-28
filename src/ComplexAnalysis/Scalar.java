package ComplexAnalysis;

public interface Scalar {

    /**
     * @param other
     * @return Sum of the scalars.
     */
    Scalar add(Scalar other);

    /**
     *
     * @param other
     * @return Subtraction of the scalars.
     */
    Scalar sub(Scalar other);

    /**
     *
     * @param other
     * @return Multiplication of the scalars.
     */
    Scalar mul(Scalar other);

    /**
     * @pre: Assumes the other scalar is not the 0 in the appropriate field.
     * @param other
     * @return Division of the scalars.
     */
    Scalar divide(Scalar other);

    /**
     *
     * @return The conjugate of the scalar.
     */
    Scalar conjugate();

    double getReal();

    double getImaginary();

}
