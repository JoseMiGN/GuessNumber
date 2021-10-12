package com.example.guessnumber.data.model;

import java.io.Serializable;

public class Numero implements Serializable {
    Jugador jugador;
    Integer numeroAdivinar;
    Integer intentosMAX;
    Integer intentosActual;
    Boolean hasGanado;

    public Numero() {
        intentosActual = 1;
    }

    public Numero(Jugador jugador, Integer numeroAdivinar, Integer intentosMAX, Integer intentosActual, Boolean hasGanado) {
        this.jugador = jugador;
        this.numeroAdivinar = numeroAdivinar;
        this.intentosMAX = intentosMAX;
        this.intentosActual = intentosActual;
        this.hasGanado = hasGanado;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Integer getIntentosMAX() {
        return intentosMAX;
    }

    public void setIntentosMAX(Integer intentosMAX) {
        this.intentosMAX = intentosMAX;
    }

    public Integer getNumeroAdivinar() {
        return numeroAdivinar;
    }

    public void setNumeroAdivinar(Integer numeroAdivinar) {
        this.numeroAdivinar = numeroAdivinar;
    }

    public Integer getIntentosActual() {
        return intentosActual;
    }

    public void setIntentosActual(Integer intentosActual) {
        this.intentosActual = intentosActual;
    }

    public Boolean getHasGanado() {
        return hasGanado;
    }

    public void setHasGanado(Boolean hasGanado) {
        this.hasGanado = hasGanado;
    }

    @Override
    public String toString() {
        return "Numero{" +
                "jugador=" + jugador +
                ", numeroAdivinar=" + numeroAdivinar +
                ", intentosMAX=" + intentosMAX +
                ", intentosActual=" + intentosActual +
                ", hasGanado=" + hasGanado +
                '}';
    }
}
