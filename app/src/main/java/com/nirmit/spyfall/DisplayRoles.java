package com.nirmit.spyfall;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collection;
import java.util.Random;


public class DisplayRoles extends Activity {

    GameSetting gameSetting; // variable to get game setting information
    EnterRoles enteredRoles;

    // variable to get references to the widgets
    Button pass, tapMe;
    TextView finalLocation, playerNumber, locationRole;

    Random rand = new Random(); // class to get a random number

    // local class variables
    private int spyPlayer;
    private int player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_display_roles);

        // instantiating the classes
        gameSetting = new GameSetting();
        enteredRoles = new EnterRoles();

        // getting references to the widgets
        tapMe = findViewById(R.id.tapMe);
        pass = findViewById(R.id.pass);
        finalLocation = findViewById(R.id.location);
        locationRole = findViewById(R.id.locationRole);
        playerNumber = findViewById(R.id.playerNumber);

        // setting role information invisible initially
        playerNumber.setVisibility(View.INVISIBLE);
        finalLocation.setVisibility(View.INVISIBLE);
        locationRole.setVisibility(View.INVISIBLE);
        pass.setVisibility(View.INVISIBLE);

        addClickEffect(tapMe);
        addClickEffect(pass);

        setDisplay(); // displaying appropriate screen to the user.

    }

    public void setDisplay() {

        player = 0; // player number

        spyPlayer = rand.nextInt(GameSetting.getPlayers()) + 1; // picking a random player to be spy

        // information image button is clicked
        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // if role to all the players is conveyed...
                if (player == GameSetting.getPlayers()) {
                    // go to the end game activity
                    Intent intent = new Intent(DisplayRoles.this, EndGame.class);
                    startActivity(intent);
                } else {
                    //things that display role becomes invisible
                    playerNumber.setVisibility(View.INVISIBLE);
                    finalLocation.setVisibility(View.INVISIBLE);
                    locationRole.setVisibility(View.INVISIBLE);
                    pass.setVisibility(View.INVISIBLE);
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
                tapMe.setVisibility(View.INVISIBLE);

                // information widgets go visible
                playerNumber.setVisibility(View.VISIBLE);
                finalLocation.setVisibility(View.VISIBLE);
                locationRole.setVisibility(View.VISIBLE);
                pass.setVisibility(View.VISIBLE);

                playerNumber.setText(Integer.toString(player));

                // if the player number is same is random number...
                if (spyPlayer == player) {
                    finalLocation.setText("SPY");
                    locationRole.setText("");
                } else {
                    // random location is picked each time from the list and displayed.
                    finalLocation.setText(GameSetting.getLocation());
                    Collection<String> roles = GameSetting.getRoles();
                    locationRole.setText(roles.toArray()[rand.nextInt(roles.toArray().length)].toString());
                }
            }
        });
    }

    // Adds click effect on the custom buttons
    void addClickEffect(View view) {
        Drawable drawableNormal = view.getBackground();

        Drawable drawablePressed = view.getBackground().getConstantState().newDrawable();
        drawablePressed.mutate();
        drawablePressed.setColorFilter(Color.argb(100, 0, 0, 0), PorterDuff.Mode.SRC_ATOP);

        StateListDrawable listDrawable = new StateListDrawable();
        listDrawable.addState(new int[]{android.R.attr.state_pressed}, drawablePressed);
        listDrawable.addState(new int[]{}, drawableNormal);
        view.setBackground(listDrawable);
    }
}
