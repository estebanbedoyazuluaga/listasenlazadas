
package com.listase.modelo.motogp;

public class Moto {
    
    private String nombre,marca,modelo,matricula,color;
    
    private int kilometraje;

    public Moto(String nombre, String marca, String modelo, String matricula, String color, int kilometraje) {
        this.nombre = nombre;
        this.marca = marca;
        this.modelo = modelo;
        this.matricula = matricula;
        this.color = color;
        this.kilometraje = kilometraje;
    }

    public Moto() {
    }
    
    

    //<editor-fold defaultstate="collapsed" desc="Metodos de Acceso">
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(int kilometraje) {
        this.kilometraje = kilometraje;
    }

//</editor-fold>

    
    
}
