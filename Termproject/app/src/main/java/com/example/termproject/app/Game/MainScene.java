package com.example.termproject.app.Game;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import com.example.termproject.R;

import java.util.ArrayList;
import java.util.Random;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.Sound;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.ITouchable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Button;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Score;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.VertScrollBackground;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class MainScene extends Scene {
    public enum Layer {
        bg, platform, player, ui,floor;
        public static final int COUNT = values().length;
    }

    private Player player;
    private Score score;

    public MainScene() {
        initLayers(Layer.COUNT);

        // 배경
        add(Layer.bg, new VertScrollBackground(R.mipmap.background2, 100f));

        // 플레이어
        float startX = 600f;
        float startY = 1400f;
        float playerWidth = 200f;
        float playerHeight = 500f;
        player = new Player();
        player.setPosition(startX, startY, playerWidth, playerHeight);
        add(Layer.player, player);

        // 계단 계산
        Bitmap stairBitmap = BitmapPool.get(R.mipmap.stair);
        float aspectRatio = (float) stairBitmap.getWidth() / stairBitmap.getHeight();
        float stepHeight = 50f;
        float stepWidth = stepHeight * aspectRatio;

        float margin = 70f;
        float currentX = startX - (playerWidth / 2) - (stepWidth / 2) + margin;
        float currentY = startY + (playerHeight / 2) - (stepHeight / 2) - margin;

        int stairCount = 100;
        float screenMinX = 50f;
        float screenMaxX = 1000f;
        Random random = new Random();
        int direction = -1;
        int stepsInDirection = 0;
        int targetSteps = random.nextInt(5) + 1;

        for (int i = 0; i < stairCount; i++) {
            add(Layer.platform, new Floor(currentX, currentY, stepHeight));
            stepsInDirection++;

            if (stepsInDirection >= targetSteps) {
                direction *= -1;
                stepsInDirection = 0;
                targetSteps = random.nextInt(5) + 1;
            }

            float nextX = currentX + (stepWidth * direction);
            float nextY = currentY - stepHeight;

            if ((nextX - stepWidth / 2) < screenMinX || (nextX + stepWidth / 2) > screenMaxX) {
                direction *= -1;
                stepsInDirection = 0;
                targetSteps = random.nextInt(5) + 1;
                nextX = currentX + (stepWidth * direction);
            }

            currentX = nextX;
            currentY = nextY;
        }

        // ✅ 텍스트 점수 객체 추가
        score = new Score(50f, 200f, 60f); // x, y, textSize
        score.setScore(0);
        add(Layer.ui, score);

        // 점프 버튼
        Button jumpButton = new Button(
                R.mipmap.upbtn, 750f, 1800f, 200f, 200f,
                pressed -> {
                    if (pressed) {
                        player.jump();
                        score.add(10);
                    }
                    return true;
                }
        );
        add(Layer.ui, jumpButton);

        // 방향 버튼
        Button dirButton = new Button(
                R.mipmap.changedirectionbtn, 150f, 1800f, 200f, 200f,
                pressed -> {
                    if (pressed) {
                        player.changeDirection();
                    }
                    return true;
                }
        );
        add(Layer.ui, dirButton);

        // 일시정지 버튼
        add(Layer.ui, new Button(R.mipmap.pausebtn, Metrics.width - 100, 100, 100, 100, pressed -> {
            if (pressed) {
                GameView.view.pushScene(new PauseScene());
            }
            return false;
        }));

        // 충돌 체크
        add(Layer.ui, new CollisionChecker(this, player));
    }

    @Override
    public void update() {
        super.update();
        player.update(GameView.frameTime);

        float playerY = player.getY();
        float scrollThreshold = 800.0f;

        if (playerY < scrollThreshold) {
            float dy = scrollThreshold - playerY;

            player.offsetY(dy);
            scrollScene(dy);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        for (int i = Layer.COUNT - 1; i >= 0; i--) {
            ArrayList<IGameObject> objects = layers.get(i);
            for (IGameObject obj : objects) {
                if (obj instanceof ITouchable) {
                    if (((ITouchable) obj).onTouchEvent(event)) return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onEnter() {
        Sound.playMusic(R.raw.main);
    }

    @Override
    public void onExit() {
        Sound.stopMusic();
    }

    @Override
    public boolean onBackPressed() {
        GameView.view.pushScene(new PauseScene());
        return true;
    }



    private void scrollScene(float dy) {
        scrollLayer(Layer.floor, dy);
        scrollLayer(Layer.platform, dy); // 계단을 포함시킴
        scrollLayer(Layer.bg, dy);
    }
    private void scrollLayer(Layer layer, float dy) {
        for (IGameObject obj : objectsAt(layer)) {
            if (obj instanceof MapObject) {
                ((MapObject) obj).offsetY(dy);
            }
        }
    }

}
