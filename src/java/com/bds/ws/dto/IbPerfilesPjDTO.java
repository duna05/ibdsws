/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbPerfilesPj;
import java.util.List;

/**
 *
 * @author luis.perez
 */
public class IbPerfilesPjDTO {
    private IbPerfilesPjDTO ibPerfilPj;
    private List<IbPerfilesPj> ibPerfilesPj;
    private RespuestaDTO respuesta;

    public IbPerfilesPjDTO getIbPerfilPj() {
        return ibPerfilPj;
    }

    public void setIbPerfilPj(IbPerfilesPjDTO ibPerfilPj) {
        this.ibPerfilPj = ibPerfilPj;
    }

    public List<IbPerfilesPj> getIbPerfilesPj() {
        return ibPerfilesPj;
    }

    public void setIbPerfilesPj(List<IbPerfilesPj> ibPerfilesPj) {
        this.ibPerfilesPj = ibPerfilesPj;
    }

    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }
}
