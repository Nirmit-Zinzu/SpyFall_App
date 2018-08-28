package com.nirmit.spyfall;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class EndGame extends Activity {

    private static long GAME_TIME = 480000;
    private static boolean timerRunning = false;
    private static long timeLeft = GAME_TIME;
    GameSetting gameSetting;
    // end game image
    TextView timer, locationList;
    Switch timeSwitch;
    Button gameEnd;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_end_game);


        gameSetting = new GameSetting();
        timeLeft = GAME_TIME;

        // getting reference to the widget
        gameEnd = findViewById(R.id.endGameBtn);
        timer = findViewById(R.id.timeView);
        locationList = findViewById(R.id.locationList);
        timeSwitch = findViewById(R.id.timerSwitch);

        addClickEffect(gameEnd);

        updateCountDownText();  //
        startNewGame();        // goes back to the game setting activity
        timeController();
        setLocationList();

    }

    // Updates timer text each second
    private void updateCountDownText() {
        long minutes = (timeLeft / 1000) / 60;
        long seconds = (timeLeft / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timer.setText(timeLeftFormatted);
    }

    // Sets the timer value to 8 minutes when user exit the view
    @Override
    public void onBackPressed() {

        if (timerRunning == true) {
            countDownTimer.cancel();
            timerRunning = false;
            timeLeft = GAME_TIME;
        }
        super.onBackPressed();
    }

    private void timeController() {
        timeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                // if switch is turned on,
                if (bChecked) {

                    countDownTimer = new CountDownTimer(timeLeft, 1000) {

                        // updating timer every second
                        public void onTick(long millisUntilFinished) {
                            timeLeft = millisUntilFinished;
                            updateCountDownText();
                        }

                        public void onFinish() {
                            timer.setText("Done!");
                            timerRunning = false;
                        }
                    }.start();

                    timerRunning = true;

                } else {
                    if (timerRunning == true) {
                        countDownTimer.cancel();
                        timerRunning = false;
                    }

                }
            }
        });
    }

    // new game is started (goes back to the game setting page)
    private void startNewGame() {
        gameEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // activity goes to the game setting page upon click!
                if (timerRunning == true) {
                    countDownTimer.cancel();
                    timerRunning = false;
                    timeLeft = GAME_TIME;
                }
                Intent i = new Intent(EndGame.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    // Displays location list at the end.
    private void setLocationList() {
        String locationText = "\n";
        int number;

        List<String> defaultLocations = gameSetting.getKeysList();

        // setting it to alphabetical order
        if (defaultLocations != null) {
            java.util.Collections.sort(defaultLocations);
        }

        for (int i = 0; i < defaultLocations.size(); i++) {
            number = i + 1;
            locationText += number + ". " + defaultLocations.get(i) + "\n";
        }

        locationList.setText(locationText);
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
