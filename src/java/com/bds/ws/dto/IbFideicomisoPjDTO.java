/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbFideicomisoPj;
import java.util.List;

/**
 *
 * @author robinson.rodriguez
 */
public class IbFideicomisoPjDTO {

    private IbFideicomisoPj ibFideicomisoPj;
    private List<IbFideicomisoPj> ibFideicomisoPjsList;
    private RespuestaDTO respuestaDTO;

    public IbFideicomisoPj getIbFideicomisoPj() {
        return ibFideicomisoPj;
    }

    public void setIbFideicomisoPj(IbFideicomisoPj ibFideicomisoPj) {
        this.ibFideicomisoPj = ibFideicomisoPj;
    }

    public List<IbFideicomisoPj> getIbFideicomisoPjsList() {
        return ibFideicomisoPjsList;
    }

    public void setIbFideicomisoPjsList(List<IbFideicomisoPj> ibFideicomisoPjsList) {
        this.ibFideicomisoPjsList = ibFideicomisoPjsList;
    }

    public RespuestaDTO getRespuestaDTO() {
        return respuestaDTO;
    }

    public void setRespuestaDTO(RespuestaDTO respuestaDTO) {
        this.respuestaDTO = respuestaDTO;
    }
    
}
