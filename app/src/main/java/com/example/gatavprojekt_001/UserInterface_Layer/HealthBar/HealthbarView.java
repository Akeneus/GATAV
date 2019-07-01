package com.example.gatavprojekt_001.UserInterface_Layer.HealthBar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class HealthbarView extends SurfaceView implements SurfaceHolder.Callback{


    private float maxHealth;
    private float currentHealth;
    private Paint borderColor = new Paint();
    private Paint fillColor = new Paint();

    public HealthbarView(Context context) {

        super(context);
        getHolder().addCallback(this);

    }

    public HealthbarView(Context context, AttributeSet attributes){
        super(context,attributes);
        getHolder().addCallback(this);
    }

    public HealthbarView(Context context, AttributeSet attributes, int style){
        super(context,attributes,style);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        drawHealthbar(maxHealth, currentHealth);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    private void drawHealthbar(float maxHealth, float currentHealth){

        fillColor.setARGB(255,30,185,12);

        if(currentHealth < maxHealth*0.50){

            fillColor.setARGB(255,195,139,8);
        }

        if(currentHealth <= maxHealth*0.25){

            fillColor.setARGB(255,164,14,14);
        }


        if(getHolder().getSurface().isValid()){

            Canvas myCanvas = this.getHolder().lockCanvas();
            borderColor.setColor(Color.BLACK);
            borderColor.setStrokeWidth(3);

            /* Text im Zentrum des Healthbars*/
            TextPaint textPaint = new TextPaint();
            textPaint.setColor(Color.WHITE);
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setTextSize(40);
            RectF bounds = new RectF(0, 0, getWidth(), getHeight());
            float textHeight = textPaint.descent() - textPaint.ascent();
            float textOffset = (textHeight / 2) - textPaint.descent();

            myCanvas.drawRect(0, 0, getWidth(), getHeight(), borderColor);
            myCanvas.drawRect(0, 5,  getWidth()*(currentHealth/maxHealth), getHeight()-5, fillColor);
            myCanvas.drawText((int)currentHealth+" / "+(int)maxHealth, bounds.centerX(), bounds.centerY() + textOffset, textPaint);
            getHolder().unlockCanvasAndPost(myCanvas);
        }
    }

    public void UpdateHealthBar(float Dmg, float Heal, float IncMaxHealth, float DecMaxHealth){

        while(currentHealth+Heal>this.maxHealth){
                Heal -= 1;
            }

        if(this.currentHealth - Dmg<0){
            Log.d("DeathMassage" ,"U ded lul");
                   }

        this.currentHealth += Heal;
        this.currentHealth -= Dmg;

        Log.d("CheckHealthOverflow", "CurrentHealth:" +this.currentHealth+" MaxHealth: "+this.maxHealth);
        this.maxHealth += IncMaxHealth;
        this.maxHealth -= DecMaxHealth;
        drawHealthbar(maxHealth,currentHealth);


    }

    public void setMaxHealth(float maxHealth){
        this.maxHealth = maxHealth;
    }

    public void setCurrentHealth(float CurrentHealth){
        this.currentHealth = CurrentHealth;
    }


    public float getMaxHealth() {
        return maxHealth;
    }

    public float getCurrentHealth() {
        return currentHealth;
    }

}
