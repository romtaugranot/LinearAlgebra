package com.LinearAlgebra.Matrices.VectorSets.VectorSpaces;

import com.LinearAlgebra.Matrices.VectorSets.Vector;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MyVectorSpace implements VectorSpace {

    private final Set<SpanVector> base;

    public MyVectorSpace() {
        this.base = new HashSet<>();
    }

    @Override
    public boolean add(Vector vector) {
        if (contains(vector)) return false;
        return base.add(new MySpanVector(vector));
    }

    @Override
    public int getDim() {
        return base.size();
    }

    @Override
    public boolean contains(Object o) {
        if (base.contains(o)) return true;
        if (!(o instanceof Vector || o instanceof SpanVector)) return false;
        if (o instanceof Vector v) {
            for (SpanVector span : base) {
                if (span.contains(v))
                    return true;
            }
        } else {
            SpanVector s = (SpanVector) o;
            for (SpanVector span : base) {
                if (span.equals(s))
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return c.stream().allMatch(MyVectorSpace.this::contains);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof MyVectorSpace other)) return false;
        if (other.base.size() != this.base.size()) return false;
        for (SpanVector s : base) {
            if (!other.base.contains(s))
                return false;
        }
        return true;
    }


    @Override
    public Set<SpanVector> getBase() {
        return Set.copyOf(base);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("span[");
        for (SpanVector s : base) {
            sb.append(s.getV()).append(", ");
        }
        sb.reverse();
        sb.replace(0, 2, "");
        sb.reverse();
        sb.append("]");
        return sb.toString();
    }
}
