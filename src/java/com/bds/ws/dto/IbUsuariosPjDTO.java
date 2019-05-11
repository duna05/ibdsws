/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;
import com.bds.ws.model.IbEmpresasUsuariosPj;
import com.bds.ws.model.IbUsuariosPj;
import java.util.List;

/**
 *
 * @author luis.perez
 */
public class IbUsuariosPjDTO {
    private IbUsuariosPj ibUsuarioPj;
    private List<IbUsuariosPj> ibUsuariosPj;
    private RespuestaDTO respuesta;
    private List<IbEmpresasUsuariosPj> ibEmpresasUsuariosPj;

    public IbUsuariosPj getIbUsuarioPj() {
        return ibUsuarioPj;
    }

    public void setIbUsuarioPj(IbUsuariosPj ibUsuarioPj) {
        this.ibUsuarioPj = ibUsuarioPj;
    }

    public List<IbUsuariosPj> getIbUsuariosPj() {
        return ibUsuariosPj;
    }

    public void setIbUsuariosPj(List<IbUsuariosPj> ibUsuariosPj) {
        this.ibUsuariosPj = ibUsuariosPj;
    }

    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }

    public List<IbEmpresasUsuariosPj> getIbEmpresasUsuariosPj() {
        return ibEmpresasUsuariosPj;
    }

    public void setIbEmpresasUsuariosPj(List<IbEmpresasUsuariosPj> ibEmpresasUsuariosPj) {
        this.ibEmpresasUsuariosPj = ibEmpresasUsuariosPj;
    }
}
