package com.LinearAlgebra.Matrices.VectorSets;

import com.LinearAlgebra.ComplexMath.FieldScalars.BigRational;
import com.LinearAlgebra.ComplexMath.FieldScalars.ComplexScalar;
import com.LinearAlgebra.ComplexMath.FieldScalars.Scalar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComplexVector implements Vector {

    public final int size;

    private final ArrayList<ComplexScalar> entries;

    public ComplexVector(List<Scalar> entries) {
        this.entries = new ArrayList<>();
        this.size = entries.size();
        for (Scalar scalar : entries) {
            ComplexScalar alpha = new ComplexScalar(scalar.getReal(), scalar.getImaginary());
            this.entries.add(alpha);
        }
    }

    public ComplexVector(Scalar... entries) {
        this(Arrays.asList(entries));
    }

    public ComplexVector(ComplexScalar... entries) {
        this.entries = new ArrayList<>(List.of(entries));
        this.size = entries.length;
    }


    @Override
    public Vector add(Vector other) {
        ComplexScalar[] newEntries = new ComplexScalar[size];
        for (int i = 0; i < size; i++) {
            newEntries[i] = (ComplexScalar) entries.get(i).add(other.getEntries().get(i));
        }
        return new ComplexVector(newEntries);
    }

    @Override
    public Scalar dotProduct(Vector other) {
        Scalar alpha = Scalar.getZero();
        for (int i = 0; i < size; i++) {
            alpha = alpha.add(entries.get(i).mul(other.getEntries().get(i)));
        }
        return alpha;
    }

    @Override
    public Vector mul(Scalar alpha) {
        ComplexScalar[] newEntries = new ComplexScalar[size];
        for (int i = 0; i < size; i++) {
            newEntries[i] = (ComplexScalar) entries.get(i).mul(alpha);
        }
        return new ComplexVector(newEntries);
    }

    @Override
    public List<Scalar> getEntries() {
        return new ArrayList<>(entries);
    }

    public boolean isReal() {
        for (ComplexScalar alpha : entries) {
            if (!alpha.isReal()) return false;
        }
        return true;
    }

    @Override
    public Vector getMinus() {
        ComplexVector v = new ComplexVector(this.entries.toArray(ComplexScalar[]::new));
        for (int i = 0; i < size; i++) {
            v.entries.set(i, (ComplexScalar) this.entries.get(i).getMinus());
        }
        return v;
    }

    @Override
    public String toString() {
        if (isReal()) {
            BigRational[] real = new BigRational[size];
            for (int i = 0; i < size; i++) {
                real[i] = entries.get(i).getReal();
            }
            return new RealVector(real).toString();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < size - 1; i++) {
            sb.append(entries.get(i) + ", ");
        }
        sb.append(entries.get(size - 1)).append(")");
        return sb.toString();
    }

    @Override
    public int getSize() {
        return size;
    }
}
