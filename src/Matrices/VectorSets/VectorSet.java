package Matrices.VectorSets;

import Matrices.VectorSets.VectorSpaces.MyVectorSpace;
import Matrices.VectorSets.VectorSpaces.VectorSpace;

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
