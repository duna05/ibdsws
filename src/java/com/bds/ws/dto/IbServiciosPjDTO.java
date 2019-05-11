/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbServiciosPj;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author luis.perez
 */
public class IbServiciosPjDTO implements Serializable {
    private IbServiciosPj ibServicioPj;
    private List<IbServiciosPj> ibServiciosPj;
    private RespuestaDTO respuesta; 

    public IbServiciosPj getIbServicioPj() {
        return ibServicioPj;
    }

    public void setIbServicioPj(IbServiciosPj ibServicioPj) {
        this.ibServicioPj = ibServicioPj;
    }

    public List<IbServiciosPj> getIbServiciosPj() {
        return ibServiciosPj;
    }

    public void setIbServiciosPj(List<IbServiciosPj> ibServiciosPj) {
        this.ibServiciosPj = ibServiciosPj;
    }

    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }
}
