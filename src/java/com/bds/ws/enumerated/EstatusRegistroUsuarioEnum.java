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
public enum EstatusRegistroUsuarioEnum {

    ACTIVO(1, "Activo"),
    EN_PROCESO_AFILIACION(2, "En Proceso Afiliacion"),
    POR_COMPLETAR_AFILIACION(3, "Por Completar Afiliacion");

    private int id;
    private String descripcion;

    private EstatusRegistroUsuarioEnum(int id, String descripcion) {
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
}
