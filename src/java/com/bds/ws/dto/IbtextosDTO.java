/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbTextos;

/**
 * Clase IbtextosDTO
 * @author juan.faneite
 */
public class IbtextosDTO {

    private IbTextos ibTextos;
    private RespuestaDTO respuesta;

    /**
     * 
     * @return textos
     */
    public IbTextos getIbTextos() {
        return ibTextos;
    }

    /**
     * 
     * @param ibTextos textos
     */
    public void setIbTextos(IbTextos ibTextos) {
        this.ibTextos = ibTextos;
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
