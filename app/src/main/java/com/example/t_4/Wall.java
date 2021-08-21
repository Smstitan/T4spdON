package com.example.t_4;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.t_4.GameView.screenRatioX;
import static com.example.t_4.GameView.screenRatioY;

public class Wall {

    public int speed = 20;
    int x = 0, y, width, height, wallCounter = 1;
    Bitmap wall1, wall2;

    Wall (Resources res) {

        wall1 = BitmapFactory.decodeResource(res, R.drawable.wall1);
        wall2 = BitmapFactory.decodeResource(res, R.drawable.wall2);

        width = wall1.getWidth();
        height = wall1.getHeight();

        width /= 4;
        height /= 3;

        width = (int) (width * screenRatioX);
        height = (int) (3 * height * screenRatioY);

        wall1 = Bitmap.createScaledBitmap(wall1, width, height, false);
        wall2 = Bitmap.createScaledBitmap(wall2, width, height, false);

        y = height * 5;
    }

    Bitmap getWall () {

        if (wallCounter == 1) {
            wallCounter++;
            return wall1;
        }
    return wall2;
    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

}

