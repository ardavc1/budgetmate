package com.budgetmate;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import java.io.IOException;

public class DashboardController {

    @FXML
    private BorderPane rootPane; // BorderPane'i FXML'de fx:id="rootPane" olarak tanımlamalısın

    @FXML
    private Button btnBudget;

    @FXML
    private Button btnCalendar;

    @FXML
    public void handleBudget() {
        loadCenterContent("/views/budget.fxml");
    }

    @FXML
    public void handleCalendar() {
        loadCenterContent("/views/calendar.fxml");
    }

    private void loadCenterContent(String fxmlPath) {
        try {
            Node node = FXMLLoader.load(getClass().getResource(fxmlPath));
            rootPane.setCenter(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
