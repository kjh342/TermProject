package com.example.termproject.app.Game;

import com.example.termproject.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Button;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.VertScrollBackground;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class MainScene extends Scene {
    public enum Layer {
        bg, player, ui;
        public static final int COUNT = values().length;
    }

    private Player player;

    public MainScene() {
        initLayers(Layer.COUNT);

        add(Layer.bg, new VertScrollBackground(R.mipmap.background2, 100f));

        player = new Player();
        add(Layer.player, player);

        // 점프 버튼 생성 및 기능 할당
        Button jumpButton = new Button(
                R.mipmap.upbtn, 750f, 1800f, 200f, 200f,
                pressed -> {
                    if (pressed) {
                        player.jump();
                    }
                    return true;
                }
        );
        add(Layer.ui, jumpButton);

        // 방향 전환 버튼 생성 및 기능 할당
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
    }
}