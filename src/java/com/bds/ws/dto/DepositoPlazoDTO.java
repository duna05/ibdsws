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
 * Clase DepositoPlazoDTO
 * @author cesar.mujica
 */
public class DepositoPlazoDTO implements Serializable {

    private String numeroDeposito;	//Numero de deposito de 20 digitos.
    private String codigoTipoProducto;	//Codigo del tipo de producto (ver Tipos de producto).
    private String nombreProducto;	//Nombre del producto (ver Tipos de producto).
    private String siglasTipoMoneda;	//Siglas nemonicas del tipo de moneda.
    private BigDecimal saldoDisponible;	//Saldo disponible de la cuenta (saldo total en linea).
    private BigDecimal plazo;           //Numero de dias en los que el deposito estara vigente.
    private Date fechaVigencia;         //Fecha de inicio del deposito a plazo a partir de la cual el plazo es considerado.
    private Date fechaVencimiento;      //Fecha de vencimiento del deposito a plazo.
    private Long tasa;                   //Valor total de la tasa a pagar sobre el deposito a plazo.
    private RespuestaDTO respuesta;     //manejo de respuestas

    /**
     * retorna Numero de deposito de 20 digitos.
     *
     * @return String numeroDeposito Numero de cuenta de 20 digitos.
     */
    public String getNumeroDeposito() {
        return numeroDeposito;
    }

    /**
     * asigna Numero de deposito de 20 digitos.
     * @param numeroDeposito String numeroDeposito
     */
    public void setNumeroDeposito(String numeroDeposito) {
        this.numeroDeposito = numeroDeposito;
    }

    /**
     * retorna Codigo del tipo de producto (ver Tipos de producto).
     *
     * @return String codigoTipoProducto Codigo del tipo de producto (ver Tipos
     * de producto).
     */
    public String getCodigoTipoProducto() {
        return codigoTipoProducto;
    }

    /**
     * asigna Codigo del tipo de producto (ver Tipos de producto).
     * @param codigoTipoProducto String codigoCliente
     */
    public void setCodigoTipoProducto(String codigoTipoProducto) {
        this.codigoTipoProducto = codigoTipoProducto;
    }

    /**
     * retorna Nombre del producto (ver Tipos de producto).
     *
     * @return String nombreProducto Nombre del producto (ver Tipos de
     * producto).
     */
    public String getNombreProducto() {
        return nombreProducto;
    }

    /**
     * asigna Nombre del producto (ver Tipos de producto).
     * @param nombreProducto String nombreProducto
     */
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    /**
     * retorna Siglas nemonicas del tipo de moneda.
     *
     * @return String siglasTipoMoneda Siglas nemonicas del tipo de moneda.
     */
    public String getSiglasTipoMoneda() {
        return siglasTipoMoneda;
    }

    /**
     * asigna Siglas nemonicas del tipo de moneda.
     * @param siglasTipoMoneda String siglasTipoMoneda
     */
    public void setSiglasTipoMoneda(String siglasTipoMoneda) {
        this.siglasTipoMoneda = siglasTipoMoneda;
    }

    /**
     * retorna Saldo disponible de la cuenta (saldo total en linea).
     *
     * @return BigDecimal saldoDisponible Saldo disponible de la cuenta (saldo
     * total en linea).
     */
    public BigDecimal getSaldoDisponible() {
        return saldoDisponible;
    }

    /**
     * asigna Saldo disponible de la cuenta (saldo total en linea).
     * @param saldoDisponible BigDecimal saldoDisponible
     */
    public void setSaldoDisponible(BigDecimal saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    /**
     * retorna Numero de dias en los que el deposito estara vigente.
     *
     * @return String plazo Numero de dias en los que el deposito estara
     * vigente.
     */
    public BigDecimal getPlazo() {
        return plazo;
    }

    /**
     * asigna Siglas Numero de dias en los que el deposito estara vigente.
     * @param plazo String plazo
     */
    public void setPlazo(BigDecimal plazo) {
        this.plazo = plazo;
    }

    /**
     * retorna Fecha de inicio del deposito a plazo a partir de la cual el plazo
     * es considerado.
     *
     * @return Date fechaVigencia Fecha de inicio del deposito a plazo a partir
     * de la cual el plazo es considerado.
     */
    public Date getFechaVigencia() {
        return fechaVigencia;
    }

    /**
     * asigna Fecha de inicio del deposito a plazo a partir de la cual el plazo
     * es considerado.
     *
     * @param fechaVigencia Date fechaVigencia
     */
    public void setFechaVigencia(Date fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }

    /**
     * retorna Fecha de vencimiento del deposito a plazo.
     *
     * @return Date fechaVencimiento fecha de vencimiento del deposito a plazo.
     */
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     * asigna Fecha de vencimiento del deposito a plazo.
     * @param fechaVencimiento Date fechaVencimiento
     */
    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     * retorna Valor total de la tasa a pagar sobre el deposito a plazo.
     *
     * @return Long tasa Valor total de la tasa a pagar sobre el deposito a
     * plazo.
     */
    public Long getTasa() {
        return tasa;
    }

    /**
     * asigna Siglas Valor total de la tasa a pagar sobre el deposito a plazo.
     * @param tasa Long tasa
     */
    public void setTasa(Long tasa) {
        this.tasa = tasa;
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
