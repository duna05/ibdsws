/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import java.io.Serializable;


public class RecaudacionPjDTO implements Serializable {

    private String identificador;
    private String numeroPlanilla;
    private String fechaValida;
    private String lvOperacion;
    private String agenciaOrigen;
    private String numeroIdentificacion;
    private String ValorLocal;
    private RespuestaDTO respuesta;

  
    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getNumeroPlanilla() {
        return numeroPlanilla;
    }

    public void setNumeroPlanilla(String numeroPlanilla) {
        this.numeroPlanilla = numeroPlanilla;
    }

    public String getFechaValida() {
        return fechaValida;
    }

    public void setFechaValida(String fechaValida) {
        this.fechaValida = fechaValida;
    }

    public String getLvOperacion() {
        return lvOperacion;
    }

    public void setLvOperacion(String lvOperacion) {
        this.lvOperacion = lvOperacion;
    }

    public String getAgenciaOrigen() {
        return agenciaOrigen;
    }

    public void setAgenciaOrigen(String agenciaOrigen) {
        this.agenciaOrigen = agenciaOrigen;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getValorLocal() {
        return ValorLocal;
    }

    public void setValorLocal(String ValorLocal) {
        this.ValorLocal = ValorLocal;
    }

}
