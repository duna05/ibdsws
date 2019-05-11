/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbTransacciones;
import java.io.Serializable;
import java.util.Collection;

/**
 * Clase IbTransaccionesDTO
 * @author cesar.mujica
 */
public class IbTransaccionesDTO implements Serializable {

    private IbTransacciones transaccion;                 //objeto para almacenar el detalle de una transaccion
    private Collection<IbTransacciones> transaccciones;  //objeto para lamacenar una lista de transacciones
    private RespuestaDTO respuesta;                         //objeto para alamcenar el resultado de la transaccion

    /**
     *
     * @return objeto para almacenar el detalle de una transaccion
     */
    public IbTransacciones getTransaccion() {
        return transaccion;
    }

    /**
     * asigna objeto para almacenar el detalle de una transaccion
     *
     * @param transaccion objeto para almacenar el detalle de una transaccion
     */
    public void setTransaccion(IbTransacciones transaccion) {
        this.transaccion = transaccion;
    }

    /**
     * objeto para lamacenar una lista de transacciones
     *
     * @return objeto para lamacenar una lista de transacciones
     */
    public Collection<IbTransacciones> getTransaccciones() {
        return transaccciones;
    }

    /**
     * asigna objeto para lamacenar una lista de transacciones
     *
     * @param transaccciones objeto para lamacenar una lista de transacciones
     */
    public void setTransaccciones(Collection<IbTransacciones> transaccciones) {
        this.transaccciones = transaccciones;
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
