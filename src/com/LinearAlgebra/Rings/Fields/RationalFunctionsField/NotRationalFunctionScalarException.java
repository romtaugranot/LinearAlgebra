package com.LinearAlgebra.Rings.Fields.RationalFunctionsField;

import com.LinearAlgebra.Rings.Fields.DifferentFieldException;

public class NotRationalFunctionScalarException extends DifferentFieldException {

    public NotRationalFunctionScalarException(){
        super("Not a rational function scalar.");
    }

}
