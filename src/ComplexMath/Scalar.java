package ComplexMath;

public interface Scalar {
    
    Scalar add(Scalar other);

    default Scalar sub(Scalar other){
        return this.add(other.getMinus());
    }

    Scalar mul(Scalar other);

    default Scalar div(Scalar other) throws ScalarDivisionByZeroException{
        if (other.equal(getZero())) throw new ScalarDivisionByZeroException();
        return this.mul(other.getInverse());
    }

    Scalar getInverse() throws ScalarDivisionByZeroException;

    Scalar getMinus();

    double getReal();

    double getImaginary();

    static Scalar getZero(){
        return new ComplexNumber(0,0);
    }

    default boolean equal(Scalar other){
        return this.getReal() == other.getReal()
                && this.getImaginary() == other.getImaginary();
    }
    
}
