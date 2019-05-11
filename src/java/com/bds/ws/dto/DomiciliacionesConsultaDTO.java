/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Jose.Perez
 */
public class DomiciliacionesConsultaDTO {
    private Date fechaValida; 
    private Integer cantidadCargado;
    private Integer cantidadRechazado; 
    private Integer cantidadPresentado;
    private Integer cantidadTotal; 
    private RespuestaDTO respuesta; 
    List<DomiciliacionesConsultaDTO> listDomiciliacionesConsultaDTO;

    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }    

    public Date getFechaValida() {
        return fechaValida;
    }

    public void setFechaValida(Date fechaValida) {
        this.fechaValida = fechaValida;
    }
    
    public Integer getCantidadCargado() {
        return cantidadCargado;
    }

    public void setCantidadCargado(Integer cantidadCargado) {
        this.cantidadCargado = cantidadCargado;
    }

    public Integer getCantidadRechazado() {
        return cantidadRechazado;
    }

    public void setCantidadRechazado(Integer cantidadRechazado) {
        this.cantidadRechazado = cantidadRechazado;
    }

    public Integer getCantidadPresentado() {
        return cantidadPresentado;
    }

    public void setCantidadPresentado(Integer cantidadPresentado) {
        this.cantidadPresentado = cantidadPresentado;
    }

    public Integer getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(Integer cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }

    public List<DomiciliacionesConsultaDTO> getListDomiciliacionesConsultaDTO() {
        return listDomiciliacionesConsultaDTO;
    }

    public void setListDomiciliacionesConsultaDTO(List<DomiciliacionesConsultaDTO> listDomiciliacionesConsultaDTO) {
        this.listDomiciliacionesConsultaDTO = listDomiciliacionesConsultaDTO;
    }
}
