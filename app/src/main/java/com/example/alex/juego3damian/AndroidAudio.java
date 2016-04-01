package com.example.alex.juego3damian;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import java.io.IOException;

public class AndroidAudio implements Audio {
    AssetManager assets;
    SoundPool soundPool;

    public AndroidAudio(Activity activity) {
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        this.assets = activity.getAssets();
        this.soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
    }

    @Override
    public Music newMusic(String fileName) throws IOException {
        AssetFileDescriptor assetDescriptor = assets.openFd(fileName);
        return new AndroidMusic(assetDescriptor);
    }

    @Override
    public Sound newSound(String fileName) throws IOException {
        AssetFileDescriptor assetFileDescriptor = assets.openFd(fileName);
        int soundId = soundPool.load(assetFileDescriptor, 0);
        return new AndroidSound(soundPool, soundId);

    }


}
