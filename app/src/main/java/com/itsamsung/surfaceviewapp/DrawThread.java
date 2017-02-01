package com.itsamsung.surfaceviewapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

/**
 * Created by TEACHER on 01.02.2017.
 */
public class DrawThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private volatile boolean running = true;
    private Paint paint = new Paint();
    private Bitmap pepeFrog;
    private int toX, toY;

    public void moveToXY(int x, int y) {
        toX = x;
        toY = y;
    }

    public DrawThread(Context context, SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        pepeFrog = BitmapFactory.decodeResource(context.getResources(), R.drawable.pepe);
    }

    public void requestStop() {
        running = false;
    }

    @Override
    public void run() {
        int pepeFrogX = 0;
        int pepeFrogY = 0;
        int rot = 0;
        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas();

            if (canvas != null) {
                try {
                    canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
                    if (pepeFrogX + pepeFrog.getWidth() / 2 < toX) pepeFrogX += 10;
                    if (pepeFrogX + pepeFrog.getWidth() / 2 > toX) pepeFrogX -= 10;
                    if (pepeFrogY + pepeFrog.getHeight() / 2 < toY) pepeFrogY += 10;
                    if (pepeFrogY + pepeFrog.getHeight() / 2 > toY) pepeFrogY -= 10;
                    canvas.save();
                    canvas.rotate(rot++, pepeFrogX + pepeFrog.getWidth() / 2, pepeFrogY + pepeFrog.getHeight() / 2);
                    canvas.drawBitmap(pepeFrog, pepeFrogX, pepeFrogY, paint);
                    canvas.restore();

                }
                finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
