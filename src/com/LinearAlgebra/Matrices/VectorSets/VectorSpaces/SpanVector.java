package com.LinearAlgebra.Matrices.VectorSets.VectorSpaces;

import com.LinearAlgebra.Rings.Fields.ComplexField.ComplexScalar;
import com.LinearAlgebra.Matrices.VectorSets.ComplexVector;
import com.LinearAlgebra.Matrices.VectorSets.VectorSet;

public interface SpanVector extends VectorSet {

    ComplexVector get(ComplexScalar alpha);

    ComplexVector getV();

}
