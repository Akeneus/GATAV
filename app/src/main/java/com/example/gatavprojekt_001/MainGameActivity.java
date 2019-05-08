package com.example.gatavprojekt_001;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gatavprojekt_001.Background_Layer.MapData;
import com.example.gatavprojekt_001.Background_Layer.MapLoader;

public class MainGameActivity extends AppCompatActivity
{
    private static final String FILE_PATH = "maps/StartMap.tmx";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_game);

        MapData data = MapLoader.ReadTMXFile(FILE_PATH, this);

        ImageView map = findViewById(R.id.Map);

        Bitmap mapImage = MapLoader.CreateBitmap(data, this, 0, data.layers.size());

        if(mapImage != null)
        {
            mapImage = scaleBitmap(mapImage, this);
            map.setImageBitmap(mapImage);
        }
        else
        {
            Toast errorMessage = Toast.makeText(getApplicationContext(), "Map could not be loaded", Toast.LENGTH_LONG);
            errorMessage.show();
        }
    }

    private static Bitmap scaleBitmap(Bitmap map, Context context)
    {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        int displayWidth = size.x;
        int displayHeight = size.y;

        Log.i("Device Width", Integer.toString(displayWidth));
        Log.i("Device Height", Integer.toString(displayHeight));

        return Bitmap.createScaledBitmap(map, displayWidth, displayHeight, true);
    }
}
