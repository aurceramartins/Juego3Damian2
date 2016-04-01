package com.example.alex.juego3damian;

import java.io.IOException;

public interface Game {

    public Input getInput();

    public FileIO getFileIO();

    public Graphics getGraphics();

    public Audio getAudio();

    //Permite definir la pantalla Screen de Game.
    public void setScreen(Screen screen);

    //Muestra la pantalla Screen que esta activa
    public Screen getCurrentScreen();

    //Sirve para instanciar.Para ello usamos la clase abstracta AndroidGame
    public Screen getStartScreen();

}