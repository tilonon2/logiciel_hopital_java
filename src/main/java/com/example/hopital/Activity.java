package com.example.hopital;

import java.time.LocalDate;

public class Activity {
    private String type;
    private String description;
    private LocalDate date;

    public Activity(String type, String description, LocalDate date) {
        this.type = type;
        this.description = description;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
