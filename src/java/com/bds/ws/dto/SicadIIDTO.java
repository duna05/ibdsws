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
 * Clase SicadIIDTO
 * @author juan.faneite
 */
public class SicadIIDTO extends BDSUtil implements Serializable {

    private String secuenciaEmision;                  //Numero de secuencia.
    private Date fechaAdicion;                        //Fecha de la compra.
    private BigDecimal montoCompra;                   //Monto de la compra.
    private BigDecimal montoOperacion;                //Monto de la operacion.
    private BigDecimal montoComision;                 //Monto de la comision.
    private BigDecimal porcentajeFinanc;              //Porcentaje de financimiento.
    private String numeroCuenta;                      //Numero de cuenta en bolivares.
    private String numeroCuentaDolar2;                //Numero de cuenta en dolares.
    private BigDecimal montoAdjudicado;               //Monto adjudicado.
    private String tipoCambio;                        //Tipo de cambio
    private String instrumento;                       //Instrumento.
    private String estatus;                           //Estatus de la operacion.
    private RespuestaDTO respuesta;

    public SicadIIDTO() {
    }

    /**
     * Numero de secuencia.
     *
     * @return String Numero de secuencia.
     */
    public String getSecuenciaEmision() {
        return secuenciaEmision;
    }

    /**
     * Numero de secuencia.
     *
     * @param secuenciaEmision String Numero de secuencia.
     */
    public void setSecuenciaEmision(String secuenciaEmision) {
        this.secuenciaEmision = secuenciaEmision;
    }

    /**
     * Fecha de la compra.
     *
     * @return Date Fecha de la compra.
     */
    public Date getFechaAdicion() {
        return fechaAdicion;
    }

    /**
     * Fecha de la compra.
     *
     * @param fechaAdicion Date Fecha de la compra.
     */
    public void setFechaAdicion(Date fechaAdicion) {
        this.fechaAdicion = fechaAdicion;
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
     * Monto de la operacion.
     *
     * @return BigDecimal Monto de la operacion.
     */
    public BigDecimal getMontoOperacion() {
        return montoOperacion;
    }

    /**
     * Monto de la operacion.
     *
     * @param montoOperacion BigDecimal Monto de la operacion.
     */
    public void setMontoOperacion(BigDecimal montoOperacion) {
        this.montoOperacion = montoOperacion;
    }

    /**
     * Monto de la comision.
     *
     * @return BigDecimal Monto de la comision.
     */
    public BigDecimal getMontoComision() {
        return montoComision;
    }

    /**
     * Monto de la comision.
     *
     * @param montoComision BigDecimal Monto de la comision.
     */
    public void setMontoComision(BigDecimal montoComision) {
        this.montoComision = montoComision;
    }

    /**
     * Porcentaje de financimiento.
     *
     * @return BigDecimal Porcentaje de financimiento.
     */
    public BigDecimal getPorcentajeFinanc() {
        return porcentajeFinanc;
    }

    /**
     * Porcentaje de financimiento.
     *
     * @param porcentajeFinanc BigDecimal Porcentaje de financimiento.
     */
    public void setPorcentajeFinanc(BigDecimal porcentajeFinanc) {
        this.porcentajeFinanc = porcentajeFinanc;
    }

    /**
     * Numero de cuenta en bolivares.
     *
     * @return String Numero de cuenta en bolivares.
     */
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    /**
     * Numero de cuenta en bolivares.
     *
     * @param numeroCuenta String Numero de cuenta en bolivares.
     */
    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    /**
     * Numero de cuenta en dolares.
     *
     * @return String Numero de cuenta en dolares.
     */
    public String getNumeroCuentaDolar2() {
        return numeroCuentaDolar2;
    }

    /**
     * Numero de cuenta en dolares.
     *
     * @param numeroCuentaDolar2 String Numero de cuenta en dolares.
     */
    public void setNumeroCuentaDolar2(String numeroCuentaDolar2) {
        this.numeroCuentaDolar2 = numeroCuentaDolar2;
    }

    /**
     * Monto adjudicado.
     *
     * @return BigDecimal Monto adjudicado.
     */
    public BigDecimal getMontoAdjudicado() {
        return montoAdjudicado;
    }

    /**
     * Monto adjudicado.
     *
     * @param montoAdjudicado BigDecimal Monto adjudicado.
     */
    public void setMontoAdjudicado(BigDecimal montoAdjudicado) {
        this.montoAdjudicado = montoAdjudicado;
    }

    /**
     * Tipo de cambio.
     *
     * @return String Tipo de cambio.
     */
    public String getTipoCambio() {
        return tipoCambio;
    }

    /**
     * Tipo de cambio.
     *
     * @param tipoCambio String Tipo de cambio.
     */
    public void setTipoCambio(String tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    /**
     * Instrumento.
     *
     * @return String Instrumento.
     */
    public String getInstrumento() {
        return instrumento;
    }

    /**
     * Instrumento.
     *
     * @param instrumento String Instrumento.
     */
    public void setInstrumento(String instrumento) {
        this.instrumento = instrumento;
    }

    /**
     * Estatus de la operacion.
     *
     * @return String Estatus de la operacion.
     */
    public String getEstatus() {
        return estatus;
    }

    /**
     * Estatus de la operacion.
     *
     * @param estatus String Estatus de la operacion.
     */
    public void setEstatus(String estatus) {
        this.estatus = estatus;
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
