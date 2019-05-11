/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbErroresPaCorpDetPj;

import java.util.List;

/**
 *
 * @author robinson rodriguez
 */
public class IbErroresCargaPagosCorpDetPjDTO {
    
    private IbErroresPaCorpDetPj ibErrCargaPagosEspDetPj;
    private List<IbErroresPaCorpDetPj> ibErrCargaPagosEspDetPjsList;
    private RespuestaDTO respuestaDTO;

    public IbErroresPaCorpDetPj getIbErrCargaPagosEspDetPj() {
        return ibErrCargaPagosEspDetPj;
    }

    public void setIbErrCargaPagosEspDetPj(IbErroresPaCorpDetPj ibErrCargaPagosEspDetPj) {
        this.ibErrCargaPagosEspDetPj = ibErrCargaPagosEspDetPj;
    }

    public List<IbErroresPaCorpDetPj> getIbErrCargaPagosEspDetPjsList() {
        return ibErrCargaPagosEspDetPjsList;
    }

    public void setIbErrCargaPagosEspDetPjsList(List<IbErroresPaCorpDetPj> ibErrCargaPagosEspDetPjsList) {
        this.ibErrCargaPagosEspDetPjsList = ibErrCargaPagosEspDetPjsList;
    }

    public RespuestaDTO getRespuestaDTO() {
        return respuestaDTO;
    }

    public void setRespuestaDTO(RespuestaDTO respuestaDTO) {
        this.respuestaDTO = respuestaDTO;
    }
    
}
