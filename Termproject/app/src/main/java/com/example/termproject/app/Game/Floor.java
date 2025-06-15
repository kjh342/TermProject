package com.example.termproject.app.Game;

import android.graphics.Bitmap;
import android.graphics.RectF;

import com.example.termproject.R;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;

public class Floor extends MapObject  implements IBoxCollidable {
    private final RectF collisionBox = new RectF();

    public Floor(float cx, float cy, float desiredHeight) {
        super(R.mipmap.stair);

        // 원본 비율 계산
        Bitmap bitmap = BitmapPool.get(R.mipmap.stair);
        float imageWidth = bitmap.getWidth();
        float imageHeight = bitmap.getHeight();
        float aspectRatio = imageWidth / imageHeight;

        float width = desiredHeight * aspectRatio;
        setPosition(cx, cy, width, desiredHeight);
        updateCollisionBox();
    }

    private void updateCollisionBox() {
        collisionBox.set(x - width / 2, y - height / 2, x + width / 2, y + height / 2);
    }


    public void update(float elapsedSeconds) {
        // 충돌 박스 업데이트 (필요 시 위치 이동 있을 때만)
        updateCollisionBox();
    }

    @Override
    public RectF getCollisionRect() {
        return collisionBox;
    }
}
