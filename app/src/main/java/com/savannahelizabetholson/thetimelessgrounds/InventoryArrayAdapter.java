package com.savannahelizabetholson.thetimelessgrounds;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by savannaholson on 10/13/16.
 */

public class InventoryArrayAdapter extends ArrayAdapter<InventoryItem> {

    private ArrayList<InventoryItem> inventoryItems;

    public InventoryArrayAdapter(Context context, int resource, ArrayList<InventoryItem> objects) {

        super(context, resource, objects);

        inventoryItems = objects;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.inventory_line, null);
        }

        InventoryItem item = inventoryItems.get(position);

        if (item != null) {

            //Now I have a valid contact & a valid view to work with so I can set the various data in the cell.

            TextView itemName = (TextView) view.findViewById(R.id.item_name);
            TextView itemQuantity = (TextView) view.findViewById(R.id.item_quantity);

            itemName.setText(item.getName());

            itemQuantity.setText("x" + item.getQuantity());



        }

        return view;


    }

}
