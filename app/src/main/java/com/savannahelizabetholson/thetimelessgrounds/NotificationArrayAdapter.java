package com.savannahelizabetholson.thetimelessgrounds;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by savannaholson on 11/2/16.
 */

public class NotificationArrayAdapter extends ArrayAdapter<String> {

    private ArrayList<String> objects;

    public NotificationArrayAdapter(Context context, int resource, ArrayList<String> objects) {

        super(context, resource, objects);

        this.objects = objects;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.notification_line, null);
        }

        String item = objects.get(position);

        if (item != null) {

            //Now I have a valid contact & a valid view to work with so I can set the various data in the cell.

            TextView text = (TextView) view.findViewById(R.id.text);

            text.setText(item);



        }

        return view;


    }

}
