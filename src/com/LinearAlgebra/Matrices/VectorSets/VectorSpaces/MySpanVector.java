package com.LinearAlgebra.Matrices.VectorSets.VectorSpaces;

import com.LinearAlgebra.Rings.Fields.ComplexField.ComplexScalar;
import com.LinearAlgebra.Matrices.MatrixMathUtils;
import com.LinearAlgebra.Matrices.VectorSets.MyComplexVector;
import com.LinearAlgebra.Matrices.VectorSets.ComplexVector;

import java.util.Collection;

public class MySpanVector implements SpanVector {

    private final ComplexVector v;

    public MySpanVector(ComplexVector v) {
        this.v = new MyComplexVector(v.entries());
    }

    @Override
    public ComplexVector get(ComplexScalar alpha) {
        return v.mul(alpha);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SpanVector)) return false;
        return MatrixMathUtils.divide(((SpanVector) o).getV(), v);
    }

    @Override
    public boolean contains(Object o) {
        if (!(o instanceof ComplexVector || o instanceof SpanVector)) return false;
        if (o instanceof ComplexVector) {
            return MatrixMathUtils.divide(v, (ComplexVector) o);
        } else {
            return this.equals(o);
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return c.stream().allMatch(MySpanVector.this::contains);
    }

    @Override
    public ComplexVector getV() {
        return new MyComplexVector(v.entries());
    }

    @Override
    public String toString() {
        return "span[" + v.toString() + "]";
    }
}
