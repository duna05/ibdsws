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
 * Clase PosicionConsolidadaDTO
 * @author cesar.mujica
 */
public class PosicionConsolidadaDTO implements Serializable {

    private List<CuentaDTO> cuentasAhorro;              //listado de cuentas de ahorro
    private List<CuentaDTO> cuentasCorriente;           //listado de cuentas corriente
    private List<CuentaDTO> cuentasME;                  //listado de cuentas en moneda extrajera
    private List<PrestamoDTO> prestamos;                //listado de prestamos
    private List<DepositoPlazoDTO> depositosPlazo;      //listado de depositos a plazo
    private List<TarjetasCreditoDTO> tarjetasCredito;   //listado de TDC
    private List<FideicomisoDTO> fideicomisos;          //listado de fideicomisos de un cliente.
    private BigDecimal totalDispCuentasAhorro;          //total de saldo diponible de cuentas de ahorro    
    private BigDecimal totalDispCuentasCorriente;       //total de saldo diponible de cuentas corriente
    private BigDecimal totalDispCuentasME;              //total de saldo diponible de cuentas en ME
    private BigDecimal totalDispPrestamos;              //total de saldo diponible de prestamos
    private BigDecimal totalDispDepositosPlazo;         //total de saldo diponible de depositos a plazo  
    private BigDecimal totalDispTDC;                    //total de saldo diponible de TDC
    private BigDecimal saldoTotalTDC;                   //Saldo Total de todas las TDC
    private RespuestaDTO respuesta;                     //manejo de respuesta

    /**
     * retorna listado de cuentas de ahorro
     * @return List < CuentaDTO > cuentasAhorro listado de cuentas de ahorro
     */
    public List<CuentaDTO> getCuentasAhorro() {
        return cuentasAhorro;
    }

    /**
     * asigna listado de cuentas de ahorro
     * @param cuentasAhorro List < CuentaDTO > cuentasAhorro
     */
    public void setCuentasAhorro(List<CuentaDTO> cuentasAhorro) {
        this.cuentasAhorro = cuentasAhorro;
    }

    /**
     * retorna listado de cuentas corriente
     * @return List < CuentaDTO > cuentasCorriente listado de cuentas corriente
     */
    public List<CuentaDTO> getCuentasCorriente() {
        return cuentasCorriente;
    }

    /**
     * asigna listado de cuentas corriente
     * @param cuentasCorriente List < CuentaDTO > cuentasCorriente
     */
    public void setCuentasCorriente(List<CuentaDTO> cuentasCorriente) {
        this.cuentasCorriente = cuentasCorriente;
    }

    /**
     * retorna listado de cuentas en moneda extrajera
     * @return List < CuentaDTO >  cuentasME listado de cuentas en moneda extrajera
     */
    public List<CuentaDTO> getCuentasME() {
        return cuentasME;
    }

    /**
     * asigna listado de cuentas en moneda extrajera
     * @param cuentasME List < CuentaDTO > cuentasME
     */
    public void setCuentasME(List<CuentaDTO> cuentasME) {
        this.cuentasME = cuentasME;
    }

    /**
     * retorna listado de prestamos
     * @return List < PrestamoDTO > prestamos listado de prestamos
     */
    public List<PrestamoDTO> getPrestamos() {
        return prestamos;
    }

    /**
     * asigna listado de prestamos
     * @param prestamos List < PrestamoDTO > prestamos
     */
    public void setPrestamos(List<PrestamoDTO> prestamos) {
        this.prestamos = prestamos;
    }

    /**
     * retorna listado de depositos a Plazo
     * @return List < DepositoPlazoDTO > depositosPlazo listado de depositos a Plazo
     */
    public List<DepositoPlazoDTO> getDepositosPlazo() {
        return depositosPlazo;
    }

    /**
     * asigna listado de TDC
     * @return List < TarjetasCreditoDTO > tarjetasCredito
     */
    public List<TarjetasCreditoDTO> getTarjetasCredito() {
        return tarjetasCredito;
    }

    /**
     * retorna listado de TDC
     * @param tarjetasCredito List < TarjetasCreditoDTO > tarjetasCredito listado de TDC
     */
    public void setTarjetasCredito(List<TarjetasCreditoDTO> tarjetasCredito) {
        this.tarjetasCredito = tarjetasCredito;
    }

    /**
     * asigna listado de depositos a Plazo
     * @param depositosPlazo List < DepositoPlazoDTO > depositosPlazo
     */
    public void setDepositosPlazo(List<DepositoPlazoDTO> depositosPlazo) {
        this.depositosPlazo = depositosPlazo;
    }

    /**
     * retorna total de saldo diponible de cuentas de ahorro
     *
     * @return BigDecimal totalDispCuentasAhorro total de saldo diponible de
     * cuentas de ahorro
     */
    public BigDecimal getTotalDispCuentasAhorro() {
        return totalDispCuentasAhorro;
    }

    /**
     * asigna total de saldo diponible de cuentas de ahorro
     *
     * @param totalDispCuentasAhorro BigDecimal totalDispCuentasAhorro
     */
    public void setTotalDispCuentasAhorro(BigDecimal totalDispCuentasAhorro) {
        this.totalDispCuentasAhorro = totalDispCuentasAhorro;
    }

    /**
     * retorna total de saldo diponible de cuentas corriente
     *
     * @return BigDecimal totalDispCuentasCorriente total de saldo diponible de
     * cuentas corriente
     */
    public BigDecimal getTotalDispCuentasCorriente() {
        return totalDispCuentasCorriente;
    }

    /**
     * asigna total de saldo diponible de cuentas corriente
     * @param totalDispCuentasCorriente BigDecimal totalDispCuentasCorriente
     */
    public void setTotalDispCuentasCorriente(BigDecimal totalDispCuentasCorriente) {
        this.totalDispCuentasCorriente = totalDispCuentasCorriente;
    }

    /**
     * retorna total de saldo diponible de cuentas en ME
     *
     * @return BigDecimal totalDispCuentasME total de saldo diponible de cuentas
     * en ME
     */
    public BigDecimal getTotalDispCuentasME() {
        return totalDispCuentasME;
    }

    /**
     * asigna total de saldo diponible de cuentas en ME
     * @param totalDispCuentasME BigDecimal totalDispCuentasME
     */
    public void setTotalDispCuentasME(BigDecimal totalDispCuentasME) {
        this.totalDispCuentasME = totalDispCuentasME;
    }

    /**
     * retorna total de saldo diponible de prestamos
     *
     * @return BigDecimal totalDispPrestamos total de saldo diponible de
     * prestamos
     */
    public BigDecimal getTotalDispPrestamos() {
        return totalDispPrestamos;
    }

    /**
     * asigna total de saldo diponible de prestamos
     * @param totalDispPrestamos BigDecimal totalDispPrestamos
     */
    public void setTotalDispPrestamos(BigDecimal totalDispPrestamos) {
        this.totalDispPrestamos = totalDispPrestamos;
    }

    /**
     * retorna total de saldo diponible de depositos a plazo
     *
     * @return BigDecimal totalDispDepositosPlazo total de saldo diponible de
     * depositos a plazo
     */
    public BigDecimal getTotalDispDepositosPlazo() {
        return totalDispDepositosPlazo;
    }

    /**
     * asigna total de saldo diponible de depositos a plazo
     *
     * @param totalDispDepositosPlazo BigDecimal totalDispDepositosPlazo
     */
    public void setTotalDispDepositosPlazo(BigDecimal totalDispDepositosPlazo) {
        this.totalDispDepositosPlazo = totalDispDepositosPlazo;
    }

    /**
     * retorna total de saldo diponible de TDC
     *
     * @return BigDecimal totalDispTDC total de saldo diponible de TDC
     */
    public BigDecimal getTotalDispTDC() {
        return totalDispTDC;
    }

    /**
     * asigna total de saldo diponible de TDC
     * @param totalDispTDC BigDecimal totalDispTDC
     */
    public void setTotalDispTDC(BigDecimal totalDispTDC) {
        this.totalDispTDC = totalDispTDC;
    }

    public BigDecimal getSaldoTotalTDC() {
        return saldoTotalTDC;
    }

    public void setSaldoTotalTDC(BigDecimal saldoTotalTDC) {
        this.saldoTotalTDC = saldoTotalTDC;
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

    public List<FideicomisoDTO> getFideicomisos() {
        return fideicomisos;
    }

    public void setFideicomisos(List<FideicomisoDTO> fideicomisos) {
        this.fideicomisos = fideicomisos;
    }
    
    

}
