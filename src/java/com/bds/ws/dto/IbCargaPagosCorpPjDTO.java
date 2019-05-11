/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbCargaPagosCorpPj;
import java.util.List;

/**
 *
 * @author luis.perez
 */
public class IbCargaPagosCorpPjDTO {
    
    private IbCargaPagosCorpPj ibCargaPagosCorpPj;
    private List<IbCargaPagosCorpPj> ibCargaPagosCorpPjList;
    private RespuestaDTO respuestaDTO;

    public IbCargaPagosCorpPj getIbCargaPagosCorpPj() {
        return ibCargaPagosCorpPj;
    }

    public void setIbCargaPagosCorpPj(IbCargaPagosCorpPj ibCargaPagosCorpPj) {
        this.ibCargaPagosCorpPj = ibCargaPagosCorpPj;
    }

    public List<IbCargaPagosCorpPj> getIbCargaPagosCorpPjList() {
        return ibCargaPagosCorpPjList;
    }

    public void setIbCargaPagosCorpPjList(List<IbCargaPagosCorpPj> ibCargaPagosCorpPjList) {
        this.ibCargaPagosCorpPjList = ibCargaPagosCorpPjList;
    }
    
    public RespuestaDTO getRespuestaDTO() {
        return respuestaDTO;
    }

    public void setRespuestaDTO(RespuestaDTO respuestaDTO) {
        this.respuestaDTO = respuestaDTO;
    }
}
