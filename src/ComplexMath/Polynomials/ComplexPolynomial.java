package ComplexMath.Polynomials;

import ComplexMath.FieldScalars.Scalar;
import VectorSpaces.ComplexVector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComplexPolynomial implements Polynomial{

    private ComplexVector co;

    public ComplexPolynomial(Scalar... co){
        this.co = new ComplexVector(co);
    }

    public ComplexPolynomial(ComplexPolynomial p){
        this.co = new ComplexVector(p.getCoefficients());
    }

    public ComplexPolynomial(List<Scalar> co){
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
        List<Scalar> coefficients = new ArrayList<>(List.copyOf(l1));
        if (l1.size() >= l2.size()){
            for (int i = 0 ; i < l2.size(); i++){
                coefficients.set(i, l1.get(i).add(l2.get(i)));
            }
        } else {
            other.add(this);
        }
        return new ComplexPolynomial(coefficients);
    }

    @Override
    public Polynomial mul(Polynomial other) {
        return null;
    }

    @Override
    public Polynomial mulByScalar(Scalar other) {
        return null;
    }
}
