package Matrices;

/**
 * getRank() == getSize()
 */
public interface InvertibleMatrix extends SquareMatrix{

    default int getRank(){
        return this.getSize();
    }

    default Vector getNullSpace(){
        return Vector.getZeroVector(getN());
    }

    Matrix getInvertible();

}
