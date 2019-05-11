/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbTransaccionesPendientes;
import java.io.Serializable;
import java.util.List;

/**
 * Clase IbTransaccionesPendientesDTO
 * @author juan.faneite
 */
public class IbTransaccionesPendientesDTO implements Serializable {

    private IbTransaccionesPendientes transaccionesPendientes;
    private List<IbTransaccionesPendientes> listTransaccionesPendientes;
    private RespuestaDTO respuesta;

    public IbTransaccionesPendientesDTO() {
    }

    /**
     * Modelo de IbTransaccionesPendientes.
     *
     * @return transaccionesPendientes IbTransaccionesPendientes Modelo de
     * IbTransaccionesPendientes.
     */
    public IbTransaccionesPendientes getTransaccionesPendientes() {
        return transaccionesPendientes;
    }

    /**
     * Modelo de IbTransaccionesPendientes.
     *
     * @param transaccionesPendientes IbTransaccionesPendientes Modelo de
     * IbTransaccionesPendientes.
     */
    public void setTransaccionesPendientes(IbTransaccionesPendientes transaccionesPendientes) {
        this.transaccionesPendientes = transaccionesPendientes;
    }

    /**
     * Listado de Modelo de IbTransaccionesPendientes.
     * @return listTransaccionesPendientes List < IbTransaccionesPendientes > Modelo de IbTransaccionesPendientes.
     */
    public List<IbTransaccionesPendientes> getListTransaccionesPendientes() {
        return listTransaccionesPendientes;
    }

    /**
     * Modelo de IbTransaccionesPendientes.
     * @param listTransaccionesPendientes List < IbTransaccionesPendientes > Modelo de IbTransaccionesPendientes.
     */
    public void setListTransaccionesPendientes(List<IbTransaccionesPendientes> listTransaccionesPendientes) {
        this.listTransaccionesPendientes = listTransaccionesPendientes;
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
