package com.example.alex.juego3damian;

//Con la clase abstracta tendremos que escribir menos codigo cuando la queramos implementar que una interface por ejemplo
public abstract class Screen {

/*Gracias a la clase abstracta podremos:
Acceder a los modulos de bajo nivel de Game para audio leer y escribir datos dibujar en pantalla
Podremos sacar una nueva pantalla cuando queramos llamando al metodo Game.setScreen()
*/

    protected final Game game;

    public Screen(Game game) {
        this.game = game;
    }

    //Se encargara de actualizar el estado de la pantalla
    public abstract void update(float deltaTime);

    //Este se encargara de presentar el estado actualizado por pantalla
    public abstract void present(float deltaTime);

    //pause y resume se les llamara cuando se detenga o se reinicie el juego
    public abstract void pause();

    public abstract void resume();

    //Borra y libera los recursos de la pantalla actual para cargar los nuevos
    public abstract void dispose();
}