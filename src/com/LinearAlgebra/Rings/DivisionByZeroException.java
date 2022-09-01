package com.LinearAlgebra.Rings;

public class DivisionByZeroException extends ArithmeticException{

    public DivisionByZeroException(){
        super("/ by zero.");
    }

}
