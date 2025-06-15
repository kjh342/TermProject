package com.example.termproject.app.Game;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.example.termproject.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Button;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class PauseScene extends Scene {
    public enum Layer {
        bg, ui, touch
    }

    public PauseScene() {
        initLayers(Layer.values().length);

        float w = Metrics.width, h = Metrics.height;

        // 반투명 배경
        add(Layer.bg, new Sprite(R.mipmap.trans_50b, w / 2, h / 2, w, h));

        // 계속하기 버튼
        add(Layer.touch, new Button(R.mipmap.btn_resume_n, w / 2, h / 2 - 100f, 300f, 120f, new Button.OnTouchListener() {
            @Override
            public boolean onTouch(boolean pressed) {
                pop(); // PauseScene 종료 → 게임 재개
                return false;
            }
        }));

        // 나가기 버튼
        add(Layer.touch, new Button(R.mipmap.btn_exit_n, w / 2, h / 2 + 100f, 300f, 120f, new Button.OnTouchListener() {
            @Override
            public boolean onTouch(boolean pressed) {
                new AlertDialog.Builder(GameView.view.getContext())
                        .setTitle("게임 종료")
                        .setMessage("게임을 종료하시겠습니까?")
                        .setNegativeButton("아니요", null)
                        .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                popAll(); // 전체 씬 종료
                            }
                        })
                        .create()
                        .show();
                return false;
            }
        }));
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    protected int getTouchLayerIndex() {
        return Layer.touch.ordinal();
    }
}
