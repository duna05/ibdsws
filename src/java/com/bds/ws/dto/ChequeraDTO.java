/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Clase ChequeraDTO
 *
 * @author rony.rodriguez
 */
public class ChequeraDTO implements Serializable {

    private String activa;
    private String agenciaOrigen;
    private String cantidadCheques;
    private String chequeFinal;
    private String chequePago;
    private String agencia;
    private String aplicacion;
    private String empresa;
    private String subAplicacion;
    private String codigoUsuario;
    private Date fechaEmision;
    private Date fechaEntrega;
    private String numeroCuenta;
    private String numeroPrimerCheque;
    private String numeroUltimoCheque;
    private String secuenciaSolicitud;
    private String valorChequera;
    private List<ChequeDTO> listCheque;
    
    private RespuestaDTO respuesta;

    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }

    /**
     * 
     * @return Listado de cheques
     */
    public List<ChequeDTO> getListCheque() {
        return listCheque;
    }

    /**
     * 
     * @param listCheque Listado de cheques
     */
    public void setListCheque(List<ChequeDTO> listCheque) {
        this.listCheque = listCheque;
    }
    
    /**
     * 
     * @return Estatus de Chequera
     */
    public String getActiva() {
        return activa;
    }

    /**
     * 
     * @param activa Estatus de Chequera
     */
    public void setActiva(String activa) {
        this.activa = activa;
    }

    /**
     * 
     * @return Agencia Origen
     */
    public String getAgenciaOrigen() {
        return agenciaOrigen;
    }

    /**
     * 
     * @param agenciaOrigen Agencia Origen
     */
    public void setAgenciaOrigen(String agenciaOrigen) {
        this.agenciaOrigen = agenciaOrigen;
    }

    /**
     * 
     * @return Cantidad de Cheques
     */
    public String getCantidadCheques() {
        return cantidadCheques;
    }

    /**
     * 
     * @param cantidadCheques Cantidad de Cheques
     */
    public void setCantidadCheques(String cantidadCheques) {
        this.cantidadCheques = cantidadCheques;
    }

    /**
     * 
     * @return Numero de Cheque Final
     */
    public String getChequeFinal() {
        return chequeFinal;
    }

    /**
     * 
     * @param chequeFinal Numero de Cheque Final
     */
    public void setChequeFinal(String chequeFinal) {
        this.chequeFinal = chequeFinal;
    }

    /**
     * 
     * @return Numero de Cheque Pago
     */
    public String getChequePago() {
        return chequePago;
    }

    /**
     * 
     * @param chequePago Cheque Pago
     */
    public void setChequePago(String chequePago) {
        this.chequePago = chequePago;
    }
    
    /**
     * 
     * @return Agencia
     */
    public String getAgencia() {
        return agencia;
    }

    /**
     * 
     * @param agencia Agencia
     */
    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    /**
     * 
     * @return Aplicacion
     */
    public String getAplicacion() {
        return aplicacion;
    }

    /**
     * 
     * @param aplicacion Aplicacion
     */
    public void setAplicacion(String aplicacion) {
        this.aplicacion = aplicacion;
    }

    /**
     * 
     * @return Empresa
     */
    public String getEmpresa() {
        return empresa;
    }

    /**
     * 
     * @param empresa Empresa
     */
    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    /**
     * 
     * @return SubAplicacion
     */
    public String getSubAplicacion() {
        return subAplicacion;
    }

    /**
     * 
     * @param subAplicacion SubAplicacion
     */
    public void setSubAplicacion(String subAplicacion) {
        this.subAplicacion = subAplicacion;
    }

    /**
     * 
     * @return Codigo de Usuario
     */
    public String getCodigoUsuario() {
        return codigoUsuario;
    }

    /**
     * 
     * @param codigoUsuario Codigo de Usuario
     */
    public void setCodigoUsuario(String codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    /**
     * 
     * @return Fecha de Emision
     */
    public Date getFechaEmision() {
        return fechaEmision;
    }

    /**
     * 
     * @param fechaEmision Fecha de Emision
     */
    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    /**
     * 
     * @return Fecha de Entrega
     */
    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    /**
     * 
     * @param fechaEntrega Fecha de Entrega
     */
    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    /**
     * 
     * @return Numero de Cuenta
     */
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    /**
     * 
     * @param numeroCuenta Numero de Cuenta
     */
    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    /**
     * 
     * @return Numero del Primer Cheque
     */
    public String getNumeroPrimerCheque() {
        return numeroPrimerCheque;
    }

    /**
     * 
     * @param numeroPrimerCheque Numero del Primer Cheque
     */
    public void setNumeroPrimerCheque(String numeroPrimerCheque) {
        this.numeroPrimerCheque = numeroPrimerCheque;
    }

    /**
     * 
     * @return Numero del Ultimo Cheque
     */
    public String getNumeroUltimoCheque() {
        return numeroUltimoCheque;
    }

    /**
     * 
     * @param numeroUltimoCheque Numero del Ultimo Cheque
     */
    public void setNumeroUltimoCheque(String numeroUltimoCheque) {
        this.numeroUltimoCheque = numeroUltimoCheque;
    }

    /**
     * 
     * @return Numero de la Scuencia de la Solicitud
     */
    public String getSecuenciaSolicitud() {
        return secuenciaSolicitud;
    }

    /**
     * 
     * @param secuenciaSolicitud Numero de la Scuencia de la Solicitud
     */
    public void setSecuenciaSolicitud(String secuenciaSolicitud) {
        this.secuenciaSolicitud = secuenciaSolicitud;
    }

    /**
     * 
     * @return Valor de Chequera
     */
    public String getValorChequera() {
        return valorChequera;
    }

    /**
     * 
     * @param valorChequera Valor de Chequera
     */
    public void setValorChequera(String valorChequera) {
        this.valorChequera = valorChequera;
    }
    
    

}
