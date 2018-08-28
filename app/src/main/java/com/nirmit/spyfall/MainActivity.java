package com.nirmit.spyfall;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends Activity {

    ImageView logoView;
    Button rulesButton, gameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        logoView = findViewById(R.id.gameLogoImgView);
        rulesButton = findViewById(R.id.rulesBtn);
        gameButton = findViewById(R.id.startBtn);

        addClickEffect(gameButton);
        addClickEffect(rulesButton);

        playGameThruBtn();  // goes to the next activity
        showRules();        // shows rules
    }

    private void playGameThruBtn() {
        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, GameSetting.class);
                startActivity(intent);
            }
        });
    }

    // Populates Alert Dialog to show the rules.
    private void showRules() {
        rulesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String totalMessage = "";

                // Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);

                //button to allow the user to go back.
                builder.setPositiveButton("Back", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                // setting the string based on list array.
                totalMessage += "••••• OVERVIEW •••••\n" +
                        "A game of Spyfall is played in several short rounds. " +
                        "Each round, the players find themselves  in a certain location with a " +
                        "specific role assigned to each player. One player (two players in a game " +
                        "with many players) is always a spy who doesn’t know " +
                        "where he is. The spy’s mission is to listen carefully, identify the location," +
                        " and keep from blowing his cover. Each non-spy should give a vague hint " +
                        "to other non-spies suggesting that he knows the location’s identity," +
                        " thus proving he’s not the spy. Observation, concentration, iron endurance," +
                        " and cunning — you’ll need all of them in this game. Stay on your toes!" +

                        "\n\n\n••• PREPARING FOR ROUND ONE •••\n" +
                        "This app offers two modes. Mode 1 requires a dealer. Dealer's job is to" +
                        "customize the game. The dealer is required to enter a location along with roles" +
                        "for each players. Mode 2 does not require a dealer. A random location is " +
                        "picked from series of list and each player is assigned a role, other than the " +
                        "spy of course. Pass the device to each person for them to view their role" +
                        "and location. At the end, list of all the locations along with a timer is " +
                        "provided. Each game is 8 minutes long. The spy has to be able to detect the " +
                        "location before the time is up! Enjoy!" +

                        "\n\n\n ••••• GAME FLOW •••••\n" +
                        "The game begins as soon as the stopwatch starts. One of the player begins by " +
                        "asking any of the members a question, calling him by name: “So tell me, Bob..." +
                        "The questions usually pertain to the current location. This is prudent, but " +
                        "not mandatory. Questions are asked once; no follow-up questions are allowed. " +
                        "Answers can take any form. Then the player who has answered the question " +
                        "proceeds to ask any other player a question of his own, but he cannot ask " +
                        "the player who has just asked him the question (in other words, you cannot " +
                        "ask a question in return). The order in which the questions are asked " +
                        "is established by the players themselves and is based on the suspicions " +
                        "they have after hearing the questions and answers.";


                // Chain together various setter methods to set the dialog characteristics
                builder.setMessage(totalMessage);
                builder.setTitle("Rules");

                // dialog is displayed on the screen.
                builder.show();

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


