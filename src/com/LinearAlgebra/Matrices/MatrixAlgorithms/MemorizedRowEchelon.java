package com.LinearAlgebra.Matrices.MatrixAlgorithms;

import com.LinearAlgebra.Matrices.Matrix;
import com.LinearAlgebra.Matrices.SquareMatrices.ElementryMatrix;

import java.util.List;

public interface MemorizedRowEchelon {

    Matrix getMatrix();

    Matrix rowEchelon();

    List<ElementryMatrix> getOperationsOfRowEchelon();

}
