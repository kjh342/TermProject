package com.example.termproject;


import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;

    public GameThread(SurfaceHolder surfaceHolder, GameView gameView) {
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    public void setRunning(boolean isRunning) {
        running = isRunning;
    }

    @Override
    public void run() {
        while (running) {
            Canvas canvas = null;

            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    gameView.draw(canvas); // 화면 그리기 호출
                }
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

            // 약 60fps로 설정
            try {
                sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
