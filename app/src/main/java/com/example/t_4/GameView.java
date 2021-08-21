package com.example.t_4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView<walls> extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying, isGameOver = false;
    private int screenX, screenY, score = 0;
    public static float screenRatioX, screenRatioY;
    private Paint paint;
    private Wall[] walls;
    private SharedPreferences prefs;
    private Random random;
    private SoundPool soundPool;

    private Crush crush;
    private GameActivity activity;
    private Background background1, background2;

    public GameView(GameActivity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);


        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());

        crush = new Crush(this, screenY, getResources());

        background2.x = screenX;

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        walls = new Wall[2];

        for (int i = 0; i < 2; i++) {

            Wall wall = new Wall(getResources());
            walls[i] = wall;

        }

        random = new Random();

    }

    @Override
    public void run() {

        while (isPlaying) {

            update();
            draw();
            sleep();

        }

    }

    private void update() {

        background1.x -= 10 * screenRatioX;
        background2.x -= 10 * screenRatioX;

        if (background1.x + background1.background.getWidth() < 0) {
            background1.x = screenX;
        }

        if (background2.x + background2.background.getWidth() < 0) {
            background2.x = screenX;
        }
        double a = crush.y;

        if (crush.isGoingUp)
            crush.y -= 60 * screenRatioY;
        else
            crush.y += 45 * screenRatioY;

        if (crush.y < 0)
            crush.y = 0;

        if (crush.y >= screenY - crush.height)
            crush.y = screenY - crush.height;

        for (Wall wall : walls) {

            wall.x -= wall.speed;
            if (wall.x < 0) {
                score++;
            }

            if (wall.x + wall.width < 0) {

                int bound = (int) (30 * screenRatioX);
                wall.speed = random.nextInt(bound);

                if (wall.speed < -10 * screenRatioX)
                    wall.speed = (int) (-10 * screenRatioX);

                wall.x = random.nextInt(screenX);
                wall.y = random.nextInt(screenY - wall.height);
            }
            if (Rect.intersects(wall.getCollisionShape(), crush.getCollisionShape())) {
                    isGameOver = true;
                    return;
            }


            }

        }



    private void draw() {

        if (getHolder().getSurface().isValid()) {

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            for (Wall wall : walls)
                canvas.drawBitmap(wall.getWall(), wall.x, wall.y, paint);

            canvas.drawText(score + "", screenX / 2f, 164, paint);

            if (isGameOver) {
                isPlaying = false;
                canvas.drawBitmap(crush.getDead(), crush.x, crush.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                saveIfHighScore();
                waitBeforeExiting();
                return;
            }

            canvas.drawBitmap(crush.getCrush(), crush.x, crush.y, paint);

            getHolder().unlockCanvasAndPost(canvas);

        }

    }

    private void waitBeforeExiting() {

        try {
            Thread.sleep(3000);
            activity.startActivity(new Intent(activity, MainActivity.class));
            activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void saveIfHighScore() {

        if (prefs.getInt("highscore", 0) < score) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore", score);
            editor.apply();
        }

    }

    private void sleep() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {

        isPlaying = true;
        thread = new Thread(this);
        thread.start();

    }

    public void pause() {

        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

//    public boolean OnClickListener;


    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() < screenX / 2) {
                    crush.isGoingUp = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                crush.isGoingUp = false;
                if (event.getX() > screenX / 2){

                }
                break;
        }

        return true;
}}

