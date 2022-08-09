package com.LinearAlgebra.ComplexMath.Rings.Fields;

import com.LinearAlgebra.ComplexMath.Rings.DifferentRingException;

public class DifferentFieldException extends DifferentRingException {

    public DifferentFieldException(){
        super("Not same type of field.");
    }

    public DifferentFieldException(String msg){
        super(msg);
    }

}
