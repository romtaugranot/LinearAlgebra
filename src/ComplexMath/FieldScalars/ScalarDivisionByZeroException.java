package ComplexMath.FieldScalars;

public class ScalarDivisionByZeroException extends ArithmeticException {

    public ScalarDivisionByZeroException() {
        super("Division by a zero scalar.");
    }

}
