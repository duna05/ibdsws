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
public class ClasificacionDTO extends BDSUtil implements Serializable{
    
    private BigDecimal codigoClasificacion;
    private String descripcion;
    private RespuestaDTO respuesta;
    private List<ClasificacionDTO> ibClasificacionList;

    public BigDecimal getCodigoClasificacion() {
        return codigoClasificacion;
    }

    public void setCodigoClasificacion(BigDecimal codigoClasificacion) {
        this.codigoClasificacion = codigoClasificacion;
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

    public List<ClasificacionDTO> getIbClasificacionList() {
        return ibClasificacionList;
    }

    public void setIbClasificacionList(List<ClasificacionDTO> ibClasificacionList) {
        this.ibClasificacionList = ibClasificacionList;
    }

    
    
}
