package ComplexMath.Polynomials;

import ComplexMath.FieldScalars.BigRational;
import ComplexMath.FieldScalars.RealScalar;
import ComplexMath.FieldScalars.Scalar;
import Matrices.VectorSets.ComplexVector;

import java.util.ArrayList;
import java.util.List;

public class ComplexPolynomial implements Polynomial {

    private final ComplexVector co;

    public ComplexPolynomial(Scalar... co) {
        this.co = new ComplexVector(co);
    }

    public ComplexPolynomial(ComplexPolynomial p) {
        this.co = new ComplexVector(p.getCoefficients());
    }

    public ComplexPolynomial(List<Scalar> co) {
        this.co = new ComplexVector(co);
    }


    @Override
    public int getDegree() {
        return co.size;
    }

    @Override
    public List<Scalar> getCoefficients() {
        return co.getEntries();
    }

    @Override
    public Polynomial add(Polynomial other) {
        List<Scalar> l1 = co.getEntries();
        List<Scalar> l2 = other.getCoefficients();
        if (l1.size() >= l2.size()) {
            List<Scalar> coefficients = new ArrayList<>(List.copyOf(l1));
            for (int i = 0; i < l2.size(); i++) {
                coefficients.set(i, l1.get(i).add(l2.get(i)));
            }
            return new ComplexPolynomial(coefficients);
        } else {
            return other.add(this);
        }
    }

    @Override
    public Polynomial mul(Polynomial other) {
        List<Scalar> l1 = co.getEntries();
        List<Scalar> l2 = other.getCoefficients();
        List<Scalar> coefficients = new ArrayList<>(Polynomial.getZeroPolynomial(l1.size() + l2.size()).getCoefficients());
        for (int i = 0; i < l1.size(); i++) {
            for (int j = 0; j < l2.size(); j++) {
                coefficients.set(i + j, coefficients.get(i + j).add(l1.get(i).mul(l2.get(j))));
            }
        }
        return new ComplexPolynomial(coefficients);
    }

    @Override
    public Polynomial mulByScalar(Scalar other) {
        return this.mul(new ComplexPolynomial(other));
    }

    @Override
    public Scalar calculate(Scalar alpha) {
        Scalar ans = Scalar.getZero();
        for (int i = 0; i < co.size; i++) {
            ans = ans.add(co.getEntries().get(i).mul(alpha.pow(i)));
        }
        return ans;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < co.size; i++) {
            Scalar alpha = co.getEntries().get(i);
            if (i == 0 && !alpha.equal(Scalar.getZero()))
                sb.append(alpha);
            else if (i == 1 && !alpha.equal(Scalar.getZero()))
                sb.append(alpha).append("x");
            else if (!alpha.equal(Scalar.getZero()) && !alpha.equal(new RealScalar("1")))
                sb.append(alpha).append("x^").append(i);
            else if (alpha.equal(new RealScalar("1")))
                sb.append("x^").append(i);
            else if (alpha.equal(Scalar.getZero()) && sb.toString().length() != 0)
                sb.replace(sb.lastIndexOf(" + "), sb.lastIndexOf(" + ") + 3, "");
            if (alpha.isReal() && alpha.getReal().compareTo(BigRational.ZERO) < 0 && sb.toString().length() != 0)
                sb.replace(sb.lastIndexOf(" + "), sb.lastIndexOf(" + ") + 3, " ");
            if (i != co.size - 1 && sb.toString().length() != 0)
                sb.append(" + ");
        }
        return sb.toString();
    }
}
