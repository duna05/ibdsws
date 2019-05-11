/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author juan.faneite
 */
public class ReclamoDTO implements Serializable{
    private String codigoReclamo;           //codigp del reclamo
    private String nombreReclamo;           //nombre del reclamo
    private String numeroSolicitud;
    private Date fechaSolicitud;
    private Date fechaSolucion;
    private BigDecimal montoReclamo;
    private String estatus;
        
    /*AGREGAR LOS CAMPOS QUE HAGAN FALTA CUANDO SE DEFINA EL DETALLE DEL RECLAMO*/
    
    private List<ReclamoDTO> reclamos;      //lista de reclamos en caso de ser una consulta global
    private RespuestaDTO respuesta;

    /**
     * 
     * @return numero de solicitud de reclamo
     */
    public String getNumeroSolicitud() {
        return numeroSolicitud;
    }

    /**
     * 
     * @param numeroSolicitud numero de solicitud de reclamo
     */
    public void setNumeroSolicitud(String numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }

    /**
     * 
     * @return fecha de solicitud
     */
    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    /**
     * 
     * @param fechaSolicitud fecha de solicitud
     */
    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    /**
     * 
     * @return fecha de solucion
     */
    public Date getFechaSolucion() {
        return fechaSolucion;
    }

    /**
     * 
     * @param fechaSolucion fecha de solucion
     */
    public void setFechaSolucion(Date fechaSolucion) {
        this.fechaSolucion = fechaSolucion;
    }

    /**
     * 
     * @return monto de reclamo
     */
    public BigDecimal getMontoReclamo() {
        return montoReclamo;
    }

    /**
     * 
     * @param montoReclamo monto de reclamo
     */
    public void setMontoReclamo(BigDecimal montoReclamo) {
        this.montoReclamo = montoReclamo;
    }

    /**
     * 
     * @return estatus de reclamo
     */
    public String getEstatus() {
        return estatus;
    }

    /**
     * 
     * @param estatus estatus de reclamo
     */
    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

        
    /**
     * retorna el codigo del reclamo
     * @return codigo del reclamo
     */
    public String getCodigoReclamo() {
        return codigoReclamo;
    }

    /**
     * asigna el codigo del reclamo
     * @param codigoReclamo codigo del reclamo
     */
    public void setCodigoReclamo(String codigoReclamo) {
        this.codigoReclamo = codigoReclamo;
    }

    /**
     * retorna el nombre del reclamo
     * @return nombre del reclamo
     */
    public String getNombreReclamo() {
        return nombreReclamo;
    }

    /**
     * asigna el nombre del reclamo
     * @param nombreReclamo nombre del reclamo
     */
    public void setNombreReclamo(String nombreReclamo) {
        this.nombreReclamo = nombreReclamo;
    }

    /**
     * retorna la lista de reclamos en caso de ser una consulta global
     * @return lista de reclamos en caso de ser una consulta global
     */
    public List<ReclamoDTO> getReclamos() {
        return reclamos;
    }

    /**
     * asigna la lista de reclamos en caso de ser una consulta global
     * @param reclamos lista de reclamos en caso de ser una consulta global
     */
    public void setReclamos(List<ReclamoDTO> reclamos) {
        this.reclamos = reclamos;
    }

    /**
     * 
     * @return 
     */
    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    /**
     * 
     * @param respuestaDTO 
     */
    public void setRespuesta(RespuestaDTO respuestaDTO) {
        this.respuesta = respuestaDTO;
    }
    
    
    
    
    
}
