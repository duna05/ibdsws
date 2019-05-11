/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.SubtipoClienteJuridico;
import com.bds.ws.util.BDSUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author alejandro.flores
 */
public class SubtipoClienteDTO extends BDSUtil implements Serializable{
    
    private BigDecimal codigoSubtipo;
    private String descripcion;
    private RespuestaDTO respuesta;
    private List<SubtipoClienteDTO> ibSubtipoClienteJuridicoList;

    public List<SubtipoClienteDTO> getIbSubtipoClienteJuridicoList() {
        return ibSubtipoClienteJuridicoList;
    }

    public void setIbSubtipoClienteJuridicoList(List<SubtipoClienteDTO> ibSubtipoClienteJuridicoList) {
        this.ibSubtipoClienteJuridicoList = ibSubtipoClienteJuridicoList;
    }
      

  
    
    

    public BigDecimal getCodigoSubtipo() {
        return codigoSubtipo;
    }

    public void setCodigoSubtipo(BigDecimal codigoSubtipo) {
        this.codigoSubtipo = codigoSubtipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }
    
}
