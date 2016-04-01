package com.example.alex.juego3damian;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

public class SpriteBatcherTest extends GLGame {
    @Override
    public Screen getStartScreen() {
        return new SpriteBatcherScreen(this);
    }
}

class SpriteBatcherScreen extends Screen {

    Texture texture;
    TextureRegion cannonRegion;
    TextureRegion ballRegion;
    TextureRegion yihadistaRegion;
    SpriteBatcher batcher;

    final int NUM_TARGETS = 20;
    final float WORLD_WITH = 9.6f;
    final float WORLD_HEIGHT = 4.8f;
    GLGraphics glGraphics;
    Cannon cannon;
    DynamicGameObject ball;
    List<GameObject> targets;
    SpatialHashGrid grid;

    Vector2 touchPos = new Vector2();
    Vector2 gravity = new Vector2(0, -10);

    Camera2D camera;


    public SpriteBatcherScreen(Game game) {
        super(game);

        glGraphics = ((GLGame) game).getGlGraphics();

        cannon = new Cannon(0, 0, 1, 0.5f);
        ball = new DynamicGameObject(0, 0, 0.2f, 0.2f);
        targets = new ArrayList<GameObject>(NUM_TARGETS);
        grid = new SpatialHashGrid(WORLD_WITH, WORLD_HEIGHT, 2.5f);


        for (int i = 0; i < NUM_TARGETS; i++) {
            GameObject target = new GameObject((float) Math.random() * WORLD_WITH, (float) Math.random() * WORLD_HEIGHT, 0.5f, 0.5f);
            grid.insertStaticObject(target);
            targets.add(target);
        }
        batcher = new SpriteBatcher(glGraphics, 100);
        camera = new Camera2D(glGraphics, WORLD_WITH, WORLD_HEIGHT);
    }

    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);

            camera.touchWorld(touchPos.set(event.x, event.y));

            cannon.angle = touchPos.sub(cannon.position).angle();

            if (event.type == Input.TouchEvent.TOUCH_UP) {
                float radians = cannon.angle * Vector2.TO_RADIANS;
                float ballSpeed = touchPos.len() * 2;
                ball.position.set(cannon.position);
                ball.velocity.x = (float) Math.cos(radians) * ballSpeed;
                ball.velocity.y = (float) Math.sin(radians) * ballSpeed;
                ball.bounds.lowerLeft.set(ball.position.x - 0.1f, ball.position.y - 0.1f);
            }
        }
        ball.velocity.add(gravity.x * deltaTime, gravity.y * deltaTime);
        ball.position.add(ball.velocity.x * deltaTime, ball.velocity.y * deltaTime);
        ball.bounds.lowerLeft.add(ball.velocity.x * deltaTime, ball.velocity.y * deltaTime);

        List<GameObject> colliders = grid.getPotentialColliders(ball);
        len = colliders.size();
        for (int i = 0; i < len; i++) {
            GameObject collider = colliders.get(i);
            if (OverlapTester.overlapRectangles(ball.bounds, collider.bounds)) {
                targets.remove(collider);
                grid.removeObject(collider);
            }
        }

        if (ball.position.y > 0) {
            camera.position.set(ball.position);
            camera.zoom = 1 + ball.position.y / WORLD_HEIGHT;
        } else {
            camera.position.set(WORLD_WITH / 2, WORLD_HEIGHT / 2);
            camera.zoom = 1;
        }
        /*

        camera.position.set(WORLD_WITH / 2, WORLD_HEIGHT / 2);
        camera.zoom = 1;
*/
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

        int len = targets.size();
        for (int i = 0; i < len; i++) {
            GameObject target = targets.get(i);
            batcher.drawSprite(target.position.x, target.position.y, 0.5f, 0.5f, yihadistaRegion);
        }

        batcher.drawSprite(ball.position.x, ball.position.y, 0.2f, 0.2f, ballRegion);
        batcher.drawSprite(cannon.position.x, cannon.position.y, 1, 0.5f, cannon.angle, cannonRegion);
        batcher.endBatch();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        texture = new Texture(((GLGame) game), "atlas.png");
        cannonRegion = new TextureRegion(texture, 0, 0, 602, 250);
        ballRegion = new TextureRegion(texture, 0, 250, 150, 125);
        yihadistaRegion = new TextureRegion(texture, 301, 250, 301, 250);
    }

    @Override
    public void dispose() {

    }
}
