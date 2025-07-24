// Transaction.java
package com.budgetmate;

import java.time.LocalDate;

public class Transaction {
    public enum TransactionType {
        GELIR,
        GIDER
    }

    private LocalDate date;
    private String category;
    private double amount;
    private TransactionType type;

    public Transaction(LocalDate date, String category, double amount, TransactionType type) {
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public String getAmountFormatted() {
        return String.format("%.2f ₺", amount);
    }

    public String getTypeDisplay() {
        return (type == TransactionType.GELIR) ? "Gelir" : "Gider";
    }
}
