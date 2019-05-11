/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;


import com.bds.ws.util.BDSUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author humberto.rojas
 */
public class SubClasificacionDTO extends BDSUtil implements Serializable{
    
    private BigDecimal codigoSubClasificacion;
    private String descripcion;
    private RespuestaDTO respuesta;
    private List<SubClasificacionDTO> ibSubClasificacionList;

    public BigDecimal getCodigoSubClasificacion() {
        return codigoSubClasificacion;
    }

    public void setCodigoSubClasificacion(BigDecimal codigoSubClasificacion) {
        this.codigoSubClasificacion = codigoSubClasificacion;
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

    public List<SubClasificacionDTO> getIbSubClasificacionList() {
        return ibSubClasificacionList;
    }

    public void setIbSubClasificacionList(List<SubClasificacionDTO> ibSubClasificacionList) {
        this.ibSubClasificacionList = ibSubClasificacionList;
    }

   

    
    
}
