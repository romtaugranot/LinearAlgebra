package com.LinearAlgebra.Matrices.VectorSets;

import com.LinearAlgebra.ComplexMath.Scalars.BigRational;
import com.LinearAlgebra.ComplexMath.Scalars.ComplexScalar;
import com.LinearAlgebra.ComplexMath.Scalars.RealScalar;
import com.LinearAlgebra.ComplexMath.Scalars.Scalar;

import java.util.List;

public interface Vector {

    static Vector getZeroVector(int size) {
        ComplexScalar[] zeros = new ComplexScalar[size];
        for (int i = 0; i < size; i++) {
            zeros[i] = (ComplexScalar) Scalar.getZero();
        }
        return new ComplexVector(zeros);
    }

    static Vector getFnBaseVector(int n, int index) {
        ComplexScalar[] co = new ComplexScalar[n];
        for (int i = 0; i < n; i++) {
            if (i != index)
                co[i] = (ComplexScalar) Scalar.getZero();
            else co[i] = new RealScalar(BigRational.ONE);
        }
        return new ComplexVector(co);
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
            if (!(s instanceof ComplexScalar alpha) || !alpha.isReal())
                return false;
        }
        return true;
    }

    default boolean equal(Vector vector){
        List<Scalar> entries = vector.getEntries();
        if (entries.size() != getSize()) return false;
        for (int i = 0; i < getSize(); i++) {
            if (!entries.get(i).equal(getEntries().get(i)))
                return false;
        }
        return true;
    };
}