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
public enum ServiciosAprobadoresEnum {
    /**
     *
     */
    NOMINA(1, "pjw.menu.opcion.cashmanagement.nomina.autorizar"),
    PAGOS_ESPECIALES(2,"pjw.menu.opcion.cashmanagement.pagosesp.autorizar"),
    PAGOS_CORPORATIVOS(3,"pjw.menu.opcion.cashmanagement.pagoscorp.autorizar"),
    PAGOS_BENEFICIARIOS(4,"pjw.pagoscorporativos.beneficiarios.autorizar"),
    DOMICILIACIONES(5,"pjw.menu.opcion.cashmanagement.domicili.autorizar"),
    FIDEICOMISO(6,"pjw.menu.opcion.cashmanagement.fide.autorizar");
    
    private int id;
    private String descripcion;
    
    private ServiciosAprobadoresEnum(int id, String descripcion) {
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
