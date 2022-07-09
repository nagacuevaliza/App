package com.example.app.gifs;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Shake {
    private TranslateTransition t;
    public Shake(Node node) {
        t = new TranslateTransition(Duration.millis(80), node);
        t.setFromX(0f);
        t.setByX(10f);
        t.setCycleCount(4);
        t.setAutoReverse(true);
    }

    public void animation(){
        t.playFromStart();
    }
}
