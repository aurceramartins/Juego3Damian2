package com.example.alex.juego3damian;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Window;
import android.view.WindowManager;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public abstract class GLGame extends Activity implements Game, GLSurfaceView.Renderer {
    enum GLGameState {
        Initialized,
        Running,
        Paused,
        Finished,
        Idle
    }

    GLSurfaceView glSurfaceView;
    GLGraphics glGraphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;
    GLGameState glGameState = GLGameState.Initialized;
    Object stateChanged = new Object();
    long startTime = System.nanoTime();
    PowerManager.WakeLock wakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setRenderer(this);
        setContentView(glSurfaceView);

        glGraphics = new GLGraphics(glSurfaceView);
        fileIO = new AndroidFileIO(getAssets());
        audio = new AndroidAudio(this);
        input = new AndroidInput(this, glSurfaceView, 1, 1);
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");
    }

    public void onResume() {
        super.onResume();
        glSurfaceView.onResume();
        wakeLock.acquire();
    }

    @Override
    public void onPause() {
        synchronized (stateChanged) {
            if (isFinishing()) {
                glGameState = GLGameState.Finished;
            } else {
                glGameState = GLGameState.Paused;
            }
            while (true) {
                try {
                    stateChanged.wait();
                    break;
                } catch (InterruptedException e) {
                }
            }
        }
        wakeLock.release();
        glSurfaceView.onPause();
        super.onPause();
    }

    public GLGraphics getGlGraphics() {
        return glGraphics;
    }

    @Override
    public Input getInput() {
        return input;
    }

    @Override
    public FileIO getFileIO() {
        return fileIO;
    }

    @Override
    public Graphics getGraphics() {
        throw new IllegalStateException("Estamos usando OpenGL");
    }

    @Override
    public Audio getAudio() {
        return audio;
    }

    @Override
    public void setScreen(Screen screen) {
        if (screen == null) {
            throw new IllegalArgumentException("La pantalla no puede esta jodida");
        }
        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }

    @Override
    public Screen getCurrentScreen() {
        return screen;
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glGraphics.setGL(gl);

        synchronized (stateChanged) {
            if (glGameState == GLGameState.Initialized) {
                screen = getStartScreen();
            }
            glGameState = GLGameState.Running;
            screen.resume();
            startTime = System.nanoTime();
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLGameState glGameState = null;
        synchronized (stateChanged) {
            glGameState = this.glGameState;
        }
        if (glGameState == GLGameState.Running) {
            float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
            startTime = System.nanoTime();
            screen.update(deltaTime);
            screen.present(deltaTime);
        }
        if (glGameState == GLGameState.Paused) {
            screen.pause();
            synchronized (stateChanged) {
                this.glGameState = GLGameState.Idle;
                stateChanged.notifyAll();
            }
        }
        if (glGameState == GLGameState.Finished) {
            screen.pause();
            screen.dispose();
            synchronized (stateChanged) {
                this.glGameState = GLGameState.Idle;
                stateChanged.notifyAll();
            }
        }
    }
}
