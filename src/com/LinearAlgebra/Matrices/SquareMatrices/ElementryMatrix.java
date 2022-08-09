package com.LinearAlgebra.Matrices.SquareMatrices;

import com.LinearAlgebra.ComplexMath.Scalars.RealScalar;
import com.LinearAlgebra.ComplexMath.Scalars.Scalar;
import com.LinearAlgebra.Matrices.ComplexMatrix;
import com.LinearAlgebra.Matrices.Matrix;
import com.LinearAlgebra.Matrices.VectorSets.ComplexVector;

public class ElementryMatrix extends NonSingularMatrix {
    private final ElementryOperation op;

    public ElementryMatrix(int size, int i, int j, Scalar s) {
        super(new ComplexMatrix(Matrix.getOneMatrix(size).getRowVectors().stream().map(x -> (ComplexVector) x)
                .map(x -> x.getEntries().get(i).equals(new RealScalar("1")) ? x.add(Matrix.getOneMatrix(size).getRowVectors().get(j).mul(s)) : x).toList()));
        op = ElementryOperation.ROW_ADDITION;
    }

    public ElementryMatrix(int size, int i, int j) {
        super(new ComplexMatrix(Matrix.getOneMatrix(size).getRowVectors().stream().map(x -> (ComplexVector) x)
                .map(x -> x.getEntries().get(i).equals(new RealScalar("1")) ? Matrix.getOneMatrix(size).getRowVectors().get(j) :
                        (x.getEntries().get(j).equals(new RealScalar("1")) ? Matrix.getOneMatrix(size).getRowVectors().get(i) : x))
                .toList()));
        op = ElementryOperation.ROW_SWITCH;

    }

    public ElementryMatrix(int size, int i, Scalar s) {
        super(new ComplexMatrix(Matrix.getOneMatrix(size).getRowVectors().stream().map(x -> (ComplexVector) x)
                .map(x -> x.getEntries().get(i).equals(new RealScalar("1")) ? x.mul(s) : x).toList()));
        op = ElementryOperation.ROW_MUL;
    }

    @Override
    public Scalar getDeterminant() {
        if (op.equals(ElementryOperation.ROW_SWITCH))
            return new RealScalar("-1");
        else if (op.equals(ElementryOperation.ROW_MUL)) {
            for (int i = 0; i < m; i++) {
                Scalar s = getMatrix()[i][i];
                if (!s.equals(new RealScalar("1")))
                    return s.getInverse();
            }
        }
        return new RealScalar("1");
    }

    public ElementryOperation getOp() {
        return op;
    }

    public enum ElementryOperation {
        ROW_SWITCH, ROW_MUL, ROW_ADDITION
    }
}
