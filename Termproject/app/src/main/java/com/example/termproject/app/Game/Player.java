package com.example.termproject.app.Game;

import com.example.termproject.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;
public class Player extends Sprite {
    private boolean lookingLeft = true;
    public enum Direction { LEFT, RIGHT }
    private Direction direction = Direction.LEFT;

    public Player() {
        super(R.mipmap.character_l); // 초기 방향: 왼쪽
        setPosition(600f, 1500f, 200f, 500f);
    }

    public void changeDirection() {
        lookingLeft = !lookingLeft;
        if (lookingLeft) {
            bitmap = BitmapPool.get(R.mipmap.character_l); // 왼쪽
        } else {
            bitmap = BitmapPool.get(R.mipmap.character_r); // 오른쪽
        }
    }
    public void jump() {
        float jumpHeight = 150f;
        y -= jumpHeight;
    }

    public Direction getDirection() {
        return direction;
    }
}
