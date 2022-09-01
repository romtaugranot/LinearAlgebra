package com.LinearAlgebra.Rings.Polynomials;

import com.LinearAlgebra.Rings.Fields.ComplexField.ComplexScalar;
import com.LinearAlgebra.Rings.Fields.ComplexField.RealField.RealScalar;

import java.util.ArrayList;
import java.util.List;

public interface Polynomial {

    Polynomial ONE = new ComplexPolynomial(RealScalar.ONE);

    Polynomial ZERO = new ComplexPolynomial(RealScalar.ZERO);


    /**
     * @param f
     * @param g
     * @return {a,b} such that f = a * g + b && b.degree() < g.degree()
     */
    static Polynomial[] euclideanAlgorithm(Polynomial f, Polynomial g) {
        if (g.degree() > f.degree()) return new Polynomial[]{f, Polynomial.ZERO};
        // now g.degree() <= f.degree()
        Polynomial a = Polynomial.ZERO;
        Polynomial b;
        while (g.degree() <= f.degree()) {
            int pow = f.degree() - g.degree();
            Polynomial monom = monom(pow);
            monom = monom.mulByScalar(f.entries().get((f.degree()))
                    .div(g.entries().get(g.degree())));
            a = a.add(monom);
            f = f.sub(g.mul(monom));
        }
        b = f;
        return new Polynomial[]{a, b};
    }

    static Polynomial monom(int pow) {
        if (pow == 0) return ONE;
        List<ComplexScalar> p = new ArrayList<>();
        for (int i = 0; i < pow; i++)
            p.add(ComplexScalar.ZERO);
        p.add(ComplexScalar.ONE);
        return new ComplexPolynomial(p);
    }

    Polynomial add(Polynomial other);

    default Polynomial sub(Polynomial other) {
        return this.add(other.minus());
    }

    Polynomial mul(Polynomial other);

    Polynomial mulByScalar(ComplexScalar alpha);

    Polynomial minus();

    default Polynomial pow(int n) {
        Polynomial p = Polynomial.ONE;
        for (int i = 0; i < n; i++) {
            p = p.mul(this);
        }
        return p;
    }

    int degree();

    Polynomial gcd(Polynomial other);

    List<ComplexScalar> entries();

    ComplexScalar calculate(ComplexScalar x);

    default boolean divides(Polynomial g) {
        return euclideanAlgorithm(this, g)[1].equals(Polynomial.ZERO);
    }

    Polynomial differentiate();

    default boolean isReal(){
        for (ComplexScalar s : entries())
            if (!s.isReal()) return false;
        return true;
    }
}
