package com.example.termproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Stair {
    private Bitmap stairBitmap;
    private int x, y;

    public Stair(Context context, int x, int y) {
        stairBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.stair);
        this.x = x;
        this.y = y;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(stairBitmap, x, y, null);
    }

    public void moveDown(int dy) {
        y += dy;
    }
    public boolean isPlayerOnStair(int playerX, int playerY, int playerWidth, int playerHeight) {
        return playerX + playerWidth > x &&
                playerX < x + stairBitmap.getWidth() &&
                playerY + playerHeight >= y &&
                playerY + playerHeight <= y + stairBitmap.getHeight();
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getHeight() { return stairBitmap.getHeight(); }
}
