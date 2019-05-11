/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.enumerated;

/**
 *
 * @author luis.perez
 */
public enum EstatusEmpresaEnum {
    /**
     *
     */
    ACTIVO(1,"ACTIVO"), 
    BLOQUEADO(2,"EN_PROCESO_AFILIACION"),
    INACTIVO(3, "INACTIVO");
    
    private int id;
    private String descripcion;
    
    private EstatusEmpresaEnum(int id, String descripcion) {
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
