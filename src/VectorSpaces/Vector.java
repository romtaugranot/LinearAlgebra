package VectorSpaces;


import ComplexAnalysis.RealScalar;
import ComplexAnalysis.Scalar;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class representing a vector.
 * @implNote size is constant and arraylist vector is constant.
 */
public abstract class Vector<S extends Scalar> {

    public final int size;

    protected ArrayList<S> vector;

    public Vector(S[] vector){
        size = vector.length;
        this.vector = new ArrayList<>();
        this.vector.addAll(Arrays.asList(vector).subList(0, size));
    }

    public abstract Vector<S> add(Vector<S> other);

    public abstract Vector<S> mulByScalar(S scalar);

}