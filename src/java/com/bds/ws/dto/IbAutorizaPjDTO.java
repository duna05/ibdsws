/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbAutorizaPj;
import java.util.List;

/**
 *
 * @author juan.vasquez
 */
public class IbAutorizaPjDTO {


    private IbAutorizaPj ibAutorizaPj;
    private List<IbAutorizaPj> ibAutorizaPjsList;
    private RespuestaDTO respuestaDTO;

    public IbAutorizaPj getIbAutorizaPj() {
        return ibAutorizaPj;
    }

    public void setIbAutorizaPj(IbAutorizaPj ibAutorizaPj) {
        this.ibAutorizaPj = ibAutorizaPj;
    }

    public List<IbAutorizaPj> getIbAutorizaPjsList() {
        return ibAutorizaPjsList;
    }

    public void setIbAutorizaPjsList(List<IbAutorizaPj> ibAutorizaPjsList) {
        this.ibAutorizaPjsList = ibAutorizaPjsList;
    }
    
   

    public RespuestaDTO getRespuestaDTO() {
        return respuestaDTO;
    }

    public void setRespuestaDTO(RespuestaDTO respuestaDTO) {
        this.respuestaDTO = respuestaDTO;
    }
    

    
}
