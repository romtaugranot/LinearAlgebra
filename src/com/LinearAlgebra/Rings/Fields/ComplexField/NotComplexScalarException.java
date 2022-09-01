package com.LinearAlgebra.Rings.Fields.ComplexField;

import com.LinearAlgebra.Rings.Fields.DifferentFieldException;

public class NotComplexScalarException extends DifferentFieldException {

    public NotComplexScalarException(){
        super("Not a complex scalar.");
    }

}
