/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;
import com.bds.ws.model.IbMontosUsuariosPj;
import java.util.List;

/**
 *
 * @author luis.perez
 */
public class IbMontosUsuariosPjDTO {
    private IbMontosUsuariosPj ibMontoUsuarioPj;
    private List<IbMontosUsuariosPj> ibMontosUsuariosPj;
    private RespuestaDTO respuesta;

    public IbMontosUsuariosPj getIbMontoUsuarioPj() {
        return ibMontoUsuarioPj;
    }

    public void setIbMontoUsuarioPj(IbMontosUsuariosPj ibMontoUsuarioPj) {
        this.ibMontoUsuarioPj = ibMontoUsuarioPj;
    }

    public List<IbMontosUsuariosPj> getIbMontosUsuariosPj() {
        return ibMontosUsuariosPj;
    }

    public void setIbMontosUsuariosPj(List<IbMontosUsuariosPj> ibMontosUsuariosPj) {
        this.ibMontosUsuariosPj = ibMontosUsuariosPj;
    }

    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }
}
