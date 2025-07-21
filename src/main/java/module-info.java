module com.budgetmate {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.budgetmate to javafx.fxml;
    exports com.budgetmate;
}