package com.ericknathan.libri.models;

public class Item {
    /*
     * 1 = LIVRO
     * 2 = HQ
     * 3 = MANGÁ
     */
    private int type;
    private Object object;

    public Item(int type, Object object) {
        this.type = type;
        this.object = object;
    }

    public int getType() {
        return type;
    }

    public Object getObject() {
        return object;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
