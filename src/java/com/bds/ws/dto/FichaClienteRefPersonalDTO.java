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
 * @author alejandro.flores
 */
public class FichaClienteRefPersonalDTO extends BDSUtil implements Serializable {

    private BigDecimal codigoTipoIdentificacion;
    /* CODIGO_TIPO_IDENTIFICACION  */
    private String numeroIdentificacion;
    /* NUMERO_IDENTIFICACION */
    private String nombre;
    /* nombre */
    private String primerApellido;
    /* PRIMER_APELLIDO */
    private String segundoApellido;
    /* SEGUNDO_APELLIDO */
    private String parentesco;
    /*  PARENTESCO   */
    private String telefono;
    /* TELEFONO */
    private String telefono2;
    /* TELEFONO */
    private RespuestaDTO respuesta;
    
     private List<FichaClienteRefPersonalDTO> ibFichaClienteRefPersonalList;

    public List<FichaClienteRefPersonalDTO> getIbFichaClienteRefPersonalList() {
        return ibFichaClienteRefPersonalList;
    }

    public void setIbFichaClienteRefPersonalList(List<FichaClienteRefPersonalDTO> ibFichaClienteRefPersonalList) {
        this.ibFichaClienteRefPersonalList = ibFichaClienteRefPersonalList;
    }

    public BigDecimal getCodigoTipoIdentificacion() {
        return codigoTipoIdentificacion;
    }

    public void setCodigoTipoIdentificacion(BigDecimal codigoTipoIdentificacion) {
        this.codigoTipoIdentificacion = codigoTipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }

}
