import ComplexMath.ComplexScalar;
import ComplexMath.RealScalar;
import ComplexMath.Scalar;
import Matrices.*;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        //checkComplexScalar();
        //checkRealComplex();
        //checkComplexVector();
        //checkRealVector();
        //checkComplexMatrix();
        checkRowEchelon();
    }

    private static void checkRowEchelon() {
        RealVector a = new RealVector(1,2,0,4,5);
        RealVector b = new RealVector(0,0,0,0,0);
        RealVector c = new RealVector(1,2,3,4,5);
        Matrix mat1 = new ComplexMatrix(a,b,c);
        System.out.println(mat1.canonicalRowEchelon());
    }

    private static void checkComplexMatrix() {
        RealVector a = new RealVector(1,2,3,4,5);
        RealVector b = new RealVector(1,2,3,4,5);
        RealVector c = new RealVector(1,2,3,4,5);
        Matrix mat1 = new ComplexMatrix(a,b,c);
        System.out.println(mat1);

        System.out.println(mat1.mul(mat1.transpose()));

    }

    private static void checkRealVector() {
        Vector a = new RealVector(1,2,3,4,5);
        Vector b = new ComplexVector(new ComplexScalar(10,9),
                new ComplexScalar(8,7),new ComplexScalar(6,5),
                new ComplexScalar(4,3),new ComplexScalar(2,1));
        System.out.println(a);
        System.out.println(b);
        System.out.println(a.add(b));
        System.out.println(a.dotProduct(b));
        System.out.println(b.dotProduct(a));
        System.out.println(a.mul(new ComplexScalar(1,2)));
    }

    private static void checkComplexVector() {
        Vector a = new ComplexVector(new ComplexScalar(1,2),
                new ComplexScalar(3,4),new ComplexScalar(5,6),
                new ComplexScalar(7,8),new ComplexScalar(9,10));
        Vector b = new ComplexVector(new ComplexScalar(10,9),
                new ComplexScalar(8,7),new ComplexScalar(6,5),
                new ComplexScalar(4,3),new ComplexScalar(2,1));
        System.out.println(a.mul(new RealScalar(2)));
    }

    private static void checkRealComplex() {
        Scalar a = new RealScalar(1);
        Scalar b = new ComplexScalar(5,5);
        System.out.println(a.equal(Scalar.getZero()));
        System.out.println(a.add(b));
        System.out.println(a.mul(b));
        System.out.println(a.sub(b));
        System.out.println(a.div(b));
        System.out.println(a.getInverse());
    }

    private static void checkComplexScalar() {
        Scalar a = new ComplexScalar(1,1);
        Scalar b = new ComplexScalar(1,1);
        System.out.println(a.equal(Scalar.getZero()));
        System.out.println(a.add(b));
        System.out.println(a.mul(b));
        System.out.println(a.sub(b));
        System.out.println(a.div(b));
        System.out.println(a.getInverse());

    }

}
