package Matrices.VectorSets.VectorSpaces;

import ComplexMath.FieldScalars.Scalar;
import Matrices.VectorSets.Vector;
import Matrices.VectorSets.VectorSet;

public interface SpanVector extends VectorSet {

    Vector get(Scalar alpha);

    Vector getV();

}
