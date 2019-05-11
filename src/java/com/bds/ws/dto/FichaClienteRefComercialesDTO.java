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
public class FichaClienteRefComercialesDTO extends BDSUtil implements Serializable{
    
    private String casaComercial;
    private String telefonos;
    private String fechaConcesion;    
    /*private BigDecimal codigoFinanciera;
    private BigDecimal tipoFinanciera;
    private String tipoCuentaFinanciera;
    private String numeroCuentaReferencia;
    private String fechaApertura;  */  
    private RespuestaDTO respuesta;
    private List<FichaClienteRefComercialesDTO> ibReferenciaList;

    public String getCasaComercial() {
        return casaComercial;
    }

    public void setCasaComercial(String casaComercial) {
        this.casaComercial = casaComercial;
    }

    public String getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(String telefonos) {
        this.telefonos = telefonos;
    }

    public String getFechaConcesion() {
        return fechaConcesion;
    }

    public void setFechaConcesion(String fechaConcesion) {
        this.fechaConcesion = fechaConcesion;
    }

    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }

    public List<FichaClienteRefComercialesDTO> getIbReferenciaList() {
        return ibReferenciaList;
    }

    public void setIbReferenciaList(List<FichaClienteRefComercialesDTO> ibReferenciaList) {
        this.ibReferenciaList = ibReferenciaList;
    }
   
}
