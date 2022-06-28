import ComplexAnalysis.ComplexScalar;
import ComplexAnalysis.FpScalar;
import ComplexAnalysis.Scalar;

public class Main {

    public static void main(String[] args) {
        Scalar alpha = new FpScalar(2, 5);
        Scalar beta = new FpScalar(2, 4);
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
