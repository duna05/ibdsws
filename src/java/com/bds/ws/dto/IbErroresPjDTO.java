/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbErroresPj;
import java.util.List;

/**
 *
 * @author juan.vasquez
 */
public class IbErroresPjDTO {
    
    private IbErroresPj ibErroresPj;
    private List<IbErroresPj> ibErroresPjsList;
    private RespuestaDTO respuestaDTO;

    public IbErroresPj getIbErroresCargaPj() {
        return ibErroresPj;
    }

    public IbErroresPj getIbErroresPj() {
        return ibErroresPj;
    }

    public void setIbErroresPj(IbErroresPj ibErroresPj) {
        this.ibErroresPj = ibErroresPj;
    }

    public List<IbErroresPj> getIbErroresPjsList() {
        return ibErroresPjsList;
    }

    public void setIbErroresPjsList(List<IbErroresPj> ibErroresPjsList) {
        this.ibErroresPjsList = ibErroresPjsList;
    }

    public RespuestaDTO getRespuestaDTO() {
        return respuestaDTO;
    }

    public void setRespuestaDTO(RespuestaDTO respuestaDTO) {
        this.respuestaDTO = respuestaDTO;
    }
    
    
            
}
