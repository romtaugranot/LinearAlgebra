package Matrices;

import ComplexMath.BigRational;
import ComplexMath.Scalar;

import java.util.List;

public interface Vector {

    Vector add(Vector other);

    default Vector sub(Vector other){
        return this.add(other.getMinus());
    }

    Vector getMinus();

    Scalar dotProduct(Vector other);

    Vector mul(Scalar alpha);

    List<Scalar> getEntries();

    int getSize();

    default boolean isZero(){
        List<Scalar> entries = getEntries();
        for (Scalar s : entries){
            if (!s.isZero())
                return false;
        }
        return true;
    }

    default boolean isReal(){
        List<Scalar> entries = getEntries();
        for (Scalar s : entries){
            if (!s.getImaginary().equals(BigRational.ZERO))
                return false;
        }
        return true;
    }

}
