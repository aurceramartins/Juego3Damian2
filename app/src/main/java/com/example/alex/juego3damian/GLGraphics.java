package com.example.alex.juego3damian;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.opengles.GL10;

public class GLGraphics {
    GLSurfaceView glSurfaceView;
    GL10 gl;

    GLGraphics(GLSurfaceView glSurfaceView) {
        this.glSurfaceView = glSurfaceView;
    }

    public GL10 getGL() {
        return gl;
    }

    void setGL(GL10 gl) {
        this.gl = gl;
    }

    public int getWidth() {
        return glSurfaceView.getWidth();
    }

    public int getHeight() {
        return glSurfaceView.getHeight();
    }
}
