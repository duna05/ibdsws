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
public enum EstatusPagosEnum {
    //TABLA IB_ESTATUS_PAGOS_PJ
    PRECARGADO(0, "Precargado"),
    CARGADO(1, "Cargado"),
    PENDIENTE_X_AUTORIZAR(2, "Pendiente por autorizar"),
    PENDIENTE_FIRMA(3, "Pendiente por firma"),
    AUTORIZADO(4, "Autorizado"),
    PAGADO(5, "Pagado"),
    RECHAZADO(6, "Rechazado"),
    DETENIDO(7, "Detenido"),
    REACTIVADA(8, "Reactivada"),
    CANCELADA(9, "Cancelada"),
    PENDIENTE_X_PAGO(10, "Pendiente por pago"),
    NO_PROCESADO(11, "No procesado");

    private long id;
    private String descripcion;

    private EstatusPagosEnum(long id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
