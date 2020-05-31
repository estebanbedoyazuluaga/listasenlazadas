/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.listase.modelo.pirinola;

import com.listase.excepciones.JugadorException;
import java.io.Serializable;

/**
 *
 * @author carloaiza
 */
public class ListaCircularDE implements Serializable {

    private NodoCircularDE cabeza;

    public NodoCircularDE getCabeza() {
        return cabeza;
    }

    public void setCabeza(NodoCircularDE cabeza) {
        this.cabeza = cabeza;
    }

    public void adicionarNodo(Jugador jugador) {
        if (cabeza == null) {
            cabeza = new NodoCircularDE(jugador);
            ///Hago los enlaces circulares
            cabeza.setSiguiente(cabeza);
            cabeza.setAnterior(cabeza);

        } else {
            //Lamo a mi ayudante
            NodoCircularDE temp = cabeza.getAnterior();
            //temp= temp.getAnterior();
            NodoCircularDE nodoInsertar = new NodoCircularDE(jugador);
            temp.setSiguiente(nodoInsertar);
            nodoInsertar.setAnterior(temp);
            nodoInsertar.setSiguiente(cabeza);
            cabeza.setAnterior(nodoInsertar);
        }
    }

    public void adicionarNodoAlInicio(Jugador jugador) {
        if (cabeza == null) {
            cabeza = new NodoCircularDE(jugador);
            ///Hago los enlaces circulares
            cabeza.setSiguiente(cabeza);
            cabeza.setAnterior(cabeza);
        } else {
            NodoCircularDE temp = cabeza.getAnterior();
            //temp= temp.getAnterior();
            NodoCircularDE nodoInsertar = new NodoCircularDE(jugador);
            temp.setSiguiente(nodoInsertar);
            nodoInsertar.setAnterior(temp);
            nodoInsertar.setSiguiente(cabeza);
            cabeza.setAnterior(nodoInsertar);
            cabeza = cabeza.getAnterior();
        }
    }

    public short contarNodos() {
        if (cabeza == null) {
            return 0;
        } else {
            //llamar a mi ayudante
            NodoCircularDE temp = cabeza;
            short cont = 1;
            while (temp.getSiguiente() != cabeza) {
                temp = temp.getSiguiente();
                cont++;
            }
            return cont;
        }
    }

    public String listarJugadors(String listado) throws JugadorException {
        if (cabeza != null) {
            NodoCircularDE temp = cabeza;
//            while (temp.getSiguiente() != cabeza) {
//                listado += temp.getDato() + "\n";
//                temp = temp.getSiguiente();
//            }
//            listado += temp.getDato() + "\n";
            do {
                listado += temp.getDato() + "\n";
                temp = temp.getSiguiente();
            } while (temp != cabeza);

            return listado;
        }
        throw new JugadorException(("No existen jugadors en la lista"));
    }

    //Eliminar NOdo
    public void eliminarNodo(String nombre) throws JugadorException {
        if (cabeza != null) {
            if (cabeza.getSiguiente() == cabeza) {
                if (cabeza.getDato().getNombre().equals(nombre)) {
                    cabeza = null;
                    return;
                }
            } else {
                NodoCircularDE temp = cabeza;
                do {
                    if (temp.getDato().getNombre().equals(nombre)) {
                        //estamos parados en el que hay que eliminar
                        temp.getAnterior().setSiguiente(temp.getSiguiente());
                        temp.getSiguiente().setAnterior(temp.getAnterior());
                        return;
                    }
                    temp = temp.getSiguiente();
                } while (temp != cabeza);
            }
            throw new JugadorException(("El Jugador no se encuentra en la lista"));
        } else {
            throw new JugadorException(("No existen jugadors en la lista"));
        }
    }
    
    public int obtenerMayoresFichas() throws JugadorException {
        if (cabeza == null) {
            throw new JugadorException("La lista de pilotos está vacía");
        } else {
            NodoCircularDE temp = cabeza;
            int max = temp.getDato().getFichas();
            do {
                if (temp.getDato().getFichas() > max) {
                    max = temp.getDato().getFichas();
                }
                temp = temp.getSiguiente();
            } while (temp != cabeza);
            return max;
        }
    }
    
    public String obtenerGanadores()throws JugadorException{
        int numFichas = obtenerMayoresFichas();
        NodoCircularDE temp = cabeza;
        String ganadores="";
        do{
            if (temp.getDato().getFichas() == numFichas)
                ganadores += temp.getDato().getNombre() + ", ";
            temp=temp.getSiguiente();
        }while(temp!=cabeza);
        return ganadores;
    }
    
}
