/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.listase.controlador;

import com.listase.excepciones.PilotoException;
import com.listase.modelo.motogp.Piloto;
import com.listase.modelo.motogp.ListaGP;
import com.listase.modelo.motogp.Moto;
import com.listase.modelo.motogp.NodoGP;
import com.listase.utilidades.JsfUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.DiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.StateMachineConnector;
import org.primefaces.model.diagram.endpoint.BlankEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import org.primefaces.model.diagram.overlay.ArrowOverlay;
import org.primefaces.model.diagram.overlay.LabelOverlay;

/**
 *
 * @author ESTEBAN
 */
@Named(value = "SesionMotoGP")
@SessionScoped
public class SesionMotoGP implements Serializable {

    private ListaGP listaPilotos;
    private Piloto piloto;
    private String alInicio = "1";
    private boolean deshabilitarFormulario = true;
    private NodoGP ayudante;
    private String textoVista = "Gráfico";

    private List listadoPilotos;

    private DefaultDiagramModel model;

    private short codigoEliminar;

    private ControladorLocalidades controlLocalidades;

    private String codigoDeptoSel;

    private short pilotoSeleccionado;

    private Piloto pilotoDiagrama;

    private short moverPilotoCantidad;
    
    private Piloto pilotoMenorEdad;
    
    byte pilotoAIntercambiarA;
    
    byte pilotoAIntercambiarB;
    
    byte posicionReingreso;
    
    public SesionMotoGP() {
    }
    
    //<editor-fold defaultstate="collapsed" desc="Clasificacion [ IGNORAR | NO ESTÁ EN USO ]">
    
    private Piloto pilotoClasificacion;
    
    
    private double tiempoPiloto;//segundos
    
    //sin usar:
    public void guardarPilotoClasificacion() throws PilotoException {
        pilotoClasificacion.setTiempoClasi(tiempoPiloto);

        if (listaPilotos.getCabeza() == null) {
            listaPilotos.adicionarNodo(pilotoClasificacion);
        } else {
            NodoGP temp = listaPilotos.getCabeza();
            short cont = 1;//cuenta las posiciones
            
            //verifica si la lista solo tiene un nodo:
            if (temp.getSiguiente() == listaPilotos.getCabeza()) {
                if (tiempoPiloto >= listaPilotos.getCabeza().getDato().getTiempoClasi()) {
                    listaPilotos.adicionarNodo(pilotoClasificacion);
                } else {
                    listaPilotos.adicionarNodoAlInicio(pilotoClasificacion);
                }
            } else {
                do {
                    //pregunta si se ha llegado al final de la lista:
                    if (temp.getSiguiente() == listaPilotos.getCabeza()) {
                        listaPilotos.adicionarNodoAlInicio(pilotoClasificacion);
                        break;
                    }//Pregunta si el jugador debería ir entre temp y el siguiente de temp
                    else if (temp.getDato().getTiempoClasi() <= tiempoPiloto
                            && temp.getSiguiente().getDato().getTiempoClasi() > tiempoPiloto) {

                        listaPilotos.adicionarNodoEnPosicion(pilotoClasificacion, ++cont);
                        break;
                    }
                    cont++;
                    temp = temp.getSiguiente();
                } while (temp != listaPilotos.getCabeza());
            }
        }
    }
    
    public void limpiarClasificacion(){
        listaPilotos = new ListaGP();
    }
    
    
    //Sin usar:
    public String generarListaClasificacion() {
        if (listaPilotos.getCabeza() != null) {
            String retorno = "";
            NodoGP temp = listaPilotos.getCabeza();
            while(temp != null){
                //esta mondá no sirve 
                //WONTFIX: la siguiente línea genera un NullPointerException y no entiendo por qué
                retorno += temp.getSiguiente().getDato().toStringMejor() + '\n';
                temp = temp.getSiguiente();
            }
            return retorno;
        } else {
            return "(lista vacía)";
        }
    }
    
    
    
    //</editor-fold>
    
    private boolean verPanelClasificacion;
    private boolean carreraEnCurso;
    
    private String mensajeFinalCarrera;
    
    public void comenzarCarrera() throws PilotoException {
        if (listaPilotos.getCabeza() != null) {
            ordenarPilotos();
            this.verPanelClasificacion = false;
            this.carreraEnCurso = true;
            listadoPilotos = listaPilotos.obtenerListaPilotos();
            pintarLista();
        } else {
            JsfUtil.addErrorMessage("Debe haber por lo menos un piloto compitiendo");
        }
    }
    
    public void finalizarCarrera() throws PilotoException{
        this.carreraEnCurso = false;
    }
    
    public String obtenerPosicionesCarrera(){
        NodoGP temp = listaPilotos.getCabeza();
        if (temp!=null){
            String msg="";
            short cont = 0;
            while(temp != null) {
                msg += ++cont + "." + temp.getDato().getNombre() + "[Cod." + temp.getDato().getCodigo() + "] \n";
                temp = temp.getSiguiente();
            }
            return msg;
        } else {
            return "No finalizó ningún piloto";
        }
    }
    
    public void reingresarPiloto() throws PilotoException{
        short posicionActual = listaPilotos.obtenerPosicion(pilotoSeleccionado);
        if (posicionReingreso > posicionActual){
            
            Piloto temp = listaPilotos.obtenerPiloto(pilotoSeleccionado);
            listaPilotos.eliminarPiloto(pilotoSeleccionado);
            listaPilotos.adicionarNodoEnPosicion(temp, posicionReingreso);
            
            listadoPilotos = listaPilotos.obtenerListaPilotos();
            pintarLista();
        }else {
            JsfUtil.addErrorMessage("Posicion de reingreso inválida");
        }
    }
    
    @PostConstruct
    public void inicializar() {
        controlLocalidades = new ControladorLocalidades();
        //inicializando el combo en el primer depto
        codigoDeptoSel = controlLocalidades.getDepartamentos().get(0).getCodigo();

        listaPilotos = new ListaGP();
        
        this.carreraEnCurso = false;
        
        this.verPanelClasificacion = true;

        //LLenado de la bds
        listaPilotos.adicionarNodo(new Piloto("Fulano", "Colombiano", "rosa", (byte) 22, (byte) 4, new Moto(), (short) 0001, 3.0));
        listaPilotos.adicionarNodo(new Piloto("Sultano", "Ecuatoriano", "purpura", (byte) 25, (byte) 4, new Moto(), (short) 0002, 2.0));
        listaPilotos.adicionarNodo(new Piloto("Mengano", "Venezolano", "verde", (byte) 19, (byte) 4, new Moto(), (short) 0003, 1.0));
        listaPilotos.adicionarNodo(new Piloto("Perengano", "Peruano", "amarillo", (byte) 28, (byte) 4, new Moto(), (short) 0004, 0.5));
        
        ayudante = listaPilotos.getCabeza();
        piloto = ayudante.getDato();
        piloto = new Piloto();
        pilotoClasificacion = new Piloto();

        //Me llena el objeto List para la tabla
        listadoPilotos = listaPilotos.obtenerListaPilotos();
        pintarLista();
    }

    //<editor-fold defaultstate="collapsed" desc="Metodos de acceso">

    public byte getPosicionReingreso() {
        return posicionReingreso;
    }

    public void setPosicionReingreso(byte posicionReingreso) {
        this.posicionReingreso = posicionReingreso;
    }

    public String getMensajeFinalCarrera() {
        return mensajeFinalCarrera;
    }

    public void setMensajeFinalCarrera(String mensajeFinalCarrera) {
        this.mensajeFinalCarrera = mensajeFinalCarrera;
    }

    
    
    public double getTiempoPiloto() {
        return tiempoPiloto;
    }

    public void setTiempoPiloto(double tiempoPiloto) {
        this.tiempoPiloto = tiempoPiloto;
    }

    public boolean isVerPanelClasificacion() {
        return verPanelClasificacion;
    }

    public void setVerPanelClasificacion(boolean verPanelClasificacion) {
        this.verPanelClasificacion = verPanelClasificacion;
    }

    public boolean isCarreraEnCurso() {
        return carreraEnCurso;
    }

    public void setCarreraEnCurso(boolean carreraEnCurso) {
        this.carreraEnCurso = carreraEnCurso;
    }
    
    
    
    public Piloto getPilotoClasificacion() {
        return pilotoClasificacion;
    }

    public void setPilotoClasificacion(Piloto pilotoClasificacion) {
        this.pilotoClasificacion = pilotoClasificacion;
    }
    
    public Piloto getPilotoMenorEdad() {
        return pilotoMenorEdad;
    }

    public void setPilotoMenorEdad(Piloto pilotoMenorEdad) {
        this.pilotoMenorEdad = pilotoMenorEdad;
    }
    
    public Piloto getPilotoDiagrama() {
        return pilotoDiagrama;
    }

    public void setPilotoDiagrama(Piloto pilotoDiagrama) {
        this.pilotoDiagrama = pilotoDiagrama;
    }

    public short getPilotoSeleccionado() {
        return pilotoSeleccionado;
    }

    public void setPilotoSeleccionado(short pilotoSeleccionado) {
        this.pilotoSeleccionado = pilotoSeleccionado;
    }

    public String getCodigoDeptoSel() {
        return codigoDeptoSel;
    }

    public void setCodigoDeptoSel(String codigoDeptoSel) {
        this.codigoDeptoSel = codigoDeptoSel;
    }

    public ControladorLocalidades getControlLocalidades() {
        return controlLocalidades;
    }

    public void setControlLocalidades(ControladorLocalidades controlLocalidades) {
        this.controlLocalidades = controlLocalidades;
    }

    public DiagramModel getModel() {
        return model;
    }
    
    public short getCodigoEliminar() {
        return codigoEliminar;
    }

    public void setCodigoEliminar(short codigoEliminar) {
        this.codigoEliminar = codigoEliminar;
    }

    public String getTextoVista() {
        return textoVista;
    }

    public void setTextoVista(String textoVista) {
        this.textoVista = textoVista;
    }

    public List getListadoPilotos() {
        return listadoPilotos;
    }

    public void setListadoPilotos(List listadoPilotos) {
        this.listadoPilotos = listadoPilotos;
    }

    public boolean isDeshabilitarFormulario() {
        return deshabilitarFormulario;
    }

    public void setDeshabilitarFormulario(boolean deshabilitarFormulario) {
        this.deshabilitarFormulario = deshabilitarFormulario;
    }

    public String getAlInicio() {
        return alInicio;
    }

    public void setAlInicio(String alInicio) {
        this.alInicio = alInicio;
    }

    public ListaGP getListaPilotos() {
        return listaPilotos;
    }

    public void setListaPilotos(ListaGP listaPilotos) {
        this.listaPilotos = listaPilotos;
    }

    public Piloto getPiloto() {
        return piloto;
    }

    public void setPiloto(Piloto piloto) {
        this.piloto = piloto;
    }

    public short getMoverPilotoCantidad() {
        return moverPilotoCantidad;
    }

    public void setMoverPilotoCantidad(short moverPilotoCantidad) {
        this.moverPilotoCantidad = moverPilotoCantidad;
    }

    public byte getPilotoAIntercambiarA() {
        return pilotoAIntercambiarA;
    }

    public void setPilotoAIntercambiarA(byte pilotoAIntercambiarA) {
        this.pilotoAIntercambiarA = pilotoAIntercambiarA;
    }

    public byte getPilotoAIntercambiarB() {
        return pilotoAIntercambiarB;
    }

    public void setPilotoAIntercambiarB(byte pilotoAIntercambiarB) {
        this.pilotoAIntercambiarB = pilotoAIntercambiarB;
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
    
    public void guardarPiloto() {
        //obtiene el consecutivo
        piloto.setCodigo((short) (listaPilotos.contarNodos() + 1));
        if (piloto.getTiempoClasi() <= 0) {
            JsfUtil.addErrorMessage("Tiempo de vuelta inválido");
        } else {

            if (alInicio.compareTo("1") == 0) {
                listaPilotos.adicionarNodoAlInicio(piloto);
            } else {
                listaPilotos.adicionarNodo(piloto);
            }
            
            //Vuelvo a llenar la lista para la tabla
            listadoPilotos = listaPilotos.obtenerListaPilotos();
            pintarLista();
            deshabilitarFormulario = true;
            JsfUtil.addSuccessMessage("El piloto se ha guardado exitosamente");
        }
    }

    public void habilitarFormulario() {
        deshabilitarFormulario = false;
        piloto = new Piloto();
    }

    public void irAnterior() {
        if (ayudante.getAnterior() != null) {
            ayudante = ayudante.getAnterior();
            piloto = ayudante.getDato();
        }
    }

    public void irSiguiente() {
        if (ayudante.getSiguiente() != null) {
            ayudante = ayudante.getSiguiente();
            piloto = ayudante.getDato();
        }
    }

    public void irPrimero() {
        if (listaPilotos.getCabeza() != null) {
            ayudante = listaPilotos.getCabeza();
            piloto = ayudante.getDato();

        } else {
            piloto = new Piloto();
        }
        listadoPilotos = listaPilotos.obtenerListaPilotos();
        pintarLista();

    }

    public void irUltimo() {
        if (listaPilotos.getCabeza() != null) {
            while (ayudante.getSiguiente() != null) {
                ayudante = ayudante.getSiguiente();
            }
            piloto = ayudante.getDato();
        }
    }

    public void cambiarVistaPilotos() {
        if (textoVista.compareTo("Tabla") == 0) {
            textoVista = "Gráfico";
        } else {
            textoVista = "Tabla";
        }
    }

    public void invertirLista() {
        //Invierte la lista
        listaPilotos.invertirLista();
        irPrimero();
    }

    public void pintarLista() {
        //Instancia el modelo
        model = new DefaultDiagramModel();
        //Se establece para que el diagrama pueda tener infinitas flechas
        model.setMaxConnections(-1);

        StateMachineConnector connector = new StateMachineConnector();
        connector.setOrientation(StateMachineConnector.Orientation.ANTICLOCKWISE);
        connector.setPaintStyle("{strokeStyle:'#7D7463',lineWidth:3}");
        model.setDefaultConnector(connector);

        ///Adicionar los elementos
        if (listaPilotos.getCabeza() != null) {
            //llamo a mi ayudante
            NodoGP temp = listaPilotos.getCabeza();
            int posX = 2;
            int posY = 2;
            
            
            //recorro la lista de principio a fin
            while (temp != null) {
                //Parado en un elemento
                //Crea el cuadrito y lo adiciona al modelo
                Element ele = new Element(temp.getDato().getCodigo() + " "
                        + temp.getDato().getNombre(),
                        posX + "em", posY + "em");
                ele.setId(String.valueOf(temp.getDato().getCodigo()));
                //adiciona un conector al cuadrito
                ele.addEndPoint(new BlankEndPoint(EndPointAnchor.TOP));
                ele.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM_RIGHT));

                ele.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM_LEFT));
                ele.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM));
                
                
                //selecciona la clase css segun el color del piloto
                switch(temp.getDato().getColor()){
                    case "rosa":
                        ele.setStyleClass("ui-diagram-rosa");
                        break;
                    case "purpura":
                        ele.setStyleClass("ui-diagram-purpura");
                        break;
                    case "naranja":
                        ele.setStyleClass("ui-diagram-naranja");
                        break;
                    case "rojo":
                        ele.setStyleClass("ui-diagram-rojo");
                        break;
                    case "azul":
                        ele.setStyleClass("ui-diagram-azul");
                        break;
                    case "verde":
                        ele.setStyleClass("ui-diagram-verde");
                        break;
                    case "amarillo":
                        ele.setStyleClass("ui-diagram-amarillo");
                        break;
                    default:
                        break;
                }
                
                model.addElement(ele);
                temp = temp.getSiguiente();
                posX = posX + 5;
                posY = posY + 6;
            }

            //Pinta las flechas            
            for (int i = 0; i < model.getElements().size() - 1; i++) {
                model.connect(createConnection(model.getElements().get(i).getEndPoints().get(1),
                        model.getElements().get(i + 1).getEndPoints().get(0), "Sig"));

                model.connect(createConnection(model.getElements().get(i + 1).getEndPoints().get(2),
                        model.getElements().get(i).getEndPoints().get(3), "Ant"));
            }
            
        }
    }
    
    public void pintarListaMenorEdad() {
        //Instancia el modelo
        model = new DefaultDiagramModel();
        //Se establece para que el diagrama pueda tener infinitas flechas
        model.setMaxConnections(-1);

        StateMachineConnector connector = new StateMachineConnector();
        connector.setOrientation(StateMachineConnector.Orientation.ANTICLOCKWISE);
        connector.setPaintStyle("{strokeStyle:'#7D7463',lineWidth:3}");
        model.setDefaultConnector(connector);

        ///Adicionar los elementos
        if (listaPilotos.getCabeza() != null) {
            //llamo a mi ayudante
            NodoGP temp = listaPilotos.getCabeza();
            int posX = 2;
            int posY = 2;
            
            byte menorEdad = 0;
            
            try {
                menorEdad = listaPilotos.obtenerMenorEdad();
            } catch (PilotoException ex) {
                JsfUtil.addErrorMessage(ex.getMessage());
            }
            //recorro la lista de principio a fin
            while (temp != null) {
                //Parado en un elemento
                //Crea el cuadrito y lo adiciona al modelo
                Element ele = new Element(temp.getDato().getCodigo() + " "
                        + temp.getDato().getNombre(),
                        posX + "em", posY + "em");
                ele.setId(String.valueOf(temp.getDato().getCodigo()));
                //adiciona un conector al cuadrito
                ele.addEndPoint(new BlankEndPoint(EndPointAnchor.TOP));
                ele.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM_RIGHT));

                ele.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM_LEFT));
                ele.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM));
                
                if (temp.getDato().getEdad() == menorEdad){
                    ele.setStyleClass("ui-diagram-purpura");
                    pilotoMenorEdad = temp.getDato();
                }

                model.addElement(ele);
                temp = temp.getSiguiente();
                posX = posX + 5;
                posY = posY + 6;
            }

            //Pinta las flechas            
            for (int i = 0; i < model.getElements().size() - 1; i++) {
                model.connect(createConnection(model.getElements().get(i).getEndPoints().get(1),
                        model.getElements().get(i + 1).getEndPoints().get(0), "Sig"));

                model.connect(createConnection(model.getElements().get(i + 1).getEndPoints().get(2),
                        model.getElements().get(i).getEndPoints().get(3), "Ant"));
            }

        }
    }

    public void handleToggle(ToggleEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Toggled", "Visibility:" + event.getVisibility());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void onClickRight() {
        String id = FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap().get("elementId");

        pilotoSeleccionado = Short.valueOf(id.replaceAll("frmPiloto:diagrama-", ""));

    }

    public void eliminarPiloto() {
        if (codigoEliminar > 0) {
            //llamo el eliminar de la lista
            try {
                listaPilotos.eliminarPiloto(codigoEliminar);
                irPrimero();
                JsfUtil.addSuccessMessage("Piloto " + codigoEliminar + " eliminado.");
            } catch (PilotoException e) {
                JsfUtil.addErrorMessage(e.getMessage());
            }
        } else {
            JsfUtil.addErrorMessage("El código a eliminar " + codigoEliminar + " no es válido");
        }
    }

    public void obtenerPilotoDiagrama() {
        try {
            pilotoDiagrama = listaPilotos.obtenerPiloto(pilotoSeleccionado);
        } catch (PilotoException ex) {
            JsfUtil.addErrorMessage(ex.getMessage());
        }
    }

    public void eliminarPilotoDiagrama() {
        try {
            listaPilotos.eliminarPiloto(pilotoSeleccionado);
            pintarLista();
        } catch (PilotoException ex) {
            JsfUtil.addErrorMessage(ex.getMessage());
        }
    }

    public void enviarAlFinal() {
        try {
            ///Buscar el piloto y guardar los datos en una variable temporal
            Piloto infTemporal = listaPilotos.obtenerPiloto(pilotoSeleccionado);
            // Eliminar el nodo
            listaPilotos.eliminarPiloto(pilotoSeleccionado);
            // Adicionarlo al final
            listaPilotos.adicionarNodo(infTemporal);

            pintarLista();
        } catch (PilotoException ex) {
            JsfUtil.addErrorMessage(ex.getMessage());
        }
    }

    public void enviarAlInicio() {
        try {
            ///Buscar el piloto y guardar los datos en una variable temporal
            Piloto infTemporal = listaPilotos.obtenerPiloto(pilotoSeleccionado);
            // Eliminar el nodo
            listaPilotos.eliminarPiloto(pilotoSeleccionado);
            // Adicionarlo al inicio
            listaPilotos.adicionarNodoAlInicio(infTemporal);

            pintarLista();
        } catch (PilotoException ex) {
            JsfUtil.addErrorMessage(ex.getMessage());
        }
    }

    public void adelantarPiloto() throws PilotoException {
        try {
            //calcular posicion deseada
            int pos = listaPilotos.obtenerPosicion(pilotoSeleccionado) - moverPilotoCantidad;
            short tam = listaPilotos.contarNodos();
            
            if (pos>tam || pos<0){
//                JsfUtil.addErrorMessage("posición \"" + pos + "\" inválida");
                JsfUtil.addErrorMessage("Valor inválido");

            }
                
            else {
                //guardar datos del piloto
                Piloto temp = listaPilotos.obtenerPiloto(pilotoSeleccionado);

                //eliminar el piloto
                listaPilotos.eliminarPiloto(pilotoSeleccionado);

                //guardar el piloto en la posicion deseada
                listaPilotos.adicionarNodoEnPosicion(temp, (short) pos);
                pintarLista();
                JsfUtil.addSuccessMessage("Posición modificada exitosamente");
                
            }
            
        } catch (PilotoException ex) {
            JsfUtil.addErrorMessage(ex.getMessage());
        }
    }

    public void verMenorPiloto() {
        pintarListaMenorEdad();
    }
    
    public void intercambiarPilotos() throws PilotoException{
        listaPilotos.intercambiarNodos(pilotoAIntercambiarA, pilotoAIntercambiarA);
        pintarLista();
    }
    
    public void ordenarPilotos() throws PilotoException{
        listaPilotos.ordenarBubbleSort();
        pintarLista();
    }
    
}
