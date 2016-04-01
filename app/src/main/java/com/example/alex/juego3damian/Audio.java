package com.example.alex.juego3damian;

import java.io.IOException;

public interface Audio {
    //Clase que crea objetos sound y music

    public Music newMusic(String fileName) throws IOException;

    public Sound newSound(String fileName) throws IOException;
}
