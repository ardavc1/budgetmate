package com.budgetmate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;

import java.time.LocalDate;

public class CalendarController {

    @FXML private DatePicker taskDatePicker;
    @FXML private TextField taskField;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private ComboBox<String> statusComboBox;
    @FXML private Button addTaskButton;
    @FXML private TableView<Task> taskTable;
    @FXML private TableColumn<Task, LocalDate> dateColumn;
    @FXML private TableColumn<Task, String> descriptionColumn;
    @FXML private TableColumn<Task, String> categoryColumn;
    @FXML private TableColumn<Task, String> statusColumn;

    private final ObservableList<Task> tasks = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Kategori ve durum seçenekleri
        categoryComboBox.setItems(FXCollections.observableArrayList("Kişisel", "İş", "Önemli", "Hatırlatma", "Diğer"));
        statusComboBox.setItems(FXCollections.observableArrayList("Bekliyor", "Tamamlandı"));

        // Tablo sütunlarını eşleştir
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumn.setOnEditCommit(event -> {
            Task task = event.getRowValue();
            task.setDescription(event.getNewValue());
        });

        categoryColumn.setCellFactory(ComboBoxTableCell.forTableColumn("Kişisel", "İş", "Önemli", "Hatırlatma", "Diğer"));
        categoryColumn.setOnEditCommit(event -> {
            Task task = event.getRowValue();
            task.setCategory(event.getNewValue());
        });

        statusColumn.setCellFactory(ComboBoxTableCell.forTableColumn("Bekliyor", "Tamamlandı"));
        statusColumn.setOnEditCommit(event -> {
            Task task = event.getRowValue();
            task.setStatus(event.getNewValue());
        });

        taskTable.setItems(tasks);

        // Satır renklendirme
        taskTable.setRowFactory(tv -> new TableRow<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                if (task == null || empty) {
                    setStyle("");
                } else if ("Tamamlandı".equals(task.getStatus())) {
                    setStyle("-fx-background-color: #e6ffed;");
                } else if ("Bekliyor".equals(task.getStatus())) {
                    setStyle("-fx-background-color: #e7f0fd;");
                } else {
                    setStyle("");
                }
            }
        });
    }

    @FXML
    public void handleAddTask() {
        LocalDate date = taskDatePicker.getValue();
        String description = taskField.getText();
        String category = categoryComboBox.getValue();
        String status = statusComboBox.getValue();

        if (date == null || description == null || description.isBlank() || category == null || status == null) {
            showAlert("Lütfen tüm alanları doldurun.");
            return;
        }

        Task task = new Task(date, description, category, status);
        tasks.add(task);

        taskDatePicker.setValue(null);
        taskField.clear();
        categoryComboBox.getSelectionModel().clearSelection();
        statusComboBox.getSelectionModel().clearSelection();
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Uyarı");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
