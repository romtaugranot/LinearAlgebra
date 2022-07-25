package Matrices;

import ComplexMath.FieldScalars.BigRational;
import ComplexMath.FieldScalars.ComplexScalar;
import ComplexMath.FieldScalars.Scalar;
import Matrices.VectorSets.*;
import Matrices.VectorSets.VectorSpaces.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComplexMatrix implements Matrix{

    public final int m,n;

    private final ArrayList<ComplexVector> rowVectors;

    public ComplexMatrix(final List<Vector> rowVectors){
        this.m = rowVectors.size();
        this.n = rowVectors.get(0).getSize();
        this.rowVectors = new ArrayList<>();
        for (Vector v : rowVectors){
            List<Scalar> entries = v.getEntries();
            this.rowVectors.add(new ComplexVector(entries));
        }
    }

    public ComplexMatrix(ComplexVector... rowVectors){
        this.m = rowVectors.length;
        this.n = rowVectors[0].size;
        this.rowVectors = new ArrayList<>();
        this.rowVectors.addAll(Arrays.asList(rowVectors));
    }

    public ComplexMatrix(ComplexScalar[][] matrix){
        this.m = matrix.length;
        this.n = matrix[0].length;
        this.rowVectors = new ArrayList<>();
        for (int i = 0; i < m; i++){
            rowVectors.add(new ComplexVector(matrix[i]));
        }
    }

    public ComplexMatrix(ComplexMatrix complexMatrix) {
        this((ComplexScalar[][]) complexMatrix.getMatrix());
    }

    @Override
    public Matrix add(Matrix other) {
        ComplexScalar[][] thisMat = (ComplexScalar[][]) getMatrix();
        Scalar[][] otherMat = other.getMatrix();
        for (int i = 0; i < m; i++){
            for (int j = 0; j < n; j++){
                thisMat[i][j] = (ComplexScalar) thisMat[i][j].add(otherMat[i][j]);
            }
        }
        return new ComplexMatrix(thisMat);
    }

    @Override
    public Matrix mul(Matrix other) {
        ComplexScalar[][] matrix = new ComplexScalar[m][other.getN()];
        for (int i = 0; i < this.m; i++){
            for (int j = 0; j < other.getN(); j++){
                matrix[i][j] = (ComplexScalar) rowVectors.get(i).dotProduct(other.getColVectors().get(j));
            }
        }
        return new ComplexMatrix(matrix);
    }

    @Override
    public Matrix mulByScalar(Scalar alpha) {
        ComplexScalar[][] thisMat = (ComplexScalar[][]) getMatrix();
        for (int i = 0; i < m; i++){
            for (int j = 0; j < n; j++){
                thisMat[i][j] = (ComplexScalar) thisMat[i][j].mul(alpha);
            }
        }
        return new ComplexMatrix(thisMat);
    }

    @Override
    public Matrix transpose() {
        return new ComplexMatrix(this.getColVectors());
    }

    @Override
    public Matrix rowEchelon() {
        return MatrixMathUtils.rowEchelon(this);
    }

    @Override
    public Matrix canonicalRowEchelon() {
        return MatrixMathUtils.canonicalRowEchelon(this);
    }


    @Override
    public VectorSet solve(Vector b) {
        if (this.getRank() == this.n) {
            Vector v = MatrixMathUtils.solveNoFreeVar(this, b);
            return new MyVectorSet(v);
        }
        try {
            return MatrixMathUtils.solve(this, b);
        } catch (ContradictionLineException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public VectorSpace getNullSpace() {
        try {
            return MatrixMathUtils.solveNullSpace(this);
        } catch (ContradictionLineException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Vector mulByVector(Vector v) {
        List<Scalar> entries = new ArrayList<>();
        for (int i = 0; i < m; i++){
            entries.add(rowVectors.get(i).dotProduct(v));
        }
        return new ComplexVector(entries);
    }

    // TODO: 13/07/2022
    @Override
    public BigRational getDeterminant() {
        return null;
    }

    @Override
    public int getRank() {
        Matrix canonical = rowEchelon().transpose().canonicalRowEchelon();
        ComplexScalar[][] matrix = (ComplexScalar[][]) canonical.getMatrix();
        int count = 0;
        for (int i = 0 ; i < matrix.length && i < matrix[0].length; i++){
            if (!matrix[i][i].isZero()) {
                count++;
            } else{
                break;
            }
        }
        return count;
    }

    @Override
    public List<Vector> getRowVectors() {
        return new ArrayList<>(rowVectors);
    }

    @Override
    public List<Vector> getColVectors() {
        ArrayList<Vector> colVectors = new ArrayList<>();
        for (int j = 0; j < n; j++){
            ComplexScalar[] entries = new ComplexScalar[m];
            for (int i = 0; i < m; i++){
                entries[i] = (ComplexScalar) rowVectors.get(i).getEntries().get(j);
            }
            colVectors.add(new ComplexVector(entries));
        }
        return colVectors;
    }

    @Override
    public Scalar[][] getMatrix() {
        ComplexScalar[][] matrix = new ComplexScalar[m][n];
        for (int i = 0; i < m; i++){
            for (int j = 0; j < n; j++){
                matrix[i][j] = (ComplexScalar) rowVectors.get(i).getEntries().get(j);
            }
        }
        return matrix;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < m; i++)
            sb.append(Arrays.toString(getMatrix()[i])).append("\n");
        return sb.toString();
    }

    @Override
    public int getM() {
        return m;
    }

    @Override
    public int getN() {
        return n;
    }
}
