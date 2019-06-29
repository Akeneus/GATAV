package com.example.gatavprojekt_001.Playing_Layer.Player.drawable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.gatavprojekt_001.R;

import java.io.InputStream;

class Graphics implements Drawable {


    protected float x;
    protected float y;

    protected static float tileSize = 45f;

    protected Paint tilePaint = new Paint();
    protected Bitmap tileBitmap = null;

    /**
     * Liefert Auskunft darüber, ob ein Block für den Spieler passierbar ist
     * @return <code>true</code> wenn passierbar, andernfalls <code>false</code>
     */

    public Graphics(float x, float y) {
        this.x = x;
        this.y = y;
    }


    /**
     * Verschieben des Blockes an neue Gitterkoordinaten
     * @param x neue X-Koordinate
     * @param y neue Y-Koordinate
     */
    public void move(float x, float y) {
        this.x = x;
        this.y = y;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void update(float fracsec) {}


    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Canvas canvas) {


    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}
