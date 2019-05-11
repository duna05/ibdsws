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
public class TipoIdentificacionDTO extends BDSUtil implements Serializable{
    
    private BigDecimal codigoTipoIdentificacion;
    private String descripcion;
    private RespuestaDTO respuesta;
    private List<TipoIdentificacionDTO> ibTipoIdentificacionList;

    public BigDecimal getCodigoTipoIdentificacion() {
        return codigoTipoIdentificacion;
    }

    public void setCodigoTipoIdentificacion(BigDecimal codigoTipoIdentificacion) {
        this.codigoTipoIdentificacion = codigoTipoIdentificacion;
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

    public List<TipoIdentificacionDTO> getIbTipoIdentificacionList() {
        return ibTipoIdentificacionList;
    }

    public void setIbTipoIdentificacionList(List<TipoIdentificacionDTO> ibTipoIdentificacionList) {
        this.ibTipoIdentificacionList = ibTipoIdentificacionList;
    }

    
    

   

    
    
}
