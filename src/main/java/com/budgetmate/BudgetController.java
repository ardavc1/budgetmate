package com.budgetmate;

import com.budgetmate.Transaction;
import com.budgetmate.Transaction.TransactionType;
import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.time.LocalDate;

public class BudgetController {

    @FXML private ComboBox<String> categoryComboBox;
    @FXML private TextField amountField;
    @FXML private ComboBox<String> typeComboBox;
    @FXML private Button addButton;
    @FXML private Label totalBalanceLabel;
    @FXML private PieChart budgetPieChart;
    @FXML private TableView<Transaction> transactionTable;
    @FXML private TableColumn<Transaction, LocalDate> dateColumn;
    @FXML private TableColumn<Transaction, String> categoryColumn;
    @FXML private TableColumn<Transaction, String> amountColumn;
    @FXML private TableColumn<Transaction, String> typeColumn;

    private final ObservableList<Transaction> transactions = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Tablo sütunlarını eşleştir
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amountFormatted"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("typeDisplay"));

        transactionTable.setItems(transactions);

        // Satır renklendirme
        transactionTable.setRowFactory(new Callback<>() {
            @Override
            public TableRow<Transaction> call(TableView<Transaction> tableView) {
                return new TableRow<>() {
                    @Override
                    protected void updateItem(Transaction item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            if (item.getType() == TransactionType.GELIR) {
                                setStyle("-fx-background-color: #e9f7ef;");
                            } else {
                                setStyle("-fx-background-color: #f9ebea;");
                            }
                        } else {
                            setStyle("");
                        }
                    }
                };
            }
        });

        // ComboBox verileri
        categoryComboBox.setItems(FXCollections.observableArrayList("Maaş", "Yemek", "Kira", "Fatura", "Ulaşım", "Diğer"));
        typeComboBox.setItems(FXCollections.observableArrayList("Gelir", "Gider"));

        updateBalance();
        updatePieChart();
    }

    @FXML
    public void handleAddTransaction() {
        String category = categoryComboBox.getValue();
        String amountText = amountField.getText();
        String typeText = typeComboBox.getValue();

        if (category == null || amountText.isBlank() || typeText == null) {
            showAlert("Lütfen tüm alanları doldurun.");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException e) {
            showAlert("Lütfen geçerli bir tutar girin.");
            return;
        }

        TransactionType type = typeText.equals("Gelir") ? TransactionType.GELIR : TransactionType.GIDER;
        Transaction transaction = new Transaction(LocalDate.now(), category, amount, type);
        transactions.add(transaction);

        // Temizle
        amountField.clear();
        categoryComboBox.getSelectionModel().clearSelection();
        typeComboBox.getSelectionModel().clearSelection();

        updateBalance();
        updatePieChart();
    }

    private void updateBalance() {
        double balance = transactions.stream()
                .mapToDouble(t -> t.getType() == TransactionType.GELIR ? t.getAmount() : -t.getAmount())
                .sum();
        totalBalanceLabel.setText(String.format("₺%,.2f", balance));
    }

    private void updatePieChart() {
        double totalIncome = transactions.stream()
                .filter(t -> t.getType() == TransactionType.GELIR)
                .mapToDouble(Transaction::getAmount)
                .sum();

        double totalExpense = transactions.stream()
                .filter(t -> t.getType() == TransactionType.GIDER)
                .mapToDouble(Transaction::getAmount)
                .sum();

        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList(
                new PieChart.Data("Gelir", totalIncome),
                new PieChart.Data("Gider", totalExpense)
        );

        budgetPieChart.setData(chartData);


        for (PieChart.Data data : chartData) {
            String label = data.getName();
            if (label.equals("Gelir")) {
                data.getNode().setStyle("-fx-pie-color: #4CAF50;");
            } else if (label.equals("Gider")) {
                data.getNode().setStyle("-fx-pie-color: #bd1812;");
            }
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Uyarı");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
