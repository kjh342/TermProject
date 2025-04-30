package com.example.termproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private boolean isGameOver = false; // 게임 오버 상태
    private Bitmap gameOverBitmap; // 게임 오버 이미지
    private GameThread gameThread;
    private Player player;

    private Bitmap btnTurnBitmap;
    private Bitmap btnJumpBitmap;
    private RectF btnTurnRect;
    private RectF btnJumpRect;

    private Bitmap background;
    private ArrayList<Stair> stairs = new ArrayList<>();
    private Random random = new Random();
    private int lastStairY;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);

        player = new Player(context);

        btnTurnBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.changedirectionbtn);
        btnJumpBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.upbtn);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background1);

        int screenW = getResources().getDisplayMetrics().widthPixels;
        int screenH = getResources().getDisplayMetrics().heightPixels;

        float buttonWidth = 150;
        float buttonHeight = 150;

        btnTurnRect = new RectF(30, screenH - buttonHeight - 30, 30 + buttonWidth, screenH - 30);
        btnJumpRect = new RectF(screenW - buttonWidth - 30, screenH - buttonHeight - 30, screenW - 30, screenH - 30);

        int stepX = 100;
        int stepY = 60;
        int startX = screenW / 2;
        int startY = screenH - 300;

        int stairX = startX;
        int stairY = startY;
        int maxInitialStairY = -screenH * 2;
        boolean isLeft = true; // 시작 방향을 왼쪽으로 설정
        int stairBatchCount = random.nextInt(3) + 4; // 4~6개 연속
        int currentCount = 0;

        while (stairY > maxInitialStairY) {
            if (currentCount >= stairBatchCount) {
                // 방향 바꾸기
                isLeft = !isLeft;
                stairBatchCount = random.nextInt(3) + 4; // 다시 4~6개 연속
                currentCount = 0;
            }

            // 좌우 벽 끝에 도달하면 반대 방향으로 이동
            if (isLeft) {
                stairX -= stepX;
                if (stairX <= 0) { // 벽에 도달하면 반대 방향으로 전환
                    stairX = 0;
                    isLeft = false;
                }
            } else {
                stairX += stepX;
                if (stairX >= screenW - 200) { // 벽에 도달하면 반대 방향으로 전환
                    stairX = screenW - 200;
                    isLeft = true;
                }
            }

            // 겹침 방지: 새로 생성할 계단이 이전 계단과 겹치지 않도록
            if (!stairs.isEmpty()) {
                Stair lastStair = stairs.get(stairs.size() - 1);
                // 마지막 계단과 새로 생성할 계단의 위치가 겹치지 않도록 확인
                while (Math.abs(stairX - lastStair.getX()) < 100) {
                    stairX = isLeft ? stairX + stepX : stairX - stepX; // 겹치면 방향 전환
                }
            }

            stairs.add(new Stair(getContext(), stairX, stairY));
            stairY -= stepY;

            currentCount++;
        }
        if (!stairs.isEmpty()) {
            Stair firstStair = stairs.get(0);
            int initialX = firstStair.getX() - 60; // 첫 번째 계단의 x보다 100만큼 왼쪽으로 이동
            player.setPosition(initialX, firstStair.getY() - player.getHeight() + 160); // y 위치는 50만큼 내리기
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread = new GameThread(getHolder(), this);
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        gameThread.setRunning(false);
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (canvas != null) {
            canvas.drawBitmap(background, 0, 0, null);

            int screenMid = getHeight() / 2;
            int offset = screenMid - player.getY();

            if (offset > 0) {
                for (Stair stair : stairs) {
                    stair.moveDown(offset);
                }
                player.setPosition(player.getX(), screenMid);
            }

            int createThreshold = screenMid - 600;
            while (lastStairY > createThreshold) {
                int stepX = 40;
                int stepY = 120;
                int newX = player.getX() + (random.nextBoolean() ? -stepX : stepX);
                int newY = lastStairY - stepY;
                newX = Math.max(0, Math.min(newX, getWidth() - 200));
                stairs.add(new Stair(getContext(), newX, newY));
                lastStairY = newY;
            }

            for (Stair stair : stairs) {
                stair.draw(canvas);
            }

            player.draw(canvas);

            canvas.drawBitmap(btnTurnBitmap, null, btnTurnRect, null);
            canvas.drawBitmap(btnJumpBitmap, null, btnJumpRect, null);

            Iterator<Stair> iterator = stairs.iterator();
            while (iterator.hasNext()) {
                Stair stair = iterator.next();
                if (stair.getY() > getHeight()) {
                    iterator.remove();
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();

            if (btnTurnRect.contains(x, y)) {
                if (player.isFacingRight()) {
                    player.setFacingLeft();
                } else {
                    player.setFacingRight();
                }
            } else if (btnJumpRect.contains(x, y)) {
                if (player.isFacingRight()) {
                    player.jumpRight();
                } else {
                    player.jumpLeft();
                }
            }
        }
        return true;
    }
}
