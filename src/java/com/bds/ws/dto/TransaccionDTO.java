/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import java.io.Serializable;

/**
 * Clase TransaccionDTO
 * @author cesar.mujica
 */
public class TransaccionDTO implements Serializable {

    private String nroReferencia; //numero de referencia de la transaccion
    private RespuestaDTO respuesta; //objeto para almacenar la respuesta de la transaccion

    /**
     * numero de referencia de la transaccion
     * @return numero de referencia de la transaccion
     */
    public String getNroReferencia() {
        return nroReferencia;
    }

    /**
     * asigna numero de referencia de la transaccion
     * @param nroReferencia numero de referencia
     */
    public void setNroReferencia(String nroReferencia) {
        this.nroReferencia = nroReferencia;
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
