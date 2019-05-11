/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.enumerated;

/**
 *
 * @author juan.vasquez
 */
public enum TipoCargaNominaEnum {
    
    CARGA_MANUAL(0, "CARGA MANUAL"),
    CARGA_MASIVA(1, "CARGA MASIVA"); 
    
    private int id;
    private String descripcion;

    private TipoCargaNominaEnum(int id, String descripcion) {
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
