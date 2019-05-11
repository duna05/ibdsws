/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbErroresPaEspDetPj;
import java.util.List;

/**
 *
 * @author robinson rodriguez
 */
public class IbErroresCargaPagosEspDetPjDTO {
    
    private IbErroresPaEspDetPj ibErrCargaPagosEspDetPj;
    private List<IbErroresPaEspDetPj> ibErrCargaPagosEspDetPjsList;
    private RespuestaDTO respuestaDTO;
    
    public IbErroresPaEspDetPj getIbErrCargaPagosEspPj() {
        return ibErrCargaPagosEspDetPj;
    }

    public void setIbErrCargaPagosEspDetPj(IbErroresPaEspDetPj ibErrCargaPagosEspDetPj) {
        this.ibErrCargaPagosEspDetPj = ibErrCargaPagosEspDetPj;
    }

    public List<IbErroresPaEspDetPj> getIbErrCargaPagosEspDetPjsList() {
        return ibErrCargaPagosEspDetPjsList;
    }

    public void setIbErrCargaPagosEspDetPjsList(List<IbErroresPaEspDetPj> ibErrCargaPagosEspDetPjsList) {
        this.ibErrCargaPagosEspDetPjsList = ibErrCargaPagosEspDetPjsList;
    }

    public RespuestaDTO getRespuestaDTO() {
        return respuestaDTO;
    }

    public void setRespuestaDTO(RespuestaDTO respuestaDTO) {
        this.respuestaDTO = respuestaDTO;
    }
    
}
