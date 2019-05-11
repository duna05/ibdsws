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
public class EstadoDTO extends BDSUtil implements Serializable{
    
    private BigDecimal codigoEstado;
    private String nombre;
    private RespuestaDTO respuesta;
    private List<EstadoDTO> ibEstadoList;

    public BigDecimal getCodigoEstado() {
        return codigoEstado;
    }

    public void setCodigoEstado(BigDecimal codigoEstado) {
        this.codigoEstado = codigoEstado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }

    public List<EstadoDTO> getIbEstadoList() {
        return ibEstadoList;
    }

    public void setIbEstadoList(List<EstadoDTO> ibEstadoList) {
        this.ibEstadoList = ibEstadoList;
    }

    

    

   

    

    
    

   

    
    
}
