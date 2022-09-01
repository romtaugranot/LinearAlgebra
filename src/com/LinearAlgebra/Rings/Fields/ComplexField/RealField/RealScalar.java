package com.LinearAlgebra.Rings.Fields.ComplexField.RealField;

import com.LinearAlgebra.Rings.Fields.ComplexField.ComplexScalar;

public interface RealScalar extends ComplexScalar {

    String getReal();

    default String getImaginary(){
        return "0";
    }

    default boolean isReal(){
        return true;
    }

    default boolean isImaginary(){
        return false;
    }

    @Override
    RealScalar minus();

    @Override
    RealScalar inverse();


}