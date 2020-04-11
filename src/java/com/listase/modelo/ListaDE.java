package com.listase.modelo;

import com.listase.excepciones.InfanteExcepcion;
import java.util.ArrayList;
import java.util.List;

public class ListaDE {
    private NodoDE cabeza;
    
    public ListaDE(NodoDE cabeza){
        this.cabeza=cabeza;
    }
    public ListaDE(){}
    
    public List obtenerListaInfantes() {
        List<Infante> listado = new ArrayList<>();
        //Un método recursivo que recoora mis infantes y que sacando la
        // info la adicione een el string
        listarInfantes(listado);
        return listado;
    }
    public void listarInfantes(List listado) {
        if (cabeza != null) {
            NodoDE temp = cabeza;
            while (temp != null) {
                //listado += temp.getDato()+"\n";
                listado.add(temp.getDato());
                temp = temp.getSiguiente();
            }
        }

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
    
    public void eliminarNodo(NodoDE delete) throws InfanteExcepcion {
        if(cabeza!=null) {
            NodoDE temp=cabeza;
            while (temp!=delete && temp.getSiguiente() != null){
                temp=temp.getSiguiente();
            }
            if (temp==delete) {
                temp.getAnterior().setSiguiente(temp.getSiguiente());
            } else {
                throw new InfanteExcepcion("El código no se encuentra en la lista.");
            }
        }
    }
    public void eliminarNodo(Infante delete) throws InfanteExcepcion {
        if(cabeza!=null) {
            NodoDE temp=cabeza;
            while (temp.getDato()!=delete && temp.getSiguiente() != null){
                temp=temp.getSiguiente();
            }
            temp.getAnterior().setSiguiente(temp.getSiguiente());
            if (temp.getDato()==delete) {
                temp.getAnterior().setSiguiente(temp.getSiguiente());
            } else {
                throw new InfanteExcepcion("El código no se encuentra en la lista.");
            }
        }
    }
    public short contarNodos() {
        if (cabeza == null) {
            return 0;
        } else {
            //llamar a mi ayudante
            NodoDE temp = cabeza;
            short cont = 1;
            while (temp.getSiguiente() != null) {
                temp = temp.getSiguiente();
                cont++;
            }
            return cont;
        }
    }
    public void invertirLista() {
        if (cabeza != null) {
            //Crear una lista temporal la cabeza de la lista temporal está vacía
            ListaDE listaTemporal = new ListaDE();
            // Llamo un ayudante
            NodoDE temp = cabeza;
            //Recorro la lista de principio a fin
            while (temp != null) {
                //Parado en cada nodo , se extrae la información y se
                // envía a la otra lista al inicio
                listaTemporal.adicionarNodoAlInicio(temp.getDato());
                temp = temp.getSiguiente();
            }
            //Igualo la cabeza de mi lista principal a la cabeza de la lista temporal
            cabeza = listaTemporal.getCabeza();
        }
    }
    public void eliminarInfante(short delete) throws InfanteExcepcion {
        if (cabeza != null) {
            NodoDE temp = cabeza;
            while (temp.getDato().getCodigo() != delete && temp.getSiguiente() != null) {
                temp = temp.getSiguiente();
            }
            if (temp.getDato().getCodigo() == delete) {
                temp.getAnterior().setSiguiente(temp.getSiguiente());
            } else {
                throw new InfanteExcepcion("El código no se encuentra en la lista.");
            }

        }
        throw new InfanteExcepcion("La lista de infantes está vacía");
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
