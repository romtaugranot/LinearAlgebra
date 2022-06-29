import ComplexAnalysis.ComplexScalar;
import ComplexAnalysis.RealScalar;
import ComplexAnalysis.Scalar;
import Vectors.Vector;
import Vectors.VectorFactory;

public class Main {

    public static void main(String[] args) {
        //scalarCheck();
        vectorCheck();
    }

    private static void vectorCheck() {
        ComplexScalar[] co1 = new ComplexScalar[5];
        for (int i = 0; i < 5; i++){
            co1[i] = new ComplexScalar(5,2);
        }
        ComplexScalar[] co2 = new ComplexScalar[5];
        for (int i = 0; i < 5; i++){
            co2[i] = new ComplexScalar(-4,-1);
        }
        Vector<ComplexScalar> v1 = new Vector<>(co1);
        Vector<ComplexScalar> v2 = new Vector<>(co2);
        System.out.println(v1);
        System.out.println(v2);
        //System.out.println(VectorFactory.toFpVector(2, v1));

        Vector<? extends ComplexScalar> v3 = VectorFactory.add(v1,v2);
        System.out.println(v3);
    }

    private static void scalarCheck(){
        Scalar alpha = new ComplexScalar(2, 5);
        Scalar beta = new RealScalar(4);
        System.out.println(alpha.toString());
        System.out.println(beta.toString());

        System.out.println(alpha.add(beta));
        System.out.println(beta.add(alpha));

        System.out.println(alpha.sub(beta));
        System.out.println(beta.sub(alpha));

        System.out.println(alpha.mul(beta));
        System.out.println(beta.mul(alpha));

        System.out.println(alpha.divide(beta));
        System.out.println(beta.divide(alpha));
    }

}
