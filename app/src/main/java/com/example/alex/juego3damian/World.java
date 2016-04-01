package com.example.alex.juego3damian;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {
    public interface WorldListener {
        public void jump();

        public void highJump();

        public void hit();

        public void heart();
    }

    public static final float WORLD_WIDTH = 10;
    public static final float WORLD_HEIGHT = 15 * 20;

    public static final int WORLD_STATE_RUNNING = 0;
    public static final int WORLD_STATE_NEXT_LEVEL = 1;
    public static final int WORLD_STATE_GAME_OVER = 15 * 20;

    public static final Vector2 gravity = new Vector2(0, -12);

    public final Belcebu belcebu;
    public final List<Platform> platforms;
    public final List<Spring> springs;
    public final List<Marulos> marulos;
    public final List<Heart> hearts;
    public InfernalDoor infernalDoor;
    public final WorldListener listener;
    public final Random random;
    public float heightSoFar;
    public int score;
    public int state;

    public World(WorldListener listener) {
        this.belcebu = new Belcebu(5, 1);
        this.platforms = new ArrayList<Platform>();
        this.springs = new ArrayList<Spring>();
        this.marulos = new ArrayList<Marulos>();
        this.hearts = new ArrayList<Heart>();
        this.listener = listener;
        random = new Random();
        generateLevel();

        this.heightSoFar = 0;
        this.score = 0;
        this.state = WORLD_STATE_RUNNING;
    }

    private void generateLevel() {
        float y = Platform.PLATFORM_HEIGHT / 2;
        float maxJumpHeight = Belcebu.BELCEBU_JUMP_VELOCITY * Belcebu.BELCEBU_JUMP_VELOCITY / (2 * -gravity.y);
        while (y < WORLD_HEIGHT - WORLD_WIDTH / 2) {
            int type = random.nextFloat() > 0.8f ? Platform.PLATFORM_TYPE_MOVING : Platform.PLATFORM_TYPE_STATIC;
            float x = random.nextFloat() * (WORLD_WIDTH - Platform.PLATFORM_WIDTH) + Platform.PLATFORM_WIDTH;

            Platform platform = new Platform(type, x, y);
            platforms.add(platform);

            if (random.nextFloat() > 0.9f && type != Platform.PLATFORM_TYPE_MOVING) {
                Spring spring = new Spring(platform.position.x, platform.position.y + Platform.PLATFORM_HEIGHT / 2 + Spring.SPRING_HEIGHT / 2);
                springs.add(spring);
            }
            if (y > WORLD_HEIGHT / 3 && random.nextFloat() > 0.8f) {
                Marulos marulo = new Marulos(platform.position.x + random.nextFloat(), platform.position.y + Marulos.MARULO_HEIGHT + random.nextFloat() * 2);
                marulos.add(marulo);
            }
            if (random.nextFloat() > 0.6f) {
                Heart heart = new Heart(platform.position.x + random.nextFloat(), platform.position.y + Heart.HEART_HEIGHT + random.nextFloat() * 3);
                hearts.add(heart);
            }
            y += (maxJumpHeight - 0.5f);
            y -= random.nextFloat() * (maxJumpHeight / 3);
        }
        infernalDoor = new InfernalDoor(WORLD_WIDTH / 2, y);
    }

    public void update(float deltaTime, float accelX) {
        updateBelcebu(deltaTime, accelX);
        updatePlatforms(deltaTime);
        updateMarulos(deltaTime);
        updateHeart(deltaTime);

        if (belcebu.state != Belcebu.BELCEBU_STATE_HIT) {
            checkCollisions();
        }
        checkGameOver();
    }

    private void updateBelcebu(float deltaTime, float accelX) {
        if (belcebu.state != Belcebu.BELCEBU_STATE_HIT && belcebu.position.y <= 0.5f) {
            belcebu.hitPlatform();
        }
        if (belcebu.state != Belcebu.BELCEBU_STATE_HIT) {
            belcebu.velocity.x = -accelX / 10 * Belcebu.BELCEBU_MOVE_VELOCITY;
        }
        belcebu.update(deltaTime);
        heightSoFar = Math.max(belcebu.position.y, heightSoFar);
    }

    void updatePlatforms(float deltaTime) {
        int len = platforms.size();
        for (int i = 0; i < len; i++) {
            Platform platform = platforms.get(i);
            platform.update(deltaTime);
            if (platform.state == Platform.PLATFORM_STATE_PULVERIZING && platform.stateTime > Platform.PLATFORM_PULVERIZE_TIME) {
                platforms.remove(platform);
                len = platforms.size();
            }
        }
    }

    private void updateMarulos(float deltaTime) {
        int len = marulos.size();
        for (int i = 0; i < len; i++) {
            Marulos marulo = marulos.get(i);
            marulo.update(deltaTime);
        }
    }

    private void updateHeart(float deltaTime) {
        int len = hearts.size();
        for (int i = 0; i < len; i++) {
            Heart heart = hearts.get(i);
            heart.update(deltaTime);
        }
    }

    private void checkCollisions() {
        checkPlatformCollisions();
        checkMaruloCollisions();
        checkItemCollisions();
        checkDoorCollisions();
    }

    private void checkPlatformCollisions() {
        if (belcebu.velocity.y > 0) {
            return;
        }
        int len = platforms.size();
        for (int i = 0; i < len; i++) {
            Platform platform = platforms.get(i);
            if (belcebu.position.y > platform.position.y) {
                if (OverlapTester.overlapRectangles(belcebu.bounds, platform.bounds)) {
                    belcebu.hitPlatform();
                    listener.jump();
                    if (random.nextFloat() > 0.5f) {
                        platform.pulverize();
                    }
                    break;
                }
            }
        }
    }

    private void checkMaruloCollisions() {
        int len = marulos.size();
        for (int i = 0; i < len; i++) {
            Marulos marulo = marulos.get(i);
            if (OverlapTester.overlapRectangles(marulo.bounds, belcebu.bounds)) {
                belcebu.hitMarulo();
                listener.hit();
            }
        }
    }

    private void checkItemCollisions() {
        int len = hearts.size();
        for (int i = 0; i < len; i++) {
            Heart heart = hearts.get(i);
            if (OverlapTester.overlapRectangles(belcebu.bounds, heart.bounds)) {
                hearts.remove(heart);
                len=hearts.size();
                listener.heart();
                score += Heart.HEART_SCORE;
            }
        }
        if (belcebu.velocity.y > 0) {
            return;
        }
        len = springs.size();
        for (int i = 0; i < len; i++) {
            Spring spring = springs.get(i);
            if (belcebu.position.y > spring.position.y) {
                if (OverlapTester.overlapRectangles(belcebu.bounds, spring.bounds)) {
                    belcebu.hitSpring();
                    listener.highJump();
                }
            }
        }
    }

    private void checkDoorCollisions() {
        if (OverlapTester.overlapRectangles(infernalDoor.bounds, belcebu.bounds)) {
            state = WORLD_STATE_NEXT_LEVEL;
        }
    }

    private void checkGameOver() {
        if (heightSoFar - 7.5f > belcebu.position.y) {
            state = WORLD_STATE_GAME_OVER;
        }
    }
}
