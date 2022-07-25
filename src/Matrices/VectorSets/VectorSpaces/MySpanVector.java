package Matrices.VectorSets.VectorSpaces;

import ComplexMath.FieldScalars.Scalar;
import Matrices.MatrixMathUtils;
import Matrices.VectorSets.ComplexVector;
import Matrices.VectorSets.Vector;

import java.util.Collection;

public class MySpanVector implements SpanVector {

    private Vector v;

    public MySpanVector(Vector v){
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
