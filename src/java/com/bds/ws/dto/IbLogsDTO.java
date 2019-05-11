/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbLogs;
import java.io.Serializable;
import java.util.List;

/**
 * Clase IbLogsDTO
 * @author juan.faneite
 */
public class IbLogsDTO implements Serializable{
    private IbLogs log;
    private List<IbLogs> logs;
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

    /**
     * obtener listado de modelo de ib_logs
     * @return List < IbLogs >
     */
    public List<IbLogs> getLogs() {
        return logs;
    }

    /**
     * insertar modelo de ib_logs
     * @param logs List < IbLogs >
     */
    public void setLogs(List<IbLogs> logs) {
        this.logs = logs;
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
