package com.example.termproject.app.Game;

import android.graphics.RectF;
import android.util.Log;

import com.example.termproject.R;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;

public class Player extends Sprite implements IBoxCollidable {
    private boolean lookingLeft = true;
    private boolean isDead = false;
    private boolean isGameOver = false;
    public void setGameOver() {
        isGameOver = true;
    }
    private final float JUMP_STEP_X = 80f;
    private final float JUMP_STEP_Y = 50f;

    public enum Direction { LEFT, RIGHT }
    private Direction direction = Direction.LEFT;

    private final RectF collisionBox = new RectF();

    public Player() {
        super(R.mipmap.character_l);
        setPosition(600f, 1600f, 200f, 500f);
        updateCollisionBox();
    }

    public void changeDirection() {
        lookingLeft = !lookingLeft;
        bitmap = BitmapPool.get(lookingLeft ? R.mipmap.character_l : R.mipmap.character_r);
        direction = lookingLeft ? Direction.LEFT : Direction.RIGHT;
    }

    public void jump() {
        if (isDead) return;

        if (lookingLeft) {
            x -= JUMP_STEP_X;
        } else {
            x += JUMP_STEP_X;
        }

        y -= JUMP_STEP_Y;

        setPosition(x, y, width, height);
        updateCollisionBox();
        Log.d("Player", "Jumped to: x=" + x + " y=" + y);
    }


    public void update(float elapsedSeconds) {
        // 중력 없음, 낙하 없음
        updateCollisionBox();
    }
    public float getY() {
        return dstRect.centerY();
    }

    public void offsetY(float dy) {
        y += dy;
        dstRect.offset(0, dy);
        collisionBox.offset(0, dy); // ★ 충돌 박스도 함께 이동
    }

    private void updateCollisionBox() {
        collisionBox.set(x - width / 2, y - height / 2,
                x + width / 2, y + height / 2);
    }

    @Override
    public RectF getCollisionRect() {
        return collisionBox;
    }

    public void setDead(boolean dead) {
        this.isDead = dead;
    }

    public boolean isDead() {
        return isDead;
    }

    public Direction getDirection() {
        return direction;
    }
}
