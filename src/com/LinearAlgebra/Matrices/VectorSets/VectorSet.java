package com.LinearAlgebra.Matrices.VectorSets;

import com.LinearAlgebra.Matrices.VectorSets.VectorSpaces.MyVectorSpace;
import com.LinearAlgebra.Matrices.VectorSets.VectorSpaces.VectorSpace;

import java.util.Collection;

public interface VectorSet {

    static VectorSet getZeroSet(int m) {
        VectorSpace v = new MyVectorSpace();
        v.add(Vector.getZeroVector(m));
        return v;
    }

    boolean contains(Object o);

    boolean containsAll(Collection<?> c);

}
