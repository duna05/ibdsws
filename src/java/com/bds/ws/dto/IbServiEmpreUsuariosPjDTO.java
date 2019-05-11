/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbServiEmpreUsuariosPj;
import java.util.List;

/**
 *
 * @author luis.perez
 */
public class IbServiEmpreUsuariosPjDTO {
    private IbServiEmpreUsuariosPj ibServiEmpreUsuariosPj;
    private List<IbServiEmpreUsuariosPj> ibServiEmpreUsuariosPjList;
    private RespuestaDTO respuestaDTO;

    public IbServiEmpreUsuariosPj getIbServiEmpreUsuariosPj() {
        return ibServiEmpreUsuariosPj;
    }

    public void setIbServiEmpreUsuariosPj(IbServiEmpreUsuariosPj ibServiEmpreUsuariosPj) {
        this.ibServiEmpreUsuariosPj = ibServiEmpreUsuariosPj;
    }

    public List<IbServiEmpreUsuariosPj> getIbServiEmpreUsuariosPjList() {
        return ibServiEmpreUsuariosPjList;
    }

    public void setIbServiEmpreUsuariosPjList(List<IbServiEmpreUsuariosPj> ibServiEmpreUsuariosPjList) {
        this.ibServiEmpreUsuariosPjList = ibServiEmpreUsuariosPjList;
    }

    public RespuestaDTO getRespuestaDTO() {
        return respuestaDTO;
    }

    public void setRespuestaDTO(RespuestaDTO respuestaDTO) {
        this.respuestaDTO = respuestaDTO;
    }
}
