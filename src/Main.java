import Matrices.RealMatrix;
import Vectors.RealVector;

public class Main {

    public static void main(String[] args) {
        //checkRealVector();
        checkRealMatrix();
    }

    private static void checkRealMatrix() {
        RealVector v1 = new RealVector(1,2,3,4,5);
        RealVector v2 = new RealVector(5,4,3,2,1);
        RealVector v3 = new RealVector(1,1,1,1,1);
        RealMatrix matrix1 = new RealMatrix(v1,v2,v3,v2);

        RealVector v4 = new RealVector(1,2,3,4,5);
        RealVector v5 = new RealVector(5,4,3,2,1);
        RealVector v6 = new RealVector(1,1,1,1,1);
        RealMatrix matrix2 = new RealMatrix(v4,v5,v6,v5);
        System.out.println(matrix1);
        System.out.println(matrix2.equals(matrix1));
        System.out.println(matrix1.getRowVectors());
        System.out.println(matrix1.getColVectors());
    }

    private static void checkRealVector() {
        System.out.println(RealVector.getZeroVector(5));
        double[] co1 = new double[5];
        double[] co2 = new double[5];
        for (int i = 0; i < 5; i++){
            co1[i] = i + 1;
            co2[i] = 5 - i;
        }
        RealVector v1 = new RealVector(co1);
        RealVector v2 = new RealVector(co2);
        System.out.println(v1);
        System.out.println(v2);
        System.out.println(v1.add(v2));
        System.out.println(v1.multiply(3));
        System.out.println(v1.dotProduct(v2));
        System.out.println(RealVector.getZeroVector(5).equals(RealVector.getZeroVector(6)));
    }

}
