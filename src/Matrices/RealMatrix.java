package Matrices;

import Vectors.RealVector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RealMatrix {

    public final int m,n;

    private ArrayList<RealVector> rowVectors;

    /**
     * @pre: for all 0 <= i,j <= rowVectors.length-1: rowVectors[i].size = rowVectors[j].size
     * @param rowVectors
     */
    public RealMatrix(RealVector... rowVectors){
        this.n = rowVectors.length;
        this.m = rowVectors[0].size;
        this.rowVectors = new ArrayList<>();
        this.rowVectors.addAll(Arrays.asList(rowVectors));
    }

    /**
     *
     * @return Row vectors list.
     */
    public List<RealVector> getRowVectors() {
        ArrayList<RealVector> rowVec = new ArrayList<>();
        for (int i = 0; i < n; i++){
            rowVec.add(new RealVector(this.rowVectors.get(i).getCoefficients()));
        }
        return rowVec;
    }

    /**
     *
     * @return column vectors list.
     */
    public List<RealVector> getColVectors() {
        ArrayList<RealVector> colVec = new ArrayList<>();
        for (int j = 0; j < m; j++){
            double[] co = new double[n];
            for (int i = 0; i < n; i++){
                co[i] = rowVectors.get(i).getCoefficients()[j];
            }
            colVec.add(new RealVector(co));
        }
        return colVec;
    }

    /**
     *
     * @return 2D array representation of the matrix.
     */
    public double[][] getMatrix(){
        double[][] matrix = new double[n][m];
        for (int i = 0; i < n; i++){
            double[] co = new double[m];
            for (int j = 0; j < m; j++){
                matrix[i][j] = rowVectors.get(i).getCoefficients()[j];
            }
        }
        return matrix;
    }

    @Override
    public String toString() {
        double[][] matrix = getMatrix();
        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < n; i++){
            sb.append("\n[");
            for (int j = 0; j < m-1; j++){
                sb.append(matrix[i][j] + ", ");
            }
            sb.append(matrix[i][m-1] + "]");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof RealMatrix))
            return false;
        RealMatrix other = (RealMatrix) obj;
        if (other.n != this.n || this.m != other.m)
            return false;
        double[][] m1 = getMatrix(), m2 = other.getMatrix();
        for (int i = 0; i < n; i++){
            for (int j = 0; j < m; j++){
                if (m1[i][j] != m2[i][j])
                    return false;
            }
        }
        return true;
    }
}
