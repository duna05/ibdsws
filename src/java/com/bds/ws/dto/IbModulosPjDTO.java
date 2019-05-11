/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbModulosPj;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author luis.perez
 */
public class IbModulosPjDTO implements Serializable {
    private IbModulosPj ibModuloPj;             //Objeto de tipo IbModulos para retornar un solo parametro
    private List<IbModulosPj> ibModulosPj;      //Lisa de objetos IbModulos para retornar varios parametros 
    private RespuestaDTO respuesta;             //objeto de tipo RespuestaDTO para retornar el resultado de la transaccion

    public IbModulosPj getIbModuloPj() {
        return ibModuloPj;
    }

    public void setIbModuloPj(IbModulosPj ibModuloPj) {
        this.ibModuloPj = ibModuloPj;
    }

    public List<IbModulosPj> getIbModulosPj() {
        return ibModulosPj;
    }

    public void setIbModulosPj(List<IbModulosPj> ibModulosPj) {
        this.ibModulosPj = ibModulosPj;
    }

    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }
}
