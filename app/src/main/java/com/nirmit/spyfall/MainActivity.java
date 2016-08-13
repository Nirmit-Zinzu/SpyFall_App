package com.nirmit.spyfall;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

public class MainActivity extends Activity {

    // Image buttons on the main page
    ImageButton playButton, webLogo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        playButton = (ImageButton) findViewById(R.id.playBtn);
        webLogo = (ImageButton) findViewById(R.id.nZlogo);

        playGame();  // goes to the next activity
        goToMyWeb(); // goes to my website
    }


    private void playGame() {
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // goes to game setting activity
                Intent intent = new Intent(MainActivity.this, GameSetting.class);
                startActivity(intent);
            }
        });
    }

    private void goToMyWeb() {
        webLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUrl ("https://nirmit-zinzu.github.io/");
            }
        });
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}


