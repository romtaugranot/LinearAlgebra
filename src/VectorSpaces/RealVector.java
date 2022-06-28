package VectorSpaces;

import ComplexAnalysis.RealScalar;

/**
 * A class representing a vector.
 * @implNote size is constant and arraylist vector is constant.
 */
public class RealVector extends Vector<RealScalar> {

    public RealVector(RealScalar[] vector) {
        super(vector);
    }

    @Override
    public Vector<RealScalar> add(Vector<RealScalar> other) {
        RealScalar[] newVector = new RealScalar[size];
        for (int i = 0; i < size; i++){
            newVector[i] = (RealScalar) this.vector.get(i).add(other.vector.get(i));
        }
        return new RealVector(newVector);
    }

    @Override
    public Vector<RealScalar> mulByScalar(RealScalar scalar) {
        RealScalar[] newVector = new RealScalar[size];
        for (int i = 0; i < size; i++){
            newVector[i] = (RealScalar) this.vector.get(i).mul(scalar);
        }
        return new RealVector(newVector);
    }
}
