/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbErroresNominaPj;
import java.util.List;

/**
 *
 * @author juan.vasquez
 */
public class IbErroresNominaPjDTO {
    
    private IbErroresNominaPj ibErroresNominaPj;
    private List<IbErroresNominaPj> ibErroresNominaPjList;
    private RespuestaDTO respuestaDTO;

    public IbErroresNominaPj getIbErroresNominaPj() {
        return ibErroresNominaPj;
    }

    public void setIbErroresNominaPj(IbErroresNominaPj ibErroresNominaPj) {
        this.ibErroresNominaPj = ibErroresNominaPj;
    }

    public List<IbErroresNominaPj> getIbErroresNominaPjList() {
        return ibErroresNominaPjList;
    }

    public void setIbErroresNominaPjList(List<IbErroresNominaPj> ibErroresNominaPjList) {
        this.ibErroresNominaPjList = ibErroresNominaPjList;
    }

    public RespuestaDTO getRespuestaDTO() {
        return respuestaDTO;
    }

    public void setRespuestaDTO(RespuestaDTO respuestaDTO) {
        this.respuestaDTO = respuestaDTO;
    }
    
    
}
