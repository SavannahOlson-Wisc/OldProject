package com.savannahelizabetholson.thetimelessgrounds;

/**
 * Created by savannaholson on 10/5/16.
 */

public class InventoryItem {

    private int id;
    private String name;
    private int quantity;

    public InventoryItem(int id, String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
        this.id = id;
    }

    public InventoryItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
