package com.LinearAlgebra.Matrices.Algorithms;

import com.LinearAlgebra.Rings.Fields.ComplexField.ComplexScalar;
import com.LinearAlgebra.Rings.Polynomials.Polynomial;

public interface RationalRootAlgorithm {

    /**
     * @pre: p.isReal() && for all 0 <= i <= p.entries().size():
     * p.entries().get(i).getReal().isInteger()
     * @param p
     * @return Rational roots of p.
     */
    ComplexScalar[] getRationalRoots(Polynomial p);

}
