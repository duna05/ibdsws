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
 * Clase TarjetasCreditoDTO
 * @author cesar.mujica
 */
public class TarjetasCreditoDTO implements Serializable {

    private String numeroTarjeta;	//Numero de Tarjeta de 20 digitos.
    private String codigoTipoProducto;	//Codigo del tipo de producto (ver Tipos de producto).
    private String nombreProducto;	//Nombre del producto (ver Tipos de producto).
    private String siglasTipoMoneda;	//Siglas nemonicas del tipo de moneda.
    private BigDecimal saldoDisponible;	//Saldo disponible de la cuenta (saldo total en linea).
    private BigDecimal saldoDiferido;	//Monto de los valores que se ecuentran en reserva y son afectados durante el dia antes del proceso de fin de dia.
    private BigDecimal saldoBloqueado;	//Saldo embargado (bloqueado) en linea de la cuenta.
    private BigDecimal saldoTotal;	//Saldo total de la cuenta.

    private String codigoTdc;         // codigo de 8 digitos de la TDC.
    private Date fechaCliente;      // Fecha desde la cual el cliente es usuario (Visa/Mater).
    private Date fechaExpiracion;   // fecha de venciminto de la tarjeta.
    private String codigoValidacion;  // Ultimos tres digitos de la parte tresera de la TDC.
    private String nombreCliente;     // Nombre del usuario de la TDC.
    private String estatus;           // Estatus de la tdc.
    private List<MovimientoTDCDTO> movimientos; //lista de movimientos de tarjetas

    //Detalle Encabezado TDC    
    private BigDecimal saldoAlCorte;	//Saldo al corte de la TDC.
    private BigDecimal montoDisponible;	//Monto disponible.
    private BigDecimal limiteCredito;//límite de crédito
    private BigDecimal pagoMinimo; //Pago minimo de la TDC.
    private Date fechaLimite; 	//Fecha limite de pago    
    private Date fechaCorte; //Fecha de corte.

    private RespuestaDTO respuesta;     //manejo de respuestas

    /**
     * retorna Numero de cuenta de 20 digitos.
     *
     * @return String numeroTarjeta Numero de cuenta de 20 digitos.
     */
    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    /**
     * asigna Numero de cuenta de 20 digitos.
     *
     * @param numeroTarjeta Numero de cuenta de 20 digitos.
     */
    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
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
     *
     * @param codigoTipoProducto Codigo del tipo de producto (ver Tipos de producto).
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
     *
     * @param nombreProducto Nombre del producto (ver Tipos de producto).
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
     *
     * @param siglasTipoMoneda Siglas nemonicas del tipo de moneda.
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
     *
     * @param saldoDisponible Saldo disponible de la cuenta (saldo total en linea).
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
     * @param saldoDiferido saldo diferido
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
     *
     * @param saldoBloqueado Saldo embargado (bloqueado)
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
     *
     * @param saldoTotal Saldo total de la cuenta.
     */
    public void setSaldoTotal(BigDecimal saldoTotal) {
        this.saldoTotal = saldoTotal;
    }

    /**
     * obtiene el identificdor unico del codigo de 8 digitos de la TDC.
     *
     * @return String identificdor unico del codigo de 8 digitos de la TDC.
     */
    public String getCodigoTdc() {
        return codigoTdc;
    }

    /**
     * ingresa Identificdor unico del codigo de 8 digitos de la TDC.
     *
     * @param codigoTdc codigo de 8 digitos de la TDC.
     */
    public void setCodigoTdc(String codigoTdc) {
        this.codigoTdc = codigoTdc;
    }

    /**
     * obtiene la fecha desde la cual el cliente es usuario (Visa/Mater).
     *
     * @return Date fecha desde la cual el cliente es usuario (Visa/Mater).
     */
    public Date getFechaCliente() {
        return fechaCliente;
    }

    /**
     * ingresa la fecha desde la cual el cliente es usuario (Visa/Mater).
     *
     * @param fechaCliente Fecha desde la cual el cliente es usuario
     * (Visa/Mater).
     */
    public void setFechaCliente(Date fechaCliente) {
        this.fechaCliente = fechaCliente;
    }

    /**
     * obtiene la fecha de venciminto de la tarjeta.
     *
     * @return Date fecha de venciminto de la tarjeta.
     */
    public Date getFechaExpiracion() {
        return fechaExpiracion;
    }

    /**
     * ingresa la fecha de venciminto de la tarjeta.
     *
     * @param fechaExpiracion fecha de venciminto de la tarjeta.
     */
    public void setFechaExpiracion(Date fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    /**
     * obtiene los Ultimos tres digitos de la parte tresera de la TDC.
     *
     * @return String Ultimos tres digitos de la parte tresera de la TDC.
     */
    public String getCodigoValidacion() {
        return codigoValidacion;
    }

    /**
     * ingresa los ultimos tres digitos de la parte tresera de la TDC.
     *
     * @param codigoValidacion Ultimos tres digitos de la parte tresera de la
     * TDC.
     */
    public void setCodigoValidacion(String codigoValidacion) {
        this.codigoValidacion = codigoValidacion;
    }

    /**
     * obtiene el nombre del usuario de la TDC.
     *
     * @return String nombre del usuario de la TDC.
     */
    public String getNombreCliente() {
        return nombreCliente;
    }

    /**
     * ingresa el nombre del usuario de la TDC.
     *
     * @param nombreCliente Nombre del usuario de la TDC.
     */
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    /**
     * obtiene el estatus de la tdc.
     *
     * @return String Estatus de la tdc.
     */
    public String isEstatus() {
        return estatus;
    }

    /**
     * ingresa el estaus de la tdc.
     *
     * @param estatus Estaus de la tdc.
     */
    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    /**
     * lista de movimientos de tarjetas
     *
     * @return lista de movimientos de tarjetas
     */
    public List<MovimientoTDCDTO> getMovimientos() {
        return movimientos;
    }

    /**
     * lista de movimientos de tarjetas
     *
     * @param movimientos lista de movimientos de tarjetas
     */
    public void setMovimientos(List<MovimientoTDCDTO> movimientos) {
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
     * Saldo al corte de la TDC.
     *
     * @return saldo Al Corte
     */
    public BigDecimal getSaldoAlCorte() {
        return saldoAlCorte;
    }

    /**
     * Setea el Saldo al corte de la TDC.
     *
     * @param saldoAlCorte saldo Al Corte
     */
    public void setSaldoAlCorte(BigDecimal saldoAlCorte) {
        this.saldoAlCorte = saldoAlCorte;
    }

    /**
     * Retorna el Monto disponible.
     *
     * @return monto Disponible
     */
    public BigDecimal getMontoDisponible() {
        return montoDisponible;
    }

    /**
     * Setea el Monto disponible.
     *
     * @param montoDisponible monto Disponible
     */
    public void setMontoDisponible(BigDecimal montoDisponible) {
        this.montoDisponible = montoDisponible;
    }

    /**
     *
     * Pago minimo de la TDC.
     *
     * @return the pago Minimo
     */
    public BigDecimal getPagoMinimo() {
        return pagoMinimo;
    }

    /**
     * Setear el pago minimo
     *
     * @param pagoMinimo pago Minimo
     */
    public void setPagoMinimo(BigDecimal pagoMinimo) {
        this.pagoMinimo = pagoMinimo;
    }

    /**
     * Retorna Fecha Limite
     *
     * @return the fecha Limite
     */
    public Date getFechaLimite() {
        return fechaLimite;
    }

    /**
     * Setea la Fecha Limite
     *
     * @param fechaLimite the fechaLimite to set
     */
    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    /**
     * Retorna la fecha de corte
     *
     * @return the fecha Corte
     */
    public Date getFechaCorte() {
        return fechaCorte;
    }

    /**
     * Setea la fecha la fecha de corte
     *
     * @param fechaCorte fecha Corte
     */
    public void setFechaCorte(Date fechaCorte) {
        this.fechaCorte = fechaCorte;
    }

    public BigDecimal getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(BigDecimal limiteCredito) {
        this.limiteCredito = limiteCredito;
    }   
    
}
