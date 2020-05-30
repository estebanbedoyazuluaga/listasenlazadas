/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.listase.modelo.pirinola;

/**
 *
 * @author carloaiza
 */
public class NodoCircularDE {
    private Jugador dato;
    private NodoCircularDE siguiente;
    private NodoCircularDE anterior;

    public NodoCircularDE(Jugador dato) {
        this.dato = dato;
    }

    public Jugador getDato() {
        return dato;
    }

    public void setDato(Jugador dato) {
        this.dato = dato;
    }

    public NodoCircularDE getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoCircularDE siguiente) {
        this.siguiente = siguiente;
    }

    public NodoCircularDE getAnterior() {
        return anterior;
    }

    public void setAnterior(NodoCircularDE anterior) {
        this.anterior = anterior;
    }
    
    
    
}
