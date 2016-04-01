package com.example.alex.juego3damian;

import android.widget.Toast;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

public class AnimationTest extends GLGame {
    @Override
    public Screen getStartScreen() {
        return new AnimationScreen(this);
    }

    static final float WORLD_WIDTH = 4.8f;
    static final float WORLD_HEIGHT = 3.2f;


    static class Caveman extends DynamicGameObject {

        public float walkingTime = 0;

        public Caveman(float x, float y, float width, float height) {
            super(x, y, width, height);
            this.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
            this.velocity.set(Math.random() > 0.5f ? -0.5f : 0.5f, 0);
            this.walkingTime = (float) Math.random() * 10;
        }

        public void update(float deltaTime) {

             position.add(velocity.x * deltaTime, velocity.y * deltaTime);
            if (position.x < 0) {
                position.x = WORLD_WIDTH;
            }
            if (position.x > WORLD_WIDTH) {
                position.x = 0;
            }
            walkingTime += deltaTime;
        }
    }


    class AnimationScreen extends Screen {
        static final int NUM_CAVEMEN = 1;
        GLGraphics glGraphics;
        Caveman[] cavemen;
        SpriteBatcher batcher;
        Camera2D camera;
        Texture texture;
        Animation walkAnim;

        public AnimationScreen(Game game) {
            super(game);
            glGraphics = ((GLGame) game).getGlGraphics();


            cavemen = new Caveman[NUM_CAVEMEN];
            for (int i = 0; i < NUM_CAVEMEN; i++) {
                cavemen[i] = new AnimationTest.Caveman((float) Math.random(), (float) Math.random(), 1, 1);
            }
            batcher = new SpriteBatcher(glGraphics, NUM_CAVEMEN);
            camera = new Camera2D(glGraphics, WORLD_WIDTH, WORLD_HEIGHT);
        }

        @Override
        public void update(float deltaTime) {
            int len = cavemen.length;
            for (int i = 0; i < len; i++) {
                cavemen[i].update(deltaTime);
            }
        }

        @Override
        public void present(float deltaTime) {
            GL10 gl = glGraphics.getGL();
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
            camera.setViewportAndMatrices();

            gl.glEnable(GL10.GL_BLEND);
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            gl.glEnable(GL10.GL_TEXTURE_2D);

            batcher.beginBatch(texture);
            int len = cavemen.length;
            for (int i = 0; i < len; i++) {
                Caveman caveman = cavemen[i];
                TextureRegion keyFrame = walkAnim.getKeyFrames(caveman.walkingTime, Animation.ANIMATION_LOOPING);
                batcher.drawSprite(caveman.position.x, caveman.position.y, caveman.velocity.x < 0 ? 1 : -1, 1, keyFrame);
            }
            batcher.endBatch();
        }

        @Override
        public void pause() {

        }

        @Override
        public void resume() {
        /*
            texture = new Texture(((GLGame) game), "juego.png");
            walkAnim = new Animation(0.2f, new TextureRegion(texture, 867, 369, 308, 304),
                    new TextureRegion(texture, 1175, 369, 308, 304)
                    );
           texture = new Texture(((GLGame) game), "juego.png");
            walkAnim = new Animation(0.2f, new TextureRegion(texture, 867, 369, 308, 304),
                    new TextureRegion(texture, 1175, 369, 308, 304)
                    );
*/
           texture = new Texture(((GLGame) game), "juego.png");
            walkAnim = new Animation(0.2f, new TextureRegion(texture, 855, 980, 316, 299),
                    new TextureRegion(texture, 1170, 980, 315, 299)
                    );
/*
           texture = new Texture(((GLGame) game), "juego.png");
            walkAnim = new Animation(0.2f, new TextureRegion(texture, 860, 673, 314, 307),
                    new TextureRegion(texture, 1172, 673, 314, 307),
            new TextureRegion(texture, 1483, 673, 314, 307));


           texture = new Texture(((GLGame) game), "juego.png");
            walkAnim = new Animation(0.2f, new TextureRegion(texture, 867, 369, 308, 304),
                    new TextureRegion(texture, 1175, 369, 308, 304),
                    new TextureRegion(texture, 1483, 369, 308, 304)
            );*/
/*
            texture = new Texture(((GLGame) game), "galletagalletametralleta.png");
            walkAnim = new Animation(0.2f, new TextureRegion(texture, 10, 0, 200, 200),
                    new TextureRegion(texture, 210, 0, 200, 200),
                    new TextureRegion(texture, 410, 0, 200, 200),
                    new TextureRegion(texture, 610, 0, 200, 200));
            /*
            texture = new Texture(((GLGame) game), "jetpistol.png");
            walkAnim = new Animation(0.2f, new TextureRegion(texture, 0, 0, 43, 64),
                    new TextureRegion(texture, 49, 0, 58, 64),
                    new TextureRegion(texture, 117, 0, 118, 64),
                    new TextureRegion(texture, 235, 0, 122, 64),
                    new TextureRegion(texture, 357, 0, 119, 64),
                    new TextureRegion(texture, 485, 0, 60, 64),
                    new TextureRegion(texture, 545, 0, 47, 64));
                    */
/*
            texture = new Texture(((GLGame) game), "luffyquieto.png");
            walkAnim = new Animation(0.1f, new TextureRegion(texture, 0, 0, 30, 48),
                    new TextureRegion(texture, 46, 0, 32, 48),
                    new TextureRegion(texture, 92, 0, 33, 48),
                    new TextureRegion(texture, 139, 0, 34, 48),
                    new TextureRegion(texture, 187, 0, 33, 48),
                    new TextureRegion(texture, 234, 0, 31, 48));*/
        }


        @Override
        public void dispose() {

        }
    }
}
