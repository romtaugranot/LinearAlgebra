import ComplexMath.ComplexNumber;
import ComplexMath.RealScalar;
import ComplexMath.Scalar;

public class Main {

    public static void main(String[] args) {
        //checkComplexScalar();
        checkRealComplex();

    }

    private static void checkRealComplex() {
        Scalar a = new RealScalar(1);
        Scalar b = new RealScalar(5);
        System.out.println(a.equal(Scalar.getZero()));
        System.out.println(a.add(b));
        System.out.println(a.mul(b));
        System.out.println(a.sub(b));
        System.out.println(a.div(b));
        System.out.println(a.getInverse());
    }

    private static void checkComplexScalar() {
        Scalar a = new ComplexNumber(1,1);
        Scalar b = new ComplexNumber(1,1);
        System.out.println(a.equal(Scalar.getZero()));
        System.out.println(a.add(b));
        System.out.println(a.mul(b));
        System.out.println(a.sub(b));
        System.out.println(a.div(b));
        System.out.println(a.getInverse());
    }

}
