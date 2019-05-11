/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbMediosNotificacion;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author juan.faneite
 */
public class IbMediosNotificacionDTO implements Serializable{
    
    private IbMediosNotificacion medioNotificacion;    
    private List<IbMediosNotificacion> mediosNotificacion;    
    private RespuestaDTO respuesta;

    /**
     * 
     * @return modelo de Ib_Medios_Notificacion
     */
    public IbMediosNotificacion getMedioNotificacion() {
        return medioNotificacion;
    }

    /**
     * 
     * @param medioNotificacion modelo de Ib_Medios_Notificacion
     */
    public void setMedioNotificacion(IbMediosNotificacion medioNotificacion) {
        this.medioNotificacion = medioNotificacion;
    }

    /**
     * 
     * @return lista de modelo de Ib_Medios_Notificacion
     */
    public List<IbMediosNotificacion> getMediosNotificacion() {
        return mediosNotificacion;
    }

    /**
     * 
     * @param mediosNotificacion lista de modelo de Ib_Medios_Notificacion
     */
    public void setMediosNotificacion(List<IbMediosNotificacion> mediosNotificacion) {
        this.mediosNotificacion = mediosNotificacion;
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
