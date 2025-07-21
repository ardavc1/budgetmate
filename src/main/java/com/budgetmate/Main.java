package com.budgetmate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // FXML dosyasını src/main/resources/views/dashboard.fxml içinden yükle
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/dashboard.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setTitle("BudgetMate");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
