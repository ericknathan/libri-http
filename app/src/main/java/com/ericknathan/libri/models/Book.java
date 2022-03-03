package com.ericknathan.libri.models;

public class Book {
    private String title, description;

    public Book(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
