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
 * Clase PrestamoDTO
 * @author cesar.mujica
 */
public class PrestamoDTO implements Serializable {

    private String numeroPrestamo;	//Numero de prestamo de 20 di­gitos.
    private String codigoTipoProducto;  //Codigo del tipo de producto (ver Tipos de producto).
    private String nombreProducto;	//Nombre del producto (ver Tipos de producto).
    private String siglasTipoMoneda;	//Siglas nemonicas del tipo de moneda.
    private BigDecimal saldoPrestamo;	//Saldo incial del prestamo.
    private Date fechaLiquidacion;      //Fecha en la que se liquida el contrato.
    private Date fechaVencimiento;      //Fecha en la que se vence el prestamo.
    private Date fechaProximoPago;      //Fecha del proximo pago del prestamo.
    private BigDecimal montoAprobado;   //Monto aprobado del prestamo.
    private BigDecimal tasa;            //Valor de la tasa del prestamo.
    private BigDecimal montoCuota;      //Monto de las coutas a pagar.
    private BigDecimal saldoActual;     //Saldo actual del prestamo.
    private String codigoEstadoCartera; //Codigo Estado Cartera.
    private BigDecimal tasaTotal;       //Tasa Total.
    private String tipoTasa;        //Tipo de Tasa.
    private String cuotaIntereses;  //Intereses de la cuota.
    private String tipoLiquidacionMes;  //Tipo de Liquidacion del Mes.
    private String tipoLiquidacionAnio;  //Tipo de Liquidacion del Ano.
    private BigDecimal tipoFrecuenciaInteres;   //Tipo de frecuencia de interes.
    private BigDecimal frecuenciaInteres;       //Frecuencia de interes.
    private Date fechaPagoInteres;              //Fecha de paggo de interes.
    private Date fechaPrimerPagoInteres;        //Fecha de primer pago de interes.
    private BigDecimal diaPagoInteres;          //Dia de pago de interes.
    private Date fechaProximoPagoInteres;       //Fecha proximo pago de interes.
    private Date fechaCuotaAnteriorInteres;     //Fecha cuota anterior de interes.
    private String interesesVencidos;        //Intereses vencidos
    private BigDecimal claveProducto;            //Clave del producto. 
    private BigDecimal saldoCapital;             //Saldo de capital a pagar.
    private BigDecimal interes;                  //Saldo de intereses a pagar
    private BigDecimal mora;                     //Saldo de mora a pagar.
    private BigDecimal gastos;                   //Saldo de gastos a pagar.
    private BigDecimal seguro;                   //Saldo de seguro a pagar.
    RespuestaDTO respuesta;             //manejo de respuesta    
    private List<MovimientoPrestamoDTO> movimientos;
    private BigDecimal saldoExigible;
    private BigDecimal cantidadCuotasPagadas;

    /**
     * Codigo Estado Cartera.
     *
     * @return Codigo Estado Cartera.
     */
    public String getCodigoEstadoCartera() {
        return codigoEstadoCartera;
    }

    /**
     * Codigo Estado Cartera.
     *
     * @param codigoEstadoCartera Codigo Estado Cartera.
     */
    public void setCodigoEstadoCartera(String codigoEstadoCartera) {
        this.codigoEstadoCartera = codigoEstadoCartera;
    }

    /**
     * Tasa Total.
     *
     * @return Tasa Total.
     */
    public BigDecimal getTasaTotal() {
        return tasaTotal;
    }

    /**
     * Tasa Total.
     *
     * @param tasaTotal Tasa Total.
     */
    public void setTasaTotal(BigDecimal tasaTotal) {
        this.tasaTotal = tasaTotal;
    }

    /**
     * Tipo de Tasa.
     *
     * @return Tipo de Tasa.
     */
    public String getTipoTasa() {
        return tipoTasa;
    }

    /**
     * Tipo de Tasa.
     *
     * @param tipoTasa Tipo de Tasa.
     */
    public void setTipoTasa(String tipoTasa) {
        this.tipoTasa = tipoTasa;
    }

    /**
     * Intereses de la cuota.
     *
     * @return Intereses de la cuota.
     */
    public String getCuotaIntereses() {
        return cuotaIntereses;
    }

    /**
     * Intereses de la cuota.
     *
     * @param cuotaIntereses Intereses de la cuota.
     */
    public void setCuotaIntereses(String cuotaIntereses) {
        this.cuotaIntereses = cuotaIntereses;
    }

    /**
     * tipoLiquidacionMes
     *
     * @return tipoLiquidacionMes
     */
    public String getTipoLiquidacionMes() {
        return tipoLiquidacionMes;
    }

    /**
     * tipoLiquidacionMes
     *
     * @param tipoLiquidacionMes tipoLiquidacionMes
     */
    public void setTipoLiquidacionMes(String tipoLiquidacionMes) {
        this.tipoLiquidacionMes = tipoLiquidacionMes;
    }

    /**
     * Tipo de Liquidacion del Ano.
     *
     * @return Tipo de Liquidacion del Ano.
     */
    public String getTipoLiquidacionAnio() {
        return tipoLiquidacionAnio;
    }

    /**
     * Tipo de Liquidacion del Ano.
     *
     * @param tipoLiquidacionAnio Tipo de Liquidacion del Ano.
     */
    public void setTipoLiquidacionAnio(String tipoLiquidacionAnio) {
        this.tipoLiquidacionAnio = tipoLiquidacionAnio;
    }

    /**
     * Tipo de frecuencia de interes.
     *
     * @return Tipo de frecuencia de interes.
     */
    public BigDecimal getTipoFrecuenciaInteres() {
        return tipoFrecuenciaInteres;
    }

    /**
     * Tipo de frecuencia de interes.
     *
     * @param tipoFrecuenciaInteres Tipo de frecuencia de interes.
     */
    public void setTipoFrecuenciaInteres(BigDecimal tipoFrecuenciaInteres) {
        this.tipoFrecuenciaInteres = tipoFrecuenciaInteres;
    }

    /**
     * Frecuencia de interes.
     *
     * @return Frecuencia de interes.
     */
    public BigDecimal getFrecuenciaInteres() {
        return frecuenciaInteres;
    }

    /**
     * Frecuencia de interes.
     *
     * @param frecuenciaInteres Frecuencia de interes.
     */
    public void setFrecuenciaInteres(BigDecimal frecuenciaInteres) {
        this.frecuenciaInteres = frecuenciaInteres;
    }

    /**
     * Fecha de paggo de interes.
     *
     * @return Fecha de paggo de interes.
     */
    public Date getFechaPagoInteres() {
        return fechaPagoInteres;
    }

    /**
     * Fecha de paggo de interes.
     *
     * @param fechaPagoInteres Fecha de paggo de interes.
     */
    public void setFechaPagoInteres(Date fechaPagoInteres) {
        this.fechaPagoInteres = fechaPagoInteres;
    }

    /**
     * Fecha de primer pago de interes.
     *
     * @return Fecha de primer pago de interes.
     */
    public Date getFechaPrimerPagoInteres() {
        return fechaPrimerPagoInteres;
    }

    /**
     * Fecha de primer pago de interes.
     *
     * @param fechaPrimerPagoInteres Fecha de primer pago de interes.
     */
    public void setFechaPrimerPagoInteres(Date fechaPrimerPagoInteres) {
        this.fechaPrimerPagoInteres = fechaPrimerPagoInteres;
    }

    /**
     * Dia de pago de interes.
     *
     * @return Dia de pago de interes.
     */
    public BigDecimal getDiaPagoInteres() {
        return diaPagoInteres;
    }

    /**
     * Dia de pago de interes.
     *
     * @param diaPagoInteres Dia de pago de interes.
     */
    public void setDiaPagoInteres(BigDecimal diaPagoInteres) {
        this.diaPagoInteres = diaPagoInteres;
    }

    /**
     * Fecha proximo pago de interes.
     *
     * @return Fecha proximo pago de interes.
     */
    public Date getFechaProximoPagoInteres() {
        return fechaProximoPagoInteres;
    }

    /**
     * Fecha proximo pago de interes.
     * @param fechaProximoPagoInteres proximo pago de interes. 
     */
    public void setFechaProximoPagoInteres(Date fechaProximoPagoInteres) {
        this.fechaProximoPagoInteres = fechaProximoPagoInteres;
    }

    /**
     * Fecha cuota anterior de interes.
     *
     * @return Fecha cuota anterior de interes.
     */
    public Date getFechaCuotaAnteriorInteres() {
        return fechaCuotaAnteriorInteres;
    }

    /**
     * Fecha cuota anterior de interes.
     *
     * @param fechaCuotaAnteriorInteres Fecha cuota anterior de interes.
     */
    public void setFechaCuotaAnteriorInteres(Date fechaCuotaAnteriorInteres) {
        this.fechaCuotaAnteriorInteres = fechaCuotaAnteriorInteres;
    }

    /**
     * Intereses vencidos
     *
     * @return Intereses vencidos
     */
    public String getInteresesVencidos() {
        return interesesVencidos;
    }

    /**
     * Intereses vencidos
     *
     * @param interesesVencidos Intereses vencidos
     */
    public void setInteresesVencidos(String interesesVencidos) {
        this.interesesVencidos = interesesVencidos;
    }

    /**
     * Clave del producto.
     *
     * @return Clave del producto.
     */
    public BigDecimal getClaveProducto() {
        return claveProducto;
    }

    /**
     * Clave del producto.
     *
     * @param claveProducto Clave del producto.
     */
    public void setClaveProducto(BigDecimal claveProducto) {
        this.claveProducto = claveProducto;
    }

    /**
     * Saldo de capital a pagar.
     *
     * @return Saldo de capital a pagar.
     */
    public BigDecimal getSaldoCapital() {
        return saldoCapital;
    }

    /**
     * Saldo de capital a pagar.
     *
     * @param saldoCapital Saldo de capital a pagar.
     */
    public void setSaldoCapital(BigDecimal saldoCapital) {
        this.saldoCapital = saldoCapital;
    }

    /**
     * Saldo de intereses a pagar
     *
     * @return Saldo de intereses a pagar
     */
    public BigDecimal getInteres() {
        return interes;
    }

    /**
     * Saldo de intereses a pagar
     *
     * @param interes Saldo de intereses a pagar
     */
    public void setInteres(BigDecimal interes) {
        this.interes = interes;
    }

    /**
     * Saldo de mora a pagar.
     *
     * @return Saldo de mora a pagar.
     */
    public BigDecimal getMora() {
        return mora;
    }

    /**
     * Saldo de mora a pagar.
     *
     * @param mora Saldo de mora a pagar.
     */
    public void setMora(BigDecimal mora) {
        this.mora = mora;
    }

    /**
     * Saldo de gastos a pagar.
     *
     * @return Saldo de gastos a pagar.
     */
    public BigDecimal getGastos() {
        return gastos;
    }

    /**
     * Saldo de gastos a pagar.
     *
     * @param gastos Saldo de gastos a pagar.
     */
    public void setGastos(BigDecimal gastos) {
        this.gastos = gastos;
    }

    /**
     * Saldo de seguro a pagar.
     *
     * @return Saldo de seguro a pagar.
     */
    public BigDecimal getSeguro() {
        return seguro;
    }

    /**
     * Saldo de seguro a pagar.
     *
     * @param seguro Saldo de seguro a pagar.
     */
    public void setSeguro(BigDecimal seguro) {
        this.seguro = seguro;
    }

    /**
     * obtiene Fecha en la que se liquida el contrato.
     *
     * @return Date fechaLiquidacion Fecha en la que se liquida el contrato.
     */
    public Date getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    /**
     * ingresa Fecha en la que se liquida el contrato.
     *
     * @param fechaLiquidacion Fecha en la que se liquida el contrato.
     */
    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    /**
     * obtiene Fecha en la que se vence el prestamo.
     *
     * @return Date fechaVencimiento Fecha en la que se vence el prestamo.
     */
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     * ingresa Fecha en la que se vence el prestamo.
     *
     * @param fechaVencimiento Fecha en la que se vence el prestamo.
     */
    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     * obtiene Fecha del proximo pago del prestamo.
     *
     * @return Date fechaProximoPago Fecha del proximo pago del prestamo.
     */
    public Date getFechaProximoPago() {
        return fechaProximoPago;
    }

    /**
     * ingresa Fecha del proximo pago del prestamo.
     *
     * @param fechaProximoPago Fecha del proximo pago del prestamo.
     */
    public void setFechaProximoPago(Date fechaProximoPago) {
        this.fechaProximoPago = fechaProximoPago;
    }

    /**
     * obtiene Monto aprobado del prestamo.
     *
     * @return BigDecimal montoAprobado Monto aprobado del prestamo.
     */
    public BigDecimal getMontoAprobado() {
        return montoAprobado;
    }

    /**
     * ingresa Monto aprobado del prestamo.
     *
     * @param montoAprobado Monto aprobado del prestamo.
     */
    public void setMontoAprobado(BigDecimal montoAprobado) {
        this.montoAprobado = montoAprobado;
    }

    /**
     * obtiene Valor de la tasa del prestamo.
     *
     * @return BigDecimal Valor de la tasa del prestamo.
     */
    public BigDecimal getTasa() {
        return tasa;
    }

    /**
     * ingresa Valor de la tasa del prestamo.
     *
     * @param tasa Valor de la tasa del prestamo.
     */
    public void setTasa(BigDecimal tasa) {
        this.tasa = tasa;
    }

    /**
     * obtiene Monto de las coutas a pagar.
     *
     * @return BigDecimal Monto de las coutas a pagar.
     */
    public BigDecimal getMontoCuota() {
        return montoCuota;
    }

    /**
     * ingresa Monto de las coutas a pagar.
     *
     * @param montoCuota Monto de las coutas a pagar.
     */
    public void setMontoCuota(BigDecimal montoCuota) {
        this.montoCuota = montoCuota;
    }

    /**
     * Saldo actual del prestamo.
     *
     * @return BigDecimal Saldo actual del prestamo.
     */
    public BigDecimal getSaldoActual() {
        return saldoActual;
    }

    /**
     * ingresa Saldo actual del prestamo.
     *
     * @param saldoActual Saldo actual del prestamo.
     */
    public void setSaldoActual(BigDecimal saldoActual) {
        this.saldoActual = saldoActual;
    }

    /**
     * retorna Numero de prestamo de 20 di­gitos.
     *
     * @return String numeroPrestamo Numero de prestamo de 20 di­gitos.
     */
    public String getNumeroPrestamo() {
        return numeroPrestamo;
    }

    /**
     * asigna Numero de prestamo de 20 di­gitos.
     * @param numeroPrestamo numeroPrestamo
     */
    public void setNumeroPrestamo(String numeroPrestamo) {
        this.numeroPrestamo = numeroPrestamo;
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
     * @param codigoTipoProducto codigoTipoProducto
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
     * @param nombreProducto nombreProducto
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
     * @param siglasTipoMoneda siglasTipoMoneda
     */
    public void setSiglasTipoMoneda(String siglasTipoMoneda) {
        this.siglasTipoMoneda = siglasTipoMoneda;
    }

    /**
     * retorna Saldo incial del prestamo.
     *
     * @return String saldoPrestamo Saldo incial del prestamo.
     */
    public BigDecimal getSaldoPrestamo() {
        return saldoPrestamo;
    }

    /**
     * asigna Saldo incial del prestamo.
     * @param saldoPrestamo saldoPrestamo
     */
    public void setSaldoPrestamo(BigDecimal saldoPrestamo) {
        this.saldoPrestamo = saldoPrestamo;
    }

    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }

    /**
     * @return the movimientos
     */
    public List<MovimientoPrestamoDTO> getMovimientos() {
        return movimientos;
    }

    /**
     * @param movimientos the movimientos to set
     */
    public void setMovimientos(List<MovimientoPrestamoDTO> movimientos) {
        this.movimientos = movimientos;
    }

    /**
     * @return the saldoExigible
     */
    public BigDecimal getSaldoExigible() {
        return saldoExigible;
    }

    /**
     * @param saldoExigible the saldoExigible to set
     */
    public void setSaldoExigible(BigDecimal saldoExigible) {
        this.saldoExigible = saldoExigible;
    }

    public BigDecimal getCantidadCuotasPagadas() {
        return cantidadCuotasPagadas;
    }

    public void setCantidadCuotasPagadas(BigDecimal cantidadCuotasPagadas) {
        this.cantidadCuotasPagadas = cantidadCuotasPagadas;
    }        
}
