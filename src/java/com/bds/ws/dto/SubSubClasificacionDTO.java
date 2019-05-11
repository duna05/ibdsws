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
public class SubSubClasificacionDTO extends BDSUtil implements Serializable{
    
    private BigDecimal codigoSubSubClasificacion;
    private String descripcion;
    private RespuestaDTO respuesta;
    private List<SubSubClasificacionDTO> ibSubSubClasificacionList;

    public BigDecimal getCodigoSubSubClasificacion() {
        return codigoSubSubClasificacion;
    }

    public void setCodigoSubSubClasificacion(BigDecimal codigoSubSubClasificacion) {
        this.codigoSubSubClasificacion = codigoSubSubClasificacion;
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

    public List<SubSubClasificacionDTO> getIbSubSubClasificacionList() {
        return ibSubSubClasificacionList;
    }

    public void setIbSubSubClasificacionList(List<SubSubClasificacionDTO> ibSubSubClasificacionList) {
        this.ibSubSubClasificacionList = ibSubSubClasificacionList;
    }

    

   

    
    
}
