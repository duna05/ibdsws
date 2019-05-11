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
public class ActividadEconomicaDTO extends BDSUtil implements Serializable{
    
    private BigDecimal codigoActividadEconomica;
    private String descripcion;
    private RespuestaDTO respuesta;
    private List<ActividadEconomicaDTO> ibActividadEconomicaList;

    public BigDecimal getCodigoActividadEconomica() {
        return codigoActividadEconomica;
    }

    public void setCodigoActividadEconomica(BigDecimal codigoActividadEconomica) {
        this.codigoActividadEconomica = codigoActividadEconomica;
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

    public List<ActividadEconomicaDTO> getIbActividadEconomicaList() {
        return ibActividadEconomicaList;
    }

    public void setIbActividadEconomicaList(List<ActividadEconomicaDTO> ibActividadEconomicaList) {
        this.ibActividadEconomicaList = ibActividadEconomicaList;
    }

    

    
    

   

    
    
}
