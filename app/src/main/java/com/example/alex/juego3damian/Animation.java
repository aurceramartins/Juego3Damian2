package com.example.alex.juego3damian;

public class Animation {
    public static final int ANIMATION_LOOPING = 0;
    public static final int ANIMATION_NON_LOOPING = 1;

    final TextureRegion[] keyFrames;
    final float frameDuration;

    public Animation(float frameDuration, TextureRegion... keyFrames) {
        this.keyFrames = keyFrames;
        this.frameDuration = frameDuration;
    }

    public TextureRegion getKeyFrames(float stateTime, int mode) {
        int frameNumber = (int) (stateTime / frameDuration);
        if (mode == ANIMATION_NON_LOOPING) {
            frameNumber = Math.min(keyFrames.length - 1, frameNumber);
        } else {
            frameNumber = frameNumber % keyFrames.length;
        }
        return keyFrames[frameNumber];
    }
}
