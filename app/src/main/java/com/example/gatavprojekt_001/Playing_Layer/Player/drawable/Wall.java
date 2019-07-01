package com.example.gatavprojekt_001.Playing_Layer.Player.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Wall implements Drawable {


    private final float wallWidth;
    private final float wallHeight;
    private float posX;
    private float posY;
    private Rect wall;
    private float posLEFT;
    private float posRIGHT;
    private float posTOP;
    private float posBOTTOM;


    public Wall (float centerX, float centerY){


        posX = centerX+100;
        posY = centerY+100;

        wallWidth = 600;
        wallHeight = 150;

        posLEFT = posX-wallWidth;
        posTOP = posY;
        posRIGHT = posX+wallWidth;
        posBOTTOM = posY+wallHeight;

        wall = new Rect((int)posLEFT,(int)posTOP,(int)posRIGHT,(int)posBOTTOM);



    }


    @Override
    public void draw(Canvas canvas) {
        Paint myPaint = new Paint();
        myPaint.setColor(Color.rgb(0, 0, 0));
        myPaint.setStrokeWidth(10);
        canvas.drawRect(wall,myPaint);
    }

    @Override
    public void update(float fracsec) {

    }

    public float getPosBOTTOM() {
        return posBOTTOM;
    }

    public float getPosLEFT() {
        return posLEFT;
    }

    public float getPosRIGHT() {
        return posRIGHT;
    }

    public float getPosTOP() {
        return posTOP;
    }

    public float getWallHeight() {
        return wallHeight;
    }

    public float getWallWidth() {
        return wallWidth;
    }
}
