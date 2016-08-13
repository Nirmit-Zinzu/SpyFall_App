package com.nirmit.spyfall;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class EndGame extends Activity {

    // end game image
    ImageView gameEnd;
    TextView timer;
    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_end_game);

        // getting reference to the widget
        gameEnd = (ImageView) findViewById(R.id.endGame);
      start = (Button) findViewById(R.id.startBtn);
        timer = (TextView) findViewById(R.id.timer);

        startNewGame(); // goes back to the game setting activity
       startThisGame();
    }

    // new game is started (goes back to the game setting page)
    private void startNewGame() {
        gameEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // activity goes to the game setting page upon click!
                Intent i = new Intent(EndGame.this, GameSetting.class);
                startActivity(i);
            }
        });
    }

    private void startThisGame() {
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CountDownTimer(480000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        long minutes = millisUntilFinished/60000;
                        long seconds = (millisUntilFinished - (minutes*60000)) / 1000;
                        timer.setText(minutes + ":" + seconds);
                    }

                    public void onFinish() {
                        timer.setText("done!");
                    }
                }.start();
            }
        });
    }

}
