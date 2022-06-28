package VectorSpaces;

import ComplexAnalysis.ComplexScalar;

/**
 * A class representing a vector.
 * @implNote size is constant and arraylist vector is constant.
 */
public class ComplexVector extends Vector<ComplexScalar> {

    public ComplexVector(ComplexScalar[] vector) {
        super(vector);
    }

    @Override
    public Vector<ComplexScalar> add(Vector<ComplexScalar> other) {
        ComplexScalar[] newVector = new ComplexScalar[size];
        for (int i = 0; i < size; i++){
            newVector[i] = (ComplexScalar) this.vector.get(i).add(other.vector.get(i));
        }
        return new ComplexVector(newVector);
    }

    @Override
    public Vector<ComplexScalar> mulByScalar(ComplexScalar scalar) {
        ComplexScalar[] newVector = new ComplexScalar[size];
        for (int i = 0; i < size; i++){
            newVector[i] = (ComplexScalar) this.vector.get(i).mul(scalar);
        }
        return new ComplexVector(newVector);
    }
}
