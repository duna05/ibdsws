/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbCargaBeneficiariosPj;
import java.util.List;

/**
 *
 * @author robinson.rodriguez
 */
public class IbCargaBeneficiariosPjDTO {

    private IbCargaBeneficiariosPj ibCargaBeneficiariosPj;
    private List<IbCargaBeneficiariosPj> ibCargaBeneficiariosPjsList;
    private RespuestaDTO respuestaDTO;

    public IbCargaBeneficiariosPj getIbCargaBeneficiariosPj() {
        return ibCargaBeneficiariosPj;
    }

    public void setIbCargaBeneficiariosPj(IbCargaBeneficiariosPj ibCargaBeneficiariosPj) {
        this.ibCargaBeneficiariosPj = ibCargaBeneficiariosPj;
    }

    public List<IbCargaBeneficiariosPj> getIbCargaBeneficiariosPjsList() {
        return ibCargaBeneficiariosPjsList;
    }

    public void setIbCargaBeneficiariosPjsList(List<IbCargaBeneficiariosPj> ibCargaBeneficiariosPjsList) {
        this.ibCargaBeneficiariosPjsList = ibCargaBeneficiariosPjsList;
    }

    public RespuestaDTO getRespuestaDTO() {
        return respuestaDTO;
    }

    public void setRespuestaDTO(RespuestaDTO respuestaDTO) {
        this.respuestaDTO = respuestaDTO;
    }
    
}
