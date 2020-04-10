/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.listase.modelo;

/**
 *
 * @author ESTEBAN
 */
public class ListaDE {
    private NodoDE cabeza;
    
    public ListaDE(NodoDE cabeza){
        this.cabeza=cabeza;
    }
    
    public void adicionarNodo(NodoDE nuevo){
        if (cabeza==null)
            cabeza=nuevo;
        else {
            NodoDE temp=cabeza;
            while (temp.getSiguiente()!=null){
                temp=temp.getSiguiente();
            }
            temp.setSiguiente(nuevo);
        }
    }
    public void adicionarNodo(Infante nuevo){
        if (cabeza==null)
            cabeza=new NodoDE(nuevo);
        else {
            NodoDE temp=cabeza;
            while (temp.getSiguiente()!=null){
                temp=temp.getSiguiente();
            }
            temp.setSiguiente(new NodoDE(nuevo));
        }
    }
    public void adicionarNodoAlInicio(NodoDE nuevo){
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoDE temp = nuevo;
            temp.setSiguiente(cabeza);
            cabeza = temp;
        }
    }
    public void adicionarNodoAlInicio(Infante nuevo){
        if (cabeza == null) {
            cabeza = new NodoDE(nuevo);
        } else {
            NodoDE temp = new NodoDE(nuevo);
            temp.setSiguiente(cabeza);
            cabeza = temp;
        }
    }
    
    public void eliminarNodo(NodoDE delete){
        if(cabeza!=null) {
            NodoDE temp=cabeza;
            while (temp!=delete){
                temp=temp.getSiguiente();
            }
            temp.getAnterior().setSiguiente(temp.getSiguiente());
        }
    }
    public void eliminarNodo(Infante delete){
        if(cabeza!=null) {
            NodoDE temp=cabeza;
            while (temp.getDato()!=delete){
                temp=temp.getSiguiente();
            }
            temp.getAnterior().setSiguiente(temp.getSiguiente());
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="Métodos de acceso">
    public NodoDE getCabeza() {
        return cabeza;
    }

    public void setCabeza(NodoDE cabeza) {
        this.cabeza = cabeza;
    }
//</editor-fold>
    
}
