package com.example.gatavprojekt_001.Playing_Layer.Player;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.support.v4.content.res.ResourcesCompat;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.gatavprojekt_001.R;

public class Player extends SurfaceView implements SurfaceHolder.Callback{

    private float startX;
    private float startY;
    private float baseRadius;
    private float maxHealth;
    private float currentHealth;

    public Player(Context context) {
        super(context);
        setMaxHealth(200);
        setCurrentHealth(100);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        setMaxHealth(100);
        setCurrentHealth(100);
        drawPlayer(startX, startY);
    }


    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    private void setupDimensions(){
        startX = getWidth()/2.0F;
        startY = getHeight()/2.0F;
        baseRadius = 5;
    }

    private void drawPlayer(float posX, float posY) {

        if(getHolder().getSurface().isValid()){
            Canvas myCanvas = this.getHolder().lockCanvas();
            Paint colorPlayer = new Paint();
            colorPlayer.setColor(ResourcesCompat.getColor(getResources(), R.color.HotPink,null));
            myCanvas.drawCircle(posX,posY,baseRadius,colorPlayer);
            getHolder().unlockCanvasAndPost(myCanvas);
        }

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



}
