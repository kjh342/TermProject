package com.example.termproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class Player {
    private Bitmap bitmapLeft;
    private Bitmap bitmapRight;
    private Bitmap currentBitmap;

    private int x, y;
    private int screenWidth, screenHeight;
    private final int STEP_X = 100;
    private final int STEP_Y = 60;

    private boolean facingRight = false; // 초기 방향을 왼쪽으로 설정

    public Player(Context context) {
        bitmapLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.character_l);
        bitmapRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.character_r);
        currentBitmap = bitmapLeft;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;

        x = screenWidth / 2;
        y = screenHeight - 200;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(currentBitmap, x, y, null);
    }

    public void jumpLeft() {
        setFacingLeft();
        x -= STEP_X;
        y -= STEP_Y;
    }

    public void jumpRight() {
        setFacingRight();
        x += STEP_X;
        y -= STEP_Y;
    }

    public boolean isFacingRight() {
        return facingRight;
    }

    public void setFacingRight() {
        facingRight = true;
        currentBitmap = bitmapRight;
    }

    public void setFacingLeft() {
        facingRight = false;
        currentBitmap = bitmapLeft;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getHeight() { return currentBitmap.getHeight(); }
    public Bitmap getCurrentBitmap() { return currentBitmap; }
}