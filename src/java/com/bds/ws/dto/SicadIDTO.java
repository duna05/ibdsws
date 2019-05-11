/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.util.BDSUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Clase SicadIDTO
 * @author juan.faneite
 */
public class SicadIDTO extends BDSUtil implements Serializable {

    private String nombreBono;                   //Nombre del Bono.
    private String codigoAdjudicacion;           //Codigo de la Adjudicacion.
    private String estatus;                      //Estatus de la solicitud.
    private BigDecimal montoCompra;              //Monto de la compra.
    private BigDecimal precio;                   //Precio.
    private BigDecimal mtoAsignadoDolar;         //Monto asignado en dolares .
    private BigDecimal mtoAsignadoBS;            //Monto asignado en bolivares
    private BigDecimal tipoCambio;               //Tipo de cambio.
    private Date fechaSolicitud;                 //Fecha de la Solicitud.
    private RespuestaDTO respuesta;

    public SicadIDTO() {
    }

    /**
     * Nombre del Bono.
     *
     * @return String Nombre del Bono.
     */
    public String getNombreBono() {
        return nombreBono;
    }

    /**
     * Nombre del Bono.
     *
     * @param nombreBono String Nombre del Bono.
     */
    public void setNombreBono(String nombreBono) {
        this.nombreBono = nombreBono;
    }

    /**
     * Codigo de la Adjudicacion.
     *
     * @return String Codigo de la Adjudicacion.
     */
    public String getCodigoAdjudicacion() {
        return codigoAdjudicacion;
    }

    /**
     * Codigo de la Adjudicacion.
     *
     * @param codigoAdjudicacion String Codigo de la Adjudicacion.
     */
    public void setCodigoAdjudicacion(String codigoAdjudicacion) {
        this.codigoAdjudicacion = codigoAdjudicacion;
    }

    /**
     * Estatus de la solicitud.
     *
     * @return String Estatus de la solicitud.
     */
    public String getEstatus() {
        return estatus;
    }

    /**
     * Estatus de la solicitud.
     *
     * @param estatus String Estatus de la solicitud.
     */
    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    /**
     * Monto de la compra.
     *
     * @return BigDecimal Monto de la compra.
     */
    public BigDecimal getMontoCompra() {
        return montoCompra;
    }

    /**
     * Monto de la compra.
     *
     * @param montoCompra BigDecimal Monto de la compra.
     */
    public void setMontoCompra(BigDecimal montoCompra) {
        this.montoCompra = montoCompra;
    }

    /**
     * Precio.
     *
     * @return BigDecimal Precio.
     */
    public BigDecimal getPrecio() {
        return precio;
    }

    /**
     * Precio.
     *
     * @param precio BigDecimal Precio.
     */
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    /**
     * Monto asignado en dolares.
     *
     * @return BigDecimal Monto asignado en dolares.
     */
    public BigDecimal getMtoAsignadoDolar() {
        return mtoAsignadoDolar;
    }

    /**
     * Monto asignado en dolares.
     *
     * @param mtoAsignadoDolar BigDecimal Monto asignado en dolares.
     */
    public void setMtoAsignadoDolar(BigDecimal mtoAsignadoDolar) {
        this.mtoAsignadoDolar = mtoAsignadoDolar;
    }

    /**
     * Monto asignado en bolivares.
     *
     * @return BigDecimal Monto asignado en bolivares.
     */
    public BigDecimal getMtoAsignadoBS() {
        return mtoAsignadoBS;
    }

    /**
     * Monto asignado en bolivares.
     *
     * @param mtoAsignadoBS BigDecimal Monto asignado en bolivares.
     */
    public void setMtoAsignadoBS(BigDecimal mtoAsignadoBS) {
        this.mtoAsignadoBS = mtoAsignadoBS;
    }

    /**
     * Tipo de cambio.
     *
     * @return BigDecimal Tipo de cambio.
     */
    public BigDecimal getTipoCambio() {
        return tipoCambio;
    }

    /**
     * Tipo de cambio.
     *
     * @param tipoCambio BigDecimal Tipo de cambio.
     */
    public void setTipoCambio(BigDecimal tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    /**
     * Fecha de la Solicitud.
     *
     * @return Date Fecha de la Solicitud.
     */
    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    /**
     * Fecha de la Solicitud.
     *
     * @param fechaSolicitud Date Fecha de la Solicitud.
     */
    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    /**
     * retorna la respuesta de la operacion que se realiza.
     * @return RespuestaDTO
     */
    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    /**
     * asigna objeto para almacenar la respuesta de la transaccion
     * @param respuesta objeto para almacenar la respuesta de la transaccion
     */
    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }

}
