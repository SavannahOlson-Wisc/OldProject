package com.savannahelizabetholson.thetimelessgrounds;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by savannaholson on 10/12/16.
 */

public class InventoryDatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "timeless_grounds";

    // Contacts table name
    private static final String TABLE_INVENTORY= "inventory_items";

    // Contacts Table Columns names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ITEM_NAME = "item_name";
    private static final String COLUMN_ITEM_QUANTITY = "item_quantity";

    //Instance of the method
    private static InventoryDatabaseHandler handler;

    /**
     * This is a method called to obtain an instance of the InventoryDatabaseHandler
     *
     * @param context the context
     * @return
     */
    public static InventoryDatabaseHandler getInstance(Context context) {

        if (handler == null) {
            handler = new InventoryDatabaseHandler(context);
        }

        return handler;

    }

    /**
     * This method is private so that the only way people can get an instance is through the proper method
     *
     * @param context the context.
     */
    private InventoryDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_INVENTORY + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_ITEM_NAME + " TEXT,"
                + COLUMN_ITEM_QUANTITY + " INTEGER" + ")";

        db.execSQL(CREATE_CONTACTS_TABLE);

        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, "Basket");
        values.put(COLUMN_ITEM_QUANTITY, 1);

        db.insert(TABLE_INVENTORY, null, values);


    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVENTORY);

        // Create tables again
        onCreate(db);
    }

    // CRUD METHODS

    /**
     * This method adds an inventory item
     *
     * @param item an inventory item object
     * @return row number maybe?
     */
    public int addInventoryItem(InventoryItem item) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, item.getName());
        values.put(COLUMN_ITEM_QUANTITY, item.getQuantity());

        return (int) db.insert(TABLE_INVENTORY, null, values);

    }

    /**
     * This method is used to get an inventory item
     *
     * @param id the id of the item you want to get
     * @return the item requested
     */
    public InventoryItem getInventoryItem(int id) {
        String query = "Select * FROM " + TABLE_INVENTORY + " WHERE " + COLUMN_ID + " =  \"" + id + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        InventoryItem item = new InventoryItem();

        //only the first match will then be returned, contained within a
        // new instance of our Product data model class
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            item.setId(Integer.parseInt(cursor.getString(0)));
            item.setName(cursor.getString(1));
            item.setQuantity(Integer.parseInt(cursor.getString(2)));
            cursor.close();
        } else {
            item = null;
        }
        db.close();
        return item;


    }

    /**
     * This method gets all items
     *
     * @return An array list containing all of the inventory items
     */
    public ArrayList<InventoryItem> getInventory() {

        ArrayList<InventoryItem> inventoryItems = new ArrayList<InventoryItem>();

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "Select * FROM " + TABLE_INVENTORY;



        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {

            InventoryItem item = new InventoryItem(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Integer.parseInt(cursor.getString(2)));

            inventoryItems.add(item);
        }

        return inventoryItems;

    }

    /**
     * This method is used to remove an item
     *
     * @param id this is the id of the item that should be removed
     * @return a boolean indicating success or failure
     */
    public boolean removeItem(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_INVENTORY, COLUMN_ID + "=" + id, null) > 0;

    }


}
