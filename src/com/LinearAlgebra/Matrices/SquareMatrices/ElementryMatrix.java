package com.LinearAlgebra.Matrices.SquareMatrices;

import com.LinearAlgebra.Rings.Fields.ComplexField.ComplexScalar;
import com.LinearAlgebra.Rings.Fields.ComplexField.RealField.RealScalar;
import com.LinearAlgebra.Matrices.MyComplexMatrix;
import com.LinearAlgebra.Matrices.ComplexMatrix;
import com.LinearAlgebra.Matrices.VectorSets.MyComplexVector;

public class ElementryMatrix extends NonSingularMatrix {
    private final ElementryOperation op;

    public ElementryMatrix(int size, int i, int j, ComplexScalar s) {
        super(new MyComplexMatrix(ComplexMatrix.oneMatrix(size).rowVectors().stream().map(x -> (MyComplexVector) x)
                .map(x -> x.entries().get(i).equals(RealScalar.ONE) ? x.add(ComplexMatrix.oneMatrix(size).rowVectors().get(j).mul(s)) : x).toList()));
        op = ElementryOperation.ROW_ADDITION;
    }

    public ElementryMatrix(int size, int i, int j) {
        super(new MyComplexMatrix(ComplexMatrix.oneMatrix(size).rowVectors().stream().map(x -> (MyComplexVector) x)
                .map(x -> x.entries().get(i).equals(RealScalar.ONE) ? ComplexMatrix.oneMatrix(size).rowVectors().get(j) :
                        (x.entries().get(j).equals(RealScalar.ONE) ? ComplexMatrix.oneMatrix(size).rowVectors().get(i) : x))
                .toList()));
        op = ElementryOperation.ROW_SWITCH;

    }

    public ElementryMatrix(int size, int i, ComplexScalar s) {
        super(new MyComplexMatrix(ComplexMatrix.oneMatrix(size).rowVectors().stream().map(x -> (MyComplexVector) x)
                .map(x -> x.entries().get(i).equals(RealScalar.ONE) ? x.mul(s) : x).toList()));
        op = ElementryOperation.ROW_MUL;
    }

    @Override
    public ComplexScalar getDeterminant() {
        if (op.equals(ElementryOperation.ROW_SWITCH))
            return RealScalar.ONE.minus();
        else if (op.equals(ElementryOperation.ROW_MUL)) {
            for (int i = 0; i < m; i++) {
                ComplexScalar s = getMatrix()[i][i];
                if (!s.equals(ComplexScalar.ONE))
                    return s.inverse();
            }
        }
        return RealScalar.ONE;
    }

    public ElementryOperation getOp() {
        return op;
    }

    public enum ElementryOperation {
        ROW_SWITCH, ROW_MUL, ROW_ADDITION
    }
}
