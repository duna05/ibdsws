/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbPeriodoClave;
import java.io.Serializable;
import java.util.List;

/**
 * Clase IbPeriodoClaveDTO
 * @author juan.faneite
 */
public class IbPeriodoClaveDTO implements Serializable{
    private IbPeriodoClave periodoClave;
    private List<IbPeriodoClave> periodosClave;
    private RespuestaDTO respuesta;

    
    /**
     * obtiene el modelo sde ib_periodo_clave
     * @return periodoClave
     */
    public IbPeriodoClave getPeriodoClave() {
        return periodoClave;
    }

    /**
     * inserta el modelo sde ib_periodo_clave
     * @param periodoClave modelo sde ib_periodo_clave
     */
    public void setPeriodoClave(IbPeriodoClave periodoClave) {
        this.periodoClave = periodoClave;
    }

    /**
     * obtiene listado modelo de ib_periodo_clave
     * @return List < IbPeriodoClave >
     */
    public List<IbPeriodoClave> getPeriodosClave() {
        return periodosClave;
    }

    /**
     * inserta listado de modelo de ib_periodo_clave
     * @param periodosClave modelo de ib_periodo_clave
     */
    public void setPeriodosClave(List<IbPeriodoClave> periodosClave) {
        this.periodosClave = periodosClave;
    }

    /**
     * retorna la respuesta de la operacion que se realiza.
     * @return RespuestaDTO
     */
    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    /**
     * asigna objeto para almacenar la respuesta de la transaccion
     * @param respuesta objeto para almacenar la respuesta de la transaccion
     */
    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }
}
