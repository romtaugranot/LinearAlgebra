package com.LinearAlgebra.ComplexMath.Polynomials;

import com.LinearAlgebra.ComplexMath.Scalars.BigRational;
import com.LinearAlgebra.ComplexMath.Scalars.ComplexScalar;
import com.LinearAlgebra.ComplexMath.Scalars.RealScalar;
import com.LinearAlgebra.ComplexMath.Scalars.Scalar;

import java.util.ArrayList;
import java.util.List;

public interface Polynomial {

    static Polynomial getOne() {
        return new ComplexPolynomial(new RealScalar(BigRational.ONE));
    }

    static Polynomial getZero() {
        return new ComplexPolynomial(new RealScalar(BigRational.ZERO));
    }

    /**
     * @param f
     * @param g
     * @return {a,b} such that f = a * g + b && b.getDegree() < g.getDegree()
     */
    static Polynomial[] euclideanAlgorithm(Polynomial f, Polynomial g) {
        if (g.getDegree() > f.getDegree()) return new Polynomial[]{f, Polynomial.getZero()};
        // now g.getDegree() <= f.getDegree()
        Polynomial a = Polynomial.getZero();
        Polynomial b;
        while (g.getDegree() <= f.getDegree()) {
            int pow = f.getDegree() - g.getDegree();
            Polynomial monom = getMonom(pow);
            monom = monom.mulByScalar((ComplexScalar) f.getEntries().get((f.getDegree()))
                    .div(g.getEntries().get(g.getDegree())));
            a = a.add(monom);
            f = f.sub(g.mul(monom));
        }
        b = f;
        return new Polynomial[]{a, b};
    }

    static Polynomial getMonom(int pow) {
        if (pow == 0) return getOne();
        List<ComplexScalar> p = new ArrayList<>();
        for (int i = 0; i < pow; i++)
            p.add(ComplexScalar.getZero());
        p.add(ComplexScalar.getOne());
        return new ComplexPolynomial(p);
    }

    Polynomial add(Polynomial other);

    default Polynomial sub(Polynomial other) {
        return this.add(other.getMinus());
    }

    Polynomial mul(Polynomial other);

    Polynomial mulByScalar(ComplexScalar alpha);

    Polynomial getMinus();

    Polynomial pow(int n);

    int getDegree();

    Polynomial gcd(Polynomial other);

    List<ComplexScalar> getEntries();

    ComplexScalar calculate(Scalar x);

    default boolean divides(Polynomial g) {
        return euclideanAlgorithm(this, g)[1].equals(Polynomial.getZero());
    }

    Polynomial differentiate();

    default boolean isReal(){
        for (ComplexScalar s : getEntries())
            if (!s.isReal()) return false;
        return true;
    }
}
