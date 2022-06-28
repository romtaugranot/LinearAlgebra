package VectorSpaces;

import ComplexAnalysis.FpScalar;

/**
 * A class representing a vector.
 * @implNote size is constant and arraylist vector is constant.
 */
public class FpVector extends Vector<FpScalar> {

    public FpVector(FpScalar[] vector) {
        super(vector);
    }

    @Override
    public Vector<FpScalar> add(Vector<FpScalar> other) {
        FpScalar[] newVector = new FpScalar[size];
        for (int i = 0; i < size; i++){
            newVector[i] = (FpScalar) this.vector.get(i).add(other.vector.get(i));
        }
        return new FpVector(newVector);
    }

    @Override
    public Vector<FpScalar> mulByScalar(FpScalar scalar) {
        FpScalar[] newVector = new FpScalar[size];
        for (int i = 0; i < size; i++){
            newVector[i] = (FpScalar) this.vector.get(i).mul(scalar);
        }
        return new FpVector(newVector);
    }
}
