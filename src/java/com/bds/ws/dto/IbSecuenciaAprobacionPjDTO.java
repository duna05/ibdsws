/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbSecuenciaAprobacionPj;
import java.util.List;

/**
 *
 * @author juan.vasquez
 */
public class IbSecuenciaAprobacionPjDTO {
    
    private IbSecuenciaAprobacionPj ibSecuenciaAprobacionPj;
    private List<IbSecuenciaAprobacionPj> ibSecuenciaAprobacionPjList;
    private RespuestaDTO respuesta;

    public IbSecuenciaAprobacionPj getIbSecuenciaAprobacionPj() {
        return ibSecuenciaAprobacionPj;
    }

    public void setIbSecuenciaAprobacionPj(IbSecuenciaAprobacionPj ibSecuenciaAprobacionPj) {
        this.ibSecuenciaAprobacionPj = ibSecuenciaAprobacionPj;
    }

    public List<IbSecuenciaAprobacionPj> getIbSecuenciaAprobacionPjList() {
        return ibSecuenciaAprobacionPjList;
    }

    public void setIbSecuenciaAprobacionPjList(List<IbSecuenciaAprobacionPj> ibSecuenciaAprobacionPjList) {
        this.ibSecuenciaAprobacionPjList = ibSecuenciaAprobacionPjList;
    }

    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }
    
    
}
