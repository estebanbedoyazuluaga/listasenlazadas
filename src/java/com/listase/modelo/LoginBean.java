/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.listase.modelo;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import com.listase.controlador.ControladorUsuarios;
import com.listase.utilidades.JsfUtil;

/**
 *
 * @author carloaiza
 */
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    //Atributo para ocultar o visualizar el panel del login
    private boolean verPanelLogin = false;

    //Atributo para capturar el texto para mostrar en el saludo
    private String textoSaludo = "";

    //Atributo contador para los saludos
    private short contadorSaludos = 0;

    //Atributo String correo
    private String correo = "ebedoya@umanizales.edu.co";

    private String contrasenia = "qwerty";

    //atributo controlador para gestionar usuarios y tipos
    private ControladorUsuarios controlUsuarios;
    /**
     * Creates a new instance of loginBean
     */
    private Usuario usuarioAutenticado;

    public LoginBean() {
        controlUsuarios = new ControladorUsuarios();
    }

    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    public void setUsuarioAutenticado(Usuario usuarioAutenticado) {
        this.usuarioAutenticado = usuarioAutenticado;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public boolean isVerPanelLogin() {
        return verPanelLogin;
    }

    public void setVerPanelLogin(boolean verPanelLogin) {
        this.verPanelLogin = verPanelLogin;
    }

    public void aumentarContadorSaludos() {
        contadorSaludos++;
    }

    //Método para cambiar el valor de la variable y ocultar o mostrar el panel
    public void habilitarOdeshabilitarLogin() {
        verPanelLogin = !verPanelLogin;
    }

    public String ingresar() {
        //Vamos al controlador y buscamos el usuario segun el correo ingresado
        Usuario usuarioEncontrado = controlUsuarios.encontrarUsuarioxCorreo(correo);
        if (usuarioEncontrado != null) {
            //Encontro el usuario y comparar las contraseñas
            if (usuarioEncontrado.getContrasenia().equals(contrasenia)) {
                usuarioAutenticado = usuarioEncontrado;
                return "inicioListas";
            }
        }
        JsfUtil.addErrorMessage("Los datos ingresados son errados");

        return null;
    }

}
