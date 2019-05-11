/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbCargaNominaPj;
import java.util.List;

/**
 *
 * @author juan.vasquez
 */
public class IbCargaNominaPjDTO {


    private IbCargaNominaPj ibCargaNominaPj;
    private List<IbCargaNominaPj> ibCargaNominaPjsList;
    private RespuestaDTO respuestaDTO;
    
    public IbCargaNominaPj getIbCargaNominaPj() {
        return ibCargaNominaPj;
    }

    public void setIbCargaNominaPj(IbCargaNominaPj ibCargaNominaPj) {
        this.ibCargaNominaPj = ibCargaNominaPj;
    }

    public List<IbCargaNominaPj> getIbCargaNominaPjsList() {
        return ibCargaNominaPjsList;
    }

    public void setIbCargaNominaPjsList(List<IbCargaNominaPj> ibCargaNominaPjsList) {
        this.ibCargaNominaPjsList = ibCargaNominaPjsList;
    }

    public RespuestaDTO getRespuestaDTO() {
        return respuestaDTO;
    }

    public void setRespuestaDTO(RespuestaDTO respuestaDTO) {
        this.respuestaDTO = respuestaDTO;
    }
    

    
}
