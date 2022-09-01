package com.LinearAlgebra.Rings;

public class DifferentRingException extends RuntimeException{

    public DifferentRingException(){
        super("Not same type of ring.");
    }

    public DifferentRingException(String msg){
        super(msg);
    }

}
