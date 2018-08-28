package com.nirmit.spyfall;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EnterRoles extends Activity {

    GameSetting gameSetting;         // get information such as number of players to set up the game
    // Variables to get references to the widgets
    TextView locationText;
    Button submit;
    private LinearLayout parentLinearLayout;
    private int rolesEntered;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_roles);

        gameSetting = new GameSetting();         // instantiating the class
        rolesEntered = GameSetting.getPlayers(); // number of roles fields populated

        // getting references to the widgets
        parentLinearLayout = findViewById(R.id.parent_linear_layout);  // first plane
        locationText = findViewById(R.id.locationEntry);
        submit = findViewById(R.id.submitBtn);

        addRoleEntries();   // adds role field based on number of players
        onSubmit();         // sets up the data struct and displays roles + location next

    }

    // Adds role field. Initialy it is set based on the number of players playing.
    public void addRoleEntries() {

        int numOfPlayers = GameSetting.getPlayers();

        // populating role filed for each players.
        for (int i = 0; i < numOfPlayers; i++) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView = inflater.inflate(R.layout.field, null);

            // adding the view before last two buttons
            parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 2);
        }
    }

    // Sets of the global structure Multimap. Adds roles and location to it before precedding.
    public void onSubmit() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast toast;
                String location = locationText.getText().toString();
                String possibleRole = "";

                // User input check. Roles must be entered.
                if (rolesEntered <= 0) {
                    toast = Toast.makeText(getApplicationContext(), "Must enter at least one Role!.", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                // User input check. Location must be entered.
                if (location.matches("") || location == null) {
                    locationText.setError("Can't be empty!");
                    return;
                }

                GameSetting.setLocation(location);   // setting the current location

                View v;

                for (int i = 0; i < parentLinearLayout.getChildCount(); i++) {

                    v = parentLinearLayout.getChildAt(i);

                    LinearLayout roleFieldLayout = v.findViewById(R.id.field);

                    // checking to see if role field is there. If a roles field is identified,
                    // role and location field is stored into the global structure. Also, roles List
                    // is populated for next activity.
                    if (roleFieldLayout != null) {

                        for (int j = 0; j < roleFieldLayout.getChildCount(); j++) {

                            v = roleFieldLayout.getChildAt(j);

                            TextView role = v.findViewById(R.id.roleEntry);

                            // Checking for roles field within sub view.
                            if (role != null && !(role.getText().toString().matches(""))) {
                                location = properCase(location);
                                possibleRole = properCase(role.getText().toString());
                                GameSetting.getGameMap().put(location, possibleRole);
                                break;
                            } else {
                                toast = Toast.makeText(getApplicationContext(), "Roles can't be empty!", Toast.LENGTH_SHORT);
                                toast.show();
                                return;
                            }
                        }
                    }
                }

                gameSetting.getKeysList().add(location);                      // adding location within the list
                GameSetting.setRoles(GameSetting.getGameMap().get(location)); // setting roles for next activity

                Intent intent = new Intent(EnterRoles.this, DisplayRoles.class);
                startActivity(intent);
            }
        });
    }

    // Adds another role field. NOTE: roles field can't exceed number of players playing.
    public void onAddField(View v) {

        if (rolesEntered < GameSetting.getPlayers()) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView = inflater.inflate(R.layout.field, null);
            parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 2);
            rolesEntered++;
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Roles can not exceed amount of players.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    // Deleted a roles field. The view which is clicked is removed.
    public void onDelete(View v) {
        parentLinearLayout.removeView((View) v.getParent());
        rolesEntered--;
    }

    // Sets proper format for location and roles. (first letter capital)
    String properCase(String inputVal) {

        if (inputVal.length() == 0) return "";

        if (inputVal.length() == 1) return inputVal.toUpperCase();

        // Uppercase first letter, lowercase the rest.
        return inputVal.substring(0, 1).toUpperCase()
                + inputVal.substring(1).toLowerCase();
    }

}


