/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbEstatusPagosPj;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author robinson.rodriguez
 */
public class FideicomisoPjDTO {

    private BigDecimal idFideicomiso;
    private String indicador;
    private Date fechaRegistro;
    private BigDecimal cantidadRegistro;
    private BigDecimal cantidadRegistroProcesados;
    private BigDecimal cantidadRegistroConErrores;
    private IbEstatusPagosPj estatus;
    private List<FideicomisoPjDTO> FideicomisoPjDTOList;

    public BigDecimal getIdFideicomiso() {
        return idFideicomiso;
    }

    public void setIdFideicomiso(BigDecimal idFideicomiso) {
        this.idFideicomiso = idFideicomiso;
    }
    
    public List<FideicomisoPjDTO> getFideicomisoPjDTOList() {
        return FideicomisoPjDTOList;
    }

    public void setFideicomisoPjDTOList(List<FideicomisoPjDTO> FideicomisoPjDTOList) {
        this.FideicomisoPjDTOList = FideicomisoPjDTOList;
    }
    
    private RespuestaDTO respuesta;

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public BigDecimal getCantidadRegistro() {
        return cantidadRegistro;
    }

    public void setCantidadRegistro(BigDecimal cantidadRegistro) {
        this.cantidadRegistro = cantidadRegistro;
    }

    public BigDecimal getCantidadRegistroProcesados() {
        return cantidadRegistroProcesados;
    }

    public void setCantidadRegistroProcesados(BigDecimal cantidadRegistroProcesados) {
        this.cantidadRegistroProcesados = cantidadRegistroProcesados;
    }

    public BigDecimal getCantidadRegistroConErrores() {
        return cantidadRegistroConErrores;
    }

    public void setCantidadRegistroConErrores(BigDecimal cantidadRegistroConErrores) {
        this.cantidadRegistroConErrores = cantidadRegistroConErrores;
    }

    public IbEstatusPagosPj getEstatus() {
        return estatus;
    }

    public void setEstatus(IbEstatusPagosPj estatus) {
        this.estatus = estatus;
    }
    
    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }

    public String getIndicador() {
        return indicador;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }
}
