package com.example.gatavprojekt_001.Playing_Layer.Player;

import android.graphics.Bitmap;

public abstract class GameObject {

    private Bitmap image;

    private final int rowCount;
    private final int colCount;

    private final int WIDTH;
    private final int HEIGHT;

    protected final int width;


    protected final int height;
    protected int x;
    protected int y;

    public GameObject(Bitmap image, int rowCount, int colCount, int x, int y)  {

        this.image = image;
        this.rowCount= rowCount;
        this.colCount= colCount;

        this.x= x;
        this.y= y;

        this.WIDTH = image.getWidth();
        this.HEIGHT = image.getHeight();

        this.width = this.WIDTH/ colCount;
        this.height= this.HEIGHT/ rowCount;
    }


    protected Bitmap createSubImageAt(int row, int col)  {
        // createBitmap(bitmap, x, y, width, height).
        return Bitmap.createBitmap(image, col* width, row* height ,width,height);
    }

    public int getX()  {
        return this.x;
    }

    public int getY()  {
        return this.y;
    }


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

}