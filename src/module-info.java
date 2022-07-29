module LinearAlgebra {

    requires javafx.fxml;
    requires javafx.controls;

    opens com.LinearAlgebra to javafx.fxml;

    exports com.LinearAlgebra;

}