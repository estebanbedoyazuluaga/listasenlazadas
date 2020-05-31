/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.listase.controlador;

import com.listase.excepciones.JugadorException;
import com.listase.modelo.Usuario;
import com.listase.modelo.pirinola.Jugador;
import com.listase.modelo.pirinola.ListaCircularDE;
import com.listase.modelo.pirinola.NodoCircularDE;
import java.io.Serializable;
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
public class ApplicationPirinola implements Serializable{

    private String correoTurno;

    private ListaCircularDE listaJugadores;

    private NodoCircularDE jugadorActual;

    private int caja = 10;

    private int turnosFaltantes = 20;

    private boolean inGame=true;
    
    //variables para el menu de opciones.
    private String jugadorAEliminar;
    private Jugador nuevoJugador = new Jugador();

    //para la vista del diagrama de listaJugadores
    private DefaultDiagramModel model;

    public ApplicationPirinola() {

        caja = 10;
        turnosFaltantes = 20;
        
        listaJugadores = new ListaCircularDE();

        cargarJugadores();

        correoTurno = ControladorUsuarios.getUsuarios().get(0).getCorreo();
        
        jugadorActual = listaJugadores.getCabeza();
        
        pintarJugadores();
    }

    public boolean validarTurno(String correo) {
        if (turnosFaltantes <= 0) {
            return false;
        }
        if (this.correoTurno.equals(correo)) {
            return true;
        }
        return false;
    }

    public void pasarTurno(String correo) throws JugadorException {
        this.correoTurno = jugadorActual.getSiguiente().getDato().getCorreo();
        girarPirinola();
    }

    public void girarPirinola() throws JugadorException {

        double randomDouble = Math.random();
        randomDouble = randomDouble * 6 + 1;
        int randomInt = (int) randomDouble;

        int fichas = jugadorActual.getDato().getFichas();

        switch (randomInt) {
            case 1:
                //pon 1
                if (fichas >= 1) {
                    jugadorActual.getDato().setFichas(fichas - 1);
                    this.caja++;
                }

                break;
            case 2:
                //pon 2
                if (fichas >= 2) {
                    jugadorActual.getDato().setFichas(fichas - 2);
                    this.caja += 2;
                } else {
                    jugadorActual.getDato().setFichas(0);
                    this.caja += fichas;
                }
                break;
            case 3:
                //toma 1
                if (caja >= 1) {
                    jugadorActual.getDato().setFichas(fichas + 1);
                    this.caja--;
                }
                break;
            case 4:
                //toma 2
                if (caja >= 2) {
                    jugadorActual.getDato().setFichas(fichas + 2);
                    this.caja -= 2;
                } else {
                    jugadorActual.getDato().setFichas(fichas + caja);
                    this.caja = 0;
                }

                break;
            case 5:
                //toma todo
                jugadorActual.getDato().setFichas(fichas + caja);
                caja = 0;
            case 6:
                //todos ponen
                NodoCircularDE temp = jugadorActual;
                do {
                    if (temp.getDato().getFichas() >= 1) {
                        temp.getDato().setFichas(temp.getDato().getFichas() - 1);
                        this.caja++;
                    }
                    if (temp.getDato().getFichas() <= 0) {
                        listaJugadores.eliminarNodo(temp.getDato().getNombre());
                    }
                    temp = temp.getSiguiente();

                } while (temp != jugadorActual);
                break;
        }

        if (caja <= 0) {
            caja = 0;
        }

        jugadorActual = jugadorActual.getSiguiente();
        listaJugadores.setCabeza(jugadorActual.getSiguiente());

        if (jugadorActual.getAnterior().getDato().getFichas() <= 0) {
            listaJugadores.eliminarNodo(jugadorActual.getAnterior().getDato().getNombre());
        }

        if (listaJugadores.contarNodos() == 1) {
            inGame = false;
        }
        pintarJugadores();

    }
    
    //<editor-fold defaultstate="collapsed" desc="Acciones del panel de administrador(DESHABILITADO)">
    public void eliminarJugador() throws JugadorException {
        listaJugadores.eliminarNodo(jugadorAEliminar);
        pintarJugadores();
    }

    public void agregarJugador() {
        if (!nuevoJugador.getNombre().equals("")) {
            listaJugadores.adicionarNodo(nuevoJugador);
            pintarJugadores();
        }
    }

    public void verSiguiente() {
        listaJugadores.setCabeza(listaJugadores.getCabeza().getSiguiente());
        pintarJugadores();
    }

    public void verAnterior() {
        listaJugadores.setCabeza(listaJugadores.getCabeza().getAnterior());
        pintarJugadores();
    }

//</editor-fold>
    
    private Connection createConnection(EndPoint from, EndPoint to, String label) {
        Connection conn = new Connection(from, to);
        conn.getOverlays().add(new ArrowOverlay(20, 20, 1, 1));

        if (label != null) {
            conn.getOverlays().add(new LabelOverlay(label, "flow-label", 0.5));
        }

        return conn;
    }
    public void pintarJugadores() {
        //Instancia el modelo
        model = new DefaultDiagramModel();
        //Se establece para que el diagrama pueda tener infinitas flechas
        model.setMaxConnections(-1);

        StateMachineConnector connector = new StateMachineConnector();
        connector.setOrientation(StateMachineConnector.Orientation.ANTICLOCKWISE);
        connector.setPaintStyle("{strokeStyle:'#7D7463',lineWidth:3}");
        model.setDefaultConnector(connector);

        ///Adicionar los elementos
        if (listaJugadores.getCabeza() != null) {
            //llamo a mi jugadorActual
            NodoCircularDE temp = listaJugadores.getCabeza();

            /**
             * variables para la posicion de los elementos en el diagrama:
             * 
             *      'posY' y 'posX': indican el centro del circulo
             *      'numElementos': indica el total de los puntos que se van a distribuir
             *      'angle': indica el ángulo en el que se dibujará el punto
             *      'cont': indica el número 
             */
            double posX;
            double posY;
            int numElementos = listaJugadores.contarNodos();
            double angle;
            int cont = 0;//
            
            
            //recorro la lista de principio a fin
            do {
                

                //calculando la posición del elemento:
                angle = (2 * Math.PI * cont) / numElementos;
                
                //para acomodar el primer jugador a 90 grados de la horizontal:
                angle += (1.5 * Math.PI);

                posX = 35 + (15 * Math.cos(angle));
                posY = 15 + (15 * Math.sin(angle));
                cont++;
                
                //Parado en un elemento
                //Crea el cuadrito y lo adiciona al modelo
                Element ele = new Element(temp.getDato().getNombre() + ": " + temp.getDato().getFichas(),
                        posX + "em", posY + "em");
                ele.setId(String.valueOf(temp.getDato().getNombre()));
                //adiciona un conector al cuadrito
                ele.addEndPoint(new BlankEndPoint(EndPointAnchor.TOP));
                ele.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM_RIGHT));

                ele.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM_LEFT));
                ele.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM));

                //si es el primer elemento, añadirlo a la clase css que lo pone morado.
                if (temp == listaJugadores.getCabeza()) {
                    ele.setStyleClass("ui-diagram-primero");
                }

                model.addElement(ele);

                temp = temp.getSiguiente();

            } while (temp != listaJugadores.getCabeza());

            //Pinta las flechas            
            for (int i = 0; i < model.getElements().size() - 1; i++) {
                model.connect(createConnection(model.getElements().get(i).getEndPoints().get(1),
                        model.getElements().get(i + 1).getEndPoints().get(0), "Sig"));

                model.connect(createConnection(model.getElements().get(i + 1).getEndPoints().get(2),
                        model.getElements().get(i).getEndPoints().get(3), "Ant"));
            }
            
            //pone un elemento en el centro del diagrama con las fichas en la caja
            model.addElement(new Element("Caja: " + this.caja, 35 + "em", 15 + "em"));
        }
    }

    private void cargarJugadores(){
        listaJugadores = new ListaCircularDE();
        for (Usuario usuario : ControladorUsuarios.getUsuarios()) {
            listaJugadores.adicionarNodo(
                    new Jugador(usuario.getNombreCompleto(),usuario.getCorreo(), 15)
            );
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="Métodos de Acceso">

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }
    
    public ListaCircularDE getListaJugadores() {
        return listaJugadores;
    }

    public void setListaJugadores(ListaCircularDE listaJugadores) {
        this.listaJugadores = listaJugadores;
    }

    public NodoCircularDE getJugadorActual() {
        return jugadorActual;
    }

    public void setJugadorActual(NodoCircularDE jugadorActual) {
        this.jugadorActual = jugadorActual;
    }

    public int getCaja() {
        return caja;
    }

    public void setCaja(int caja) {
        this.caja = caja;
    }

    public String getCorreoTurno() {
        return correoTurno;
    }

    public void setCorreoTurno(String correoTurno) {
        this.correoTurno = correoTurno;
    }

    public int getTurnosFaltantes() {
        return turnosFaltantes;
    }

    public void setTurnosFaltantes(int turnosFaltantes) {
        this.turnosFaltantes = turnosFaltantes;
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
