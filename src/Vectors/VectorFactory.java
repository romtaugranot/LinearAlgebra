package Vectors;

import ComplexAnalysis.ComplexScalar;
import ComplexAnalysis.FpScalar;
import ComplexAnalysis.RealScalar;
import ComplexAnalysis.Scalar;

import java.util.ArrayList;

public class VectorFactory {


    /**
     *
     * @param v
     * @return The complex representation of the vector.
     */
    public static Vector<ComplexScalar> toComplexVector(Vector<? extends Scalar> v){
        ComplexScalar[] co = new ComplexScalar[v.size];
        for (int i = 0; i < v.size; i++){
            co[i] = new ComplexScalar(v.getVector().get(i).getReal(),
                    v.getVector().get(i).getImaginary());
        }
        return new Vector<>(co);
    }

    /**
     *
     * @param v
     * @return The Real vector representation of the vector, which removes the imaginary part.
     */
    public static Vector<RealScalar> toRealVector(Vector<? extends Scalar> v){
        RealScalar[] co = new RealScalar[v.size];
        for (int i = 0; i < v.size; i++){
            co[i] = new RealScalar(v.getVector().get(i).getReal());
        }
        return new Vector<>(co);
    }

    /**
     * @apiNote Converts the real part into an integer type.
     * @param p
     * @param v
     * @return The Fp vector representation of the vector.
     */
    public static Vector<FpScalar> toFpVector(int p, Vector<? extends Scalar> v){
        FpScalar[] co = new FpScalar[v.size];
        for (int i = 0; i < v.size; i++){
            co[i] = new FpScalar(p, (int) v.getVector().get(i).getReal());
        }
        return new Vector<>(co);
    }

    /**
     * @pre: for all 0 <= i != j <= vectors.length-1: vectors[j].size = vectors[i].size
     * @param vectors
     * @return sum of the vectors. if all the coordinates are of real scalars then the returned vector is a RealVector.
     */
    public static Vector<? extends ComplexScalar> add(Vector<? extends ComplexScalar>... vectors){
        ArrayList<Vector<ComplexScalar>> vectorsList = toListOfComplexVectors(vectors);
        int size = vectors[0].size;
        ComplexScalar[] co = new ComplexScalar[size];
        for (int i = 0; i < size; i++){
            co[i] = new ComplexScalar(0,0);
        }

        for (Vector<ComplexScalar> vector : vectorsList){
            for (int i = 0; i < size; i++){
                co[i] = (ComplexScalar) co[i].add(vector.getVector().get(i));
            }
        }

        //check if co are real or complex
        for (int i = 0 ; i < size; i++){
            if (co[i].getImaginary() != 0)
                return new Vector<>(co);
        }
        RealScalar[] co2 = new RealScalar[size];
        for (int i = 0; i < size; i++){
            co2[i] = new RealScalar(co[i].getReal());
        }
        return new Vector<>(co2);

    }

    private static ArrayList<Vector<ComplexScalar>> toListOfComplexVectors(Vector<? extends ComplexScalar>... vectors){
        ArrayList<Vector<ComplexScalar>> vectorsList = new ArrayList<>();
        for (int i = 0; i < vectors.length; i++){
            vectorsList.add(VectorFactory.toComplexVector(vectors[i]));
        }
        return vectorsList;
    }

}
