package com.LinearAlgebra.Matrices.Algorithms;

import com.LinearAlgebra.Rings.BigRational;
import com.LinearAlgebra.Rings.Fields.ComplexField.ComplexScalar;
import com.LinearAlgebra.Rings.Fields.ComplexField.RealField.MyRealScalar;
import com.LinearAlgebra.Rings.Fields.ComplexField.RealField.RealScalar;
import com.LinearAlgebra.Rings.Polynomials.ComplexPolynomial;
import com.LinearAlgebra.Rings.Polynomials.Polynomial;

import java.util.*;

public class MyRationalRootAlgorithm implements RationalRootAlgorithm{

    @Override
    public ComplexScalar[] getRationalRoots(Polynomial p) {
        p = makeIntegerPolynomial(p);
        return applyRationalRootTheorem(p);
    }

    private static ComplexScalar[] applyRationalRootTheorem(Polynomial p) {
        return Arrays.stream(getOptionalRoots(p)).filter(x -> p.calculate(x).equals(ComplexScalar.ZERO)).toList().toArray(new ComplexScalar[0]);
    }

    private static ComplexScalar[] getOptionalRoots(Polynomial p) {
        int index = getFirstNonZeroIndex(p.entries());
        Set<ComplexScalar> optionals = new HashSet<>();
        for (RealScalar r : getIntegerFactors(p.entries().get(index))){
            for (RealScalar s : getIntegerFactors(p.entries().get(p.degree()))){
                optionals.add(r.div(s));
                optionals.add(r.div(s).minus());
                optionals.add(s.div(r));
                optionals.add(s.div(r).minus());
            }
        }

        Polynomial f = new ComplexPolynomial(RealScalar.ZERO, RealScalar.ONE); // f(x) = x
        if (p.divides(f))
            optionals.add(RealScalar.ZERO);

        return optionals.toArray(new ComplexScalar[0]);
    }

    private static RealScalar[] getIntegerFactors(ComplexScalar scalar) {
        List<RealScalar> lst = new ArrayList<>();
        int val = new BigRational(scalar.getReal()).getNumerator().intValue();
        if (val < 0) val = -val;
        for (int i = 1; i <= val; i++){
            if (val % i == 0) lst.add(new MyRealScalar(String.valueOf(i)));
        }
        return lst.toArray(new RealScalar[0]);
    }

    private static int getFirstNonZeroIndex(List<ComplexScalar> lst) {
        for (int i = 0; i < lst.size(); i++)
            if (!lst.get(i).equals(ComplexScalar.ZERO)) return i;

        // should never get here.
        return -1;
    }

    private static Polynomial makeIntegerPolynomial(Polynomial p) {
        for (int i = 0; i <= p.degree(); i++){
            BigRational co = new BigRational(p.entries().get(i).getReal());
            if (!co.isInteger())
                p = p.mulByScalar(new MyRealScalar(String.valueOf(co.getDenominator())));
        }
        return p;
    }
}
