/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbAprobacionesServiciosPj;
import java.util.List;

/**
 *
 * @author luis.perez
 */
public class IbAprobacionesServiciosPjDTO {
    private IbAprobacionesServiciosPj IbAprobacionServicioPj;
    private List<IbAprobacionesServiciosPj> IbAprobacionesServiciosPj;
    private RespuestaDTO respuesta;

    public IbAprobacionesServiciosPj getIbAprobacionServicioPj() {
        return IbAprobacionServicioPj;
    }

    public void setIbAprobacionServicioPj(IbAprobacionesServiciosPj IbAprobacionServicioPj) {
        this.IbAprobacionServicioPj = IbAprobacionServicioPj;
    }

    public List<IbAprobacionesServiciosPj> getIbAprobacionesServiciosPj() {
        return IbAprobacionesServiciosPj;
    }

    public void setIbAprobacionesServiciosPj(List<IbAprobacionesServiciosPj> IbAprobacionesServiciosPj) {
        this.IbAprobacionesServiciosPj = IbAprobacionesServiciosPj;
    }

    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }
}
