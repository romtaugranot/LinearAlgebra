package com.LinearAlgebra.Matrices.Algorithms;

import com.LinearAlgebra.Matrices.ComplexMatrix;
import com.LinearAlgebra.Matrices.SquareMatrices.SquareMatrix;
import com.LinearAlgebra.Matrices.VectorSets.ComplexVector;
import com.LinearAlgebra.Matrices.VectorSets.VectorSpaces.MyVectorSpace;
import com.LinearAlgebra.Matrices.VectorSets.VectorSpaces.VectorSpace;
import com.LinearAlgebra.Rings.Fields.ComplexField.ComplexScalar;

import java.util.ArrayList;
import java.util.List;

public class MyJordanAlgorithm implements JordanAlgorithm{


    @Override
    public VectorSpace jordan(SquareMatrix matrix) {
        int n = matrix.n();
        VectorSpace base = new MyVectorSpace();
        for (ComplexScalar s : matrix.getEigenValues()){
            SquareMatrix N = new SquareMatrix(matrix.sub((ComplexMatrix.oneMatrix(n).mulByScalar(s))));
            int l = findSmallestPower(N, matrix.countEigenValueRep(s));
            VectorSpace B = getJordanBasisForNilpotentMatrix(N, l);
            base.add(B, n);
        }
        return base;
    }

    private VectorSpace getJordanBasisForNilpotentMatrix(SquareMatrix N, int l) {
        int n = N.n();
        VectorSpace B = new MyVectorSpace();
        for (int i = l; i >= 1; i--){
            //System.out.println(i + " " + N.pow(i).getNullSpace());
            VectorSpace Ci = N.pow(i-1).getNullSpace();
            //System.out.println("Ci: " + Ci);

            VectorSpace CiWave = new MyVectorSpace();
            CiWave.add(Ci, n);
            CiWave.addAll(getSharedVectorsWithBAndVi(N.pow(i).getNullSpace(), N.pow(i-1).getNullSpace(), B));
            //System.out.println("CiWave: " + CiWave);

            VectorSpace CiTag = new MyVectorSpace();

            CiTag.add(CiWave, n);
            for (ComplexVector v : N.pow(i).getNullSpace().getBase()){
                CiTag.add(v);
            }
            //System.out.println("CiTag: " + CiTag);

            for (ComplexVector v : CiTag.getBase()){
                if (!CiWave.contains(v)){
                    for (int k = 0; k < i; k++){
                        B.add(N.pow(k).mulByVector(v));
                    }
                }
            }
            //System.out.println("B: " + B);
        }
        return B;
    }

    private ComplexVector[] getSharedVectorsWithBAndVi(VectorSpace Vi, VectorSpace ViMinusOne, VectorSpace B) {
        //System.out.println("Vi: " + Vi + ", Vi-1: " + ViMinusOne + ", B: " + B);
        List<ComplexVector> lst = new ArrayList<>();
        for (ComplexVector v : Vi.getBase()){
            //System.out.print(v);
            if (!ViMinusOne.contains(v) && B.contains(v)) {
                //System.out.println("success");
                lst.add(v);
            }
            //else System.out.println();
        }
        return lst.toArray(new ComplexVector[0]);
    }

    private int findSmallestPower(SquareMatrix N, int dim) {
        int k = 1;
        while (N.pow(k).getNullSpace().getDim() != dim) k++;
        return k;
    }


}
