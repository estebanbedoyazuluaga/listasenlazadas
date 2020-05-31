/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.listase.controlador;

import java.util.ArrayList;
import java.util.List;
import com.listase.modelo.TipoUsuario;
import com.listase.modelo.Usuario;

/**
 *
 * @author carloaiza
 */
public class ControladorUsuarios {

    private List<TipoUsuario> tiposUsuario;
    private static List<Usuario> usuarios;

    public ControladorUsuarios() {
        this.iniciarListados();
    }

    public List<TipoUsuario> getTiposUsuario() {
        return tiposUsuario;
    }

    public void setTiposUsuario(List<TipoUsuario> tiposUsuario) {
        this.tiposUsuario = tiposUsuario;
    }

    public static List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        ControladorUsuarios.usuarios = usuarios;
    }

    private void iniciarListados() {
        //Simula la conexion a bds o archivos planos
        tiposUsuario = new ArrayList<>();
        tiposUsuario.add(new TipoUsuario(1, "Administrador"));
        tiposUsuario.add(new TipoUsuario(2, "Corriente"));
        //tiposUsuario.add(new TipoUsuario(3, "El de los tintos"));
        // [  admin  ,  corriente  , el de los tintos]
        //       0   ,   1         ,  3

        usuarios = new ArrayList<>();
        
        usuarios.add(new Usuario("ebedoya@umanizales.edu.co", "qwer", "esteban",
                tiposUsuario.get(0)));
        usuarios.add(new Usuario("fulano@mail.com", "1234", "fulano",
                tiposUsuario.get(1)));
        usuarios.add(new Usuario("sultano@mail.com", "1234", "sultano",
                tiposUsuario.get(1)));
        usuarios.add(new Usuario("mengano@mail.com", "1234", "mengano",
                tiposUsuario.get(1)));
        
    }

    public Usuario encontrarUsuarioxCorreo(String correo) {
        Usuario usuarioEncontrado = null;
        //Recorre la lista de principio a fin 
        for (Usuario usu : ControladorUsuarios.usuarios) {
            if (usu.getCorreo().equals(correo)) {
                return usu;
            }
        }

        return usuarioEncontrado;
    }

}
