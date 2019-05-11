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
public enum EstatusDomiciliacionesCOREEnum {

    R("R", "DEVUELTO"),
    K("K", "PROCESADO"),
    O("O", "PROCASADO"),
    P("P", "APLICADO"),
    C("C", "CARGADO");

    private String codigo;
    private String descripcion;

    private EstatusDomiciliacionesCOREEnum(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
}
