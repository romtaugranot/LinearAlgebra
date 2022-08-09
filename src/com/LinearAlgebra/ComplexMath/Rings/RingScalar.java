package com.LinearAlgebra.ComplexMath.Rings;

public interface RingScalar {

    RingScalar add(RingScalar other) throws DifferentRingException;

    RingScalar mul(RingScalar other) throws DifferentRingException;

    default RingScalar sub(RingScalar other) throws DifferentRingException{
        return add(other.minus());
    }

    RingScalar minus();

    RingScalar pow(int n);

}
