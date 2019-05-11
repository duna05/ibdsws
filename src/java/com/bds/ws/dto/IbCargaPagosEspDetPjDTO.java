/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbCargaPagosEspDetPj;
import java.util.List;

/**
 *
 * @author robinson rodriguez
 */
public class IbCargaPagosEspDetPjDTO {
    
    private IbCargaPagosEspDetPj ibCargaPagosEspDetPj;
    private List<IbCargaPagosEspDetPj> ibCargaPagosEspDetPjsList;
    private RespuestaDTO respuestaDTO;
    
    public IbCargaPagosEspDetPj getIbCargaPagosEspDetPj() {
        return ibCargaPagosEspDetPj;
    }

    public void setIbCargaPagosEspDetPj(IbCargaPagosEspDetPj ibCargaPagosEspDetPj) {
        this.ibCargaPagosEspDetPj = ibCargaPagosEspDetPj;
    }

    public List<IbCargaPagosEspDetPj> getIbCargaPagosEspDetPjsList() {
        return ibCargaPagosEspDetPjsList;
    }

    public void setIbCargaPagosEspDetPjsList(List<IbCargaPagosEspDetPj> ibCargaPagosEspDetPjsList) {
        this.ibCargaPagosEspDetPjsList = ibCargaPagosEspDetPjsList;
    }

    public RespuestaDTO getRespuestaDTO() {
        return respuestaDTO;
    }

    public void setRespuestaDTO(RespuestaDTO respuestaDTO) {
        this.respuestaDTO = respuestaDTO;
    }
    
}
