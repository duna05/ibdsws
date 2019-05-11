/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbBeneficiariosPj;
import com.bds.ws.model.IbCtasXBeneficiariosPj;
import java.util.List;

/**
 *
 * @author robinson.rodriguez
 */
public class IbBeneficiariosPjDTO {

    private IbBeneficiariosPj ibBeneficiariosPj;
    private List<IbBeneficiariosPj> ibBeneficiariosPjsList;
    private List<IbCtasXBeneficiariosPj> ibCtasXBeneficiariosPj;
    
    private RespuestaDTO respuestaDTO;

    public IbBeneficiariosPj getIbBeneficiariosPj() {
        return ibBeneficiariosPj;
    }

    public void setIbBeneficiariosPj(IbBeneficiariosPj ibBeneficiariosPj) {
        this.ibBeneficiariosPj = ibBeneficiariosPj;
    }

    public List<IbBeneficiariosPj> getIbBeneficiariosPjsList() {
        return ibBeneficiariosPjsList;
    }

    public void setIbBeneficiariosPjsList(List<IbBeneficiariosPj> ibBeneficiariosPjsList) {
        this.ibBeneficiariosPjsList = ibBeneficiariosPjsList;
    }

    public RespuestaDTO getRespuestaDTO() {
        return respuestaDTO;
    }

    public void setRespuestaDTO(RespuestaDTO respuestaDTO) {
        this.respuestaDTO = respuestaDTO;
    }

    public List<IbCtasXBeneficiariosPj> getIbCtasXBeneficiariosPj() {
        return ibCtasXBeneficiariosPj;
    }

    public void setIbCtasXBeneficiariosPj(List<IbCtasXBeneficiariosPj> ibCtasXBeneficiariosPj) {
        this.ibCtasXBeneficiariosPj = ibCtasXBeneficiariosPj;
    }
}
