package com.LinearAlgebra.Matrices.VectorSets;

import com.LinearAlgebra.Rings.Fields.ComplexField.ComplexScalar;
import com.LinearAlgebra.Rings.Fields.ComplexField.RealField.MyRealScalar;
import com.LinearAlgebra.Rings.Fields.ComplexField.RealField.RealScalar;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RealVector extends MyComplexVector {

    public RealVector(List<RealScalar> entries) {
        super(entries.stream().map(x -> (ComplexScalar) x).collect(Collectors.toList()));
    }

    public RealVector(RealScalar... entries) {
        super(entries);
    }

    public RealVector(String... entries) {
        this(Arrays.stream(entries).map(MyRealScalar::new).toArray(RealScalar[]::new));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        List<ComplexScalar> entries = entries();
        sb.append("(");
        for (int i = 0; i < size; i++) {
            if (i != size -1)
                sb.append(entries.get(i).getReal() + ", ");
            else
                sb.append(entries.get(i).getReal());
        }
        sb.append(")");
        return sb.toString();
    }
}