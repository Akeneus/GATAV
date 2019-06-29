package com.example.gatavprojekt_001.Playing_Layer.Player.drawable;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.gatavprojekt_001.MainGameActivity;
import com.example.gatavprojekt_001.R;

import java.io.InputStream;

public abstract class MovableObject extends Graphics{

    private float speed = 10f;
    public void setSpeed(float speed) { this.speed = speed; }

    // Bewegungskoordinaten
    protected float sourceX, sourceY;
    protected float currentX, currentY;     // Gleitkomma-Koordinaten zur Bewegung zwischen zwei Kacheln
    protected float targetX, targetY;



    public MovableObject(float x, float y) {
        super(x,y);
        currentX = x;
        currentY = y;
    }

    @Override
    public void move(float x, float y) {
        // einmalig die Bewegung festlegen
        // mittels der gesetzten Direction lassen sich auch weitere Eingaben blocken,
        // bis die Bewegung schließlich (mittels updates) komplett durchgeführt wurde

        // Quelle und Zielblock festlegen
        sourceX = this.x;
        sourceY = this.y;
        targetX = x;
        targetY = y;

        // normaler Move,  vorab logisch schon einmal auf die neue Kachel vornehmen
        super.move(x, y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(float fracsec) {
        float deltaX = targetX - sourceX;
        float deltaY = targetY - sourceY;
        float playerMovementX = MainGameActivity.getUserinputX_1();
        float playerMovementY = MainGameActivity.getUserinputY_1();

        currentX = currentX +playerMovementX;
        currentY = currentY+playerMovementY;



    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Canvas canvas) {

    }
}
