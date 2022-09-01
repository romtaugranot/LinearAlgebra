package com.LinearAlgebra.Matrices;

import com.LinearAlgebra.Rings.Fields.ComplexField.ComplexScalar;
import com.LinearAlgebra.Matrices.VectorSets.MyComplexVector;
import com.LinearAlgebra.Matrices.VectorSets.MyVectorSet;
import com.LinearAlgebra.Matrices.VectorSets.ComplexVector;
import com.LinearAlgebra.Matrices.VectorSets.VectorSet;
import com.LinearAlgebra.Matrices.VectorSets.VectorSpaces.VectorSpace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MyComplexMatrix implements ComplexMatrix {

    protected final int m, n;

    private final List<ComplexVector> rowVectors;

    /**
     * @pre: for all 0 <= i,j <= rowVectors.size(): rowVectors.get(i).size() == rowVectors.get(j).size()
     * @param rowVectors
     */
    public MyComplexMatrix(List<ComplexVector> rowVectors) {
        this.m = rowVectors.size();
        this.n = rowVectors.get(0).size();
        this.rowVectors = new ArrayList<>(rowVectors);
    }

    /**
     * @pre: for all 0 <= i,j <= rowVectors.length: rowVectors[i].size() == rowVectors[j].size()
     * @param rowVectors
     */
    public MyComplexMatrix(MyComplexVector... rowVectors) {
        this.m = rowVectors.length;
        this.n = rowVectors[0].size;
        this.rowVectors = new ArrayList<>(Arrays.asList(rowVectors));
    }

    /**
     * @pre: for all 0 <= i,j <= matrix.length: matrix[i].length == matrix[j].length
     * @param matrix
     */
    public MyComplexMatrix(ComplexScalar[][] matrix) {
        this.m = matrix.length;
        this.n = matrix[0].length;
        this.rowVectors = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            rowVectors.add(new MyComplexVector(matrix[i]));
        }
    }

    @Override
    public MyComplexMatrix add(ComplexMatrix other) {
        ComplexScalar[][] thisMat = getMatrix();
        ComplexScalar[][] otherMat = other.getMatrix();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                thisMat[i][j] = thisMat[i][j].add(otherMat[i][j]);
            }
        }
        return new MyComplexMatrix(thisMat);
    }

    @Override
    public ComplexMatrix mul(ComplexMatrix other) {
        ComplexScalar[][] matrix = new ComplexScalar[m][other.n()];
        for (int i = 0; i < this.m; i++) {
            for (int j = 0; j < other.n(); j++) {
                matrix[i][j] = rowVectors.get(i).dotProduct(other.colVectors().get(j));
            }
        }
        return new MyComplexMatrix(matrix);
    }

    @Override
    public ComplexMatrix minus() {
        ComplexScalar[][] matrix = getMatrix();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = matrix[i][j].minus();
            }
        }
        return new MyComplexMatrix(matrix);
    }

    @Override
    public ComplexMatrix mulByScalar(ComplexScalar alpha) {
        ComplexScalar[][] thisMat = getMatrix();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                thisMat[i][j] = thisMat[i][j].mul(alpha);
            }
        }
        return new MyComplexMatrix(thisMat);
    }

    @Override
    public ComplexMatrix transpose() {
        return new MyComplexMatrix(this.colVectors());
    }

    @Override
    public ComplexMatrix canonicalRowEchelon() {
        return MatrixMathUtils.canonicalRowEchelon(this);
    }


    @Override
    public VectorSet solve(ComplexVector b) throws ContradictionLineException {
        if (this.getRank() == this.n) {
            ComplexVector v = MatrixMathUtils.solveNoFreeVar(this, b);
            return new MyVectorSet(v);
        }
        return MatrixMathUtils.solve(this, b);
    }

    @Override
    public VectorSpace getNullSpace() {
        try {
            return MatrixMathUtils.solveNullSpace(this);
        } catch (ContradictionLineException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ComplexVector mulByVector(ComplexVector v) {
        return new MyComplexVector(rowVectors.stream().map(x -> x.dotProduct(v)).collect(Collectors.toList()));
    }

    @Override
    public int getRank() {
        ComplexMatrix canonical = canonicalRowEchelon().transpose().canonicalRowEchelon();
        ComplexScalar[][] matrix = canonical.getMatrix();
        int count = 0;
        for (int i = 0; i < matrix.length && i < matrix[0].length; i++) {
            if (!matrix[i][i].equals(ComplexScalar.ZERO)) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    @Override
    public List<ComplexVector> rowVectors() {
        return new ArrayList<>(rowVectors);
    }

    @Override
    public List<ComplexVector> colVectors() {
        ArrayList<ComplexVector> colVectors = new ArrayList<>();
        for (int j = 0; j < n; j++) {
            ComplexScalar[] entries = new ComplexScalar[m];
            for (int i = 0; i < m; i++) {
                entries[i] = (ComplexScalar) rowVectors.get(i).entries().get(j);
            }
            colVectors.add(new MyComplexVector(entries));
        }
        return colVectors;
    }

    @Override
    public ComplexScalar[][] getMatrix() {
        ComplexScalar[][] matrix = new ComplexScalar[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = rowVectors.get(i).entries().get(j);
            }
        }
        return matrix;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++)
            sb.append(Arrays.toString(getMatrix()[i])).append("\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof MyComplexMatrix matrix)) return false;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (!getMatrix()[i][j].equals(matrix.getMatrix()[i][j]))
                    return false;
            }
        }
        return true;
    }

    @Override
    public int m() {
        return m;
    }

    @Override
    public int n() {
        return n;
    }
}
