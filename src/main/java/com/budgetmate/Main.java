package com.budgetmate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/dashboard.fxml"));
        Parent root = loader.load();

        stage.setTitle("BudgetMate");
        stage.setScene(new Scene(root, 1080, 900));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
