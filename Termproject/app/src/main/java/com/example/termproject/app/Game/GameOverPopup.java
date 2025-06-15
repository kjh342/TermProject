package com.example.termproject.app.Game;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.termproject.R;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.ITouchable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class GameOverPopup implements IGameObject, ITouchable {
    private final Bitmap gameOverBitmap;
    private final RectF dstRect;
    private final Context context;

    public GameOverPopup(Context context) {
        this.context = context;
        this.gameOverBitmap = BitmapPool.get(R.mipmap.gameover);
        this.dstRect = new RectF(100, 300, 800, 1500); // 이미지 위치 및 크기
    }

    @Override
    public void update() {}

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(gameOverBitmap, null, dstRect, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (dstRect.contains(event.getX(), event.getY())) {
                Scene.pop(); // 팝업 제거
                if (context instanceof Activity) {
                    ((Activity) context).finish(); // 현재 게임 액티비티 종료
                }
                return true;
            }
        }
        return false;
    }
}
