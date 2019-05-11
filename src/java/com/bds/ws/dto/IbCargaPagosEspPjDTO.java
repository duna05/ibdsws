/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbCargaPagosEspPj;
import java.util.List;

/**
 *
 * @author robinson rodriguez
 */
public class IbCargaPagosEspPjDTO {
    
    private IbCargaPagosEspPj ibCargaPagosEspPj;
    private List<IbCargaPagosEspPj> ibCargaPagosEspPjsList;
    private RespuestaDTO respuestaDTO;
    
    public IbCargaPagosEspPj getIbCargaPagosEspPj() {
        return ibCargaPagosEspPj;
    }

    public void setIbCargaPagosEspPj(IbCargaPagosEspPj ibCargaPagosEspPj) {
        this.ibCargaPagosEspPj = ibCargaPagosEspPj;
    }

    public List<IbCargaPagosEspPj> getIbCargaPagosEspPjsList() {
        return ibCargaPagosEspPjsList;
    }

    public void setIbCargaPagosEspPjsList(List<IbCargaPagosEspPj> ibCargaPagosEspPjsList) {
        this.ibCargaPagosEspPjsList = ibCargaPagosEspPjsList;
    }

    public RespuestaDTO getRespuestaDTO() {
        return respuestaDTO;
    }

    public void setRespuestaDTO(RespuestaDTO respuestaDTO) {
        this.respuestaDTO = respuestaDTO;
    }
    
}
