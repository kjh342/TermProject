package com.example.termproject.app;
import com.example.termproject.app.Game.MainScene;

import android.os.Bundle;
import com.example.termproject.BuildConfig;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.activity.GameActivity;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class TermActivity extends GameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GameView.drawsDebugStuffs = BuildConfig.DEBUG;
        Metrics.setGameSize(900, 2000);
        super.onCreate(savedInstanceState);

        new MainScene().push();
    }
}