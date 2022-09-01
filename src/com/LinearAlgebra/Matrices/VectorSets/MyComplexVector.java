package com.LinearAlgebra.Matrices.VectorSets;

import com.LinearAlgebra.Rings.Fields.ComplexField.ComplexScalar;

import java.util.ArrayList;
import java.util.List;

public class MyComplexVector implements ComplexVector {

    public final int size;
    private final List<ComplexScalar> entries;


    public MyComplexVector(List<ComplexScalar> entries) {
        this.entries = entries;
        this.size = entries.size();
    }

    public MyComplexVector(ComplexScalar... entries) {
        this.entries = List.of(entries);
        this.size = entries.length;
    }

    @Override
    public ComplexVector add(ComplexVector other) {
        if (other.size() != size) throw new IllegalArgumentException("vectors differ in size");
        List<ComplexScalar> entries = new ArrayList<>();
        for (int i = 0; i < size; i++){
            entries.add(this.entries.get(i).add(other.entries().get(i)));
        }
        return new MyComplexVector(entries);
    }

    @Override
    public ComplexVector minus() {
        List<ComplexScalar> entries = new ArrayList<>();
        for (int i = 0; i < size; i++){
            entries.add(this.entries.get(i).minus());
        }
        return new MyComplexVector(entries);
    }

    @Override
    public ComplexScalar dotProduct(ComplexVector other) {
        if (other.size() != size) throw new IllegalArgumentException("vectors differ in size");
        ComplexScalar alpha = ComplexScalar.ZERO;
        for (int i = 0; i < size; i++){
            alpha = alpha.add(this.entries.get(i).mul(other.entries().get(i)));
        }
        return alpha;
    }

    @Override
    public ComplexVector mul(ComplexScalar alpha) {
        List<ComplexScalar> entries = new ArrayList<>();
        for (int i = 0; i < size; i++){
            entries.add(this.entries.get(i).mul(alpha));
        }
        return new MyComplexVector(entries);
    }

    @Override
    public List<ComplexScalar> entries() {
        return new ArrayList<>(entries);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MyComplexVector o)) return false;
        if (o.size != size) return false;
        for (int i = 0; i < size; i++)
            if (!entries.get(i).equals(o.entries.get(i))) return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        List<ComplexScalar> entries = entries();
        sb.append("(");
        for (int i = 0; i < size; i++) {
            if (i != size -1)
                sb.append(entries.get(i) + ", ");
            else
                sb.append(entries.get(i));
        }
        sb.append(")");
        return sb.toString();
    }
}