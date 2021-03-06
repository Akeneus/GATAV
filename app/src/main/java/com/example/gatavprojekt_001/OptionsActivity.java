package com.example.gatavprojekt_001;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        configureBackButton();
    }


    private void configureBackButton() {
        Button startButton = findViewById(R.id.back_button);
        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View view){
                finish();
            }
        });
    }
}
