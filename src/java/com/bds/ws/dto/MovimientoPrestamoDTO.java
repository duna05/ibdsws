/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Clase MovimientoPrestamoDTO
 * @author rony.rodriguez
 */
public class MovimientoPrestamoDTO {

    private BigDecimal numeroCuota;     //Numero de cuotas a pagar.
    private Date fechaVencimiento;	//Fecha de vencimiento.
    private Date fechaDePago;           //Fecha para relizar los pagos del prestamo.
    private BigDecimal capitalPagado;	//Capital pagado.
    private BigDecimal interesesPagado;	//Intereses pagado
    private BigDecimal moraPagada;	//Mora pagada.
    private BigDecimal seguroPagado;	//Seguro pagado.
    private BigDecimal capital;         //Monto del capital
    private BigDecimal intereses;       //Intereses a pagar.
    private BigDecimal mora;            //Intereses a pagar por mora.
    private BigDecimal seguro;  	//Saldo de seguro a pagar.
    RespuestaDTO respuesta;             //manejo de la respuesta

    /**
     * Numero de cuotas a pagar.
     *
     * @return Numero de cuotas a pagar.
     */
    public BigDecimal getNumeroCuota() {
        return numeroCuota;
    }

    /**
     * Numero de cuotas a pagar.
     *
     * @param numeroCuota Numero de cuotas a pagar.
     */
    public void setNumeroCuota(BigDecimal numeroCuota) {
        this.numeroCuota = numeroCuota;
    }

    /**
     * Fecha de vencimiento.
     *
     * @return Fecha de vencimiento.
     */
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     * Fecha de vencimiento.
     *
     * @param fechaVencimiento Fecha de vencimiento.
     */
    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     * Fecha para relizar los pagos del prestamo.
     *
     * @return Fecha para relizar los pagos del prestamo.
     */
    public Date getFechaDePago() {
        return fechaDePago;
    }

    /**
     * Fecha para relizar los pagos del prestamo.
     *
     * @param fechaDePago Fecha para relizar los pagos del prestamo.
     */
    public void setFechaDePago(Date fechaDePago) {
        this.fechaDePago = fechaDePago;
    }

    /**
     * Capital pagado.
     *
     * @return Capital pagado.
     */
    public BigDecimal getCapitalPagado() {
        return capitalPagado;
    }

    /**
     * Capital pagado.
     *
     * @param capitalPagado Capital pagado.
     */
    public void setCapitalPagado(BigDecimal capitalPagado) {
        this.capitalPagado = capitalPagado;
    }

    /**
     * Intereses pagado
     *
     * @return Intereses pagado
     */
    public BigDecimal getInteresesPagado() {
        return interesesPagado;
    }

    /**
     * Intereses pagado
     *
     * @param interesesPagado Intereses pagado
     */
    public void setInteresesPagado(BigDecimal interesesPagado) {
        this.interesesPagado = interesesPagado;
    }

    /**
     * Mora pagada.
     *
     * @return Mora pagada.
     */
    public BigDecimal getMoraPagada() {
        return moraPagada;
    }

    /**
     * Mora pagada.
     *
     * @param moraPagada Mora pagada.
     */
    public void setMoraPagada(BigDecimal moraPagada) {
        this.moraPagada = moraPagada;
    }

    /**
     * Seguro pagado.
     *
     * @return Seguro pagado.
     */
    public BigDecimal getSeguroPagado() {
        return seguroPagado;
    }

    /**
     * Seguro pagado.
     *
     * @param seguroPagado Seguro pagado.
     */
    public void setSeguroPagado(BigDecimal seguroPagado) {
        this.seguroPagado = seguroPagado;
    }

    /**
     * Monto del capital
     *
     * @return Monto del capital
     */
    public BigDecimal getCapital() {
        return capital;
    }

    /**
     * Monto del capital
     *
     * @param capital Monto del capital
     */
    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    /**
     * Intereses a pagar.
     *
     * @return Intereses a pagar.
     */
    public BigDecimal getIntereses() {
        return intereses;
    }

    /**
     * Intereses a pagar.
     *
     * @param intereses Intereses a pagar.
     */
    public void setIntereses(BigDecimal intereses) {
        this.intereses = intereses;
    }

    /**
     * Intereses a pagar por mora.
     *
     * @return Intereses a pagar por mora.
     */
    public BigDecimal getMora() {
        return mora;
    }

    /**
     * Intereses a pagar por mora.
     *
     * @param mora Intereses a pagar por mora.
     */
    public void setMora(BigDecimal mora) {
        this.mora = mora;
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
