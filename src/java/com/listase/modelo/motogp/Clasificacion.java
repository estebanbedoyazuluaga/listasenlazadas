/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.listase.modelo.motogp;

/**
 *
 * @author OldarkDesu
 */
public class Clasificacion {
    private ListaGP misJugadores;

    public Clasificacion(ListaGP misJugadores) {
        this.misJugadores = misJugadores;
    }

    public Clasificacion(){
        
    }
    
    public ListaGP getMisJugadores() {
        return misJugadores;
    }

    public void setMisJugadores(ListaGP misJugadores) {
        this.misJugadores = misJugadores;
    }
    
}
