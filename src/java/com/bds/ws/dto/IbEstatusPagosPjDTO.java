/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbEstatusPagosPj;
import java.util.List;

/**
 *
 * @author juan.vasquez
 */
public class IbEstatusPagosPjDTO {
    
    private IbEstatusPagosPj ibEstatusPagosPj;
    private List<IbEstatusPagosPj> ibEstatusPagosPjList;
    private RespuestaDTO respuestaDTO;

    public IbEstatusPagosPj getIbEstatusPagosPj() {
        return ibEstatusPagosPj;
    }

    public void setIbEstatusPagosPj(IbEstatusPagosPj ibEstatusPagosPj) {
        this.ibEstatusPagosPj = ibEstatusPagosPj;
    }

    public List<IbEstatusPagosPj> getIbEstatusPagosPjList() {
        return ibEstatusPagosPjList;
    }

    public void setIbEstatusPagosPjList(List<IbEstatusPagosPj> ibEstatusPagosPjList) {
        this.ibEstatusPagosPjList = ibEstatusPagosPjList;
    }

    public RespuestaDTO getRespuestaDTO() {
        return respuestaDTO;
    }

    public void setRespuestaDTO(RespuestaDTO respuestaDTO) {
        this.respuestaDTO = respuestaDTO;
    }
    
}
