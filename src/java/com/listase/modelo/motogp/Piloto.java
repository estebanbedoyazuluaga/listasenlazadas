package com.listase.modelo.motogp;


import java.io.Serializable;

public class Piloto implements Serializable{

    
    String nombre,nacionalidad,color;
    byte edad,experiencia;
    Moto moto;
    short codigo;

    public Piloto(String nombre, String nacionalidad, String color, byte edad, byte experiencia, Moto moto, short codigo) {
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.color = color;
        this.edad = edad;
        this.experiencia = experiencia;
        this.moto = moto;
        this.codigo = codigo;
    }

    public Piloto() {
    }
    
    

    //<editor-fold defaultstate="collapsed" desc="Metodos de acceso">
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public byte getEdad() {
        return edad;
    }

    public void setEdad(byte edad) {
        this.edad = edad;
    }

    public byte getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(byte experiencia) {
        this.experiencia = experiencia;
    }

    public Moto getMoto() {
        return moto;
    }

    public void setMoto(Moto moto) {
        this.moto = moto;
    }

    public short getCodigo() {
        return codigo;
    }

    public void setCodigo(short codigo) {
        this.codigo = codigo;
    }
//</editor-fold>

    @Override
    public String toString() {
        return this.nombre;
    }

    
    
    
}
