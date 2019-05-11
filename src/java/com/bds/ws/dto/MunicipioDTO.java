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
public class MunicipioDTO extends BDSUtil implements Serializable{
    
    private BigDecimal codigoMunicipio;
    private String nombre;
    private RespuestaDTO respuesta;
    private List<MunicipioDTO> ibMunicipioList;

    public BigDecimal getCodigoMunicipio() {
        return codigoMunicipio;
    }

    public void setCodigoMunicipio(BigDecimal codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
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

    public List<MunicipioDTO> getIbMunicipioList() {
        return ibMunicipioList;
    }

    public void setIbMunicipioList(List<MunicipioDTO> ibMunicipioList) {
        this.ibMunicipioList = ibMunicipioList;
    }

    

   

    

    
    

   

    
    
}
