package com.example.tanks;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.IOException;

public class Waiter extends Thread {
    private BufferedReader in;
    private GraphicsContext gc;

    public Waiter(GraphicsContext gc, BufferedReader in) {
        this.gc = gc;
        this.in = in;
    }

    @Override
    public void run() {


    }
}
