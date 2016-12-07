package com.savannahelizabetholson.thetimelessgrounds;

/**
 * Created by savannaholson on 10/5/16.
 */

public class InventoryItem {

    private int id;
    private String name;
    private int quantity;

    /**
     * This constructor sets everything
     *
     * @param id the id of the item
     * @param name the name of the item
     * @param quantity the quantity of the item
     */
    public InventoryItem(int id, String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
        this.id = id;
    }

    /**
     * blank constructor for easier construction
     */
    public InventoryItem() {
    }

    /**
     *  This is a get method
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * This method is really self explanitory...
     *
     * @param id the new id value
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method is also really self explanitory...
     *
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * I am pretty sure you know what this does...
     *
     * @param name the new name value
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Another get method
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Setting some more stuff!
     *
     * @param quantity the new quantity value
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
