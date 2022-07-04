package Vectors;

import java.util.ArrayList;

public class RealVector{

    public final int size;

    private ArrayList<Double> coefficients;

    public RealVector(double... co){
        this.size = co.length;
        coefficients = new ArrayList<>();
        for (int i = 0; i < size; i++)
            coefficients.add(co[i]);
    }

    /**
     * @pre: this.size == other.size
     * @param other
     * @return Sum of the vectors.
     */
    public RealVector add(RealVector other){
        double[] newCo = new double[size];
        for (int i = 0 ; i < size; i++){
            newCo[i] = coefficients.get(i) + other.coefficients.get(i);
        }
        return new RealVector(newCo);
    }

    /**
     *
     * @param scalar
     * @return The stretched vector.
     */
    public RealVector multiply(double scalar){
        double[] newCo = new double[size];
        for (int i = 0 ; i < size; i++){
            newCo[i] = coefficients.get(i) * scalar;
        }
        return new RealVector(newCo);
    }

    /**
     * @pre: this.size == other.size
     * @param other
     * @return Dot product of the vectors.
     */
    public double dotProduct(RealVector other){
        double product = 0;
        for (int i = 0 ; i < size; i++){
            product += coefficients.get(i) * other.coefficients.get(i);
        }
        return product;
    }

    public static RealVector getZeroVector(int size){
        double[] co = new double[size];
        return new RealVector(co);
    }

    public double[] getCoefficients() {
        double[] co = new double[size];
        for (int i = 0 ; i < size; i++){
            co[i] = coefficients.get(i);
        }
        return co;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < size - 1; i++){
            sb.append(coefficients.get(i) + ", ");
        }
        sb.append(coefficients.get(size-1) + ")");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof RealVector))
            return false;
        RealVector other = (RealVector) obj;
        if (other.size != this.size)
            return false;
        for (int i = 0; i < size; i++){
            if (this.coefficients.get(i).doubleValue() != other.coefficients.get(i).doubleValue())
                return false;
        }
        return true;
    }

}
