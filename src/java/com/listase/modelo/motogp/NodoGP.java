/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.listase.modelo.motogp;

/**
 *
 * @author ESTEBAN
 */
public class NodoGP {

    private Piloto dato;
    private NodoGP siguiente;
    private NodoGP anterior;

    public NodoGP(Piloto dato) {
        this.dato = dato;
    }
    
    public NodoGP(){
        
    }
    

    public Piloto getDato() {
        return dato;
    }

    public void setDato(Piloto dato) {
        this.dato = dato;
    }

    public NodoGP getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoGP siguiente) {
        this.siguiente = siguiente;
    }

    public NodoGP getAnterior() {
        return anterior;
    }

    public void setAnterior(NodoGP anterior) {
        this.anterior = anterior;
    }

}
