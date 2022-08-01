package com.LinearAlgebra.Matrices.VectorSets.VectorSpaces;

import com.LinearAlgebra.ComplexMath.Scalars.Scalar;
import com.LinearAlgebra.Matrices.VectorSets.Vector;
import com.LinearAlgebra.Matrices.VectorSets.VectorSet;

public interface SpanVector extends VectorSet {

    Vector get(Scalar alpha);

    Vector getV();

}
