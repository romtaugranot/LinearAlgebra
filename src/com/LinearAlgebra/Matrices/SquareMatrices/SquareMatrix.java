package com.LinearAlgebra.Matrices.SquareMatrices;

import com.LinearAlgebra.Matrices.Algorithms.MyRationalRootAlgorithm;
import com.LinearAlgebra.Rings.Polynomials.ComplexPolynomial;
import com.LinearAlgebra.Rings.Polynomials.Polynomial;
import com.LinearAlgebra.Rings.Fields.ComplexField.ComplexScalar;
import com.LinearAlgebra.Matrices.MyComplexMatrix;
import com.LinearAlgebra.Matrices.ComplexMatrix;

import java.util.*;

/**
 * @inv: getM() == getN()
 */
public class SquareMatrix extends MyComplexMatrix {

    /**
     * @param matrix
     * @pre: matrix.getN() == matrix.getM()
     */
    public SquareMatrix(ComplexMatrix matrix) {
        super(matrix.getMatrix());
    }

    public ComplexScalar getTrace() {
        ComplexScalar[][] matrix = getMatrix();
        ComplexScalar trace = ComplexScalar.ZERO;
        for (int i = 0; i < m(); i++)
            trace = trace.add(matrix[i][i]);
        return trace;
    }

    public ComplexScalar getDeterminant() {
        ComplexMatrix canonical = canonicalRowEchelon();
        if (!canonical.equals(ComplexMatrix.oneMatrix(n)))
            return ComplexScalar.ZERO;
        return (new NonSingularMatrix(this)).getDeterminant();
    }

    public SquareMatrix pow(int n){
        ComplexMatrix mat = ComplexMatrix.oneMatrix(m);
        for (int i = 0; i < n; i++){
            mat = mat.mul(this);
        }
        return new SquareMatrix(mat);
    }

    public Polynomial getCharacteristicPolynomial(){
        ComplexScalar[][] matrix = getMatrix();
        Polynomial[][] m = new Polynomial[n][n];
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                if (i == j)
                    m[i][j] = new ComplexPolynomial(matrix[i][j].minus(), ComplexScalar.ONE);
                else m[i][j] = new ComplexPolynomial(matrix[i][j].minus());
            }
        }
        return calculateDeterminantWithMinors(m);
    }

    private static final int ROW_OF_DET_DEV = 0;

    private Polynomial calculateDeterminantWithMinors(Polynomial[][] m) {
        if (m.length == 1) return m[0][0];
        if (m.length == 2) return m[0][0].mul(m[1][1]).sub(m[0][1].mul(m[1][0]));

        Polynomial p = Polynomial.ZERO;

        // developing the determinant with first row.
        for (int i = 0; i < m.length; i++){
            Polynomial[][] minor = getMinor(m, ROW_OF_DET_DEV, i);
            Polynomial f = m[ROW_OF_DET_DEV][i].mul(calculateDeterminantWithMinors(minor));
            if (i % 2 == 0) p = p.add(f);
            else p = p.sub(f);
        }
        return p;
    }

    private Polynomial[][] getMinor(Polynomial[][] m, int indexRow, int indexCol) {
        Polynomial[][] minor = new Polynomial[m.length - 1][m.length - 1];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                if (i < indexRow) {
                    if (j < indexCol)
                        minor[i][j] = m[i][j];
                    else if (j > indexCol)
                        minor[i][j - 1] = m[i][j];
                } else if (i > indexRow) {
                    if (j < indexCol)
                        minor[i - 1][j] = m[i][j];
                    else if (j > indexCol)
                        minor[i - 1][j - 1] = m[i][j];
                }
            }
        }
        return minor;
    }


    /**
     * @pre: getCharacteristicPolynomial().isReal()
     * @return *Rational* eigen values of matrix.
     */
    public ComplexScalar[] getEigenValues() {
        return (new MyRationalRootAlgorithm()).getRationalRoots(getCharacteristicPolynomial());
    }


    protected ComplexScalar[] getRationalEigenValuesWithRep() {
        List<ComplexScalar> eigenValues = new ArrayList<>();
        Polynomial p = getCharacteristicPolynomial();
        for (ComplexScalar s : getEigenValues()){
            Polynomial f = new ComplexPolynomial(s.minus(), ComplexScalar.ONE);
            while (p.divides(f) && !p.equals(Polynomial.ONE)) {
                p = Polynomial.euclideanAlgorithm(p,f)[0];
                eigenValues.add(s);
            }
        }
        return eigenValues.toArray(new ComplexScalar[0]);
    }

    public boolean isJordanable(){
        return getCharacteristicPolynomial().isReal() && getRationalEigenValuesWithRep().length == n;
    }

    public int countEigenValueRep(ComplexScalar s) {
        int count = 0;
        for (ComplexScalar alpha : getRationalEigenValuesWithRep()){
            if (alpha.equals(s))
                count++;
        }
        return count;
    }
}
