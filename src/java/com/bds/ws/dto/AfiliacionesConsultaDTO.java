/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import java.util.List;

/**
 *
 * @author Jose.Perez
 */
public class AfiliacionesConsultaDTO {  
    private String claveOrdenante;
    private String contrato;
    private Integer bancoDestino;
    private String tipoOperacion;
    private String identificacionPagador;
    private String nombrePagador;
    private Integer numeroMensajes;
    private String descripcion;
    private String codigoResultados;
    private RespuestaDTO respuesta;   
    List<AfiliacionesConsultaDTO> listAfiliacionesConsultaDTO;

    public String getClaveOrdenante() {
        return claveOrdenante;
    }

    public void setClaveOrdenante(String claveOrdenante) {
        this.claveOrdenante = claveOrdenante;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public Integer getBancoDestino() {
        return bancoDestino;
    }

    public void setBancoDestino(Integer bancoDestino) {
        this.bancoDestino = bancoDestino;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public String getIdentificacionPagador() {
        return identificacionPagador;
    }

    public void setIdentificacionPagador(String identificacionPagador) {
        this.identificacionPagador = identificacionPagador;
    }

    public String getNombrePagador() {
        return nombrePagador;
    }

    public void setNombrePagador(String nombrePagador) {
        this.nombrePagador = nombrePagador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigoResultados() {
        return codigoResultados;
    }

    public void setCodigoResultados(String codigoResultados) {
        this.codigoResultados = codigoResultados;
    }

    public Integer getNumeroMensajes() {
        return numeroMensajes;
    }

    public void setNumeroMensajes(Integer numeroMensajes) {
        this.numeroMensajes = numeroMensajes;
    }

    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }    

    public List<AfiliacionesConsultaDTO> getListAfiliacionesConsultaDTO() {
        return listAfiliacionesConsultaDTO;
    }

    public void setListAfiliacionesConsultaDTO(List<AfiliacionesConsultaDTO> listAfiliacionesConsultaDTO) {
        this.listAfiliacionesConsultaDTO = listAfiliacionesConsultaDTO;
    }
    
}
