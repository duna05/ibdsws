/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbLogs;
import com.bds.ws.model.IbLogsPj;
import java.io.Serializable;
import java.util.List;

/**
 * Clase IbLogsDTO
 * @author luis.perez
 */
public class IbLogsPjDTO implements Serializable{
    private IbLogs log;
    private List<IbLogsPj> logsPj;
    private RespuestaDTO respuesta;

    /**
     * obtener modelo de ib_logs
     * @return IbLogs
     */
    public IbLogs getLog() {
        return log;
    }

    /**
     * insertar modelo de ib_logs
     * @param log IbLogs modelo de ib_logs
     */
    public void setLog(IbLogs log) {
        this.log = log;
    }

    public List<IbLogsPj> getLogsPj() {
        return logsPj;
    }

    public void setLogsPj(List<IbLogsPj> logsPj) {
        this.logsPj = logsPj;
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
