package com.dark.webprog26.c1tappydefender;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by webpr on 07.03.2017.
 */

public class PlayerShip {

    private static final int START_X_POSITION = 50;
    private static final int START_Y_POSITION = 50;
    private static final int MIN_SPEED = 1;
    private static final int MAX_SPEED = 20;
    private final int GRAVITY = -12;
    private int maxY;
    private int minY;

    private Bitmap bitmap;
    private int x, y;
    private int speed = 0;
    private boolean boosting;

    public PlayerShip(Context context, int screenX, int screenY) {
        this.x = START_X_POSITION;
        this.y = START_Y_POSITION;
        this.speed = MIN_SPEED;
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship);
        boosting = false;
        this.maxY = screenY - bitmap.getHeight();
        minY = 0;
    }

    public void update(){
        if(boosting){
            speed += 2;
        } else {
            speed -= 5;
        }

        if(speed > MAX_SPEED){
            speed = MAX_SPEED;
        }

        if(speed < MIN_SPEED){
            speed = MIN_SPEED;
        }

        this.y -= speed + GRAVITY;

        if(y < minY){
            y = minY;
        }

        if(y > maxY){
            y = maxY;
        }
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setBoosting() {
        this.boosting = true;
    }

    public void stopBoosting() {
        this.boosting = false;
    }
}
