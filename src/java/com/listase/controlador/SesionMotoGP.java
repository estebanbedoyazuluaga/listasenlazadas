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
import javax.faces.context.FacesContext;
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
    
    public SesionMotoGP() {
    }

    @PostConstruct
    public void inicializar() {
        controlLocalidades = new ControladorLocalidades();
        //inicializando el combo en el primer depto
        codigoDeptoSel = controlLocalidades.getDepartamentos().get(0).getCodigo();

        listaPilotos = new ListaGP();

        //LLenado de la bds
        listaPilotos.adicionarNodo(new Piloto("Fulano", "Colombiano", "rosa", (byte) 22, (byte) 4, new Moto(), (short) 0001));
        listaPilotos.adicionarNodo(new Piloto("Sultano", "Ecuatoriano", "purpura", (byte) 25, (byte) 4, new Moto(), (short) 0002));
        listaPilotos.adicionarNodo(new Piloto("Mengano", "Venezolano", "verde", (byte) 19, (byte) 4, new Moto(), (short) 0003));
        listaPilotos.adicionarNodo(new Piloto("Perengano", "Peruano", "amarillo", (byte) 28, (byte) 4, new Moto(), (short) 0004));

        //piloto = ayudante.getDato();
        piloto = new Piloto();

        //Me llena el objeto List para la tabla
        listadoPilotos = listaPilotos.obtenerListaPilotos();
        pintarLista();
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

    private Connection createConnection(EndPoint from, EndPoint to, String label) {
        Connection conn = new Connection(from, to);
        conn.getOverlays().add(new ArrowOverlay(20, 20, 1, 1));

        if (label != null) {
            conn.getOverlays().add(new LabelOverlay(label, "flow-label", 0.5));
        }

        return conn;
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


    
    public void guardarPiloto() {
        //obtiene el consecutivo
        piloto.setCodigo((short) (listaPilotos.contarNodos() + 1));
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
                    ele.setStyleClass("ui-diagram-menorEdad");
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
                //JsfUtil.addErrorMessage("posición \"" + pos + "\" inválida");
            }
                
            else {
                //guardar datos del piloto
                Piloto temp = listaPilotos.obtenerPiloto(pilotoSeleccionado);

                //eliminar el piloto
                listaPilotos.eliminarPiloto(pilotoSeleccionado);

                //guardar el piloto en la posicion deseada
                listaPilotos.adicionarNodoEnPosicion(temp, (short) pos);
                pintarLista();
                //JsfUtil.addSuccessMessage("Posición modificada exitosamente");
                
            }
            
        } catch (PilotoException ex) {
            JsfUtil.addErrorMessage(ex.getMessage());
        }
    }

    
    public void verMenorPiloto() {
        pintarListaMenorEdad();
    }
}
