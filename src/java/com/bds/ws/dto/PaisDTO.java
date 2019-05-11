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
public class PaisDTO extends BDSUtil implements Serializable{
    
    private BigDecimal codigoPais;
    private String nombre;
    private RespuestaDTO respuesta;
    private List<PaisDTO> ibPaisList;

    public BigDecimal getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(BigDecimal codigoPais) {
        this.codigoPais = codigoPais;
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

    public List<PaisDTO> getIbPaisList() {
        return ibPaisList;
    }

    public void setIbPaisList(List<PaisDTO> ibPaisList) {
        this.ibPaisList = ibPaisList;
    }

   

    

    

   

    

    
    

   

    
    
}
