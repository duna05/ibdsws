/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbCargaPagosCorpDetPj;
import java.util.List;

/**
 *
 * @author luis.perez
 */
public class IbCargaPagosCorpDetPjDTO {
    
    private IbCargaPagosCorpDetPj ibCargaPagosCorpDetPj;
    private List<IbCargaPagosCorpDetPj> ibCargaPagosCorpDetPjsList;
    private RespuestaDTO respuestaDTO;

    public IbCargaPagosCorpDetPj getIbCargaPagosCorpDetPj() {
        return ibCargaPagosCorpDetPj;
    }

    public void setIbCargaPagosCorpDetPj(IbCargaPagosCorpDetPj ibCargaPagosCorpDetPj) {
        this.ibCargaPagosCorpDetPj = ibCargaPagosCorpDetPj;
    }

    public List<IbCargaPagosCorpDetPj> getIbCargaPagosCorpDetPjsList() {
        return ibCargaPagosCorpDetPjsList;
    }

    public void setIbCargaPagosCorpDetPjsList(List<IbCargaPagosCorpDetPj> ibCargaPagosCorpDetPjsList) {
        this.ibCargaPagosCorpDetPjsList = ibCargaPagosCorpDetPjsList;
    }

   
    
    public RespuestaDTO getRespuestaDTO() {
        return respuestaDTO;
    }

    public void setRespuestaDTO(RespuestaDTO respuestaDTO) {
        this.respuestaDTO = respuestaDTO;
    }
}
