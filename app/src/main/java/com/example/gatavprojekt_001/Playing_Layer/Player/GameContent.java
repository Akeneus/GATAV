package com.example.gatavprojekt_001.Playing_Layer.Player;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import com.example.gatavprojekt_001.MainGameActivity;
import com.example.gatavprojekt_001.Playing_Layer.Player.drawable.Drawable;
import com.example.gatavprojekt_001.Playing_Layer.Player.drawable.Shot;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameContent implements Drawable {


    private Context context;
    private PlayerV1 player;
    private ArrayList<Shot> playerBullets;
    private Shot testshot;
    private float rangeX;
    private float rangeY;
    Thread shootingThread;

    public boolean drawBullet = false;

    private Runnable drawbullet = new Runnable() {
        @Override
        public void run() {
            float inputX = MainGameActivity.getUserinputX_2();
            float inputY = MainGameActivity.getUserinputY_2();
            Log.d("test123414214", "inpoutX =>"+inputX+" inpoutY =>"+inputY);
            if(inputX !=0 || inputY!=0){
                    addBullet(inputX, inputY);
            }
        }
    };

    GameContent(Context context,float centerX, float centerY){


        this.context = context;
        rangeX = centerX *2;
        rangeY = centerY *2;

        player = new PlayerV1(context, centerX,centerY);
        playerBullets = new ArrayList<>();


        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(drawbullet,0,player.getAtkSpeed(),TimeUnit.MILLISECONDS);
    }


    private void addBullet(float directionX, float directionY) {
        Shot shot = new Shot(context,player.getX(),player.getY());
        shot.setInputX(directionX);
        shot.setInputY(directionY);
        playerBullets.add(shot);
    }

    @Override
    public void draw(Canvas canvas) {


        player.draw(canvas);



        // player.draw(canvas);

        if(playerBullets.size() > 0){
            for(int i = 0; i < playerBullets.size();i++) {
                //    if (playerBullets.get(i).shoot(player.getX(),player.getY())) {
                if(checkInBounde(playerBullets.get(i))){
                    playerBullets.get(i).draw(canvas);
                }
                //    }
            }
        }







        //joystickLeft.draw(canvas);
        //joystickRight.draw(canvas);
     //   joystickRight.draw(canvas);
    }

    @Override
    public void update(float fracsec) {

        player.update();

        for(int i = 0; i < playerBullets.size();i++){
            if(checkInBounde(playerBullets.get(i))){
                playerBullets.get(i).update(fracsec);

            }
        }

    }

    private boolean checkInBounde(Shot shot) {

        return !(shot.getX() > rangeX) && !(shot.getY() > rangeY) && !(shot.getX() < 0) && !(shot.getY() < 0);


    }



}
