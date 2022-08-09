package com.LinearAlgebra.ComplexMath.Rings.Fields.ComplexField;

import com.LinearAlgebra.ComplexMath.Rings.Fields.DifferentFieldException;

public class NotComplexScalarException extends DifferentFieldException {

    public NotComplexScalarException(){
        super("Not a complex scalar.");
    }

}
