package Matrices.VectorSets;

import ComplexMath.FieldScalars.BigRational;
import ComplexMath.FieldScalars.ComplexScalar;
import ComplexMath.FieldScalars.Scalar;

import java.util.List;

public interface Vector {

    static Vector getZeroVector(int size) {
        ComplexScalar[] zeros = new ComplexScalar[size];
        for (int i = 0; i < size; i++) {
            zeros[i] = (ComplexScalar) Scalar.getZero();
        }
        return new ComplexVector(zeros);
    }

    Vector add(Vector other);

    default Vector sub(Vector other) {
        return this.add(other.getMinus());
    }

    Vector getMinus();

    Scalar dotProduct(Vector other);

    Vector mul(Scalar alpha);

    List<Scalar> getEntries();

    int getSize();

    default boolean isZero() {
        List<Scalar> entries = getEntries();
        for (Scalar s : entries) {
            if (!s.isZero())
                return false;
        }
        return true;
    }

    default boolean isReal() {
        List<Scalar> entries = getEntries();
        for (Scalar s : entries) {
            if (!s.getImaginary().equals(BigRational.ZERO))
                return false;
        }
        return true;
    }

}
