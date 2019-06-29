package com.example.gatavprojekt_001.Playing_Layer.Player;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import com.example.gatavprojekt_001.MainGameActivity;
import com.example.gatavprojekt_001.Playing_Layer.Player.drawable.Drawable;

public class GameContent implements Drawable {


    private Context context;
    private PlayerV1 player = null;

    public GameContent(Context context,float centerX, float centerY){
        this.context = context;
        player = new PlayerV1(context, centerX,centerY);


    }




    @Override
    public void draw(Canvas canvas) {
       // player.draw(canvas);
        player.draw(canvas);
        //joystickLeft.draw(canvas);
        //joystickRight.draw(canvas);
     //   joystickRight.draw(canvas);
    }

    @Override
    public void update(float fracsec) {
        player.update();
    }
}
