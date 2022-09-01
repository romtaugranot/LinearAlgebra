package com.LinearAlgebra.Rings.Fields;

import com.LinearAlgebra.Rings.DifferentRingException;

public class DifferentFieldException extends DifferentRingException {

    public DifferentFieldException(){
        super("Not same type of field.");
    }

    public DifferentFieldException(String msg){
        super(msg);
    }

}
