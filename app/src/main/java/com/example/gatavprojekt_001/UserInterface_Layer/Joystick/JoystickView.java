package com.example.gatavprojekt_001.UserInterface_Layer.Joystick;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.gatavprojekt_001.R;


public class JoystickView extends SurfaceView implements SurfaceHolder.Callback,View.OnTouchListener {


    private float centerX;
    private float centerY;
    private float baseRadius;
    private float hatRadius;
    private JoystickListener joystickCallback;



    public JoystickView(Context context){
        super(context);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(context instanceof JoystickListener){
            joystickCallback = (JoystickListener) context;
        }
        this.bringToFront();
    }

    public JoystickView(Context context, AttributeSet attributes){
        super(context,attributes);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(context instanceof JoystickListener){
            joystickCallback = (JoystickListener) context;
        }
        this.bringToFront();
    }

    public JoystickView(Context context, AttributeSet attributes, int style){
        super(context,attributes,style);
        getHolder().addCallback(this);
        setOnTouchListener( this);
        if(context instanceof JoystickListener){
            joystickCallback = (JoystickListener) context;
        }
        this.bringToFront();
    }

    private void setupDimensions(){
        centerX = getWidth()/2.0F;
        centerY = getHeight()/2.0F;
        baseRadius = Math.min(getWidth(),getHeight())/3.0F;
        hatRadius = Math.min(getWidth(),getHeight())/5.0F;
    }

    private void drawJoystick(float newX, float newY){


        if(getHolder().getSurface().isValid()){
            Canvas myCanvas = this.getHolder().lockCanvas();
            Paint colorBase = new Paint();
            Paint colorHat = new Paint();
            myCanvas.drawColor(Color.argb(255,0,0,0),PorterDuff.Mode.CLEAR);
            colorBase.setColor(ResourcesCompat.getColor(getResources(), R.color.HotPink,null));
            colorHat.setColor(ResourcesCompat.getColor(getResources(), R.color.Aqua,null));
            myCanvas.drawCircle(centerX,centerY,baseRadius,colorBase); //draw Base
            myCanvas.drawCircle(newX,newY,hatRadius,colorHat); //draw Hat
            getHolder().unlockCanvasAndPost(myCanvas);
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        setupDimensions();
        drawJoystick(centerX,centerY);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    public boolean onTouch(View v, MotionEvent e)
    {
        if(v.equals(this)){
            if(e.getAction()!= MotionEvent.ACTION_UP){
                float displacement = (float)Math.sqrt(Math.pow(e.getX()-centerX,2)+Math.pow(e.getY()-centerY,2));
                if(displacement<baseRadius){
                    drawJoystick(e.getX(),e.getY());
                    joystickCallback.onJoystickMoved((e.getX()-centerX)/baseRadius,(e.getY()-centerY)/baseRadius,getId());
                }
                else{
                    float ratio = baseRadius/displacement;
                    float constrainedX = centerX+(e.getX()-centerX)*ratio;
                    float constrainedY = centerY+(e.getY()-centerY)*ratio;
                    drawJoystick(constrainedX,constrainedY);
                    joystickCallback.onJoystickMoved((constrainedX-centerX)/baseRadius,(constrainedY-centerY)/baseRadius,getId());
                }

            }
            else{
                drawJoystick(centerX,centerY);
                joystickCallback.onJoystickMoved(0,0,getId());
            }
        }
        return true;
    }

}
