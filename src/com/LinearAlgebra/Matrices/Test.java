//package com.LinearAlgebra.Matrices;
//
//import com.LinearAlgebra.ComplexMath.Scalars.ComplexScalar;
//import com.LinearAlgebra.ComplexMath.Scalars.RealScalar;
//import com.LinearAlgebra.ComplexMath.Scalars.Scalar;
//import com.LinearAlgebra.ComplexMath.Polynomials.ComplexPolynomial;
//import com.LinearAlgebra.ComplexMath.Polynomials.Polynomial;
//import com.LinearAlgebra.Matrices.SquareMatrices.ElementryMatrix;
//import com.LinearAlgebra.Matrices.VectorSets.ComplexVector;
//import com.LinearAlgebra.Matrices.VectorSets.RealVector;
//import com.LinearAlgebra.Matrices.VectorSets.Vector;
//import com.LinearAlgebra.Matrices.VectorSets.VectorSpaces.MyVectorSpace;
//import com.LinearAlgebra.Matrices.VectorSets.VectorSpaces.VectorSpace;
//
//public class Test {
//
//    public static void main(String[] args) {
//        //checkComplexScalar();
//        //checkRealComplex();
//        //checkComplexVector();
//        //checkRealVector();
//        //checkComplexMatrix();
//        //checkRowEchelon();
//        //checkSpaceVectors();
//        //checkPoly();
//        //checkSolve();
//
//        Matrix matrix = new ElementryMatrix(5, 4,new ComplexScalar("1", "1"));
//        System.out.println(matrix);
//    }
//
//    private static void checkSolve() {
//        ComplexVector v1 = new ComplexVector(new RealScalar("1"), new ComplexScalar("3", "1"), new RealScalar("3"), new RealScalar("2"));
//        ComplexVector v2 = new ComplexVector(new RealScalar("2"), new RealScalar("6"), new ComplexScalar("0", "9"), new RealScalar("7"));
//        ComplexVector v3 = new ComplexVector(new RealScalar("-1"), new ComplexScalar("-3", "8"), new RealScalar("3"), new RealScalar("4"));
//
//        Matrix mat1 = new ComplexMatrix(v1, v2, v3);
//
//        //System.out.println(mat1.solve(new RealVector("1", "5", "5")));
//    }
//
//    private static void checkPoly() {
//        Polynomial p1 = new ComplexPolynomial(new RealScalar("1"), new RealScalar("-2"), new RealScalar("0"), new RealScalar("4"));
//        Polynomial p2 = new ComplexPolynomial(new RealScalar("1"), new RealScalar("0"), new RealScalar("0"), new RealScalar("0"), new RealScalar("0"), new RealScalar("0"), new RealScalar("1"));
//
//        System.out.println(p1);
//        System.out.println(p2);
//        System.out.println(p1.add(p2));
//        System.out.println(p1.mul(p2));
//        System.out.println(p1.mulByScalar(new ComplexScalar("1", "2")));
//        System.out.println(p1.mulByScalar(new ComplexScalar("1", "2")).calculate(new ComplexScalar("1", "2")));
//    }
//
//    private static void checkSpaceVectors() {
//        VectorSpace vs = new MyVectorSpace();
//        vs.add(new RealVector("1", "2", "3", "4", "5"));
//        vs.add(new RealVector("2", "4", "6", "8", "10"));
//        System.out.println(vs.getDim());
//        System.out.println(vs.contains(new RealVector("3", "3", "9", "0", "15")));
//    }
//
//    private static void checkRowEchelon() {
////        ComplexVector a = new ComplexVector(new ComplexScalar("0","7"), new RealScalar("5"), new RealScalar("0"), new RealScalar("7"), new RealScalar("1"));
////        ComplexVector b = new ComplexVector(new RealScalar("0"), new RealScalar("3"), new RealScalar("1"), new ComplexScalar("0","9"), new RealScalar("8"));
////        ComplexVector c = new ComplexVector(new RealScalar("0"), new ComplexScalar("0","0"), new RealScalar("0"), new RealScalar("0"), new RealScalar("0"));
////        ComplexVector d = new ComplexVector(new RealScalar("5"), new RealScalar("0"), new ComplexScalar("5", "8"), new RealScalar("2"), new RealScalar("1"));
////        ComplexVector e = new ComplexVector(new RealScalar("3"), new RealScalar("0"), new ComplexScalar("2","5"), new RealScalar("2"), new RealScalar("0"));
////        Matrix mat1 = new ComplexMatrix(a,b,c,d,e);
////        System.out.println(mat1);
////        System.out.println(mat1.rowEchelon());
////        System.out.println(mat1.getRank());
//
//        RealVector v1 = new RealVector("1", "1", "3", "1", "5");
//        RealVector v2 = new RealVector("1", "1", "-1", "10", "3");
//        RealVector v3 = new RealVector("1", "1", "1", "2", "-3");
//        RealVector v4 = new RealVector("2", "2", "8", "1", "18");
//        Matrix mat = new ComplexMatrix(v1, v2, v3, v4);
//        System.out.println(mat.canonicalRowEchelon());
//
//
//    }
//
//    private static void checkComplexMatrix() {
//        RealVector a = new RealVector("1", "2", "3", "4", "5");
//        Matrix mat1 = new ComplexMatrix(a, a, a);
//        System.out.println(mat1);
//
//        System.out.println(mat1.mul(mat1.transpose()));
//
//    }
//
//    private static void checkRealVector() {
//        Vector a = new RealVector("1", "2", "3", "4", "5");
//        Vector b = new ComplexVector(new ComplexScalar("10", "9"),
//                new ComplexScalar("8", "7"), new ComplexScalar("6", "5"),
//                new ComplexScalar("4", "3"), new ComplexScalar("2", "1"));
//        System.out.println(a);
//        System.out.println(b);
//        System.out.println(a.add(b));
//        System.out.println(a.dotProduct(b));
//        System.out.println(b.dotProduct(a));
//        System.out.println(a.mul(new ComplexScalar("1", "2")));
//    }
//
//    private static void checkComplexVector() {
//        Vector a = new ComplexVector(new ComplexScalar("1", "2"),
//                new ComplexScalar("3", "4"), new ComplexScalar("5", "6"),
//                new ComplexScalar("7", "8"), new ComplexScalar("9", "10"));
//        Vector b = new ComplexVector(new ComplexScalar("10", "9"),
//                new ComplexScalar("8", "7"), new ComplexScalar("6", "5"),
//                new ComplexScalar("4", "3"), new ComplexScalar("2", "1"));
//        System.out.println(a.mul(new RealScalar("2")));
//    }
//
//    private static void checkRealComplex() {
//        Scalar a = new RealScalar("1");
//        Scalar b = new ComplexScalar("1", "1");
//        System.out.println(a.equal(Scalar.getZero()));
//        System.out.println(a.add(b));
//        System.out.println(a.mul(b));
//        System.out.println(a.sub(b));
//        System.out.println(a.div(b));
//        System.out.println(a.getInverse());
//    }
//
//    private static void checkComplexScalar() {
//        Scalar a = new ComplexScalar("142", "1/4");
//        Scalar b = new ComplexScalar("1", "1");
//        System.out.println(a.equal(Scalar.getZero()));
//        System.out.println(a.add(b));
//        System.out.println(a.mul(b));
//        System.out.println(a.sub(b));
//        System.out.println(a.div(b));
//        System.out.println(a.getInverse());
//
//    }
//
//}
