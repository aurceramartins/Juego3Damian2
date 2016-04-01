package com.example.alex.juego3damian;

import java.io.IOException;

public interface Graphics {
    //Enumeracion. Se encargara de codificar los formatos de los pixeles
    public static enum PixmapFormat {
        ARGB8888, ARGB4444, RGB565

    }

    //Este metodo carga una imagen y nosotros le daremos el fromato PNG o JPEG
    public Pixmap newPixmap(String fileName, PixmapFormat format);

    //limpia el buffer utilizando un color determinado
    public void clear(int color);

    //Este metodo dibuja un pixel en el buffer en la posicion x,y con un color determinado,
    // las coord deben estar dentro de la pantalla si no se ignoraran
    public void drawPixel(int x, int y, int color);

    //Define el punto inicial y finald de una linea y su color
    public void drawLine(int x, int y, int x2, int y2, int color);

    //Dibuja un rectangulo en el buffer
    //x,y especifican la posicion que tendra la esquina superior izquierda es decir el punto 0,0
    //width y height seran el numero de pixeles en x e y que el rectangulo aumentara width hacia X, heigth hacia Y
    //el color sera el color del rectangulo
    public void drawRect(int x, int y, int width, int height, int color);

    //Dibuja partes de un Pixmap en el buffer
    //x,y punto principal dentro del buffer(superior izquierdo)
    //srcX y srcY crean la esquina del rectangulo Pixmap (superior izquierda)
    //srcW y srcH tamaño de la porcion de Pixmap que cogeremos
    public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight);

    public void drawPixmap(Pixmap pixmap, int x, int y);

    //Estos dos devueven el ancho y el alto del buffer (en pixeles)
    public int getWidth();

    public int getHeigth();

}
