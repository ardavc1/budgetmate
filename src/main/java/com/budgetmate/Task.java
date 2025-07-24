package com.budgetmate;

import java.time.LocalDate;

public class Task {
    private LocalDate date;
    private String description;
    private String category;
    private String status;

    public Task(LocalDate date, String description, String category, String status) {
        this.date = date;
        this.description = description;
        this.category = category;
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getStatus() {
        return status;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
