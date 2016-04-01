package com.example.alex.juego3damian;

import java.util.ArrayList;
import java.util.List;

//Clase generica para almacenar objetos de cualquier tipo.
//Esta va a ser la papelera y las instancias de las clases que tendremos que instanciar constantemente las reusaremos de la papelera en vez de tirarlas y crear una nueva
public class Pool<T> {

    public interface PoolObjectFactory<T> {
        //Devuelve un tipo generico de una instancia
        public T createObject();
    }

    private final List<T> freeObjects;
    private final PoolObjectFactory<T> factory;
    private final int maxSize;

    public Pool(PoolObjectFactory<T> factory, int maxSize) {
        this.factory = factory;
        this.maxSize = maxSize;
        //Este arraylist tiene la capacidad de determinar el numero maximo de objetos asi el pool no crecera de forma infinita
        this.freeObjects = new ArrayList<T>(maxSize);
    }

    //newObject obtiene la nueva instancia del tipo que contiene Pool
    public T newObject() {
        T object = null;

        //si la instancia no existe crea una nueva
        if (freeObjects.size() == 0) {
            object = factory.createObject();
        }
        //si ya existe nos devolvera esa instancia
        else {
            object = freeObjects.remove(freeObjects.size() - 1);
        }
        return object;
    }

    //Nos permite guardar los objetos que no estemos utilizando, mientras la lista freeObjects no este llena
    public void free(T object) {
        if (freeObjects.size() < maxSize) {
            freeObjects.add(object);
        }

    }


}
