/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.listase.modelo.motogp;

import com.listase.excepciones.PilotoException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ESTEBAN
 */
public class ListaGP {

    private NodoGP cabeza;

    public ListaGP() {
    }

    public NodoGP getCabeza() {
        return cabeza;
    }

    public void setCabeza(NodoGP cabeza) {
        this.cabeza = cabeza;
    }

    public void adicionarNodo(Piloto piloto) {
        if (cabeza == null) {
            cabeza = new NodoGP(piloto);
        } else {
            NodoGP temp = cabeza;
            while (temp.getSiguiente() != null) {
                temp = temp.getSiguiente();
            }
            temp.setSiguiente(new NodoGP(piloto));
            temp.getSiguiente().setAnterior(temp);

        }
    }

    public void adicionarNodoAlInicio(Piloto piloto) {
        if (cabeza == null) {
            cabeza = new NodoGP(piloto);
        } else {
            NodoGP temp = new NodoGP(piloto);
            temp.setSiguiente(cabeza);
            cabeza.setAnterior(temp);
            cabeza = temp;
        }
    }

    public short contarNodos() {
        if (cabeza == null) {
            return 0;
        } else {
            NodoGP temp = cabeza;
            short cont = 1;
            while (temp.getSiguiente() != null) {
                temp = temp.getSiguiente();
                cont++;
            }
            return cont;
        }
    }

    public String obtenerListadoPilotos() {

        //Un método recursivo que recoora mis pilotos y que sacando la
        // info la adicione een el string
        return listarPilotos("");
    }

    public String listarPilotos(String listado) {
        if (cabeza != null) {
            NodoGP temp = cabeza;
            while (temp != null) {
                listado += temp.getDato() + "\n";
                temp = temp.getSiguiente();

            }
            return listado;
        }
        return "No hay pilotos";
    }

    public List obtenerListaPilotos() {
        List<Piloto> listado = new ArrayList<>();
        //Un método recursivo que recoora mis pilotos y que sacando la
        // info la adicione een el string
        listarPilotos(listado);
        return listado;
    }

    public void listarPilotos(List listado) {
        if (cabeza != null) {
            NodoGP temp = cabeza;
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
            NodoGP temp = cabeza;
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
            ListaGP listaTemporal = new ListaGP();
            // Llamo un ayudante
            NodoGP temp = cabeza;
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

    public void eliminarPiloto(short codigo) throws PilotoException {
        if (cabeza != null) {
            if (cabeza.getDato().getCodigo() == codigo) {
                cabeza = cabeza.getSiguiente();
                cabeza.setAnterior(null);
                return;
            } else {
                NodoGP temp = cabeza;
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

                throw new PilotoException("El código " + codigo + " no existe en la lista");
            }
        }
        throw new PilotoException("La lista de pilotos está vacía");
    }

    public Piloto obtenerPiloto(short codigo) throws PilotoException {
        if (cabeza != null) {
            if (cabeza.getDato().getCodigo() == codigo) {
                return cabeza.getDato();
            } else {
                NodoGP temp = cabeza;
                while (temp != null) {
                    if (temp.getDato().getCodigo() == codigo) {
                        return temp.getDato();
                    }
                    temp = temp.getSiguiente();
                }

                throw new PilotoException("El código " + codigo + " no existe en la lista");
            }
        }
        throw new PilotoException("La lista de pilotos está vacía");
    }

    public void adicionarNodoEnPosicion(Piloto nuevo, short posicion) throws PilotoException {
        if (cabeza != null) {
            short tam = contarNodos();
            if (posicion <= 0) {
                throw new PilotoException("La posición " + posicion + " no existe en la lista");
            } //si la posición dada existe en la lista
            else if (tam + 1 >= posicion) {
                NodoGP temp = cabeza;
                short cont = 1;
                while (cont != posicion) {
                    temp = temp.getSiguiente();
                    cont++;
                }
                //el nuevo nodo debe estar entre el anterior de temp y temp 

                //si temp es el primer nodo
                if (temp == null) {
                    adicionarNodo(nuevo);
                } else if (temp.getAnterior() == null) {
                    adicionarNodoAlInicio(nuevo);
                } else {
                    //se crea el nuevo nodo del piloto
                    NodoGP temp2 = new NodoGP(nuevo);

                    //se le asigna su anterior y siguiente
                    temp2.setAnterior(temp.getAnterior());
                    temp2.setSiguiente(temp);

                    //ahora se reasignan el nuevo nodo a siguiente y anterior
                    //de los nodos que lo rodean
                    temp.getAnterior().setSiguiente(temp2);
                    temp.setAnterior(temp2);
                }
            } //si no existe en la lista
            else {
                throw new PilotoException("La posición dada no existe en la lista");
            }
        } else {
            throw new PilotoException("La lista de pilotos está vacía");
        }

    }

    public short obtenerPosicion(short codigo) throws PilotoException {
        if (cabeza == null) {
            throw new PilotoException("La lista de pilotos está vacía");
        } else {
            NodoGP temp = cabeza;
            short cont = 1;
            while (temp != null) {
                if (temp.getDato().getCodigo() == codigo) {
                    return cont;
                }
                temp = temp.getSiguiente();
                cont++;
            }
            throw new PilotoException("El código " + codigo + " no existe en la lista");
        }
    }

    public byte obtenerMenorEdad() throws PilotoException {
        if (cabeza == null) {
            throw new PilotoException("La lista de pilotos está vacía");
        } else {
            NodoGP temp = cabeza;
            byte menor = temp.getDato().getEdad();
            while (temp != null) {
                if (temp.getDato().getEdad() < menor) {
                    menor = temp.getDato().getEdad();
                }
                temp = temp.getSiguiente();
            }
            return menor;
        }
    }

    public void intercambiarNodos(byte datoA, byte datoB) throws PilotoException {
        if (cabeza != null) {
            throw new PilotoException("La lista de pilotos está vacía");
        } else {
            NodoGP temp = cabeza;
            NodoGP tempDatoA = null;
            NodoGP tempDatoB = null;

            while (temp != null) {
                if (temp.getDato().getCodigo() == datoA) {
                    tempDatoA = temp;
                } else if (temp.getDato().getCodigo() == datoB) {
                    tempDatoB = temp;
                    temp = temp.getSiguiente();
                }

                if (tempDatoA == null || tempDatoB == null) {
                    throw new PilotoException("Alguno de los datos no se encuentra en la lista.");
                }

                Piloto pilotoTemp = tempDatoA.getDato();

                tempDatoA.setDato(tempDatoB.getDato());
                tempDatoB.setDato(pilotoTemp);

            }

        }
    }
}
