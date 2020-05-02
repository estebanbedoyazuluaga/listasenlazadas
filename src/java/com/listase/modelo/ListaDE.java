/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.listase.modelo;

import com.listase.excepciones.InfanteExcepcion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carloaiza
 */
public class ListaDE implements Serializable {

    private NodoDE cabeza;

    public ListaDE() {
    }

    public NodoDE getCabeza() {
        return cabeza;
    }

    public void setCabeza(NodoDE cabeza) {
        this.cabeza = cabeza;
    }

    public void adicionarNodo(Infante infante) {
        if (cabeza == null) {
            cabeza = new NodoDE(infante);
        } else {
            //Lamo a mi ayudante
            NodoDE temp = cabeza;
            while (temp.getSiguiente() != null) //Mientras que en siguiente exista algo
            {
                temp = temp.getSiguiente();
            }
            //temp va estar ubicado en el ultimo nodo
            temp.setSiguiente(new NodoDE(infante));
            temp.getSiguiente().setAnterior(temp);

        }
    }

    public void adicionarNodoAlInicio(Infante infante) {
        if (cabeza == null) {
            cabeza = new NodoDE(infante);
        } else {
            NodoDE temp = new NodoDE(infante);
            temp.setSiguiente(cabeza);
            cabeza.setAnterior(temp);
            cabeza = temp;
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

    public String obtenerListadoInfantes() {

        //Un método recursivo que recoora mis infantes y que sacando la
        // info la adicione een el string
        return listarInfantes("");
    }

    public String listarInfantes(String listado) {
        if (cabeza != null) {
            NodoDE temp = cabeza;
            while (temp != null) {
                listado += temp.getDato() + "\n";
                temp = temp.getSiguiente();

            }
            return listado;
        }
        return "No hay infantes";
    }

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

    public float promediarEdades() {
        int sumaEdades = 0;
        int contador = 0;
        if (cabeza != null) {
            NodoDE temp = cabeza;
            while (temp != null) {
                //sumaEdades= sumaEdades+ temp.getDato().getEdad();
                sumaEdades += temp.getDato().getEdad();
                contador++;
                temp = temp.getSiguiente();
            }
            return sumaEdades / (float) contador;
        }
        return 0;

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

    public short contarInfantesxGenero(boolean genero) {
        if (cabeza == null) {
            return 0;
        } else {
            //llamar a mi ayudante
            NodoDE temp = cabeza;
            short cont = 0;
            while (temp != null) {
                if (temp.getDato().isGenero() == genero) {
                    cont++;
                }
                temp = temp.getSiguiente();

            }
            return cont;
        }
    }

    public void eliminarInfante(short codigo) throws InfanteExcepcion {
        if (cabeza != null) {
            if (cabeza.getDato().getCodigo() == codigo) {
                cabeza = cabeza.getSiguiente();
                cabeza.setAnterior(null);
                return;
            } else {
                NodoDE temp = cabeza;
                while (temp.getSiguiente() != null) {
                    if (temp.getSiguiente().getDato().getCodigo() == codigo) {
                        //el que sigue es el que hay que eliminar
                        temp.setSiguiente(temp.getSiguiente().getSiguiente());
                        if (temp.getSiguiente() != null) {
                            temp.getSiguiente().setAnterior(temp);
                        }
                        return;
                    }
                    temp = temp.getSiguiente();
                }

                throw new InfanteExcepcion("El código " + codigo + " no existe en la lista");
            }
        }
        throw new InfanteExcepcion("La lista de infantes está vacía");
    }

    public Infante obtenerInfante(short codigo) throws InfanteExcepcion {
        if (cabeza != null) {
            if (cabeza.getDato().getCodigo() == codigo) {
                return cabeza.getDato();
            } else {
                NodoDE temp = cabeza;
                while (temp != null) {
                    if (temp.getDato().getCodigo() == codigo) {
                        return temp.getDato();
                    }
                    temp = temp.getSiguiente();
                }

                throw new InfanteExcepcion("El código " + codigo + " no existe en la lista");
            }
        }
        throw new InfanteExcepcion("La lista de infantes está vacía");
    }
    
    public void adicionarNodoEnPosicion(Infante nuevo, short posicion) throws InfanteExcepcion{
        if (cabeza!=null){
            short tam = contarNodos();
            if (posicion<=0){
                throw new InfanteExcepcion("La posición " + posicion + " no existe en la lista");
            }
            //si la posición dada existe en la lista
            else if (tam + 1 >= posicion) {
                NodoDE temp = cabeza;
                short cont = 1;
                while ( cont != posicion ){
                    temp = temp.getSiguiente();
                    cont++;
                }
                //el nuevo nodo debe estar entre el anterior de temp y temp 
                
                //si temp es el primer nodo
                if (temp == null){
                    adicionarNodo(nuevo);
                } else if(temp.getAnterior() == null){
                    adicionarNodoAlInicio(nuevo);
                } else {
                    //se crea el nuevo nodo del piloto
                    NodoDE temp2 = new NodoDE(nuevo);
                    
                    //se le asigna su anterior y siguiente
                    temp2.setAnterior(temp.getAnterior());
                    temp2.setSiguiente(temp);
                    
                    //ahora se reasignan el nuevo nodo a siguiente y anterior
                    //de los nodos que lo rodean
                    temp.getAnterior().setSiguiente(temp2);
                    temp.setAnterior(temp2);
                }
            }
            //si no existe en la lista
            else {
                throw new InfanteExcepcion("La posición dada no existe en la lista");
            }
        } else {
            throw new InfanteExcepcion("La lista de pilotos está vacía");
        }
        
    }
    
    public short obtenerPosicion(short codigo) throws InfanteExcepcion{
        if (cabeza == null) {
            throw new InfanteExcepcion("La lista de pilotos está vacía");
        } else {
            NodoDE temp = cabeza;
            short cont = 1;
            while (temp != null) {
                if (temp.getDato().getCodigo() == codigo) {
                    return cont;
                }
                temp = temp.getSiguiente();
                cont++;
            }
            throw new InfanteExcepcion("El código " + codigo + " no existe en la lista");
        }
    }
    
    public byte obtenerMenorEdad() throws InfanteExcepcion{
        if (cabeza == null) {
            throw new InfanteExcepcion("La lista de infantes está vacía");
        } else {
            NodoDE temp = cabeza;
            byte menor = temp.getDato().getEdad();
            while (temp != null) {
                if (temp.getDato().getEdad() < menor)
                    menor = temp.getDato().getEdad();
                temp = temp.getSiguiente();
            }
            return menor;
        }
    }

}
