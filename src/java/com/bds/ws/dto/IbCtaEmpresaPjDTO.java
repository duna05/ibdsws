/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbCtasEmpresaPj;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author cesar.mujica
 */
public class IbCtaEmpresaPjDTO implements Serializable {
    IbCtasEmpresaPj cuentaEmpresaPj;
    List<IbCtasEmpresaPj> cuentasEmpresaPj;
    RespuestaDTO respuestaDTO;

    public IbCtasEmpresaPj getCuentaEmpresaPj() {
        return cuentaEmpresaPj;
    }

    public void setCuentaEmpresaPj(IbCtasEmpresaPj cuentaEmpresaPj) {
        this.cuentaEmpresaPj = cuentaEmpresaPj;
    }

    public List<IbCtasEmpresaPj> getCuentasEmpresaPj() {
        return cuentasEmpresaPj;
    }

    public void setCuentasEmpresaPj(List<IbCtasEmpresaPj> cuentasEmpresaPj) {
        this.cuentasEmpresaPj = cuentasEmpresaPj;
    }

    public RespuestaDTO getRespuestaDTO() {
        return respuestaDTO;
    }

    public void setRespuestaDTO(RespuestaDTO respuestaDTO) {
        this.respuestaDTO = respuestaDTO;
    }
    
    
    
}
