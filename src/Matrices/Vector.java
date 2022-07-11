package Matrices;

import ComplexMath.Scalar;

import java.util.List;

public interface Vector {

    Vector add(Vector other);

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

}
