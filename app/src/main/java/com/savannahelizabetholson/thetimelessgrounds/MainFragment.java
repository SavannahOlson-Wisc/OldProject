package com.savannahelizabetholson.thetimelessgrounds;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.prefs.Preferences;

/**
 * Created by savannaholson on 10/5/16.
 */

public class MainFragment extends Fragment {

    //Instance Variables
    private TreeMap<String, String> location;
    private SharedPreferences preferences;
    private ArrayList<InventoryItem> invenory;
    private ArrayList<String> notifications;
    private InventoryArrayAdapter adapter;
    private NotificationArrayAdapter notificationAdapter;
    private int positionSelected;
    private String[][] map;
    private int xCoordinate;
    private int yCoordinate;
    private String temp_username;

    //Widgets
    private TextView aheadText;
    private TextView rightText;
    private TextView behindText;
    private TextView leftText;
    private TextView currentText;
    private ListView inventoryList;
    private ListView notificationsList;
    private Button interactButton;
    private Button aheadButton;
    private Button leftButton;
    private Button rightButton;
    private Button backButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.main_fragment, container, false);

        //Setup
        preferences = this.getActivity().getSharedPreferences("app_preferences", Context.MODE_PRIVATE);
        notifications = new ArrayList<String>();
        invenory = new ArrayList<InventoryItem>();
        initializePosition();
        loadInventory();

        //Wire Up Widgets
        aheadText = (TextView) view.findViewById(R.id.aheadText);
        rightText = (TextView) view.findViewById(R.id.rightText);
        behindText = (TextView) view.findViewById(R.id.behindText);
        leftText = (TextView) view.findViewById(R.id.leftText);
        currentText = (TextView) view.findViewById(R.id.currentText);
        inventoryList = (ListView) view.findViewById(R.id.inventory);
        interactButton = (Button) view.findViewById(R.id.interactButton);
        notificationsList = (ListView) view.findViewById(R.id.notificationsList);
        aheadButton = (Button) view.findViewById(R.id.aheadButton);
        leftButton = (Button) view.findViewById(R.id.leftbutton);
        rightButton = (Button) view.findViewById(R.id.rightButton);
        backButton = (Button) view.findViewById(R.id.backButton);

        updatePositionText();

        //Setup inventory list view
        adapter = new InventoryArrayAdapter(getContext(), R.layout.inventory_line, invenory);
        inventoryList.setAdapter(adapter);

        notificationAdapter = new NotificationArrayAdapter(getContext(), R.layout.notification_line, notifications);
        notificationsList.setAdapter(notificationAdapter);

        inventoryList.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MainFragment.this.positionSelected = position;

            }

        });

        interactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Test for a valid position selected and if it is invalid get out of method.
                if (positionSelected >= invenory.size() || positionSelected < 0) {
                    return;
                }

                InventoryItem selectedItem = invenory.get(positionSelected);

                if (selectedItem.getName().equals("Apple")) {

                    InventoryDatabaseHandler db = InventoryDatabaseHandler.getInstance(getContext());

                    boolean result = db.removeItem(selectedItem.getId());

                    invenory.remove(positionSelected);

                    adapter.notifyDataSetChanged();

                    notifications.add("Ate apple x " + selectedItem.getQuantity());

                    notificationAdapter.notifyDataSetChanged();
                    notificationsList.setSelection(notificationAdapter.getCount() - 1);

                } else if (selectedItem.getName().equals("Basket")) {

                    InventoryItem item = new InventoryItem();
                    item.setQuantity((int) (Math.random() * 5 + 1));

                    if (location.get("current").equals("tree")) {
                        item.setName("Apple");
                    } else if (location.get("current").equals("grass")) {
                        item.setName("Flower");
                    } else {
                        return;
                    }

                    InventoryDatabaseHandler db = InventoryDatabaseHandler.getInstance(getContext());

                    int id = db.addInventoryItem(item);
                    item.setId(id);
                    invenory.add(item);
                    adapter.notifyDataSetChanged();

                    notifications.add("Used Basket and obtained " + item.getName() + " x " + item.getQuantity());
                    notificationAdapter.notifyDataSetChanged();
                    notificationsList.setSelection(notificationAdapter.getCount() - 1);

                } else if (selectedItem.getName().equals("Flower")) {

                    InventoryDatabaseHandler db = InventoryDatabaseHandler.getInstance(getContext());

                    boolean result = db.removeItem(selectedItem.getId());

                    invenory.remove(positionSelected);

                    adapter.notifyDataSetChanged();

                    notifications.add("Used flower x " + selectedItem.getQuantity());

                    notificationAdapter.notifyDataSetChanged();

                    notificationsList.setSelection(notificationAdapter.getCount() - 1);
                }

            }
        });

        aheadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yCoordinate > 0) {
                    yCoordinate--;
                    notifications.add("Moved forward!");
                } else {
                    notifications.add("Could not move forward!");
                }

                loadPosition();
                updatePositionText();

                notificationAdapter.notifyDataSetChanged();
                notificationsList.setSelection(notificationAdapter.getCount() - 1);
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (xCoordinate > 0) {
                    xCoordinate--;
                    notifications.add("Moved Left!");
                } else {
                    notifications.add("Could not move left!");
                }

                loadPosition();
                updatePositionText();

                notificationAdapter.notifyDataSetChanged();
                notificationsList.setSelection(notificationAdapter.getCount() - 1);
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (xCoordinate < 9) {
                    xCoordinate++;
                    notifications.add("Moved Right!");
                } else {
                    notifications.add("Could not move right!");
                }

                loadPosition();
                updatePositionText();

                notificationAdapter.notifyDataSetChanged();
                notificationsList.setSelection(notificationAdapter.getCount() - 1);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yCoordinate < 9) {
                    yCoordinate++;
                    notifications.add("Moved Back!");
                } else {
                    notifications.add("Could not move back!");
                }

                loadPosition();
                updatePositionText();

                notificationAdapter.notifyDataSetChanged();
                notificationsList.setSelection(notificationAdapter.getCount() - 1);
            }
        });

        //Check for name being stored
        String username = preferences.getString("name", "name_not_set");

        if (username.equals("name_not_set")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Please enter your name!");

            // Set up the input
            final EditText input = new EditText(getContext());
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String newName = input.getText().toString();

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("name", newName);
                    editor.commit();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();

            //load in new name
            username = preferences.getString("name", "name-error");

        } else {

            Toast.makeText(getContext(), "Welcome Back " + username, Toast.LENGTH_LONG).show();
        }

        return view;
    }

    //Custom Methods
    private void initializePosition() {

        //TODO: Refactor so is shorter
        xCoordinate = preferences.getInt("x", 0);
        yCoordinate = preferences.getInt("y", 0);

        map = new String[10][10];

        String[] line0 = getResources().getStringArray(R.array.map_row_0);
        String[] line1 = getResources().getStringArray(R.array.map_row_1);
        String[] line2 = getResources().getStringArray(R.array.map_row_2);
        String[] line3 = getResources().getStringArray(R.array.map_row_3);
        String[] line4 = getResources().getStringArray(R.array.map_row_4);
        String[] line5 = getResources().getStringArray(R.array.map_row_5);
        String[] line6 = getResources().getStringArray(R.array.map_row_6);
        String[] line7 = getResources().getStringArray(R.array.map_row_7);
        String[] line8 = getResources().getStringArray(R.array.map_row_8);
        String[] line9 = getResources().getStringArray(R.array.map_row_9);

        for (int i = 0; i < 10; i++) {
            map[0][i] = line0[i];
        }

        for (int i = 0; i < 10; i++) {
            map[1][i] = line1[i];
        }

        for (int i = 0; i < 10; i++) {
            map[2][i] = line2[i];
        }

        for (int i = 0; i < 10; i++) {
            map[4][i] = line4[i];
        }

        for (int i = 0; i < 10; i++) {
            map[5][i] = line5[i];
        }

        for (int i = 0; i < 10; i++) {
            map[6][i] = line6[i];
        }

        for (int i = 0; i < 10; i++) {
            map[7][i] = line7[i];
        }

        for (int i = 0; i < 10; i++) {
            map[3][i] = line3[i];
        }

        for (int i = 0; i < 10; i++) {
            map[8][i] = line8[i];
        }

        for (int i = 0; i < 10; i++) {
            map[9][i] = line9[i];
        }

        location = new TreeMap<String, String>();

        loadPosition();

    }

    private void loadPosition() {

        String ahead;
        String left;
        String right;
        String behind;
        String current = map[xCoordinate][yCoordinate];

        if (xCoordinate <= 0) {
            left = "wall";
        } else {
            left = map[xCoordinate - 1][yCoordinate];
        }

        if (xCoordinate >= 9) {
            right = "wall";
        } else {
            right = map[xCoordinate+1][yCoordinate];
        }

        if (yCoordinate <= 0) {
            ahead = "wall";
        } else {
            ahead = map[xCoordinate][yCoordinate-1];
        }

        if (yCoordinate >= 9) {
            behind = "wall";
        } else {
            behind = map[xCoordinate][yCoordinate+1];
        }


        location.put("ahead", ahead);
        location.put("right", right);
        location.put("behind", behind);
        location.put("left", left);
        location.put("current", current);
    }

    private void updatePositionText() {

        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt("x", xCoordinate);
        editor.putInt("y", yCoordinate);

        editor.commit();

        aheadText.setText("Ahead: " + location.get("ahead"));
        rightText.setText("Right: " + location.get("right"));
        behindText.setText("Behind: " + location.get("behind"));
        leftText.setText("Left: " + location.get("left"));
        currentText.setText("Current: " + location.get("current"));



    }

    private void loadInventory() {

        InventoryDatabaseHandler db = InventoryDatabaseHandler.getInstance(getContext());


        invenory = db.getInventory();

    }

}
