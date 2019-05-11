/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbEmpresas;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author luis.perez
 */
public class IbEmpresasDTO implements Serializable {
    private IbEmpresas ibEmpresa;
    private List<IbEmpresas> ibEmpresas;
    private RespuestaDTO respuesta;

    public IbEmpresas getIbEmpresa() {
        return ibEmpresa;
    }

    public void setIbEmpresa(IbEmpresas ibEmpresa) {
        this.ibEmpresa = ibEmpresa;
    }

    public List<IbEmpresas> getIbEmpresas() {
        return ibEmpresas;
    }

    public void setIbEmpresas(List<IbEmpresas> ibEmpresas) {
        this.ibEmpresas = ibEmpresas;
    }

    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }
    }
