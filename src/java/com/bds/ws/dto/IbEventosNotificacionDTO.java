/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbEventosNotificacion;
import java.util.List;

/**
 *
 * @author juan.faneite
 */
public class IbEventosNotificacionDTO {
    
    private IbEventosNotificacion eventoNotificacion;
    private List<IbEventosNotificacion> eventosNotificacion;
    private RespuestaDTO respuesta;

    /**
     * 
     * @return modelo Ib_Eventos_Notificacion
     */
    public IbEventosNotificacion getEventoNotificacion() {
        return eventoNotificacion;
    }

    /**
     * 
     * @param eventoNotificacion modelo Ib_Eventos_Notificacion
     */
    public void setEventoNotificacion(IbEventosNotificacion eventoNotificacion) {
        this.eventoNotificacion = eventoNotificacion;
    }

    /**
     * 
     * @return lista modelo Ib_Eventos_Notificacion
     */
    public List<IbEventosNotificacion> getEventosNotificacion() {
        return eventosNotificacion;
    }

    /**
     * 
     * @param eventosNotificacion lista modelo Ib_Eventos_Notificacion
     */
    public void setEventosNotificacion(List<IbEventosNotificacion> eventosNotificacion) {
        this.eventosNotificacion = eventosNotificacion;
    }

    /**
     * 
     * @return respuesta
     */
    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    /**
     * 
     * @param respuesta respuesta
     */
    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }
    
    
}
