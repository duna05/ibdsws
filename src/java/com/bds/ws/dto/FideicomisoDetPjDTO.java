/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbEstatusPagosPj;
import java.util.Date;
import java.util.List;

/**
 *
 * @author robinson.rodriguez
 */
public class FideicomisoDetPjDTO {

    private Date fechaHora;
    private String nroLinea;
    private String lineaTxt;
    private String detalleError;
    private IbEstatusPagosPj estatus;
    private List<FideicomisoDetPjDTO> FideicomisoDetPjDTOList;
    private RespuestaDTO respuesta;

    public List<FideicomisoDetPjDTO> getFideicomisoDetPjDTOList() {
        return FideicomisoDetPjDTOList;
    }

    public void setFideicomisoDetPjDTOList(List<FideicomisoDetPjDTO> FideicomisoDetPjDTOList) {
        this.FideicomisoDetPjDTOList = FideicomisoDetPjDTOList;
    }
    
    public String getLineaTxt() {
        return lineaTxt;
    }

    public void setLineaTxt(String lineaTxt) {
        this.lineaTxt = lineaTxt;
    }
    
    public IbEstatusPagosPj getEstatus() {
        return estatus;
    }

    public void setEstatus(IbEstatusPagosPj estatus) {
        this.estatus = estatus;
    }
    
    public String getNroLinea() {
        return nroLinea;
    }

    public void setNroLinea(String nroLinea) {
        this.nroLinea = nroLinea;
    }
    
    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getDetalleError() {
        return detalleError;
    }

    public void setDetalleError(String detalleError) {
        this.detalleError = detalleError;
    }

    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }

}
