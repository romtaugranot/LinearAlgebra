package com.LinearAlgebra.Rings.Polynomials;

import com.LinearAlgebra.Rings.BigRational;
import com.LinearAlgebra.Rings.Fields.ComplexField.ComplexScalar;
import com.LinearAlgebra.Rings.Fields.ComplexField.RealField.MyRealScalar;
import com.LinearAlgebra.Rings.Fields.ComplexField.RealField.RealScalar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        if (other.degree() < degree())
            return other.add(this);
        // now degree() <= other.degree()
        List<ComplexScalar> newEntries = new ArrayList<>();
        for (int i = 0; i <= other.degree(); i++) {
            if (i <= degree())
                newEntries.add(entries.get(i).add(other.entries().get(i)));
            else
                newEntries.add(other.entries().get(i));
        }
        return new ComplexPolynomial(newEntries);
    }

    @Override
    public Polynomial mul(Polynomial other) {
        List<ComplexScalar> newEntries = new ArrayList<>();
        for (int i = 0; i <= degree() + other.degree(); i++) {
            newEntries.add(RealScalar.ZERO);
        }
        for (int i = 0; i <= degree(); i++) {
            for (int j = 0; j <= other.degree(); j++) {
                newEntries.set(i + j, newEntries.get(i + j).add(entries.get(i).mul(other.entries().get(j))));
            }
        }
        return new ComplexPolynomial(newEntries);
    }

    @Override
    public Polynomial mulByScalar(ComplexScalar alpha) {
        return new ComplexPolynomial(entries.stream().map(x -> x.mul(alpha)).toList());
    }

    @Override
    public ComplexScalar calculate(ComplexScalar x) {
        ComplexScalar s = ComplexScalar.ZERO;
        for (int i = 0; i < entries.size(); i++) {
            s = s.add(entries.get(i).mul(x.pow(i)));
        }
        return s;
    }

    @Override
    public Polynomial minus() {
        return new ComplexPolynomial(entries.stream().map(ComplexScalar::minus).toList());
    }

    @Override
    public int degree() {
        for (int i = entries.size() - 1; i >= 0; i--)
            if (!entries.get(i).equals(ComplexScalar.ZERO))
                return i;
        return 0;
    }

    @Override
    public Polynomial gcd(Polynomial other) {
        if (other.degree() > degree())
            return other.gcd(this);
        if (other.equals(ZERO)) return this;
        return Polynomial.euclideanAlgorithm(this, other)[1].gcd(other);
    }

    @Override
    public Polynomial differentiate() {
        if (degree() <= 1) return ZERO;
        List<ComplexScalar> co = new ArrayList<>();
        for (int i = 1; i <= degree(); i++){
            co.add(new MyRealScalar(i +  "").mul(entries.get(i)));
        }
        return new ComplexPolynomial(co);
    }

    @Override
    public List<ComplexScalar> entries() {
        return new ArrayList<>(entries);
    }

    @Override
    public String toString() {
        if (entries.isEmpty()) return "0";
        if (degree()== 0) return entries.get(0).toString();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).equals(ComplexScalar.ONE)) {
                if (i == 1) sb.append("x");
                else if (i == 0) sb.append("1");
                else sb.append("x^").append(i);
            } else if (!entries.get(i).equals(ComplexScalar.ZERO)) {
                if (i == 0) sb.append(entries.get(i));
                else if (i == 1) sb.append(entries.get(i)).append("x");
                else sb.append(entries.get(i)).append("x^").append(i);
            }
            if (!sb.isEmpty() && sb.charAt(sb.length()-1) != '+' && i != entries.size() - 1 && !(entries.get(i + 1).isReal() && new BigRational(entries.get(i + 1).getReal()).compareTo(BigRational.ZERO) < 0))
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
        if (p.degree() != degree()) return false;
        for (int i = 0; i <= degree(); i++)
            if (!entries.get(i).equals(p.entries().get(i)))
                return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entries);
    }
}
