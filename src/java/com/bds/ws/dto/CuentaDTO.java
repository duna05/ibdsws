/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Clase CuentaDTO
 * @author cesar.mujica
 */
public class CuentaDTO implements Serializable {

    private String numeroCuenta;                    //Numero de cuenta de 20 digitos.
    private String numeroCuenta10Digitos;           //Numero de cuenta de 10 digitos.
    private String codigoTipoProducto;              //Codigo del tipo de producto (ver Tipos de producto).
    private String nombreProducto;                  //Nombre del producto (ver Tipos de producto).
    private String siglasTipoMoneda;                //Siglas nemonicas del tipo de moneda.
    private BigDecimal saldoDisponible;             //Saldo disponible de la cuenta (saldo total en linea).
    private BigDecimal saldoDiferido;               //Monto de los valores que se ecuentran en reserva y son afectados durante el dia antes del proceso de fin de dia.
    private BigDecimal saldoBloqueado;              //Saldo embargado (bloqueado) en linea de la cuenta.
    private BigDecimal saldoTotal;                  //Saldo total de la cuenta.
    private String codAgencia;                      //codigo de la agencia donde se aperturo la cuenta
    private List<MovimientoCuentaDTO> movimientos;  //movimientos parauna cuenta
    private List<MovimientoCuentaDTO> listSaldoDiferido;  //lista de movimientos de saldo diferido de una cuenta
    private List<MovimientoCuentaDTO> listSaldoBloqueado;  //lista de movimientos de saldo bloqueado de una cuenta
    private List<ChequeraDTO> chequeras;
    private String codAplicacion; //Codigo de la cuenta.
    private RespuestaDTO respuesta;                 //manejo de respuestas
    private CabeceraEstadoCtaDTO cabecera;    
    
    /**
     * retorna Numero de cuenta de 20 digitos.
     *
     * @return String numeroCuenta Numero de cuenta de 20 digitos.
     */
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    /**
     * asigna Numero de cuenta de 20 digitos.
     * @param numeroCuenta String numeroCuenta
     */
    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
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
     * @param codigoTipoProducto String 
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
     * retorna Monto de los valores que se ecuentran en reserva y son afectados
     * durante el dia antes del proceso de fin de dia.
     *
     * @return BigDecimal saldoDiferido Monto de los valores que se ecuentran en
     * reserva y son afectados durante el dia antes del proceso de fin de dia.
     */
    public BigDecimal getSaldoDiferido() {
        return saldoDiferido;
    }

    /**
     * asigna Monto de los valores que se ecuentran en reserva y son afectados
     * durante el dia antes del proceso de fin de dia.
     *
     * @param saldoDiferido BigDecimal saldoDiferido
     */
    public void setSaldoDiferido(BigDecimal saldoDiferido) {
        this.saldoDiferido = saldoDiferido;
    }

    /**
     * retorna Saldo embargado (bloqueado) en linea de la cuenta.
     *
     * @return BigDecimal saldoBloqueado Saldo embargado (bloqueado) en linea de
     * la cuenta.
     */
    public BigDecimal getSaldoBloqueado() {
        return saldoBloqueado;
    }

    /**
     * asigna Saldo embargado (bloqueado) en linea de la cuenta.
     * @param saldoBloqueado BigDecimal saldoBloqueado
     */
    public void setSaldoBloqueado(BigDecimal saldoBloqueado) {
        this.saldoBloqueado = saldoBloqueado;
    }

    /**
     * retorna Saldo total de la cuenta.
     *
     * @return BigDecimal saldoTotal Saldo total de la cuenta.
     */
    public BigDecimal getSaldoTotal() {
        return saldoTotal;
    }

    /**
     * asigna Saldo total de la cuenta.
     * @param saldoTotal BigDecimal saldoTotal
     */
    public void setSaldoTotal(BigDecimal saldoTotal) {
        this.saldoTotal = saldoTotal;
    }

    /**
     * retorna movimientos de cuenta
     * @return List  < MovimientoCuentaDTO > movimientos  movimientos de cuenta
     */
    public List<MovimientoCuentaDTO> getMovimientos() {
        return movimientos;
    }

    /**
     * asigna movimientos de cuenta
     *
     * @param movimientos List < MovimientoCuentaDTO > movimientos
     */
    public void setMovimientos(List<MovimientoCuentaDTO> movimientos) {
        this.movimientos = movimientos;
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

    /**
     * @return the chequeras
     */
    public List<ChequeraDTO> getChequeras() {
        return chequeras;
    }

    /**
     * @param chequeras the chequeras to set
     */
    public void setChequeras(List<ChequeraDTO> chequeras) {
        this.chequeras = chequeras;
    }

    /**
     * @return the codAplicacion
     */
    public String getCodAplicacion() {
        return codAplicacion;
    }

    /**
     * @param codAplicacion the codAplicacion to set
     */
    public void setCodAplicacion(String codAplicacion) {
        this.codAplicacion = codAplicacion;
    }

    public List<MovimientoCuentaDTO> getListSaldoDiferido() {
        return listSaldoDiferido;
    }

    public void setListSaldoDiferido(List<MovimientoCuentaDTO> listSaldoDiferido) {
        this.listSaldoDiferido = listSaldoDiferido;
    }

    public List<MovimientoCuentaDTO> getListSaldoBloqueado() {
        return listSaldoBloqueado;
    }

    public void setListSaldoBloqueado(List<MovimientoCuentaDTO> listSaldoBloqueado) {
        this.listSaldoBloqueado = listSaldoBloqueado;
    }

    public String getCodAgencia() {
        return codAgencia;
    }

    public void setCodAgencia(String codAgencia) {
        this.codAgencia = codAgencia;
    }

    public CabeceraEstadoCtaDTO getCabecera() {
        return cabecera;
    }

    public void setCabecera(CabeceraEstadoCtaDTO cabecera) {
        this.cabecera = cabecera;
    }

    public String getNumeroCuenta10Digitos() {
        return numeroCuenta10Digitos;
    }

    public void setNumeroCuenta10Digitos(String numeroCuenta10Digitos) {
        this.numeroCuenta10Digitos = numeroCuenta10Digitos;
    }
}
