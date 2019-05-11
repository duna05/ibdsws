/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbCtasXBeneficiariosPj;
import java.util.List;

/**
 *
 * @author robinson.rodriguez
 */
public class IbCtasXBeneficiariosPjDTO {

    private IbCtasXBeneficiariosPj ibCtasXBeneficiariosPj;
    private List<IbCtasXBeneficiariosPj> ibCtasXBeneficiariosPjsList;
    private RespuestaDTO respuestaDTO;

    public IbCtasXBeneficiariosPj getIbCtasXBeneficiariosPj() {
        return ibCtasXBeneficiariosPj;
    }

    public void setIbCtasXBeneficiariosPj(IbCtasXBeneficiariosPj ibCtasXBeneficiariosPj) {
        this.ibCtasXBeneficiariosPj = ibCtasXBeneficiariosPj;
    }

    public List<IbCtasXBeneficiariosPj> getIbCtasXBeneficiariosPjsList() {
        return ibCtasXBeneficiariosPjsList;
    }

    public void setIbCtasXBeneficiariosPjsList(List<IbCtasXBeneficiariosPj> ibCtasXBeneficiariosPjsList) {
        this.ibCtasXBeneficiariosPjsList = ibCtasXBeneficiariosPjsList;
    }

    public RespuestaDTO getRespuestaDTO() {
        return respuestaDTO;
    }

    public void setRespuestaDTO(RespuestaDTO respuestaDTO) {
        this.respuestaDTO = respuestaDTO;
    }

}
