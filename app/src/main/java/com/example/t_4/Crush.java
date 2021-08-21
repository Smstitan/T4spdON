package com.example.t_4;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.t_4.GameView.screenRatioX;
import static com.example.t_4.GameView.screenRatioY;

public class Crush {

//    int toShoot = 0;
    boolean isGoingUp = false;
    int x, y, width, height, wingCounter = 0; //shootCounter = 1;
    Bitmap crush1, dead; //shoot1, shoot2, shoot3, shoot4, shoot5
    private GameView gameView;

    Crush (GameView gameView, int screenY, Resources res) {

        this.gameView = gameView;

        crush1 = BitmapFactory.decodeResource(res, R.drawable.tryball);

        width = crush1.getWidth();
        height = crush1.getHeight();

        width /= 16;
        height /= 16;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        crush1 = Bitmap.createScaledBitmap(crush1, width, height, false);
        dead = BitmapFactory.decodeResource(res, R.drawable.dead);
        dead = Bitmap.createScaledBitmap(dead, width, height, false);

        y = screenY / 2;
        x = (int) (64 * screenRatioX);

    }

    Bitmap getCrush () {
        return crush1;
    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

    Bitmap getDead () {
        return dead;
    }

}

