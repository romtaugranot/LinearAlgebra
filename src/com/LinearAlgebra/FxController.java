package com.LinearAlgebra;

import com.LinearAlgebra.ComplexMath.Scalars.ComplexScalar;
import com.LinearAlgebra.ComplexMath.Scalars.RealScalar;
import com.LinearAlgebra.ComplexMath.Scalars.Scalar;
import com.LinearAlgebra.Matrices.ComplexMatrix;
import com.LinearAlgebra.Matrices.ContradictionLineException;
import com.LinearAlgebra.Matrices.Matrix;
import com.LinearAlgebra.Matrices.SquareMatrices.NonSingularMatrix;
import com.LinearAlgebra.Matrices.SquareMatrices.SquareMatrix;
import com.LinearAlgebra.Matrices.VectorSets.VectorSet;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FxController {

    private static final int MAX_SIZE = 6;
    private static final int INITIAL_SIZE = 3;
    private static final String MATRIX_NOT_SQUARE = "Something went wrong! matrix isn't square.";
    private static final String INCORRECT_MATRIX_SIZE = "Matrices sizes are not legal for this operation.";
    private static final String INCORRECT_MATRIX_INPUT = "Some matrix entry is not a scalar.";
    private static final String INCORRECT_POW_INPUT = "Power can only be a positive integer.";
    private static final String INCORRECT_SCALAR_INPUT = "Entered something that isn't a scalar.";
    private static final String MATRIX_NOT_INVERTIBLE = "Matrix is not invertible.";
    private static final String NO_SOLUTION_TO_EQUATION = "There's no solution to the equation";

    @FXML
    private ListView<Integer> leftMatRowListView, leftMatColListView, rightMatRowListView, rightMatColListView;
    @FXML
    private GridPane leftMatrixGrid, rightMatrixGrid, ansMatrixGrid;
    @FXML
    private Button traceButton, transposeButton, rankButton, mulByScalarButton,
            powButton, determinantButton, inverseButton, canonicalRowEchelonButton, nullSpaceButton
            ;
    @FXML
    private Button addButton, subButton, mulButton, solveEquationButton;
    @FXML
    private TextField mulByScalarTextField, powTextField;
    @FXML
    private ToggleButton isLeftToggleButton;
    @FXML
    private Label answerLabel;
    private boolean isLeft = true;;
    private int leftRowSize = INITIAL_SIZE, leftColSize = INITIAL_SIZE,
            rightRowSize = INITIAL_SIZE, rightColSize = INITIAL_SIZE;
    private Matrix leftMatrix = Matrix.getZeroMatrix(leftRowSize, leftColSize);
    private Matrix rightMatrix = Matrix.getZeroMatrix(rightRowSize, rightColSize);

    public void initialize() {
        // initialize left matrix.
        initMatrixSizeListView(leftMatRowListView);
        initMatrixSizeListView(leftMatColListView);
        initMatrix(leftMatrixGrid, leftRowSize, leftColSize);

        initListViewListeners(leftMatrixGrid, leftMatRowListView,true,  true);
        initListViewListeners(leftMatrixGrid, leftMatColListView,true,  false);

        // initialize right matrix.
        initMatrixSizeListView(rightMatRowListView);
        initMatrixSizeListView(rightMatColListView);
        initMatrix(rightMatrixGrid, leftRowSize, leftColSize);

        initListViewListeners(rightMatrixGrid, rightMatRowListView,false,  true);
        initListViewListeners(rightMatrixGrid, rightMatColListView,false,  false);

        List<Button> buttons = new ArrayList<>(Arrays.asList(
                traceButton, transposeButton, rankButton, mulByScalarButton,
                powButton, determinantButton, inverseButton, canonicalRowEchelonButton, nullSpaceButton
        )) ;
        for (Button b : buttons){
            initSingleMatrixOpButtons(b);
        }

        initOperationOnBothMatrixButton(addButton, OperationOnMatrices.ADD);
        initOperationOnBothMatrixButton(subButton, OperationOnMatrices.SUB);
        initOperationOnBothMatrixButton(mulButton, OperationOnMatrices.MUL);
        initOperationOnBothMatrixButton(solveEquationButton, OperationOnMatrices.SOLVE);

        initIsLeftToggleButton();

    }

    private void initIsLeftToggleButton() {
        isLeftToggleButton.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            isLeft = oldValue;
            if (isLeft)
                isLeftToggleButton.setText("Left");
            else isLeftToggleButton.setText("Right");
        }));
    }

    private boolean updateMatrices(){
        boolean flag1 = updateMatrix();
        isLeft = !isLeft;
        boolean flag2 = updateMatrix();
        isLeft = !isLeft;
        return flag1 && flag2;
    }

    private boolean checkIfSameSize(){
        return leftMatrix.getM() == rightMatrix.getM() && leftMatrix.getN() == rightMatrix.getN();
    }

    private void updateAnsMatrix(Matrix matrix){
        initMatrix(ansMatrixGrid, matrix.getM(), matrix.getN());
        for (int i = 0; i < matrix.getM(); i++) {
            for (int j = 0; j < matrix.getN(); j++) {
                setNodeByRowColumnIndex(matrix.getMatrix()[i][j].toString().replace("(","").replace(")",""), i, j, ansMatrixGrid);
            }
        }
    }

    private void displayError(String text){
        answerLabel.setText(text);
        initMatrix(ansMatrixGrid, 0, 0);
    }

    private Matrix getCurrMatrix(){
        if (isLeft)
            return leftMatrix;
        return rightMatrix;
    }

    private enum OperationOnMatrices{
        ADD, SUB, MUL, SOLVE;
    }

    private void initOperationOnBothMatrixButton(Button button, OperationOnMatrices op) {
        button.setOnAction(e -> {
            if (updateMatrices()) {
                if (op.equals(OperationOnMatrices.ADD) || op.equals(OperationOnMatrices.SUB)) { // check for Add / sub
                    if (!checkIfSameSize())
                        displayError(INCORRECT_MATRIX_SIZE);
                    else updateAnsOfBothMatrices(button);
                } else if (op.equals(OperationOnMatrices.MUL)){
                    if (leftMatrix.getN() != rightMatrix.getM())
                        displayError(INCORRECT_MATRIX_SIZE);
                    else updateAnsOfBothMatrices(button);
                } else if (op.equals(OperationOnMatrices.SOLVE)){
                    if (rightMatrix.getN() != 1 || leftMatrix.getN() != rightMatrix.getM())
                        displayError(INCORRECT_MATRIX_SIZE);
                    else updateAnsOfBothMatrices(button);
                }
            } else {
                displayError(INCORRECT_MATRIX_INPUT);
            }
        });
    }

    private void updateAnsOfBothMatrices(Button button){
        Matrix matrix = null;
        switch (button.getId()) {
            case "addButton" -> matrix = leftMatrix.add(rightMatrix);
            case "subButton" -> matrix = leftMatrix.sub(rightMatrix);
            case "mulButton" -> matrix = leftMatrix.mul(rightMatrix);
            case "solveEquationButton" -> {
                try {
                    VectorSet s = leftMatrix.solve(rightMatrix.getColVectors().get(0));
                    answerLabel.setText(String.valueOf(s));
                } catch (ContradictionLineException e){
                    answerLabel.setText(NO_SOLUTION_TO_EQUATION);
                }

                return;
            }
        }
        updateAnsMatrix(matrix);
    }

    private void initSingleMatrixOpButtons(Button button){
        button.setOnAction(e -> {
            if (updateMatrix()) {
                Matrix matrix = getCurrMatrix();
                String name = button.getId();
                switch (name) {
                    case "traceButton" -> updateTrace(matrix);
                    case "rankButton" -> updateRank(matrix);
                    case "transposeButton" -> updateTranspose(matrix);
                    case "mulByScalarButton" -> updateMulByScalar(matrix);
                    case "powButton" -> updatePow(matrix);
                    case "determinantButton" -> updateDeterminant(matrix);
                    case "inverseButton" -> updateInverse(matrix);
                    case "canonicalRowEchelonButton" -> updateRowEchelon(matrix);
                    case "nullSpaceButton" -> updateNullSpace(matrix);
                }
            } else {
                displayError(INCORRECT_MATRIX_INPUT);
            }

        });
    }

    private void updateNullSpace(Matrix matrix) {
        answerLabel.setText(String.valueOf(matrix.getNullSpace()));
    }

    private void updateRowEchelon(Matrix matrix) {
        updateAnsMatrix(matrix.canonicalRowEchelon());
    }

    private void updateInverse(Matrix matrix) {
        if (matrix.getM() != matrix.getN()) {
            displayError(MATRIX_NOT_SQUARE);
        } else {
            try {
                if (new SquareMatrix(matrix).getDeterminant().equal(Scalar.getZero()))
                    displayError(MATRIX_NOT_INVERTIBLE);
                else updateAnsMatrix(new NonSingularMatrix(matrix).getInvertible());
            } catch (Exception e) {
                displayError(MATRIX_NOT_INVERTIBLE);
            }
        }
    }

    private void updateDeterminant(Matrix matrix) {
        if (matrix.getM() != matrix.getN()) {
            displayError(MATRIX_NOT_SQUARE);
        } else {
            answerLabel.setText("Determinant is " + (new SquareMatrix(matrix)).getDeterminant());
        }
    }

    private void updatePow(Matrix matrix) {
        if (matrix.getM() != matrix.getN()) {
            displayError(MATRIX_NOT_SQUARE);
        } else {
            try {
                String powString = powTextField.getText();
                int pow = 0;
                if (!(powString == null || powString.isEmpty()))
                    pow = Integer.parseInt(powTextField.getText());
                if (pow == 0)
                    updateAnsMatrix(Matrix.getOneMatrix(matrix.getM()));
                else {
                    Matrix powerMatrix = matrix;
                    for (int i = 0; i < pow - 1; i++) {
                        powerMatrix = powerMatrix.mul(matrix);
                    }
                    updateAnsMatrix(powerMatrix);
                }
            } catch (NumberFormatException e){
                displayError(INCORRECT_POW_INPUT);
            }
        }
    }

    private void updateMulByScalar(Matrix matrix) {
        try {
            String scalarString = mulByScalarTextField.getText();
            Scalar s;
            if (scalarString == null || scalarString.isEmpty()){
                s = Scalar.getZero();
            } else {
                s = convertToScalar(scalarString);
            }
            updateAnsMatrix(matrix.mulByScalar(s));
        } catch (IllegalArgumentException e){
            displayError(INCORRECT_SCALAR_INPUT);
        }
    }

    private void updateTrace(Matrix matrix) {
        if (matrix.getM() != matrix.getN()) {
            displayError(MATRIX_NOT_SQUARE);
        } else {
            answerLabel.setText("Trace is " + (new SquareMatrix(matrix)).getTrace());
        }
    }

    private void updateRank(Matrix matrix) {
        answerLabel.setText("Rank is " + matrix.getRank());
    }

    private void updateTranspose(Matrix matrix) {
        updateAnsMatrix(matrix.transpose());
    }

    private void initListViewListeners(GridPane gridPane ,ListView<Integer> listView, boolean isLeft, boolean isRow) {
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            int size = listView.getSelectionModel().getSelectedItem();
            if (isLeft) {
                if (isRow)
                    leftRowSize = size;
                else
                    leftColSize = size;
                initMatrix(gridPane, leftRowSize, leftColSize);
            } else{
                if (isRow)
                    rightRowSize = size;
                else
                    rightColSize = size;
                initMatrix(gridPane, rightRowSize, rightColSize);
            }
        });
    }

    private void initMatrix(GridPane gridPane, int rowNum, int colNum) {
        for (Node node : gridPane.getChildren()){
            node.setVisible(false);
        }
        for (int row = 0; row < rowNum; row++){
            for (int col = 0; col < colNum; col++){
                TextField t = (TextField) getNodeByRowColumnIndex(row, col, gridPane);
                t.setVisible(true);
            }
        }
    }

    /**
     * @return true if succeeded to update matrix.
     */
    private boolean updateMatrix() {
        int rowSize, colSize;
        GridPane grid;
        if (isLeft){
            rowSize = leftRowSize;
            colSize = leftColSize;
            grid = leftMatrixGrid;
        } else {
            rowSize = rightRowSize;
            colSize = rightColSize;
            grid = rightMatrixGrid;
        }
        Scalar[][] updated = new ComplexScalar[rowSize][colSize];
        for (int i = 0; i < rowSize; i++){
            for (int j = 0; j < colSize; j++){
                TextField t = (TextField) getNodeByRowColumnIndex(i, j, grid);
                if (!t.getText().equals("")) {
                    try {
                        updated[i][j] = new ComplexScalar(convertToScalar(t.getText()));
                    } catch (IllegalArgumentException e) {
                        answerLabel.setText(INCORRECT_MATRIX_INPUT);
                        return false;
                    }
                }
                else
                    updated[i][j] = Scalar.getZero();
            }
        }
        if (isLeft)
            leftMatrix = new ComplexMatrix(updated);
        else rightMatrix = new ComplexMatrix(updated);
        return true;
    }

    private void initMatrixSizeListView(ListView<Integer> listView) {
        Integer[] sizes = new Integer[MAX_SIZE];
        for (int i = 0; i < sizes.length; i++) {
            sizes[i] = i + 1;
        }
        listView.getItems().addAll(sizes);
    }

    // for some odd reason when the node's row index is zero / col index is zero, then the get function returns null.
    public static Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            Integer nodeRow = GridPane.getRowIndex(node);
            Integer nodeCol = GridPane.getColumnIndex(node);
            if (nodeCol == null){
                if (nodeRow == null){
                    if (row == 0 && column == 0)
                        return node;
                } else {
                    if (column == 0 && row == nodeRow)
                        return node;
                }
            } else {
                if (nodeRow == null){
                    if (row == 0 && column == nodeCol)
                        return node;
                } else {
                    if (column == nodeCol && row == nodeRow)
                        return node;
                }
            }
        }
        return null;
    }

    public static void setNodeByRowColumnIndex (Object o, final int row, final int column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            Integer nodeRow = GridPane.getRowIndex(node);
            Integer nodeCol = GridPane.getColumnIndex(node);
            if (nodeCol == null){
                if (nodeRow == null){
                    if (row == 0 && column == 0)
                        ((TextField) node).setText(String.valueOf(o));
                } else {
                    if (column == 0 && row == nodeRow)
                        ((TextField) node).setText(String.valueOf(o));
                }
            } else {
                if (nodeRow == null){
                    if (row == 0 && column == nodeCol)
                        ((TextField) node).setText(String.valueOf(o));
                } else {
                    if (column == nodeCol && row == nodeRow)
                        ((TextField) node).setText(String.valueOf(o));
                }
            }
        }
    }

    private static Scalar convertToScalar(String exp) throws IllegalArgumentException{
        if (exp == null || (exp = exp.replaceAll("\\s","")).isEmpty()) return Scalar.getZero();
        if (!exp.contains("i")) return new RealScalar(exp);
        boolean firstPositive = true;
        boolean secondPositive = true;
        if (exp.charAt(0) == '-')     // See if first expr is negative
            firstPositive = false;
        if (exp.substring(1).contains("-"))
            secondPositive = false;
        String[] split = exp.split("[+-]");
        if (split[0].equals("")) {  // Handle expr beginning with `-`
            split[0] = split[1];
            if (split.length > 2)
                split[1] = split[2];
        }
        Scalar realPart = Scalar.getZero();
        Scalar imgPart = Scalar.getZero();
        if (split[0].contains("i")) { // exp is imaginary
            String temp = split[0].replace("i", "");
            if (temp.isEmpty()){
                imgPart = new ComplexScalar("0","1");
            } else {
                imgPart = new ComplexScalar("0", temp);
            }
            secondPositive = firstPositive;
        }
        else
            realPart = new RealScalar(split[0]);

        if (!split[0].contains("i") && split.length > 1) { // if complex
            if (split[1].contains("i"))
                split[1] = split[1].replace("i", "");
            if (split[1].isEmpty()){
                imgPart = new ComplexScalar("0","1");
            } else {
                imgPart = new ComplexScalar("0", split[1]);
            }
        }
        if (!firstPositive) realPart = realPart.getMinus();
        if (!secondPositive) imgPart = imgPart.getMinus();
        return realPart.add(imgPart);
    }

}
