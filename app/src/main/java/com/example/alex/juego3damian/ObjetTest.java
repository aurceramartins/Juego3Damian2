package com.example.alex.juego3damian;

import android.opengl.GLU;

import javax.microedition.khronos.opengles.GL10;

public class ObjetTest extends GLGame {
    @Override
    public Screen getStartScreen() {
        return new ObjectScreen(this);
    }
}

class ObjectScreen extends GLScreen {
    float angle;
    Vertices3 cube;
    Texture texture;
    AmbientLight ambientLight;
    PointLight pointLight;
    DirectionLight directionLight;
    Material material;

    public ObjectScreen(Game game) {
        super(game);

       // cube = ObjLoader.load(glGame, "cara_angel_catatonico.obj");
        texture = new Texture(glGame, "caja.png");
        ambientLight = new AmbientLight();
        ambientLight.setColor(0, 0.2f, 0, 1);
        pointLight = new PointLight();
        pointLight.setDiffuse(1, 0, 0, 1);
        pointLight.setPosition(3, 3, 0);
        directionLight = new DirectionLight();
        directionLight.setDiffuse(0, 0, 1, 1);
        directionLight.setDirection(1, 0, 0);
        material = new Material();
    }

    @Override
    public void resume() {
        texture.reload();
    }

    private Vertices3 createCube() {
        float[] vertices = {-0.5f, -0.5f, 0.5f, 0, 1, 0, 0, 1,
                0.5f, -0.5f, 0.5f, 1, 1, 0, 0, 1,
                0.5f, 0.5f, 0.5f, 1, 0, 0, 0, 1,
                -0.5f, 0.5f, 0.5f, 0, 0, 0, 0, 1,

                0.5f, -0.5f, 0.5f, 0, 1, 1, 0, 0,
                0.5f, -0.5f, -0.5f, 1, 1, 1, 0, 0,
                0.5f, 0.5f, -0.5f, 1, 0, 1, 0, 0,
                0.5f, 0.5f, 0.5f, 0, 0, 1, 0, 0,

                0.5f, -0.5f, -0.5f, 0, 1, 0, 0, -1,
                -0.5f, -0.5f, -0.5f, 1, 1, 0, 0, -1,
                -0.5f, 0.5f, -0.5f, 1, 0, 0, 0, -1,
                0.5f, 0.5f, -0.5f, 0, 0, 0, 0, -1,

                -0.5f, -0.5f, -0.5f, 0, 1, -1, 0, 0,
                -0.5f, -0.5f, 0.5f, 1, 1, -1, 0, 0,
                -0.5f, 0.5f, 0.5f, 1, 0, -1, 0, 0,
                -0.5f, 0.5f, -0.5f, 0, 0, -1, 0, 0,

                -0.5f, 0.5f, 0.5f, 0, 1, 0, 1, 0,
                0.5f, 0.5f, 0.5f, 1, 1, 0, 1, 0,
                0.5f, 0.5f, -0.5f, 1, 0, 0, 1, 0,
                -0.5f, 0.5f, -0.5f, 0, 0, 0, 1, 0,

                -0.5f, -0.5f, 0.5f, 0, 1, 0, -1, 0,
                0.5f, -0.5f, 0.5f, 1, 1, 0, -1, 0,
                0.5f, -0.5f, -0.5f, 1, 0, 0, -1, 0,
                -0.5f, -0.5f, -0.5f, 0, 0, 0, -1, 0
        };
        short[] indices = {0, 1, 2, 2, 3, 0,
                4, 5, 6, 6, 7, 4,
                8, 9, 10, 10, 11, 8,
                12, 13, 14, 14, 15, 12,
                16, 17, 18, 18, 19, 16,
                20, 21, 22, 22, 23, 20,
                24, 25, 26, 26, 27, 24
        };
        Vertices3 cube = new Vertices3(glGraphics, vertices.length / 8, indices.length, false, true, true);
        cube.setVertices(vertices, 0, vertices.length);
        cube.setIndices(indices, 0, indices.length);
        return cube;
    }

    @Override
    public void update(float deltaTime) {
        angle += deltaTime * 20;
    }

    @Override
    public void present(float deltaTime) {
        GL10 gl = glGraphics.getGL();
        gl.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());

        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, 67, glGraphics.getWidth() / (float) glGraphics.getHeight(), 0.1f, 10.0f);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, 0, 1, 3, 0, 0, 0, 0, 1, 0);

        gl.glEnable(GL10.GL_LIGHTING);

        ambientLight.enable(gl);
        pointLight.enable(gl, GL10.GL_LIGHT0);
        directionLight.enable(gl, GL10.GL_LIGHT1);
        material.enable(gl);

        gl.glEnable(GL10.GL_TEXTURE_2D);
        texture.bind();

        gl.glRotatef(angle, 0, 1, 0);
        cube.bind();
        cube.draw(GL10.GL_TRIANGLES, 0, 6 * 2 * 3);
        cube.unbind();

        pointLight.disable(gl);
        directionLight.disable(gl);

        gl.glDisable(GL10.GL_TEXTURE_2D);
        gl.glDisable(GL10.GL_DEPTH_TEST);
    }

    @Override
    public void pause() {
    }

    @Override
    public void dispose() {
    }
}
