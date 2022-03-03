package com.ericknathan.libri.models;

public class Item {
    private final int type;
    private final Book object;

    public Item(int type, Book object) {
        this.type = type;
        this.object = object;
    }
    
    public int getType() {
        return type;
    }

    public Book getObject() {
        return object;
    }
}
