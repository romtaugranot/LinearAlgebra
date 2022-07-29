package com.LinearAlgebra.Matrices.VectorSets.VectorSpaces;

import com.LinearAlgebra.Matrices.VectorSets.Vector;
import com.LinearAlgebra.Matrices.VectorSets.VectorSet;

import java.util.Arrays;
import java.util.Set;

public interface VectorSpace extends VectorSet {

    static VectorSpace getZeroSpace(int size) {
        VectorSpace s = new MyVectorSpace();
        s.add(Vector.getZeroVector(size));
        return s;
    }

    boolean add(Vector v);

    default boolean addAll(Vector... vectors) {
        return Arrays.stream(vectors).allMatch(this::add);
    }

    Set<SpanVector> getBase();

    int getDim();

}
