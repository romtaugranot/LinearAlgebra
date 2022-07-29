package com.LinearAlgebra;

import com.LinearAlgebra.ComplexMath.FieldScalars.ComplexScalar;
import com.LinearAlgebra.ComplexMath.FieldScalars.RealScalar;
import com.LinearAlgebra.ComplexMath.FieldScalars.Scalar;
import com.LinearAlgebra.Matrices.ComplexMatrix;
import com.LinearAlgebra.Matrices.Matrix;
import com.LinearAlgebra.Matrices.SquareMatrix;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class FxController {

    private static final int MAX_SIZE = 6;

    private static final int INITIAL_SIZE = 2;
    private static final String MATRIX_NOT_SQUARE = "Something went wrong! matrix isn't square.";
    private static final String INCORRECT_MATRIX_SIZE = "Matrix sizes are not legal for this operation.";

    private static final String INCORRECT_MATRIX_INPUT = "Something went wrong! some matrix entry is not a scalar.";

    @FXML
    private ListView<Integer> leftMatRowListView, leftMatColListView, rightMatRowListView, rightMatColListView;

    @FXML
    private GridPane leftMatrixGrid, rightMatrixGrid;

    @FXML
    private GridPane ansMatrixGrid;

    @FXML
    private Button traceButton, transposeButton, rankButton;

    @FXML
    private Button addButton, subButton, mulButton;

    @FXML
    private ToggleButton isLeftToggleButton;

    @FXML
    private Label answerLabel;

    private boolean isLeft;

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

        initSingleMatrixOpButtons(traceButton);
        initSingleMatrixOpButtons(rankButton);
        initSingleMatrixOpButtons(transposeButton);

        initAddButton();
        initSubButton();
        initMulButton();

        isLeft = true; // Initial value is true.
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

    private void initAddButton() {
        addButton.setOnAction(e -> {
            if (updateMatrices()) {
                if (!checkIfSameSize()) {
                    answerLabel.setText(INCORRECT_MATRIX_SIZE);
                    initMatrix(ansMatrixGrid, 0, 0);
                } else {
                    Matrix add = leftMatrix.add(rightMatrix);
                    updateAnsMatrix(add);
                }
            } else {
                displayError(INCORRECT_MATRIX_INPUT);
            }

        });
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

    private void initSubButton() {
        subButton.setOnAction(e -> {
            if (updateMatrices()) {
                if (!checkIfSameSize()) {
                    answerLabel.setText(INCORRECT_MATRIX_SIZE);
                    initMatrix(ansMatrixGrid, 0, 0);
                } else {
                    Matrix sub = leftMatrix.sub(rightMatrix);
                    updateAnsMatrix(sub);
                }
            } else {
                displayError(INCORRECT_MATRIX_INPUT);
            }

        });
    }

    private void initMulButton() {
        mulButton.setOnAction(e -> {
            if (updateMatrices()) {
                if (leftMatrix.getN() != rightMatrix.getM()){
                    answerLabel.setText(INCORRECT_MATRIX_SIZE);
                    initMatrix(ansMatrixGrid,0,0);
                } else {
                    Matrix mul = leftMatrix.mul(rightMatrix);
                    updateAnsMatrix(mul);
                }
            } else {
                displayError(INCORRECT_MATRIX_INPUT);
            }
        });
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
                }
            } else {
                displayError(INCORRECT_MATRIX_INPUT);
            }

        });
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
        Matrix transpose = matrix.transpose();
        updateAnsMatrix(transpose);
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
                        answerLabel.setText("Something went wrong! maybe input is incorrect.");
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
        if (exp == null || exp.isEmpty()) return Scalar.getZero();
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
