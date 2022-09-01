package com.LinearAlgebra.Matrices.Algorithms;

import com.LinearAlgebra.Matrices.ComplexMatrix;
import com.LinearAlgebra.Matrices.SquareMatrices.ElementryMatrix;

import java.util.List;

public interface MemorizedRowEchelon {

    ComplexMatrix rowEchelon();

    List<ElementryMatrix> getOperationsOfRowEchelon();

}
