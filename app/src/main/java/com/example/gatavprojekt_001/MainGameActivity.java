package com.example.gatavprojekt_001;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Button;

import com.example.gatavprojekt_001.Playing_Layer.Player.GameViewV2;
import com.example.gatavprojekt_001.UserInterface_Layer.HealthBar.HealthbarView;
import com.example.gatavprojekt_001.UserInterface_Layer.Joystick.JoystickListener;

public class MainGameActivity extends AppCompatActivity implements JoystickListener {


    GameViewV2 gameViewV2;
    private static float x = 0;
    private static float y = 0;

    private static float x2 = 0;
    private static float y2 = 0;

    public static float getUserInputX_1() {
        return x;
    }

    public static float getUserInputY_1() {
        return y;
    }


    public static float getUserInputX_2() {return x2;}

    public static float getUserInputY_2() {return y2;}


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //configurateBackButton();

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        gameViewV2 = new GameViewV2(this);

        //this.setContentView(gameView);
        //this.setContentView(R.layout.activity_main_game);
        this.setContentView(R.layout.activity_main_game_v2);
    }



    private void configurateBackButton(){
        Button startButton = findViewById(R.id.back_button);
        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View view){
                finish();
            }
        });
    }


    private void configurateIncreaseHP(final HealthbarView healthbar){
        Button startButton = findViewById(R.id.increaseHP_test_button);
        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View view){
                healthbar.UpdateHealthBar(0 ,10,0,0);
            }
        });
    }


    private void configurateDecreaseHP(final HealthbarView healthbar){
        Button startButton = findViewById(R.id.decreaseHP_test_button);
        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View view){
                healthbar.UpdateHealthBar(10,0,0,0);
            }
        });
    }


    @Override
    public void onJoystickMoved(float xPercent, float yPercent, int id) {

        switch (id){
            case R.id.JoystickRight:

               // Log.d("MainGameActivity","X percent:"+xPercent+" Y percent: "+ yPercent);

                x2 = xPercent;
                y2 = yPercent;
                break;

            case R.id.JoystickLeft:

           //     Log.d("MainGameActivity","X percent:"+xPercent+" Y percent: "+ yPercent);


                x = xPercent;
                y = yPercent;



                break;
        }

    }

/*
    //pausing the game when activity is paused
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    //running the game when activity is resumed
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }
*/
}

