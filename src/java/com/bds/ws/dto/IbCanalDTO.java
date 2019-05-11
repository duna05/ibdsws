/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbCanal;
import java.io.Serializable;
import java.util.List;

/**
 * Clase IbCanalDTO
 * @author juan.faneite
 */
public class IbCanalDTO implements Serializable{
    private IbCanal canal;
    private List<IbCanal> canales;
    private RespuestaDTO respuesta;

    /**
     * Obtener modelo de Ib_canal
     * @return IbCanal
     */
    public IbCanal getCanal() {
        return canal;
    }

    /**
     * Insertar modelo de Ib_canal
     * @param canal IbCanal
     */
    public void setCanal(IbCanal canal) {
        this.canal = canal;
    }

    /**
     * Obtener listado de modelo de Ib_canal
     * @return List < IbCanal >
     */
    public List<IbCanal> getCanales() {
        return canales;
    }

    /**
     * Insertar listado de modelo de Ib_canal
     * @param canales List < IbCanal >
     */
    public void setCanales(List<IbCanal> canales) {
        this.canales = canales;
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
