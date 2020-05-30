/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.listase.controlador;

import com.listase.excepciones.JugadorException;
import com.listase.modelo.pirinola.Jugador;
import com.listase.modelo.pirinola.ListaCircularDE;
import com.listase.modelo.pirinola.NodoCircularDE;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.StateMachineConnector;
import org.primefaces.model.diagram.endpoint.BlankEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import org.primefaces.model.diagram.overlay.ArrowOverlay;
import org.primefaces.model.diagram.overlay.LabelOverlay;

/**
 *
 * @author OldarkDesu
 */
@Named(value = "ApplicationPirinola")
@ApplicationScoped
public class ApplicationPirinola {

    private ListaCircularDE jugadores;

    private NodoCircularDE ayudante;

    private Jugador jugadorActual;

    private int caja;

    private int rondasFaltantes;
    

    //INICIO: variables para el menu de opciones.
    private String jugadorAEliminar;
    
    private Jugador nuevoJugador;
    
    //para la vista del diagrama de jugadores
    private DefaultDiagramModel model;

    public ApplicationPirinola() {
        
        nuevoJugador = new Jugador();
        
        jugadores = new ListaCircularDE();
        
        jugadores.adicionarNodo(new Jugador("esteban", 69));
        jugadores.adicionarNodo(new Jugador("juan", 42));
        jugadores.adicionarNodo(new Jugador("andres", 28));
        jugadores.adicionarNodo(new Jugador("santiago", 6));
        
        pintarJugadores();
    }

    public void girarPirinola() {
        double randomDouble = Math.random();
        randomDouble = randomDouble * 6 + 1;
        int randomInt = (int) randomDouble;

        switch (randomInt) {
            case 1:
                ponerUno();
                break;
            case 2:
                ponerDos();
                break;
            case 3:
                tomarUno();
                break;
            case 4:
                tomarDos();
                break;
            case 5:
                tomarTodo();
                break;
            case 6:
                todosPonen();
                break;
        }
        ayudante = ayudante.getSiguiente();
        pintarJugadores();
    }
    
    private void ponerUno() {
        jugadorActual = ayudante.getDato();
        jugadorActual.setFichas(jugadorActual.getFichas() - 1);
        this.caja++;
    }
    private void ponerDos() {
        jugadorActual = ayudante.getDato();
        jugadorActual.setFichas(jugadorActual.getFichas() - 2);
        this.caja += 2;
    }
    private void tomarUno() {
        jugadorActual = ayudante.getDato();
        jugadorActual.setFichas(jugadorActual.getFichas() + 1);
        this.caja--;
    }
    private void tomarDos() {
        jugadorActual = ayudante.getDato();
        jugadorActual.setFichas(jugadorActual.getFichas() - 2);
        this.caja -= 2;
    }
    private void tomarTodo() {
        jugadorActual = ayudante.getDato();
        jugadorActual.setFichas(jugadorActual.getFichas() + caja);
        caja = 0;
    }
    private void todosPonen() {
        NodoCircularDE temp = ayudante;
        do {
            temp.getDato().setFichas(temp.getDato().getFichas() - 1);
            this.caja -= 1;
            temp = temp.getSiguiente();
        } while (temp != ayudante);
    }
    
    public void eliminarJugador() throws JugadorException{
        jugadores.eliminarNodo(jugadorAEliminar);
        pintarJugadores();
    }
    public void agregarJugador(){
        if(!nuevoJugador.getNombre().equals("")){
            jugadores.adicionarNodo(nuevoJugador);
            pintarJugadores();
        }
    }
    public void verSiguiente(){
        jugadores.setCabeza(jugadores.getCabeza().getSiguiente());
        pintarJugadores();
    }
    public void verAnterior(){
        jugadores.setCabeza(jugadores.getCabeza().getAnterior());
        pintarJugadores();
    }
    
    private Connection createConnection(EndPoint from, EndPoint to, String label) {
        Connection conn = new Connection(from, to);
        conn.getOverlays().add(new ArrowOverlay(20, 20, 1, 1));

        if (label != null) {
            conn.getOverlays().add(new LabelOverlay(label, "flow-label", 0.5));
        }

        return conn;
    }
    public void pintarJugadores(){
        //Instancia el modelo
        model = new DefaultDiagramModel();
        //Se establece para que el diagrama pueda tener infinitas flechas
        model.setMaxConnections(-1);

        StateMachineConnector connector = new StateMachineConnector();
        connector.setOrientation(StateMachineConnector.Orientation.ANTICLOCKWISE);
        connector.setPaintStyle("{strokeStyle:'#7D7463',lineWidth:3}");
        model.setDefaultConnector(connector);

        ///Adicionar los elementos
        if (jugadores.getCabeza() != null) {
            //llamo a mi ayudante
            NodoCircularDE temp = jugadores.getCabeza();
            
            //variables para la posicion de los elementos en el diagrama
            double posX;
            double posY;
            int numElementos = jugadores.contarNodos();
            double angle;
            int cont = 0;
            //recorro la lista de principio a fin
            do {
                //Parado en un elemento
                //Crea el cuadrito y lo adiciona al modelo
                
                //calculando la posición del elemento:
                //angle=angulo en el cual se dibujara el elemento
                angle= (2*Math.PI*cont)/numElementos;
                //para acomodar el primer jugador a 90 grados de la horizontal:
                angle += (1.5*Math.PI);
                
                posX = 35 + (15*Math.cos(angle));
                posY = 15 + (15*Math.sin(angle));
                cont++;
                
                Element ele = new Element(temp.getDato().getNombre() + ": " + temp.getDato().getFichas(), 
                        posX + "em", posY + "em");
                ele.setId(String.valueOf(temp.getDato().getNombre()));
                //adiciona un conector al cuadrito
                ele.addEndPoint(new BlankEndPoint(EndPointAnchor.TOP));
                ele.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM_RIGHT));

                ele.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM_LEFT));
                ele.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM));
//                ele.addEndPoint(new BlankEndPoint(EndPointAnchor.TOP));
//                ele.addEndPoint(new BlankEndPoint(EndPointAnchor.RIGHT));
//                ele.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM));
//                ele.addEndPoint(new BlankEndPoint(EndPointAnchor.LEFT));

                //si es el primer elemento, añadirlo a la clase css que lo pone morado.
                if (temp == jugadores.getCabeza()){
                    ele.setStyleClass("ui-diagram-primero");
                }
                
                model.addElement(ele);
                
                temp = temp.getSiguiente();
                
            } while (temp != jugadores.getCabeza());

            //Pinta las flechas            
            for (int i = 0; i < model.getElements().size() - 1; i++) {
                model.connect(createConnection(model.getElements().get(i).getEndPoints().get(1),
                        model.getElements().get(i + 1).getEndPoints().get(0), "Sig"));

                model.connect(createConnection(model.getElements().get(i + 1).getEndPoints().get(2),
                        model.getElements().get(i).getEndPoints().get(3), "Ant"));
            }

        }
    }
    

    //<editor-fold defaultstate="collapsed" desc="Métodos de Acceso">
    public ListaCircularDE getJugadores() {
        return jugadores;
    }

    public void setJugadores(ListaCircularDE jugadores) {
        this.jugadores = jugadores;
    }

    public NodoCircularDE getAyudante() {
        return ayudante;
    }

    public void setAyudante(NodoCircularDE ayudante) {
        this.ayudante = ayudante;
    }

    public Jugador getJugadorActual() {
        return jugadorActual;
    }

    public void setJugadorActual(Jugador jugadorActual) {
        this.jugadorActual = jugadorActual;
    }

    public int getCaja() {
        return caja;
    }

    public void setCaja(int caja) {
        this.caja = caja;
    }

    public int getRondasFaltantes() {
        return rondasFaltantes;
    }

    public void setRondasFaltantes(int rondasFaltantes) {
        this.rondasFaltantes = rondasFaltantes;
    }

    public DefaultDiagramModel getModel() {
        return model;
    }

    public void setModel(DefaultDiagramModel model) {
        this.model = model;
    }

    public String getJugadorAEliminar() {
        return jugadorAEliminar;
    }

    public void setJugadorAEliminar(String jugadorAEliminar) {
        this.jugadorAEliminar = jugadorAEliminar;
    }

    public Jugador getNuevoJugador() {
        return nuevoJugador;
    }

    public void setNuevoJugador(Jugador nuevoJugador) {
        this.nuevoJugador = nuevoJugador;
    }
    
    
    
//</editor-fold>
    
    public void handleToggle(ToggleEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Toggled", "Visibility:" + event.getVisibility());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
