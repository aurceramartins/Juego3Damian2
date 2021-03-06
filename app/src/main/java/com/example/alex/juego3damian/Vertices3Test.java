package com.example.alex.juego3damian;


import android.opengl.GLU;

import javax.microedition.khronos.opengles.GL10;

public class Vertices3Test extends GLGame {
    @Override
    public Screen getStartScreen() {
        return new Vertices3Screen(this);
    }
}

class Vertices3Screen extends GLScreen {
    Vertices3 vertices;

    public Vertices3Screen(Game game) {
        super(game);

        vertices = new Vertices3(glGraphics, 6, 0, true, false,false);
        vertices.setVertices(new float[]{-0.5f, -0.5f, -3, 1, 0, 0, 1,
                0.5f, -0.5f, -3, 1, 0, 0, 1,
                0.0f, 0.5f, -3, 1, 0, 0, 1,

                0.0f, -0.5f, -5, 0, 1, 0, 1,
                1.0f, -0.5f, -5, 0, 1, 0, 1,
                0.5f, 0.5f, -5, 0, 1, 0, 1}, 0, 7 * 6);
    }


    @Override
    public void present(float deltaTime) {
        GL10 gl = glGraphics.getGL();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, 67, glGraphics.getWidth() / (float) glGraphics.getHeight(), 0.1f, 10f);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glEnable(GL10.GL_DEPTH_TEST);

        vertices.bind();
        vertices.draw(GL10.GL_TRIANGLES, 0, 6);
        vertices.unbind();

        gl.glDisable(GL10.GL_DEPTH_TEST);
    }

    @Override
    public void update(float deltaTime) {
        game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}

