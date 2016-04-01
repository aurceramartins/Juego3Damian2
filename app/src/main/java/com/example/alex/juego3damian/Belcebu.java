package com.example.alex.juego3damian;

public class Belcebu extends DynamicGameObject {
    public static final int BELCEBU_STATE_JUMP = 0;
    public static final int BELCEBU_STATE_FALL = 1;
    public static final int BELCEBU_STATE_HIT = 2;

    public static final float BELCEBU_JUMP_VELOCITY = 11;
    public static final float BELCEBU_MOVE_VELOCITY = 20;
    public static final float BELCEBU_WITDH = 0.8f;
    public static final float BELCEBU_HEIGHT = 0.8f;

    int state;
    float stateTime;

    public Belcebu(float x, float y) {
        super(x, y, BELCEBU_WITDH, BELCEBU_HEIGHT);
        state = BELCEBU_STATE_FALL;
        stateTime = 0;
    }

    public void update(float deltaTime) {
        velocity.add(World.gravity.x * deltaTime, World.gravity.y * deltaTime);
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);
        if (velocity.y > 0 && state != BELCEBU_STATE_HIT) {
            if (state != BELCEBU_STATE_JUMP) {
                state = BELCEBU_STATE_JUMP;
                stateTime = 0;
            }
        }
        if (velocity.y < 0 && state != BELCEBU_STATE_HIT) {
            if (state != BELCEBU_STATE_FALL) {
                state = BELCEBU_STATE_FALL;
                stateTime = 0;
            }
        }
        if (position.x < 0) {
            position.x = World.WORLD_WIDTH;
        }
        if (position.x > World.WORLD_WIDTH) {
            position.x = 0;
        }
        stateTime += deltaTime;
    }

    public void hitMarulo() {
        velocity.set(0, 0);
        state = BELCEBU_STATE_HIT;
        stateTime = 0;
    }

    public void hitPlatform() {
        velocity.y = BELCEBU_JUMP_VELOCITY;
        state = BELCEBU_STATE_JUMP;
        stateTime = 0;
    }

    public void hitSpring() {
        velocity.y = BELCEBU_JUMP_VELOCITY * 1.5f;
        state = BELCEBU_STATE_JUMP;
        stateTime = 0;
    }

}
