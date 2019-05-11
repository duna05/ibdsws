/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbAgendaTransaccionesPn;
import java.io.Serializable;
import java.util.List;

/**
 * Clase IBAfiliacionesDTO
 * @author juan.faneite
 */
public class IBAgendaPNDTO implements Serializable {

    private IbAgendaTransaccionesPn ibAgenda;
    private List<IbAgendaTransaccionesPn> ibAgendas;
    private RespuestaDTO respuesta;

    public IBAgendaPNDTO() {
    }

    public IbAgendaTransaccionesPn getIbAgenda() {
        return ibAgenda;
    }

    public void setIbAgenda(IbAgendaTransaccionesPn ibAgenda) {
        this.ibAgenda = ibAgenda;
    }

    public List<IbAgendaTransaccionesPn> getIbAgendas() {
        return ibAgendas;
    }

    public void setIbAgendas(List<IbAgendaTransaccionesPn> ibAgendas) {
        this.ibAgendas = ibAgendas;
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
