package com.example.guessnumber.data.model;

import java.io.Serializable;

public class Jugador implements Serializable {
    String nombre;

    public Jugador() {
    }

    public Jugador(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "nombre='" + nombre + '\'' +
                '}';
    }
}
