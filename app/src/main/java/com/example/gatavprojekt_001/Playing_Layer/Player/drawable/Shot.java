package com.example.gatavprojekt_001.Playing_Layer.Player.drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.gatavprojekt_001.R;

public class Shot implements Drawable {

    private final float bulletSize;
    private float x;
    private float y;

    private float  InputX;
    private float InputY;
    private float bulletSpeed;

    private float  startX;
    private float startY;
    private boolean inBounds;

    private Context context;
    private PlayerV1 player = null;

    private boolean isActive;

    public Shot(Context context,float initPositionX, float initPositionY){
        //this.player = player;
        this.context = context;
        x =  initPositionX;
        y =  initPositionY;
        isActive = true;
        bulletSpeed = 10;
        InputX = 1;
        InputY = 1;
        inBounds = true;
        bulletSize = 10;
    }


    @Override
    public void draw(Canvas canvas) {
        if(isActive){
            Paint colorBase = new Paint();
            colorBase.setColor(context.getResources().getColor(R.color.Yellow));
            canvas.drawCircle(x,y,bulletSize,colorBase);
        }
    }

    @Override
    public void update(float fracsec) {

        float changeX = bulletSpeed*InputX;
        float changeY = bulletSpeed*InputY;
        float minSpeed = 3;
        if(changeX > minSpeed || changeX < -minSpeed) {
            x = x+changeX;
        }

        if(changeY > minSpeed || changeY < -minSpeed){
            y = y+changeY;
        }




    }

    public void updateBullet() {


        if(InputX > 0){
            x = x+bulletSpeed*InputX;
        }
        if(InputX < 0){
            x = x+bulletSpeed*InputX;
        }
        if(InputY > 0){
            y = y+bulletSpeed*InputY;
        }
        if(InputY < 0){
            y = y+bulletSpeed*InputY;
        }



    }

    public boolean getStatus(){
        return isActive;
    }

    public void setInactive(){
        isActive = false;
    }

    public void setInputY(float inputY) {
        InputY = inputY;
    }

    public void setInputX(float inputX) {
        InputX = inputX;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setInBounds(boolean inBounds) {
        this.inBounds = inBounds;
    }

    public float getRad() {
        return bulletSize;
    }
}
