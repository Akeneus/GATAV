package com.example.gatavprojekt_001.Playing_Layer.Player;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import com.example.gatavprojekt_001.MainGameActivity;
import com.example.gatavprojekt_001.Playing_Layer.Player.drawable.Drawable;
import com.example.gatavprojekt_001.Playing_Layer.Player.drawable.PlayerV1;
import com.example.gatavprojekt_001.Playing_Layer.Player.drawable.Shot;
import com.example.gatavprojekt_001.Playing_Layer.Player.drawable.Wall;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameContent implements Drawable {


    private Context context;
    private PlayerV1 player = null;
    private ArrayList<Shot> playerBullets;
    private ArrayList<Wall> wallList;

    private Shot testshot;
    private float rangeX;
    private float rangeY;
    Thread shootingThread;
    private Wall wall;

    public boolean drawBullet = false;

    Runnable drawbullet = new Runnable() {
        @Override
        public void run() {
            float inputX = MainGameActivity.getUserinputX_2();
            float inputY = MainGameActivity.getUserinputY_2();
            Log.d("test123414214", "inpoutX =>" + inputX + " inpoutY =>" + inputY);
            if (inputX != 0 || inputY != 0) {
                addBullet(inputX, inputY);
            }
        }
    };

    public GameContent(Context context, float centerX, float centerY) {


        this.context = context;
        rangeX = centerX * 2;
        rangeY = centerY * 2;

        player = new PlayerV1(context, centerX, centerY);
        playerBullets = new ArrayList<Shot>();

        wall = new Wall(centerX, centerY);
        wallList = new ArrayList<Wall>();
        wallList.add(wall);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(drawbullet, 0, player.getatkSpeed(), TimeUnit.MILLISECONDS);
    }


    public void addBullet(float directionX, float directionY) {
        Shot shot = new Shot(context, player.getX(), player.getY());
        shot.setInputX(directionX);
        shot.setInputY(directionY);
        playerBullets.add(shot);
    }

    @Override
    public void draw(Canvas canvas) {


        player.draw(canvas);

        wall.draw(canvas);


        // player.draw(canvas);

        if (playerBullets.size() > 0) {
            for (int i = 0; i < playerBullets.size(); i++) {
                //    if (playerBullets.get(i).shoot(player.getX(),player.getY())) {
                if (checkInBounde(playerBullets.get(i))) {
                    playerBullets.get(i).draw(canvas);
                }
                //    }
            }
        }


        //joystickLeft.draw(canvas);
        //joystickRight.draw(canvas);
        //   joystickRight.draw(canvas);

        detectCollisions();
    }

    @Override
    public void update(float fracsec) {

        player.update();

        for (int i = 0; i < playerBullets.size(); i++) {
            if (checkInBounde(playerBullets.get(i))) {
                playerBullets.get(i).update(fracsec);

            }
        }

    }

    private boolean checkInBounde(Shot shot) {

        if (shot.getX() > rangeX || shot.getY() > rangeY || shot.getX() < 0 || shot.getY() < 0) {
            return false;
        }
        return true;


    }

    private void detectCollisions() {

        //detect collision Bullets
        for (int y = 0; y < playerBullets.size(); y++) {
            //collision Bullet Enemey
            //for(int x = 0; x < Enemeys.size(); x++){}


            float bulletHitbox = playerBullets.get(y).getRad();
            //collision Bullet Wall

            for (int z = 0; z < wallList.size(); z++) {
                if
                (
                        ((playerBullets.get(y).getX()-bulletHitbox >= wallList.get(z).getPosLEFT() && playerBullets.get(y).getX()-bulletHitbox <= wallList.get(z).getPosRIGHT()) ||
                                (playerBullets.get(y).getX()+bulletHitbox <= wallList.get(z).getPosLEFT() && playerBullets.get(y).getX()+bulletHitbox >= wallList.get(z).getPosRIGHT()))
                                &&
                                ((playerBullets.get(y).getY()-bulletHitbox >= wallList.get(z).getPosTOP() && playerBullets.get(y).getY()-bulletHitbox <= wallList.get(z).getPosBOTTOM()) ||
                                        (playerBullets.get(y).getY()+bulletHitbox <= wallList.get(z).getPosTOP() && playerBullets.get(y).getY()+bulletHitbox >= wallList.get(z).getPosBOTTOM()))
                )
                {
                    Log.d("GameContent", "detectCollisions1:");
                    playerBullets.get(y).setInactive();
                }
            }
        }

        //detect collision Player

        //detect Player-> Wall

        float playerdistance = player.getRad()*0.7F;

        for(int z = 0; z < wallList.size(); z++){

            if((player.getY()+playerdistance >= wallList.get(z).getPosBOTTOM() && player.getY()-playerdistance <= wallList.get(z).getPosBOTTOM())
                    &&
                    ((player.getX()+playerdistance >= wallList.get(z).getPosLEFT() && player.getX()-playerdistance <= wallList.get(z).getPosRIGHT())))
            {
                player.setCanMoveUP(false);
            }
            else
                {
                        player.setCanMoveUP(true);
                }

            if((player.getY()+playerdistance >= wallList.get(z).getPosTOP() && player.getY()-playerdistance <= wallList.get(z).getPosTOP())
                    &&
                    ((player.getX()+playerdistance >= wallList.get(z).getPosLEFT() && player.getX()-playerdistance <= wallList.get(z).getPosRIGHT())))
            {
                player.setCanMoveDown(false);
            }
            else
            {
                player.setCanMoveDown(true);
            }


            if((player.getX()+playerdistance >= wallList.get(z).getPosRIGHT() && player.getX()-playerdistance <= wallList.get(z).getPosRIGHT())
                    &&
                    ((player.getY()+playerdistance >= wallList.get(z).getPosTOP() && player.getY()-playerdistance <= wallList.get(z).getPosBOTTOM())))
            {
                player.setCanMoveLeft(false);
            }
            else
            {
                player.setCanMoveLeft(true);
            }

            if((player.getX()+playerdistance >= wallList.get(z).getPosLEFT() && player.getX()-playerdistance <= wallList.get(z).getPosLEFT())
                    &&
                    ((player.getY()+playerdistance >= wallList.get(z).getPosTOP() && player.getY()-playerdistance <= wallList.get(z).getPosBOTTOM())))
            {
                player.setCanMoveRight(false);
            }
            else
            {
                player.setCanMoveRight(true);
            }


        }
    }
}



        //detect collision Player

        //detect Player-> Wall

        //detect Player->enemy



        //detect collision Player

        //detect Enemy-> Wall

        //detect Enemy->Player

        //detect EnemyBullet -> Player












