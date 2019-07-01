package com.example.gatavprojekt_001.Playing_Layer.Player;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.gatavprojekt_001.Background_Layer.MapData;
import com.example.gatavprojekt_001.Background_Layer.MapLoader;
import com.example.gatavprojekt_001.MainGameActivity;
import com.example.gatavprojekt_001.R;
import com.example.gatavprojekt_001.UserInterface_Layer.Joystick.JoystickListener;

import static android.view.MotionEvent.ACTION_POINTER_DOWN;

public class GameViewV2 extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder surfaceHolder;

    private Thread gameThread;
    private boolean runningRenderLoop = false;
    public boolean gameOver=false;

    private String levelName;
    private static final String FILE_PATH = "maps/StartMap.tmx";
    private Bitmap mapImage;

    private int displayWidth;
    private int displayHeight;

    private Thread timeThread;
    private volatile boolean runningTimeThread=false;    // access to elementary data types (not double or long) are atomic and should be volatile to synchronize content
    private volatile double elapsedTime = 0.0;

    private GameContent gameContent;

    private GestureDetectorCompat gestureDetector;

    float userinputX_1;
    float userinputY_1;
  //  float userinputX_2;
  //  float userinputY_2;



    synchronized private void resetElapsedTime() { elapsedTime = 0.0;}
    synchronized private double getElapsedTime() { return elapsedTime; }
    synchronized private void increaseElapsedTime(double increment) { elapsedTime += increment; }


    public GameViewV2(Context context){
        super(context);
        MapData data = MapLoader.ReadTMXFile(FILE_PATH, context);
        mapImage = MapLoader.CreateBitmap(data, context, 0, data.layers.size());
        mapImage = scaleBitmap(mapImage, context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        getHolder().addCallback(this);
    }
    public GameViewV2(Context context, AttributeSet attributes){
        super(context,attributes);
        MapData data = MapLoader.ReadTMXFile(FILE_PATH, context);
        mapImage = MapLoader.CreateBitmap(data, context, 0, data.layers.size());
        mapImage = scaleBitmap(mapImage, context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        getHolder().addCallback(this);

    }

    public GameViewV2(Context context, AttributeSet attributes, int style){
        super(context,attributes,style);
        MapData data = MapLoader.ReadTMXFile(FILE_PATH, context);
        mapImage = MapLoader.CreateBitmap(data, context, 0, data.layers.size());
        mapImage = scaleBitmap(mapImage, context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        getHolder().addCallback(this);

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        gameContent = new GameContent(getContext(), getWidth()/2F, getHeight()/2F);

        // Reset der Zustände bei "onResume"
        gameOver=false;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        // Gameloop and Time Thread beenden
        runningRenderLoop = false;
        runningTimeThread = false;
        gameOver=false;

        try {
            gameThread.join();
            if(timeThread != null)  // überhaupt gestartet?
                timeThread.join();
        }catch(InterruptedException e) {
            e.printStackTrace();
        }
    }


    public  void startTimeThread() {
        if(runningTimeThread) return;
        runningTimeThread = true;
        resetElapsedTime();
        timeThread = new Thread(new Runnable() {
            public void run() {
                while (runningTimeThread) {
                    increaseElapsedTime(0.01);

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        runningTimeThread=false;
                    }
                }
            }});
        timeThread.start();
    }


    @Override
    public void run() {

        runningRenderLoop = true;

        long lastTime = System.currentTimeMillis();

        while(runningRenderLoop) {

            long currentTime = System.currentTimeMillis();
            long delta = currentTime - lastTime;
            float fracsec = (float)delta / 1000f;
            lastTime = currentTime;

            Canvas canvas = surfaceHolder.lockCanvas();
            if(canvas == null) continue;




            if(!gameOver)
                updateContent(fracsec); // kompletten Spielzustand aktualisieren



            updateGraphics(canvas); // Neu zeichnen

            surfaceHolder.unlockCanvasAndPost(canvas);

          //  Log.d("GameViewV2","check gameContent null: " + (gameContent == null));
        }
    }

    private void updateGraphics(Canvas canvas) {
        // Layer 0 (clear background)
        canvas.drawColor(getResources().getColor(R.color.red));
        canvas.drawBitmap(mapImage, new Rect(0,0,displayWidth, displayHeight), new Rect(0,0,displayWidth, displayHeight), null);
        // Layer 1 (Game content)
       // Log.d("GameViewV2","check gameContent null: " + (gameContent == null));
        if(gameContent == null) return;
        gameContent.draw(canvas);
        canvas.save();
    }


    private Bitmap scaleBitmap(Bitmap map, Context context)
    {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        displayWidth = size.x;
        displayHeight = size.y;

        Log.i("Device Width", Integer.toString(displayWidth));
        Log.i("Device Height", Integer.toString(displayHeight));

        return Bitmap.createScaledBitmap(map, displayWidth, displayHeight, true);
    }


    void updateContent(float fracsec) {
        if(gameContent != null)
            gameContent.update(fracsec);
    }

}
