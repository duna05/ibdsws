/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Clase MovimientoTDCDTO
 * @author cesar.mujica
 */
public class MovimientoTDCDTO implements Serializable {

    private Date fechaRegistro;     //Fecha cuando se registra en la tarjeta del cliente.
    private Date fechaOperacion;    //Fecha en la que se realizo la transaccion.
    private BigDecimal monto;       //Monto de la transaccion.
    private String descripcion;     //Descripcion de la transaccion.
    
    private String fechaString;
    private String fechaRegString;
    private String fechaTransString;
    private BigDecimal montoDivisa;
    private BigDecimal intereses;
    private String referencia;     
    
    
    private RespuestaDTO respuesta; //Manejo de Respuesta

    /**
     * Fecha cuando se registra en la tarjeta del cliente.
     *
     * @return Fecha cuando se registra en la tarjeta del cliente.
     */
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * Fecha cuando se registra en la tarjeta del cliente.
     *
     * @param fechaRegistro Fecha cuando se registra en la tarjeta del cliente.
     */
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * Fecha en la que se realizo la transaccion.
     *
     * @return Fecha en la que se realizo la transaccion.
     */
    public Date getFechaOperacion() {
        return fechaOperacion;
    }

    /**
     * Fecha en la que se realizo la transaccion.
     *
     * @param fechaOperacion Fecha en la que se realizo la transaccion.
     */
    public void setFechaOperacion(Date fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    /**
     * Monto de la transaccion.
     *
     * @return Monto de la transaccion.
     */
    public BigDecimal getMonto() {
        return monto;
    }

    /**
     * Monto de la transaccion.
     *
     * @param monto Monto de la transaccion.
     */
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    /**
     * Descripcion de la transaccion.
     *
     * @return Descripcion de la transaccion.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Descripcion de la transaccion.
     *
     * @param descripcion Descripcion de la transaccion.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getFechaString() {
        return fechaString;
    }

    public void setFechaString(String fechaString) {
        this.fechaString = fechaString;
    }

    public String getFechaRegString() {
        return fechaRegString;
    }

    public void setFechaRegString(String fechaRegString) {
        this.fechaRegString = fechaRegString;
    }

    public String getFechaTransString() {
        return fechaTransString;
    }

    public void setFechaTransString(String fechaTransString) {
        this.fechaTransString = fechaTransString;
    }

    public BigDecimal getMontoDivisa() {
        return montoDivisa;
    }

    public void setMontoDivisa(BigDecimal montoDivisa) {
        this.montoDivisa = montoDivisa;
    }

    public BigDecimal getIntereses() {
        return intereses;
    }

    public void setIntereses(BigDecimal intereses) {
        this.intereses = intereses;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    
}
