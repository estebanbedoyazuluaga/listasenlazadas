/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.listase.modelo.pirinola;

/**
 *
 * @author OldarkDesu
 */
public class Jugador {
    private String nombre;
    private int fichas;
   

    public Jugador(String nombre, int fichas) {
        this.fichas = fichas;
        this.nombre = nombre;
    }

    public Jugador() {
    }

    
    
    public int getFichas() {
        return fichas;
    }

    public void setFichas(int fichas) {
        this.fichas = fichas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    
}
