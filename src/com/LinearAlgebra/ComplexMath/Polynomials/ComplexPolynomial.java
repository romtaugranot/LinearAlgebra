package com.LinearAlgebra.ComplexMath.Polynomials;

import com.LinearAlgebra.ComplexMath.Scalars.BigRational;
import com.LinearAlgebra.ComplexMath.Scalars.ComplexScalar;
import com.LinearAlgebra.ComplexMath.Scalars.RealScalar;
import com.LinearAlgebra.ComplexMath.Scalars.Scalar;

import java.util.ArrayList;
import java.util.List;

public class ComplexPolynomial implements Polynomial {

    private final List<ComplexScalar> entries;

    public ComplexPolynomial(ComplexScalar... entries) {
        this.entries = new ArrayList<>(List.of(entries));
    }

    public ComplexPolynomial(List<ComplexScalar> entries) {
        this.entries = new ArrayList<>(entries);
    }

    @Override
    public Polynomial add(Polynomial other) {
        if (other.getDegree() < getDegree())
            return other.add(this);
        // now getDegree() <= other.getDegree()
        List<ComplexScalar> newEntries = new ArrayList<>();
        for (int i = 0; i <= other.getDegree(); i++) {
            if (i <= getDegree())
                newEntries.add((ComplexScalar) entries.get(i).add(other.getEntries().get(i)));
            else
                newEntries.add(other.getEntries().get(i));
        }
        return new ComplexPolynomial(newEntries);
    }

    @Override
    public Polynomial mul(Polynomial other) {
        List<ComplexScalar> newEntries = new ArrayList<>();
        for (int i = 0; i <= getDegree() + other.getDegree(); i++) {
            newEntries.add(new RealScalar(BigRational.ZERO));
        }
        for (int i = 0; i <= getDegree(); i++) {
            for (int j = 0; j <= other.getDegree(); j++) {
                newEntries.set(i + j, (ComplexScalar) newEntries.get(i + j).add(entries.get(i).mul(other.getEntries().get(j))));
            }
        }
        return new ComplexPolynomial(newEntries);
    }

    @Override
    public Polynomial mulByScalar(ComplexScalar alpha) {
        return new ComplexPolynomial(entries.stream().map(x -> (ComplexScalar) x.mul(alpha)).toList());
    }

    @Override
    public ComplexScalar calculate(Scalar x) {
        ComplexScalar s = ComplexScalar.getZero();
        for (int i = 0; i < entries.size(); i++) {
            s = (ComplexScalar) s.add(entries.get(i).mul(x.pow(i)));
        }
        return s;
    }

    @Override
    public Polynomial getMinus() {
        return new ComplexPolynomial(entries.stream().map(x -> (ComplexScalar) x.getMinus()).toList());
    }

    @Override
    public Polynomial pow(int n) {
        Polynomial p = Polynomial.getOne();
        for (int i = 0; i < n; i++) {
            p = p.mul(this);
        }
        return p;
    }

    @Override
    public int getDegree() {
        for (int i = entries.size() - 1; i >= 0; i--)
            if (!entries.get(i).equals(ComplexScalar.getZero()))
                return i;
        return 0;
    }

    @Override
    public Polynomial gcd(Polynomial other) {
        if (other.getDegree() > getDegree())
            return other.gcd(this);
        if (other.equals(Polynomial.getZero())) return this;
        return Polynomial.euclideanAlgorithm(this, other)[1].gcd(other);
    }

    @Override
    public Polynomial differentiate() {
        if (getDegree() <= 1) return Polynomial.getZero();
        List<ComplexScalar> co = new ArrayList<>();
        for (int i = 1; i <= getDegree(); i++){
            co.add((ComplexScalar) new RealScalar(i +  "").mul(entries.get(i)));
        }
        return new ComplexPolynomial(co);
    }

    @Override
    public List<ComplexScalar> getEntries() {
        return new ArrayList<>(entries);
    }

    @Override
    public String toString() {
        if (entries.isEmpty()) return "0";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).equals(ComplexScalar.getOne())) {
                if (i == 1) sb.append("x");
                else sb.append("x^").append(i);
            } else if (!entries.get(i).equals(ComplexScalar.getZero())) {
                if (i == 0) sb.append(entries.get(i));
                else if (i == 1) sb.append(entries.get(i)).append("x");
                else sb.append(entries.get(i)).append("x^").append(i);
            }
            if (!sb.isEmpty() && i != entries.size() - 1 && !(entries.get(i + 1).isReal() && entries.get(i + 1).getRationalReal().compareTo(BigRational.ZERO) < 0))
                sb.append("+");
        }
        if (sb.isEmpty()) return "0";
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ComplexScalar) && !(obj instanceof Polynomial)) return false;
        if (obj instanceof ComplexScalar s)
            return s.equals(this);
        Polynomial p = (Polynomial) obj;
        if (p.getDegree() != getDegree()) return false;
        for (int i = 0; i <= getDegree(); i++)
            if (!entries.get(i).equals(p.getEntries().get(i)))
                return false;
        return true;
    }
}
