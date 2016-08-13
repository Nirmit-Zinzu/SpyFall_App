package com.nirmit.spyfall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class DisplayRoles extends Activity {

    GameSetting gameSetting; // variable to get game setting information

    // variable to get references to the widgets
    ImageView blank, information;
    ImageButton tapMe, pass;
    TextView finalLocation, playerNumber;

    Random rand = new Random(); // class to get a random number

    // local class variables
    private int n;
    private int player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_display_roles);

        gameSetting = new GameSetting(); // instantiating the class

        // getting references to the widgets
        tapMe = (ImageButton) findViewById(R.id.tapMe);
        pass = (ImageButton) findViewById(R.id.pass);
        finalLocation = (TextView) findViewById(R.id.role);
        playerNumber = (TextView) findViewById(R.id.playerNumber);
        blank = (ImageView) findViewById(R.id.blank);
        information = (ImageView) findViewById(R.id.information);

        // setting role information invisible initially
        information.setVisibility(View.INVISIBLE);
        playerNumber.setVisibility(View.INVISIBLE);
        finalLocation.setVisibility(View.INVISIBLE);
        pass.setVisibility(View.INVISIBLE);

        setDisplay(); // displaying appropriate screen to the user.

    }

    public void setDisplay() {

        player = 0; // player number

        n = rand.nextInt(gameSetting.getPlayers()) + 1; // picking a random player to be spy

        //Toast.makeText(DisplayRoles.this, Integer.toString(n), Toast.LENGTH_SHORT).show();

        // information image button is clicked
        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // if role to all the players is conveyed...
                if (player == gameSetting.getPlayers()) {
                    // go to the end game activity
                   // Toast.makeText(DisplayRoles.this, "EQUAL!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DisplayRoles.this, EndGame.class);
                    startActivity(intent);
                } else {
                    //things that display role becomes invisible
                    information.setVisibility(View.INVISIBLE);
                    playerNumber.setVisibility(View.INVISIBLE);
                    finalLocation.setVisibility(View.INVISIBLE);
                    pass.setVisibility(View.INVISIBLE);

                    // blank page is displayed.
                    blank.setVisibility(View.VISIBLE);
                    tapMe.setVisibility(View.VISIBLE);
                }

            }
        });

        // blank image button is clicked
        tapMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                player++; // player number increases

                // blank page widgets fo invisible
                blank.setVisibility(View.INVISIBLE);
                tapMe.setVisibility(View.INVISIBLE);

                // information widgets go visible
                information.setVisibility(View.VISIBLE);
                playerNumber.setVisibility(View.VISIBLE);
                finalLocation.setVisibility(View.VISIBLE);
                pass.setVisibility(View.VISIBLE);

                playerNumber.setText(Integer.toString(player));

                // if the player number is same is random number...
                if (n == player) {
                    finalLocation.setText("SPY");
                } else {
                    finalLocation.setText(gameSetting.getLocation());
                }
            }
        });

    }

}
