package com.LinearAlgebra.Matrices.VectorSets;

import com.LinearAlgebra.Matrices.VectorSets.VectorSpaces.MyVectorSpace;
import com.LinearAlgebra.Matrices.VectorSets.VectorSpaces.SpanVector;
import com.LinearAlgebra.Matrices.VectorSets.VectorSpaces.VectorSpace;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MyVectorSet implements VectorSet {

    private final Set<Vector> vectors;

    private VectorSpace span = new MyVectorSpace();

    public MyVectorSet() {
        vectors = new HashSet<>();
    }

    public MyVectorSet(VectorSpace vs, Vector... vectors) {
        this.vectors = new HashSet<>(Arrays.asList(vectors));
        span = new MyVectorSpace();
        for (SpanVector v : vs.getBase()) {
            span.add(v.getV());
        }
    }

    public MyVectorSet(Vector... vectors) {
        this.vectors = new HashSet<>(Arrays.asList(vectors));
        span = new MyVectorSpace();
    }

    public boolean add(Vector v) {
        return vectors.add(v);
    }

    public boolean spanAdd(Vector span) {
        return this.span.add(span);
    }

    @Override
    public boolean contains(Object o) {
        return vectors.contains(o) || span.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return vectors.contains(c) || span.contains(c);
    }

    public Set<Vector> getVectors() {
        return Set.copyOf(vectors);
    }

    public Set<SpanVector> getSpanVectors() {
        return Set.copyOf(span.getBase());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (span.getBase().size() > 0) {
            int i = 1;
            for (SpanVector s : span.getBase()) {
                sb.append("x").append(i++).append(s.getV()).append(" + ");
            }
            if (vectors.size() == 0) {
                sb.reverse();
                sb.replace(0, 3, "");
                sb.reverse();
            }
        }
        if (vectors.size() > 0) {
            for (Vector v : vectors) {
                sb.append(v).append(" + ");
            }
            sb.reverse();
            sb.replace(0, 3, "");
            sb.reverse();
        }
        sb.append("}");
        return sb.toString();
    }
}
