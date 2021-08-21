package com.example.t_4;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.t_4.GameView.screenRatioX;
import static com.example.t_4.GameView.screenRatioY;

public class Bird {

    public int speed = 20;
    int x = 0, y, width, height, birdCounter = 1;
    Bitmap bird1, bird2;

    Bird (Resources res) {

        bird1 = BitmapFactory.decodeResource(res, R.drawable.wall1);
        bird2 = BitmapFactory.decodeResource(res, R.drawable.wall2);

        width = bird1.getWidth();
        height = bird1.getHeight();

        width /= 6;
        height /= 2;

        width = (int) (width * screenRatioX);
        height = (int) (3 * height * screenRatioY);

        bird1 = Bitmap.createScaledBitmap(bird1, width, height, false);
        bird2 = Bitmap.createScaledBitmap(bird2, width, height, false);

        y = height * 5;
    }

    Bitmap getBird () {

        if (birdCounter == 1) {
            birdCounter++;
            return bird1;
        }
    return bird2;
    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

}

