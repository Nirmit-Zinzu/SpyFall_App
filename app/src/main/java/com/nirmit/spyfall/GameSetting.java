package com.nirmit.spyfall;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class GameSetting extends Activity {

    // Variables to get references to the widgets
    RelativeLayout customEntry;
    EditText numPlayers, customLocation;
    Button showListBtn, goNext;
    ImageButton hostBtn, noHostBtn;
    TextView enterLocation;

    // variables to use in different activities (same copy)
    private static String location = "";   // gets the location
    private static int players = 0;        // number of players



    Random rand = new Random();   // Instantiating class to get a random integer

    // array to store default locations/people/item
    private String items[] = new String[] {
            "Airplane", "Bank", "Beach", "Broadway Theater", "Casino",
            "Circus Tent", "Spa", "Embassy", "Hospital", "Hotel",
            "Military Base", "Movie Studio", "Train", "Pirate Ship", "Police Station",
            "Restaurant", "School", "Service Station", "Space Station", "Submarine",
            "Supermarket", "University", "Office", "Art Museum", "Strip Club",
            "Jail", "Subway", "C.N Tower", "Niagara Falls", "Camping Resourt",
            "Beach", "Zoo", "Spa", "Amusement Park", "Capital Hill",
            "Carnival", "Laboratory", "Sewers", "Space Station", "Ocean Liner"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_game_setting);

        // getting references to the widgets
        customEntry = (RelativeLayout) findViewById(R.id.customEntry);
        numPlayers = (EditText) findViewById(R.id.playerNumber);
        customLocation = (EditText) findViewById(R.id.customLocation);
        enterLocation = (TextView) findViewById(R.id.note2);
        hostBtn = (ImageButton) findViewById(R.id.hostBtn);
        noHostBtn = (ImageButton) findViewById(R.id.noHostBtn);
        showListBtn = (Button) findViewById(R.id.showListBtn);
        goNext = (Button) findViewById(R.id.goNext);

        //this relative layout is hidden. It becomes visible when host game is selected.
        customEntry.setVisibility(View.INVISIBLE);

        hostGame();   // host game is selected. Option of entering custom location is provided.
        noHostGame(); // no0host, all plays.
        showList();   // Alert dialog with default items is shown to the user
        startGame();  // goes to the next activity.

    }



    // host game selected (1 narrator).
    public void hostGame() {
        hostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playersEntered() == true) {

                    players = Integer.parseInt(numPlayers.getText().toString());  //getting players

                    // relative layout becomes visible to allow the user to enter a custom location
                    customEntry.setVisibility(View.VISIBLE);


                } else {
                    // error is displayed if wrong input
                    numPlayers.setError("Enter valid player number (> 2)");
                }
            }
        });
    }

    // no-host game selected (Every player plays)
    public void noHostGame() {
        noHostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (playersEntered()) {

                    players = Integer.parseInt(numPlayers.getText().toString()); // getting players

                    // randomly location from the array is selected.
                    location = items[rand.nextInt(items.length)];

                    // goes to the next activity (Display Roles)
                    Intent intent = new Intent(GameSetting.this, DisplayRoles.class);
                    startActivity(intent);

                } else {
                    // error is displayed if wrong input
                    numPlayers.setError("Enter valid player number (> 2)");
                }
            }
        });
    }

    public void showList() {
        showListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String totalMessage = "";

                // Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(GameSetting.this);
                builder.setCancelable(true);

                //button to allow the user to go back.
                builder.setPositiveButton("Back", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                // setting the string based on list array.
                for (int i = 0; i < items.length; i++){
                    totalMessage += items[i] + "\n";
                }

                // Chain together various setter methods to set the dialog characteristics
                builder.setMessage(totalMessage);
                builder.setTitle("Locations");

                // dialog is displayed on the screen.
                builder.show();

            }
        });
    }

    // goes to the next activity from host game.
    public void startGame() {
        goNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // checks to see if any string is entered or not.
                if (customLocation.getText().toString().equals("")) {
                    customLocation.setError("Enter valid entry!");

                } else {
                    location = customLocation.getText().toString(); // getting the custom location

                    Intent intent = new Intent(GameSetting.this, DisplayRoles.class);
                    startActivity(intent);
                }
            }
        });
    }

    // checks to see if correct number of players is entered or not.
    public boolean playersEntered() {
        boolean result = true;
        if (numPlayers.getText().toString().equals("") || Integer.parseInt(numPlayers.getText().toString()) < 3) {
            result = false;
        }
        return result;
    }

    // ---- getter methods ----

    public static String getLocation() {
        return location;
    }

    public static int getPlayers() {
        return players;
    }


}
