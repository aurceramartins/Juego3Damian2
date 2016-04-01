package com.example.alex.juego3damian;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AndroidFastRenderView extends SurfaceView implements Runnable {
    AndroidGame androidGame;
    Bitmap frameBuffer;
    Thread renderThread = null;
    SurfaceHolder holder;
    volatile boolean running = false;

    public AndroidFastRenderView(AndroidGame androidGame, Bitmap bitmap) {
        super(androidGame);
        this.androidGame = androidGame;
        this.frameBuffer = frameBuffer;
        this.holder = holder;

    }


    public void resume() {
        running = true;
        renderThread = new Thread(this);
        renderThread.start();

    }

    @Override
    public void run() {

        Rect dstRect = new Rect();
        long startTime = System.nanoTime();
        while (running) {
            if (!holder.getSurface().isValid()) {
                continue;
            }
            float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
            startTime = System.nanoTime();

            androidGame.getCurrentScreen().update(deltaTime);
            androidGame.getCurrentScreen().present(deltaTime);

            Canvas canvas = holder.lockCanvas();
            canvas.getClipBounds(dstRect);
            canvas.drawBitmap(frameBuffer, null, dstRect, null);
            holder.unlockCanvasAndPost(canvas);

        }
    }

    public void pause() throws InterruptedException {
        running = false;
        while (true) {
            renderThread.join();
            break;
        }
    }
}
