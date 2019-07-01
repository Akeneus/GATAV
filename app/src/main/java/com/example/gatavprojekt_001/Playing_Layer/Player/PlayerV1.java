package com.example.gatavprojekt_001.Playing_Layer.Player;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.gatavprojekt_001.MainGameActivity;
import com.example.gatavprojekt_001.R;

public class PlayerV1 {

    private float startX;
    private float startY;
    private float inputX = 0;
    private float inputY = 0;
    private float bulletBaseRadius;
    private float playerBaseRadius;
    private float maxHealth;
    private float currentHealth;
    private Context context;
    private float x;
    private long atkSpeed;
    private float speedfactor;

    private float y;
    private boolean canMoveUp = true;
    private boolean canMoveDown = true;
    private boolean canMoveLeft = true;
    private boolean canMoveRight = true;


    //motion speed of the character



    public PlayerV1(Context context, float centerX, float centerY) {
        this.context = context;
        setMaxHealth(200);
        setCurrentHealth(100);
        playerBaseRadius = 75;
        speedfactor = 10;
        x = centerX;
        y = centerY;
        atkSpeed = 1000;
    }




    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x =  x;
    }


    public float getY() {
        return y;
    }

    public void setY( float y) {
        this.y = y;
    }


    public float getSpeed() {
        return speedfactor;
    }


    private void setMaxHealth(float maxHealth){
        this.maxHealth = maxHealth;
    }

    private void setCurrentHealth(float CurrentHealth){
        this.currentHealth = CurrentHealth;
    }


    public float getMaxHealth() {
        return maxHealth;
    }

    public float getCurrentHealth() {
        return currentHealth;
    }

    public float getSize(){
        return playerBaseRadius;
    }


    public void update(){

        if(MainGameActivity.getUserinputX_1() > 0){

            if(canMoveRight){
                x = x + MainGameActivity.getUserinputX_1()*speedfactor;
               Log.d("PLayerV1", "canMoveRight: "+canMoveRight);
            }
        }

        if(MainGameActivity.getUserinputX_1() < 0){

            if(canMoveLeft){
                x = x + MainGameActivity.getUserinputX_1()*speedfactor;
                Log.d("PLayerV1", "canMoveLeft: "+canMoveLeft);
            }
        }

        if(MainGameActivity.getUserinputY_1() < 0){

            if(canMoveUp){
                y = y + MainGameActivity.getUserinputY_1()*speedfactor;
                Log.d("PLayerV1", "canMoveUp: "+canMoveUp);
            }
        }

        if(MainGameActivity.getUserinputY_1() > 0){
            if(canMoveDown){
                y = y + MainGameActivity.getUserinputY_1()*speedfactor;
                Log.d("PLayerV1", "canMoveDown: "+canMoveDown);
            }
        }


    }




    public void draw(Canvas canvas) {
        Paint colorBase = new Paint();
        colorBase.setColor(context.getResources().getColor(R.color.Green));
        canvas.drawCircle(x,y,50,colorBase);
    }

    public long getatkSpeed() {
        return atkSpeed;
    }

    public float getRad() {
        return playerBaseRadius;
    }

    public void setCanMoveDown(boolean cantMoveDown) {
        this.canMoveDown = cantMoveDown;
    }

    public void setCanMoveLeft(boolean cantMoveLeft) {
        this.canMoveLeft = cantMoveLeft;
    }

    public void setCanMoveRight(boolean cantMoveRight) {
        this.canMoveRight = cantMoveRight;
    }

    public void setCanMoveUP(boolean cantMoveUP) {
        this.canMoveUp = cantMoveUP;
    }
}
