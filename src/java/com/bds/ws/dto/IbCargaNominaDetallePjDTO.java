/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbCargaNominaDetallePj;
import java.util.List;

/**
 *
 * @author juan.vasquez
 */
public class IbCargaNominaDetallePjDTO {
    
    private IbCargaNominaDetallePj ibCargaNominaDetallePj;
    private List<IbCargaNominaDetallePj> ibCargaNominaDetallesPjList;
    private RespuestaDTO respuestaDTO;

    public IbCargaNominaDetallePj getIbCargaNominaDetallePj() {
        return ibCargaNominaDetallePj;
    }

    public void setIbCargaNominaDetallePj(IbCargaNominaDetallePj ibCargaNominaDetallePj) {
        this.ibCargaNominaDetallePj = ibCargaNominaDetallePj;
    }

    public List<IbCargaNominaDetallePj> getIbCargaNominaDetallesPjList() {
        return ibCargaNominaDetallesPjList;
    }

    public void setIbCargaNominaDetallesPjList(List<IbCargaNominaDetallePj> ibCargaNominaDetallesPjList) {
        this.ibCargaNominaDetallesPjList = ibCargaNominaDetallesPjList;
    }

    public RespuestaDTO getRespuestaDTO() {
        return respuestaDTO;
    }

    public void setRespuestaDTO(RespuestaDTO respuestaDTO) {
        this.respuestaDTO = respuestaDTO;
    }
    
    
}
