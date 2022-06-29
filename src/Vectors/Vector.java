package Vectors;

import ComplexAnalysis.Scalar;
import java.util.ArrayList;

public class Vector<S extends Scalar> {

    public final int size;

    private ArrayList<S> vector;

    public Vector(S[] co){
        size = co.length;
        vector = new ArrayList<>();
        for (int i =0; i < size; i++){
            vector.add(co[i]);
        }
    }

    public ArrayList<S> getVector() {
        return new ArrayList<>(vector);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0 ; i < size-1; i++){
            sb.append(vector.get(i) + ", ");
        }
        sb.append(vector.get(size-1) + ")");
        return sb.toString();
    }
}
