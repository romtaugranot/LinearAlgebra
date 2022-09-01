package com.LinearAlgebra.Rings.Fields.ModulesField;

import com.LinearAlgebra.Rings.DivisionByZeroException;
import com.LinearAlgebra.Rings.Fields.DifferentFieldException;
import com.LinearAlgebra.Rings.RingScalar;

public class MyModulesScalar implements ModulesScalar{

    private int p,val;

    /**
     * @pre: p is a prime number.
     * @param p
     * @param val
     */
    public MyModulesScalar(int p, int val){
        this.p = p;
        this.val = val;
    }

    @Override
    public ModulesScalar add(RingScalar other) throws DifferentFieldException, NotSamePrimeException {
        if (!(other instanceof ModulesScalar s)) throw new NotModulesScalarException();
        if (s.p() != p) throw new NotSamePrimeException();
        return new MyModulesScalar(p, (val + s.val()) % p);
    }

    @Override
    public ModulesScalar mul(RingScalar other) throws DifferentFieldException, NotSamePrimeException {
        if (!(other instanceof ModulesScalar s)) throw new NotModulesScalarException();
        if (s.p() != p) throw new NotSamePrimeException();
        return new MyModulesScalar(p, (val * s.val()) % p);
    }

    @Override
    public ModulesScalar minus() {
        return new MyModulesScalar(p, p - val);
    }

    @Override
    public ModulesScalar inverse() throws DivisionByZeroException {
        if (val == p || val == 0) throw new DivisionByZeroException();
        return new MyModulesScalar(p, (int) Math.pow(val,p-2));
    }

    @Override
    public ModulesScalar pow(int n) {
        ModulesScalar s = new MyModulesScalar(p,1);
        for (int i = 0; i < n; i++){
            s = this.mul(s);
        }
        return s;
    }

    @Override
    public int p() {
        return p;
    }

    @Override
    public int val() {
        return val;
    }
}
