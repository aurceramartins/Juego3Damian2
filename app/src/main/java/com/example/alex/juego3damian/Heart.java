package com.example.alex.juego3damian;

public class Heart extends GameObject {
    public static float HEART_WIDTH = 0.5f;
    public static float HEART_HEIGHT = 0.8f;
    public static final int HEART_SCORE = 10;

    float stateTime;

    public Heart(float x, float y) {
        super(x, y, HEART_WIDTH, HEART_HEIGHT);
        stateTime = 0;
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
    }
}
