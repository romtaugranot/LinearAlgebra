package Matrices;


/**
 * @inv: getM() == getN()
 */
public interface SquareMatrix extends Matrix{

    default int getSize(){
        return this.getM();
    }

}
