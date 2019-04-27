package com.example.gatavprojekt_001;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.gatavprojekt_001.UserInterface_Layer.JoystickListener;
import com.example.gatavprojekt_001.UserInterface_Layer.JoystickView;

public class MainGameActivity extends AppCompatActivity implements JoystickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        configurateBackButton();
        JoystickView joystickleft = (JoystickView) findViewById(R.id.JoystickLeft);
        joystickleft.bringToFront();
    }


    private void configurateBackButton(){
        Button startButton = (Button) findViewById(R.id.back_button);
        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View view){
                finish();
            }
        });
    }

    @Override
    public void onJoystickMoved(float xPercent, float yPercent, int id) {

        switch (id){
            case R.id.JoystickRight:

                Log.d("MainGameActivity","X percent:"+xPercent+" Y percent: "+ yPercent);
                break;

            case R.id.JoystickLeft:

                Log.d("MainGameActivity","X percent:"+xPercent+" Y percent: "+ yPercent);
                break;
        }
    }
}
