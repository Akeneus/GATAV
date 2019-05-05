package com.example.gatavprojekt_001;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.gatavprojekt_001.Playing_Layer.Player.Player;
import com.example.gatavprojekt_001.UserInterface_Layer.HealthBar.HealthbarView;
import com.example.gatavprojekt_001.UserInterface_Layer.Joystick.JoystickListener;
import com.example.gatavprojekt_001.UserInterface_Layer.Joystick.JoystickView;

public class MainGameActivity extends AppCompatActivity implements JoystickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Player player = new Player(this);
        HealthbarView healthbar = findViewById(R.id.Healthbar);
        healthbar.setMaxHealth(player.getMaxHealth());
        healthbar.setCurrentHealth(player.getCurrentHealth());
        configurateBackButton();
        configurateIncreaseHP(healthbar);
        configurateDecreaseHP(healthbar);
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


    private void configurateIncreaseHP(final HealthbarView healthbar){
        Button startButton = (Button) findViewById(R.id.increaseHP_test_button);
        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View view){
                healthbar.UpdateHealthBar(0 ,10,0,0);
            }
        });
    }


    private void configurateDecreaseHP(final HealthbarView healthbar){
        Button startButton = (Button) findViewById(R.id.decreaseHP_test_button);
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

                Log.d("MainGameActivity","X percent:"+xPercent+" Y percent: "+ yPercent);
                break;

            case R.id.JoystickLeft:

                Log.d("MainGameActivity","X percent:"+xPercent+" Y percent: "+ yPercent);
                break;
        }
    }
}
