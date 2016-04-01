package com.example.alex.juego3damian;


public interface Pixmap {

    //With y heigth devuelven el ancho y el alto del Pixmap
    public int getWith();

    public int getHeight();

    //Valor del PixelFormat que el programa ha guardado en memoria RAM
    public Graphics.PixmapFormat getFormat();

    //Con este metodo vaciamos la memoria cuando el programa no este utilizando estos recursos
    public void dispose();


}