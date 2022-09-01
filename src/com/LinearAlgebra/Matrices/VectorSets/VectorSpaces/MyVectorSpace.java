package com.LinearAlgebra.Matrices.VectorSets.VectorSpaces;

import com.LinearAlgebra.Matrices.VectorSets.ComplexVector;

import java.util.*;

public class MyVectorSpace implements VectorSpace {

    private final List<SpanVector> base;

    public MyVectorSpace() {
        this.base = new ArrayList<>();
    }

    @Override
    public boolean add(ComplexVector vector) {
        if (contains(vector)) return false;
        return base.add(new MySpanVector(vector));
    }

    @Override
    public boolean add(VectorSpace other, int size) {
        ComplexVector[] base = other.getBase().toArray(new ComplexVector[0]);
        if (other.equals(VectorSpace.zeroSpace(size)))
            return false;
        return addAll(base);
    }

    @Override
    public int getDim() {
        return base.size();
    }

    @Override
    public boolean contains(Object o) {
        if (base.isEmpty()) return false;
        if (!(o instanceof ComplexVector|| o instanceof SpanVector)) return false;
        ComplexVector v;
        if (o instanceof SpanVector sv){
            v = sv.getV();
        } else {
            v = (ComplexVector) o;
        }
        return !ComplexVector.isLinearIndependent(new HashSet<>(getBase()), v);
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
    public List<ComplexVector> getBase() {
        return base.stream().map(SpanVector::getV).toList();
    }

    @Override
    public String toString() {
        if (base.isEmpty()) return "";
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
