/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.enumerated;

/**
 *
 * @author cesar.mujica
 */
public enum EstatusValidacionPositivaEnum {
    
    CORRECTO(0, "0"),
    INCORRECTO(1,"1"), 
    BLOQUEADO(2, "2");
    
    private int id;
    private String descripcion;
    
    private EstatusValidacionPositivaEnum(int id, String descripcion) {
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
