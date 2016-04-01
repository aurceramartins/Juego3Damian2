package com.example.alex.juego3damian;

import javax.microedition.khronos.opengles.GL10;

public class WorldRenderer {
    static final float FRUSTUM_WIDTH = 10;
    static final float FRUSTUM_HEIGHT = 10;

    GLGraphics glGraphics;
    World world;
    Camera2D cam;
    SpriteBatcher batcher;

    public WorldRenderer(GLGraphics glGraphics, SpriteBatcher batcher, World world) {
        this.glGraphics = glGraphics;
        this.world = world;
        this.cam = new Camera2D(glGraphics, FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        this.batcher = batcher;
    }

    public void render() {
        if (world.belcebu.position.y > cam.position.y) {
            cam.position.y = world.belcebu.position.y;
        }
        cam.setViewportAndMatrices();
        renderBackground();
        renderObjects();
    }

    public void renderBackground() {
        batcher.beginBatch(Assets.background);
        batcher.drawSprite(cam.position.x, cam.position.y, FRUSTUM_WIDTH, FRUSTUM_HEIGHT, Assets.backgroundRegion);
        batcher.endBatch();
    }

    public void renderObjects() {
        GL10 gl = glGraphics.getGL();
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        batcher.beginBatch(Assets.items);
        renderBelcebu();
        renderPlatforms();
        renderItems();
        renderMarulos();
        renderInfernalDoor();
        batcher.endBatch();
        gl.glDisable(GL10.GL_BLEND);
    }

    private void renderBelcebu() {
        TextureRegion keyFrame;
        switch (world.belcebu.state) {
            case Belcebu.BELCEBU_STATE_FALL:
                keyFrame = Assets.fall.getKeyFrames(world.belcebu.stateTime, Animation.ANIMATION_LOOPING);
                break;
            case Belcebu.BELCEBU_STATE_JUMP:
                keyFrame = Assets.jump.getKeyFrames(world.belcebu.stateTime, Animation.ANIMATION_LOOPING);
                break;
            case Belcebu.BELCEBU_STATE_HIT:
            default:
                keyFrame = Assets.petada;
        }
        float side = world.belcebu.velocity.x < 0 ? -1 : 1;
        batcher.drawSprite(world.belcebu.position.x, world.belcebu.position.y, side * 1, 1, keyFrame);
    }

    private void renderPlatforms() {
        int len = world.platforms.size();
        for (int i = 0; i < len; i++) {
            Platform platform = world.platforms.get(i);
            TextureRegion keyFrame = Assets.platform;
            if (platform.state == Platform.PLATFORM_STATE_PULVERIZING) {
                keyFrame = Assets.breakingPlatform.getKeyFrames(platform.stateTime, Animation.ANIMATION_NON_LOOPING);
            }
            batcher.drawSprite(platform.position.x, platform.position.y, 2, 0.5f, keyFrame);
        }
    }

    private void renderItems() {
        int len = world.springs.size();
        for (int i = 0; i < len; i++) {
            Spring spring = world.springs.get(i);
            batcher.drawSprite(spring.position.x, spring.position.y, 1, 1, Assets.spring);
        }
        len = world.hearts.size();
        for (int i = 0; i < len; i++) {
            Heart heart = world.hearts.get(i);
            TextureRegion keyFrame = Assets.heartAnim.getKeyFrames(heart.stateTime, Animation.ANIMATION_LOOPING);
            batcher.drawSprite(heart.position.x, heart.position.y, 1, 1, keyFrame);
        }
    }

    private void renderMarulos() {
        int len = world.marulos.size();
        for (int i = 0; i < len; i++) {
            Marulos marulos = world.marulos.get(i);
            TextureRegion keyFrame = Assets.marulosVoladores.getKeyFrames(marulos.stateTime, Animation.ANIMATION_LOOPING);
            float side = marulos.velocity.x < 0 ? -1 : 1;
            batcher.drawSprite(marulos.position.x, marulos.position.y, side * 1, 1, keyFrame);
        }
    }

    private void renderInfernalDoor() {
        InfernalDoor infernalDoor = world.infernalDoor;
        batcher.drawSprite(infernalDoor.position.x, infernalDoor.position.y, 2, 2, Assets.infernalDoor);
    }
}
