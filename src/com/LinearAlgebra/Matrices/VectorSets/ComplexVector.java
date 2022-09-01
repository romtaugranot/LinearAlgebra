package com.LinearAlgebra.Matrices.VectorSets;

import com.LinearAlgebra.Matrices.MyComplexMatrix;
import com.LinearAlgebra.Rings.Fields.ComplexField.ComplexScalar;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface ComplexVector{

    ComplexVector add(ComplexVector other);

    default ComplexVector sub(ComplexVector other) {
        return this.add(other.minus());
    }

    ComplexVector minus();

    ComplexScalar dotProduct(ComplexVector other);

    ComplexVector mul(ComplexScalar alpha);

    List<ComplexScalar> entries();

    int size();

    default boolean isZero() {
        List<ComplexScalar> entries = entries();
        for (ComplexScalar s : entries) {
            if (!s.equals(ComplexScalar.ZERO))
                return false;
        }
        return true;
    }

    default boolean isReal() {
        List<ComplexScalar> entries = entries();
        for (ComplexScalar s : entries) {
            if (!s.isReal())
                return false;
        }
        return true;
    }

    static ComplexVector zeroVector(int size) {
        ComplexScalar[] zeros = new ComplexScalar[size];
        for (int i = 0; i < size; i++) {
            zeros[i] = ComplexScalar.ZERO;
        }
        return new MyComplexVector(zeros);
    }

    static ComplexVector fnBaseVector(int n, int index) {
        ComplexScalar[] co = new ComplexScalar[n];
        for (int i = 0; i < n; i++) {
            if (i != index)
                co[i] = ComplexScalar.ZERO;
            else co[i] = ComplexScalar.ONE;
        }
        return new MyComplexVector(co);
    }

    static boolean isLinearIndependent(Set<ComplexVector> lst, ComplexVector v){
        List<ComplexVector> vectors = new ArrayList<>(lst);
        vectors.add(v);
        return new MyComplexMatrix(vectors).getRank() != new MyComplexMatrix(lst.stream().toList()).getRank();
    }

}