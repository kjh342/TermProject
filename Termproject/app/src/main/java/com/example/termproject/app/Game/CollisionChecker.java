package com.example.termproject.app.Game;

import android.graphics.Canvas;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.util.CollisionHelper;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class CollisionChecker implements IGameObject {
    private final MainScene scene;
    private final Player player;

    public CollisionChecker(MainScene scene, Player player) {
        this.scene = scene;
        this.player = player;
    }

    @Override
    public void update() {
        boolean onFloor = false;
        ArrayList<IGameObject> platforms = scene.objectsAt(MainScene.Layer.platform);
        for (IGameObject obj : platforms) {
            if (!(obj instanceof IBoxCollidable)) continue;
            if (CollisionHelper.collides(player, (IBoxCollidable) obj)) {
                onFloor = true;
                break;
            }
        }

        if (!onFloor) {
            scene.add(MainScene.Layer.ui, new GameOverPopup(GameView.view.getContext()));
            player.setGameOver();
        }
    }

    @Override
    public void draw(Canvas canvas) {}
}
