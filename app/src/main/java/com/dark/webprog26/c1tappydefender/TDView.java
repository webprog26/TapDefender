package com.dark.webprog26.c1tappydefender;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by webpr on 07.03.2017.
 */

public class TDView extends SurfaceView implements Runnable{

    private static final String TAG = "TDView";

    public ArrayList<SpaceDust> dustList = new ArrayList<>();

    volatile boolean playing;
    private Thread gameThread = null;
    private PlayerShip playerShip;
    public EnemyShip enemy1;
    public EnemyShip enemy2;
    public EnemyShip enemy3;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    public TDView(Context context, int x, int y) {
        super(context);
        ourHolder = getHolder();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        playerShip = new PlayerShip(context, x, y);
        enemy1 = new EnemyShip(context, x, y);
        enemy2 = new EnemyShip(context, x, y);
        enemy3 = new EnemyShip(context, x, y);

        int numSpecs = 40;
        for (int i = 0; i < numSpecs; i++) {
            // Where will the dust spawn?
            SpaceDust spec = new SpaceDust(x, y);
            dustList.add(spec);
        }
    }

    @Override
    public void run() {
        while (playing){
            Log.i(TAG, "executing in " + Thread.currentThread().getName());
            update();
            draw();
            control();
        }
    }

    private void update(){
        playerShip.update();
        enemy1.update(playerShip.getSpeed());
        enemy2.update(playerShip.getSpeed());
        enemy3.update(playerShip.getSpeed());

        for (SpaceDust sd : dustList) {
            sd.update(playerShip.getSpeed());
        }
    }

    private void draw(){
        if(ourHolder.getSurface().isValid()){
            canvas = ourHolder.lockCanvas();
            canvas.drawColor(Color.argb(255, 0, 0, 0));

            paint.setColor(Color.argb(255, 255, 255, 255));

            for (SpaceDust sd : dustList) {
                canvas.drawPoint(sd.getX(), sd.getY(), paint);
            }

            canvas.drawBitmap(
                    playerShip.getBitmap(),
                    playerShip.getX(),
                    playerShip.getY(),
                    paint);

            canvas.drawBitmap
                    (enemy1.getBitmap(),
                            enemy1.getX(),
                            enemy1.getY(), paint);
            canvas.drawBitmap
                    (enemy2.getBitmap(),
                            enemy2.getX(),
                            enemy2.getY(), paint);
            canvas.drawBitmap
                    (enemy3.getBitmap(),
                            enemy3.getX(),
                            enemy3.getY(), paint);

            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control(){
        try {
            gameThread.sleep(17);
        } catch (InterruptedException ie){
            ie.printStackTrace();
        }
    }

    public void pause(){
        playing = false;

        try{
            gameThread.join();
        } catch (InterruptedException ie){
            ie.printStackTrace();
        }
    }

    public void resume(){
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_UP:
                    playerShip.stopBoosting();
                break;
            case MotionEvent.ACTION_DOWN:
                    playerShip.setBoosting();
                break;
        }
        return true;
    }
}
