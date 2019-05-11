/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.enumerated;

/**
 *
 * @author daniel.herrera
 */
public enum PerfilAccesoEnum {

    MASTER(1, "Master"),
    ADMINISTRADOR(2, "Administrador"),
    AUTORIZADOR(3, "Autorizador"),
    INGRESADOR(4, "Ingresador"),
    CONSULTOR(5, "Consultor");

    private int id;
    private String descripcion;

    private PerfilAccesoEnum(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public static PerfilAccesoEnum getById(int id) {
        switch (id) {
            case 1:
                return MASTER;
            case 2:
                return ADMINISTRADOR;
            case 3:
                return AUTORIZADOR;
            case 4:
                return INGRESADOR;
            case 5:
                return CONSULTOR;
            default:
                return null;
        }
    }

}
