package com.LinearAlgebra.Matrices.SquareMatrices;

import com.LinearAlgebra.ComplexMath.Polynomials.ComplexPolynomial;
import com.LinearAlgebra.ComplexMath.Polynomials.Polynomial;
import com.LinearAlgebra.ComplexMath.Scalars.BigRational;
import com.LinearAlgebra.ComplexMath.Scalars.ComplexScalar;
import com.LinearAlgebra.ComplexMath.Scalars.RealScalar;
import com.LinearAlgebra.ComplexMath.Scalars.Scalar;
import com.LinearAlgebra.Matrices.ComplexMatrix;
import com.LinearAlgebra.Matrices.Matrix;

import java.util.*;

/**
 * @inv: getM() == getN()
 */
public class SquareMatrix extends ComplexMatrix {

    /**
     * @param matrix
     * @pre: matrix.getN() == matrix.getM()
     */
    public SquareMatrix(Matrix matrix) {
        super(matrix.getMatrix());
    }

    public Scalar getTrace() {
        Scalar[][] matrix = getMatrix();
        Scalar trace = Scalar.getZero();
        for (int i = 0; i < getM(); i++)
            trace = trace.add(matrix[i][i]);
        return trace;
    }

    public Scalar getDeterminant() {
        Matrix canonical = canonicalRowEchelon();
        if (!canonical.equals(Matrix.getOneMatrix(n)))
            return Scalar.getZero();
        return (new NonSingularMatrix(this)).getDeterminant();
    }

    public Polynomial getCharacteristicPolynomial(){
        ComplexScalar[][] matrix = (ComplexScalar[][]) getMatrix();
        Polynomial[][] m = new Polynomial[n][n];
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                if (i == j)
                    m[i][j] = new ComplexPolynomial((ComplexScalar) matrix[i][j].getMinus(), ComplexScalar.getOne());
                else m[i][j] = new ComplexPolynomial(matrix[i][j]);
            }
        }
        return calculateDeterminantWithMinors(m);
    }

    private static final int ROW_OF_DET_DEV = 0;

    private Polynomial calculateDeterminantWithMinors(Polynomial[][] m) {
        if (m.length == 1) return m[0][0];
        if (m.length == 2) return m[0][0].mul(m[1][1]).add(m[0][1].mul(m[1][0]));
        Polynomial p = Polynomial.getZero();

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
     * @return Rational roots of getCharacteristicPolynomial().
     */
    public Scalar[] getRationalEigenValues() {
        Polynomial p = getCharacteristicPolynomial();
        p = makeIntegerPolynomial(p);
        System.out.println(p);
        return applyRationalRootTheorem(p);
    }

    /**
     * @pre: p.isReal() && for all 0 <= i <= p.getEntries().size():
     * p.getEntries().get(i).getReal().isInteger()
     * @param p
     * @return Rational roots of p.
     */
    private static Scalar[] applyRationalRootTheorem(Polynomial p) {
        return Arrays.stream(getOptionalRoots(p)).filter(x -> p.calculate(x).isZero()).toList().toArray(new RealScalar[0]);
    }

    private static Scalar[] getOptionalRoots(Polynomial p) {
        int index = getFirstNonZeroIndex(p.getEntries());
        Set<Scalar> optionals = new HashSet<>();
        for (RealScalar r : getIntegerFactors(p.getEntries().get(index))){
            for (RealScalar s : getIntegerFactors(p.getEntries().get(p.getDegree()))){
                optionals.add(r.div(s));
                optionals.add(s.div(r));
            }
        }
        return optionals.toArray(new Scalar[0]);
    }

    /**
     * @pre: scalar.isReal() && scalar.getRationalReal().isInteger()
     * @param scalar
     * @return
     */
    private static RealScalar[] getIntegerFactors(ComplexScalar scalar) {
        List<RealScalar> lst = new ArrayList<>();
        int val = scalar.getRationalReal().getNumerator().intValue();
        for (int i = 1; i <= scalar.getRationalReal().getNumerator().intValue(); i++){
            if (val % i == 0) lst.add(new RealScalar(String.valueOf(i)));
        }
        return lst.toArray(new RealScalar[0]);
    }

    private static int getFirstNonZeroIndex(List<ComplexScalar> lst) {
        for (int i = 0; i < lst.size(); i++)
            if (!lst.get(i).equals(ComplexScalar.getZero())) return i;

        // should never get here.
        return -1;
    }

    private static Polynomial makeIntegerPolynomial(Polynomial p) {
        Polynomial f = Polynomial.getZero();
        for (int i = 0; i <= p.getDegree(); i++){
            BigRational co = p.getEntries().get(i).getRationalReal();
            if (!co.isInteger())
                p = p.mulByScalar(new RealScalar(String.valueOf(co.getDenominator())));
        }
        return p;
    }


}
