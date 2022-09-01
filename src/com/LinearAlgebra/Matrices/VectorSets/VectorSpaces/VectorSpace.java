package com.LinearAlgebra.Matrices.VectorSets.VectorSpaces;

import com.LinearAlgebra.Matrices.VectorSets.ComplexVector;
import com.LinearAlgebra.Matrices.VectorSets.VectorSet;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public interface VectorSpace extends VectorSet {

    static VectorSpace zeroSpace(int size) {
        VectorSpace s = new MyVectorSpace();
        s.add(ComplexVector.zeroVector(size));
        return s;
    }

    static VectorSpace fnSpan(int n) {
        VectorSpace s = new MyVectorSpace();
        for (int i = 0; i < n; i++) {
            s.add(ComplexVector.fnBaseVector(n, i));
        }
        return s;
    }

    boolean add(ComplexVector v);

    boolean add(VectorSpace other, int size);

    default boolean addAll(ComplexVector... vectors) {
        return Arrays.stream(vectors).allMatch(this::add);
    }

    List<ComplexVector> getBase();

    int getDim();

}
