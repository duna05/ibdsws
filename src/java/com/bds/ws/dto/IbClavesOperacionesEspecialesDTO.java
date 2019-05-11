/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbClavesOpEspeciales;
import com.bds.ws.model.IbHistClavesOpEspeciales;
import java.util.List;

/**
 *
 * @author ledwin.belen
 */
public class IbClavesOperacionesEspecialesDTO {
    
    private RespuestaDTO respuesta;  
    private IbClavesOpEspeciales ibClavesOpEspeciales;
    IbHistClavesOpEspeciales ibHistClavesOpEspeciales;
    List<IbHistClavesOpEspeciales> ibHistClavesOpEspecialesList;

    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }

    public IbClavesOpEspeciales getIbClavesOpEspeciales() {
        return ibClavesOpEspeciales;
    }

    public void setIbClavesOpEspeciales(IbClavesOpEspeciales ibClavesOpEspeciales) {
        this.ibClavesOpEspeciales = ibClavesOpEspeciales;
    }

    public IbHistClavesOpEspeciales getIbHistClavesOpEspeciales() {
        return ibHistClavesOpEspeciales;
    }

    public void setIbHistClavesOpEspeciales(IbHistClavesOpEspeciales ibHistClavesOpEspeciales) {
        this.ibHistClavesOpEspeciales = ibHistClavesOpEspeciales;
    }

    public List<IbHistClavesOpEspeciales> getIbHistClavesOpEspecialesList() {
        return ibHistClavesOpEspecialesList;
    }

    public void setIbHistClavesOpEspecialesList(List<IbHistClavesOpEspeciales> ibHistClavesOpEspecialesList) {
        this.ibHistClavesOpEspecialesList = ibHistClavesOpEspecialesList;
    }
}
