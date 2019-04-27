package com.example.gatavprojekt_001;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class StartupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_startup);

        configurateStartButton();
        configurateHighScoreButton();
        configurateOptionsButton();
        configurateCloseButton();
    }

    private void configurateStartButton(){
        Button startButton = findViewById(R.id.Start_button);
        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View view){
                startActivity(new Intent(StartupActivity.this,MainGameActivity.class));
            }
        });
    }

    private void configurateHighScoreButton(){
        Button startButton = findViewById(R.id.Highscore_button);
        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View view){
                startActivity(new Intent(StartupActivity.this,HighScoreActivity.class));
            }
        });
    }

    private void configurateOptionsButton(){
        Button startButton = findViewById(R.id.Options_button);
        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View view){
                startActivity(new Intent(StartupActivity.this,OptionsActivity.class));
            }
        });
    }

    private void configurateCloseButton(){
        Button startButton = findViewById(R.id.Beenden_button);
        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View view){
               finish();
            }
        });
    }

}
