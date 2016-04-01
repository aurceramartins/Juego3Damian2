package com.example.alex.juego3damian;

import java.io.IOException;

public interface Music {
    //Con esta interfaz cargamos la musica la paramos y la stopeamos desde fuera de la memoria
    //la cargamos desde el fichero
    //Esta interfaz tabien podra aumetar el volumen etc

    public void play() throws IOException;

    public void stop();

    public void pause();

    public void setLooping(boolean looping);

    public void setVolume(float volume);

    public boolean isPlaying();

    public boolean isStopped();

    public boolean isLooping();

    public void dispose();

}
