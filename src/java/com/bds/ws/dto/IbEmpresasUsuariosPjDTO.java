/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbCtasEmpresaUsuarioPj;
import com.bds.ws.model.IbEmpresasUsuariosPj;
import com.bds.ws.model.IbServiEmpreUsuariosPj;
import java.util.List;

/**
 *
 * @author luis.perez
 */
public class IbEmpresasUsuariosPjDTO {
    private IbEmpresasUsuariosPj ibEmpresaUsuarioPj;
    private List<IbEmpresasUsuariosPj> ibEmpresasUsuariosPj;
    private List<IbServiEmpreUsuariosPj> ibServiEmpreUsuariosPj;
    private List<IbCtasEmpresaUsuarioPj> ibCtasEmpresaUsuarioPj;
    
    private RespuestaDTO respuesta;


    public List<IbEmpresasUsuariosPj> getIbEmpresasUsuariosPj() {
        return ibEmpresasUsuariosPj;
    }

    public void setIbEmpresasUsuariosPj(List<IbEmpresasUsuariosPj> ibEmpresasUsuariosPj) {
        this.ibEmpresasUsuariosPj = ibEmpresasUsuariosPj;
    }

    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }

    public IbEmpresasUsuariosPj getIbEmpresaUsuarioPj() {
        return ibEmpresaUsuarioPj;
    }

    public void setIbEmpresaUsuarioPj(IbEmpresasUsuariosPj ibEmpresaUsuarioPj) {
        this.ibEmpresaUsuarioPj = ibEmpresaUsuarioPj;
    }

    public List<IbServiEmpreUsuariosPj> getIbServiEmpreUsuariosPj() {
        return ibServiEmpreUsuariosPj;
    }

    public void setIbServiEmpreUsuariosPj(List<IbServiEmpreUsuariosPj> ibServiEmpreUsuariosPj) {
        this.ibServiEmpreUsuariosPj = ibServiEmpreUsuariosPj;
    }

    public List<IbCtasEmpresaUsuarioPj> getIbCtasEmpresaUsuarioPj() {
        return ibCtasEmpresaUsuarioPj;
    }

    public void setIbCtasEmpresaUsuarioPj(List<IbCtasEmpresaUsuarioPj> ibCtasEmpresaUsuarioPj) {
        this.ibCtasEmpresaUsuarioPj = ibCtasEmpresaUsuarioPj;
    }
}
