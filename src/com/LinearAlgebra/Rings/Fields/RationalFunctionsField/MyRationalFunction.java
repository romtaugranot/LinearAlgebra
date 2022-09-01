package com.LinearAlgebra.Rings.Fields.RationalFunctionsField;

import com.LinearAlgebra.Rings.DivisionByZeroException;
import com.LinearAlgebra.Rings.Fields.ComplexField.ComplexScalar;
import com.LinearAlgebra.Rings.Fields.DifferentFieldException;
import com.LinearAlgebra.Rings.Polynomials.ComplexPolynomial;
import com.LinearAlgebra.Rings.Polynomials.Polynomial;
import com.LinearAlgebra.Rings.RingScalar;

import java.util.Objects;

public class MyRationalFunction implements RationalFunction{

    private Polynomial num, den;

    public MyRationalFunction(Polynomial num, Polynomial den) throws DivisionByZeroException{
        if (den.equals(Polynomial.ZERO)) throw new DivisionByZeroException();
        if (num.divides(den)){
            this.num = Polynomial.euclideanAlgorithm(num, den)[0];
            this.den = Polynomial.ONE;
        } else {
            this.num = num;
            this.den = den;
        }
    }

    public MyRationalFunction(Polynomial p){
        this.num = p;
        this.den = Polynomial.ONE;
    }

    public MyRationalFunction(ComplexScalar scalar){
        this(new ComplexPolynomial(scalar));
    }

    @Override
    public RationalFunction inverse() throws DivisionByZeroException {
        return new MyRationalFunction(den, num);
    }

    @Override
    public RationalFunction add(RingScalar other) throws DifferentFieldException {
        if (!(other instanceof RationalFunction f))
            throw new NotRationalFunctionScalarException();
        return new MyRationalFunction(this.num.mul(f.getDenominator()).add(this.den.mul(f.getNumerator())), this.den.mul(f.getDenominator()));
    }

    @Override
    public RationalFunction mul(RingScalar other) throws DifferentFieldException {
        if (!(other instanceof RationalFunction f))
            throw new NotRationalFunctionScalarException();
        return new MyRationalFunction(this.num.mul(f.getNumerator()), this.den.mul(f.getDenominator()));
    }

    @Override
    public RationalFunction minus() {
        return new MyRationalFunction(num.minus(), den);
    }

    @Override
    public ComplexScalar calculate(ComplexScalar x) throws DivisionByZeroException{
        ComplexScalar num = this.num.calculate(x);
        ComplexScalar den = this.den.calculate(x);
        if (den.equals(ComplexScalar.ZERO)) throw new DivisionByZeroException();
        return num.div(den);
    }

    @Override
    public Polynomial getNumerator() {
        return new ComplexPolynomial(num.entries());
    }

    @Override
    public Polynomial getDenominator() {
        return new ComplexPolynomial(den.entries());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RationalFunction o)) return false;
        return num.equals(o.getNumerator()) && den.equals(o.getDenominator());
    }

    @Override
    public String toString() {
        return "(" + num + ") / (" + den + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, den);
    }
}
