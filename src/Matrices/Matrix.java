package Matrices;

import ComplexAnalysis.Scalar;

import java.util.ArrayList;

/**
 * Class representing a matrix.
 * @inv: rowSize
 */
public class Matrix<S extends Scalar> {

    public final int rowSize;
    public final int colSize;

    private ArrayList<S> rows;

    public Matrix(int rowSize, int colSize, S[][] matrix){
        this.rowSize = rowSize;
        this.colSize = colSize;
        rows = new ArrayList<>();

    }

    public int getRowSize() {
        return rowSize;
    }

    public int getColSize() {
        return colSize;
    }



}
