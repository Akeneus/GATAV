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

    //motion speed of the character



    PlayerV1(Context context, float centerX, float centerY) {
        this.context = context;
        setMaxHealth(200);
        setCurrentHealth(100);
        playerBaseRadius = 75;
        speedfactor = 5;
        x = centerX;
        y = centerY;
        atkSpeed = 1000;
    }




    float getX() {
        return x;
    }

    public void setX(float x) {
        this.x =  x;
    }


    float getY() {
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


    void update(){
        x = x + MainGameActivity.getUserInputX_1()*speedfactor;
        y = y + MainGameActivity.getUserInputY_1()*speedfactor;

    }




    void draw(Canvas canvas) {
        Paint colorBase = new Paint();
        colorBase.setColor(context.getResources().getColor(R.color.Green));
        canvas.drawCircle(x,y,50,colorBase);
    }

    long getAtkSpeed() {
        return atkSpeed;
    }
}
