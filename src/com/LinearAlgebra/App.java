package com.LinearAlgebra;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("linear-algebra-app.fxml"));
        stage.setTitle("Rom's Matrix Calculator!");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
}
