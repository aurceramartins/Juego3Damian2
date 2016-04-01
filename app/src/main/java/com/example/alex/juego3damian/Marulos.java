package com.example.alex.juego3damian;

public class Marulos extends DynamicGameObject {
    public static float MARULO_WIDTH = 1;
    public static float MARULO_HEIGHT = 0.6f;
    public static final float MARULO_VELOCITY = 3f;

    float stateTime = 0;

    public Marulos(float x, float y) {
        super(x, y, MARULO_WIDTH, MARULO_HEIGHT);
        velocity.set(MARULO_VELOCITY, 0);
    }

    public void update(float deltaTime) {
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        bounds.lowerLeft.set(position).sub(MARULO_WIDTH / 2, MARULO_HEIGHT / 2);

        if (position.x < MARULO_WIDTH / 2) {
            position.x = MARULO_WIDTH / 2;
            velocity.x = MARULO_VELOCITY;
        }
        if (position.x > World.WORLD_WIDTH - MARULO_WIDTH / 2) {
            position.x = World.WORLD_WIDTH - MARULO_WIDTH / 2;
            velocity.x = -MARULO_VELOCITY;
        }
        stateTime+=deltaTime;
    }
}
