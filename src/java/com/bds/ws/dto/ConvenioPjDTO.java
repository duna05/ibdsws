/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import java.io.Serializable;

public class ConvenioPjDTO implements Serializable {

    private String secuenciaConvenio;
    private String nombreConvenio;
    private RespuestaDTO respuesta;   

    public ConvenioPjDTO() {
        //
    }

    public String getSecuenciaConvenio() {
        return secuenciaConvenio;
    }

    public void setSecuenciaConvenio(String secuenciaConvenio) {
        this.secuenciaConvenio = secuenciaConvenio;
    }

    public String getNombreConvenio() {
        return nombreConvenio;
    }

    public void setNombreConvenio(String nombreConvenio) {
        this.nombreConvenio = nombreConvenio;
    }

    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }

    
    
}
