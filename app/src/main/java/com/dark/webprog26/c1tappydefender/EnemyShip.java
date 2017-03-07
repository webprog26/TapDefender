package com.dark.webprog26.c1tappydefender;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

/**
 * Created by webpr on 07.03.2017.
 */

public class EnemyShip {

    private Bitmap bitmap;
    private int x, y;
    private int speed = 1;

    // Detect enemies leaving the screen
    private int maxX;
    private int minX;
    // Spawn enemies within screen bounds
    private int maxY;
    private int minY;

    public EnemyShip(Context context, int screenX, int screenY) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;

        Random generator = new Random();
        speed = generator.nextInt(6) + 10;
        x = screenX;
        y = generator.nextInt(maxY) - bitmap.getHeight();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void update(int playerSpeed){
        x -= playerSpeed;
        x -= speed;

        if(x < minX-bitmap.getWidth()){
            Random generator = new Random();
            speed = generator.nextInt(10) + 10;
            x = maxX;y = generator.nextInt(maxY) - bitmap.getHeight();
        }
    }
}
