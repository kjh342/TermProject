package com.example.termproject.app.Game;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;

public abstract class MapObject extends Sprite {
    public MapObject(int bitmapResId) {
        super(bitmapResId);
    }

    // Y축 스크롤 시 오브젝트 이동용
    public void offsetY(float dy) {
        dstRect.offset(0, dy);
    }

    // 필요하다면 X축도 추가 가능
    public void offsetX(float dx) {
        dstRect.offset(dx, 0);
    }

    public float getY() {
        return dstRect.centerY();
    }

    public float getTop() {
        return dstRect.top;
    }

    public float getBottom() {
        return dstRect.bottom;
    }

    // 확장 가능성을 위해 draw나 update를 오버라이드할 수 있게 함
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas); // 기본 draw 유지
    }
}
