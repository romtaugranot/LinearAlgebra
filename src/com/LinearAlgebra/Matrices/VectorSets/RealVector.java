package com.LinearAlgebra.Matrices.VectorSets;

import com.LinearAlgebra.ComplexMath.Scalars.BigRational;
import com.LinearAlgebra.ComplexMath.Scalars.RealScalar;
import com.LinearAlgebra.ComplexMath.Scalars.Scalar;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RealVector extends ComplexVector {

    public RealVector(List<RealScalar> entries) {
        super(entries.stream().map(x -> (Scalar) x).collect(Collectors.toList()));
    }

    public RealVector(String... entries) {
        this(Arrays.stream(entries).toList().stream()
                .map(RealScalar::new).toArray(RealScalar[]::new));
    }

    public RealVector(BigRational... entries) {
        this(Arrays.stream(entries).toList().stream()
                .map(RealScalar::new).toArray(RealScalar[]::new));
    }

    public RealVector(RealScalar... entries) {
        super(entries);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        List<Scalar> entries = getEntries();
        sb.append("(");
        for (int i = 0; i < size - 1; i++) {
            sb.append(entries.get(i).getReal() + ", ");
        }
        sb.append(entries.get(size - 1).getReal() + ")");
        return sb.toString();

    }
}