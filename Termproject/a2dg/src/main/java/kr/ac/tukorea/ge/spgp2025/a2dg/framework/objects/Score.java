package kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;

public class Score implements IGameObject {
    private int score;
    private final float x, y;
    private final Paint paint = new Paint();

    public Score(float x, float y, float textSize) {
        this.x = x;
        this.y = y;
        paint.setColor(Color.WHITE);  // 원하는 색
        paint.setTextSize(textSize);
        paint.setTextAlign(Paint.Align.LEFT); // LEFT, CENTER, RIGHT
        paint.setAntiAlias(true); // 부드럽게
    }

    @Override
    public void update() {
        // 애니메이션 효과 같은 게 필요하다면 여기에
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawText("Score: " + score, x, y, paint);
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void add(int amount) {
        this.score += amount;
    }

    public int getScore() {
        return score;
    }
}
