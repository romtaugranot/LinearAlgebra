package com.LinearAlgebra.Matrices.VectorSets.VectorSpaces;

import com.LinearAlgebra.ComplexMath.FieldScalars.Scalar;
import com.LinearAlgebra.Matrices.MatrixMathUtils;
import com.LinearAlgebra.Matrices.VectorSets.ComplexVector;
import com.LinearAlgebra.Matrices.VectorSets.Vector;

import java.util.Collection;

public class MySpanVector implements SpanVector {

    private final Vector v;

    public MySpanVector(Vector v) {
        this.v = new ComplexVector(v.getEntries());
    }

    @Override
    public Vector get(Scalar alpha) {
        return v.mul(alpha);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SpanVector)) return false;
        return MatrixMathUtils.divide(((SpanVector) o).getV(), v);
    }

    @Override
    public boolean contains(Object o) {
        if (!(o instanceof Vector || o instanceof SpanVector)) return false;
        if (o instanceof Vector) {
            return MatrixMathUtils.divide(v, (Vector) o);
        } else {
            return this.equals(o);
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return c.stream().allMatch(MySpanVector.this::contains);
    }

    @Override
    public Vector getV() {
        return new ComplexVector(v.getEntries());
    }

    @Override
    public String toString() {
        return "span[" + v.toString() + "]";
    }
}
