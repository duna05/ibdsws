/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbConexionUsuarioPj;
import java.util.List;

/**
 *
 * @author luis.perez
 */
public class IbConexionUsuarioPjDTO {
    private IbConexionUsuarioPj ibConexionUsuarioPj;
    private List<IbConexionUsuarioPj> ibConexionesUsuariosPj;
    private RespuestaDTO respuesta;

    public IbConexionUsuarioPj getIbConexionUsuarioPj() {
        return ibConexionUsuarioPj;
    }

    public void setIbConexionUsuarioPj(IbConexionUsuarioPj ibConexionUsuarioPj) {
        this.ibConexionUsuarioPj = ibConexionUsuarioPj;
    }

    public List<IbConexionUsuarioPj> getIbConexionesUsuariosPj() {
        return ibConexionesUsuariosPj;
    }

    public void setIbConexionesUsuariosPj(List<IbConexionUsuarioPj> ibConexionesUsuariosPj) {
        this.ibConexionesUsuariosPj = ibConexionesUsuariosPj;
    }

    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }
}
