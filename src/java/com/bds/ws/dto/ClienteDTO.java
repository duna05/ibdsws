/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase ClienteDTO
 * @author cesar.mujica
 */
public class ClienteDTO implements Serializable {

    private String codigoCliente;                       //Codigo del cliente.
    private String identificacion;                      //Identificacion del cliente (CI, RIF, etc).
    private String nombres;                              //Nombres del cliente.
    private String apellidos;                           //Apellidos del cliente.
    private String emailCorreo;                         //Correo electronico del cliente.
    private String codigoTlfCell;                     //Codigo de area del numero de celular del cliente.
    private String telefonoCell;                       //Numero de celular del cliente.
    private String enviarSms;                          //Indica si el OTP generado al cliente se debe enviar por SMS.
    private String enviarCorreo;                       //Indica si el OTP generado al cliente se debe enviar por email.
    private BigDecimal limiteInternas;                  //limite de monto de operaciones propias en DELSUR
    private BigDecimal limiteExternas;                  //limite de monto de operaciones propias en otros bancos
    private BigDecimal limiteInternasTerc;              ///limite de monto de operaciones terceros en DELSUR
    private BigDecimal limiteExternasTerc;              //limite de monto de operaciones terceros en otros bancos
    private List<CuentaDTO> cuentasAhorro;              //Indica el listado de cuentas de ahorro de un cliente.
    private List<CuentaDTO> cuentasCorriente;           //Indica el listado de cuentas de corriente de un cliente.
    private List<CuentaDTO> cuentasMonedaExtranjera;    //Indica el listado de cuentas de moneda extranjera de un cliente.
    private List<TarjetasCreditoDTO> tdcAsociadasCliente;      //Indica el listado de tdc asociadas al cliente.
    private List<TarjetaDebitoDTO> tddAsociadasCliente;      //Indica el listado  de tdd asociadas al cliente.
    private List<PrestamoDTO> prestamosCliente;         //Indica el listado de prestamos asociadas al cliente.
    private List<FideicomisoDTO> fideicomisos;          //Indica el listado de fideicomisos de un cliente.
    //todos los DTO deben tener este objeto
    private RespuestaDTO respuesta;

    public ClienteDTO() {
    }

    /**
     * obtiene Indica el listado de cuentas de ahorro de un cliente.
     *
     * @return List<CuentaDTO> Indica el listado de cuentas de ahorro de un
     * cliente.
     */
    public List<CuentaDTO> getCuentasAhorro() {
        return cuentasAhorro;
    }

    /**
     * ingresa Indica el listado de cuentas de ahorro de un cliente.
     *
     * @param cuentasAhorro Indica el listado de cuentas de ahorro de un
     * cliente.
     */
    public void setCuentasAhorro(List<CuentaDTO> cuentasAhorro) {
        this.cuentasAhorro = cuentasAhorro;
    }

    /**
     * obtiene Indica el listado de cuentas de corriente de un cliente.
     *
     * @return List<CuentaDTO> Indica el listado de cuentas de corriente de un
     * cliente.
     */
    public List<CuentaDTO> getCuentasCorriente() {
        return cuentasCorriente;
    }

    /**
     * ingresa Indica el listado de cuentas de corriente de un cliente.
     *
     * @param cuentasCorriente Indica el listado de cuentas de corriente de un
     * cliente.
     */
    public void setCuentasCorriente(List<CuentaDTO> cuentasCorriente) {
        this.cuentasCorriente = cuentasCorriente;
    }

    /**
     * obtiene Indica el listado de cuentas de moneda extranjera de un cliente.
     *
     * @return List<CuentaDTO> Indica el listado de cuentas de moneda extranjera
     * de un cliente.
     */
    public List<CuentaDTO> getCuentasMonedaExtranjera() {
        return cuentasMonedaExtranjera;
    }

    /**
     * ingresa Indica el listado de cuentas de moneda extranjera de un cliente.
     *
     * @param cuentasMonedaExtranjera Indica el listado de cuentas de moneda
     * extranjera de un cliente.
     */
    public void setCuentasMonedaExtranjera(List<CuentaDTO> cuentasMonedaExtranjera) {
        this.cuentasMonedaExtranjera = cuentasMonedaExtranjera;
    }

    /**
     * obtiene Indica el listado de cuentas corriente y de ahorros unidas.
     *
     * @return List<CuentaDTO> Indica el listado de cuentas corriente y de
     * ahorros unidas.
     */
    public List<CuentaDTO> getCuentasAhorroCorriente() {
        List<CuentaDTO> cuentasTotal = new ArrayList<>();
        if (cuentasAhorro != null && !cuentasAhorro.isEmpty()) {
            cuentasTotal.addAll(cuentasAhorro);
        }
        if (cuentasCorriente != null && !cuentasCorriente.isEmpty()) {
            cuentasTotal.addAll(cuentasCorriente);
        }
        return cuentasTotal;
    }

    /**
     * retorna Codigo del cliente.
     *
     * @return String codigoCliente Codigo del cliente.
     */
    public String getCodigoCliente() {
        return codigoCliente;
    }

    /**
     * asigna Codigo del cliente.
     * @param codigoCliente  String
     */
    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    /**
     * retorna Identificacion del cliente (CI, RIF, etc).
     *
     * @return String identificacion Identificacion del cliente (CI, RIF, etc).
     */
    public String getIdentificacion() {
        return identificacion;
    }

    /**
     * asigna Identificacion del cliente (CI, RIF, etc).
     * @param identificacion String
     */
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    /**
     * retorna Nombres del cliente.
     *
     * @return String nombres Nombres del cliente.
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * asigna Nombres del cliente.
     * @param nombres String
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * retorna Apellidos del cliente.
     *
     * @return String Apellidos del cliente.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * asigna Apellidos del cliente.
     * @param apellidos String
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * retorna Correo electronico del cliente.
     *
     * @return String emailCorreo Correo electronico del cliente.
     */
    public String getEmailCorreo() {
        return emailCorreo;
    }

    /**
     * asigna Correo electronico del cliente.
     * @param emailCorreo String
     */
    public void setEmailCorreo(String emailCorreo) {
        this.emailCorreo = emailCorreo;
    }

    /**
     * retorna Codigo de area del numero de celular del cliente.
     *
     * @return String codigoTlfCell Codigo de area del numero de celular del
     * cliente.
     */
    public String getCodigoTlfCell() {
        return codigoTlfCell;
    }

    /**
     * asigna Codigo de area del numero de celular del cliente.
     * @param codigoTlfCell String
     */
    public void setCodigoTlfCell(String codigoTlfCell) {
        this.codigoTlfCell = codigoTlfCell;
    }

    /**
     * retorna Numero de celular del cliente.
     *
     * @return String telefonoCell Numero de celular del cliente.
     */
    public String getTelefonoCell() {
        return telefonoCell;
    }

    /**
     * asigna Numero de celular del cliente.
     * @param telefonoCell String
     */
    public void setTelefonoCell(String telefonoCell) {
        this.telefonoCell = telefonoCell;
    }

    /**
     * retorna Indica si el OTP generado al cliente se debe enviar por SMS.
     *
     * @return String enviarSms Indica si el OTP generado al cliente se debe
     * enviar por SMS.
     */
    public String getEnviarSms() {
        return enviarSms;
    }

    /**
     * asigna Indica si el OTP generado al cliente se debe enviar por SMS.
     * @param enviarSms String
     */
    public void setEnviarSms(String enviarSms) {
        this.enviarSms = enviarSms;
    }

    /**
     * retorna Indica si el OTP generado al cliente se debe enviar por email.
     *
     * @return String enviarCorreo Indica si el OTP generado al cliente se debe
     * enviar por email.
     */
    public String getEnviarCorreo() {
        return enviarCorreo;
    }

    /**
     * asigna Indica si el OTP generado al cliente se debe enviar por email.
     *
     * @param enviarCorreo
     */
    public void setEnviarCorreo(String enviarCorreo) {
        this.enviarCorreo = enviarCorreo;
    }

    /**
     * obtiene el listado de tdc asociadas al cliente.
     * @return List < TarjetasCreditoDTO > Indica el listado de tdc asociadas al cliente.
     */
    public List<TarjetasCreditoDTO> getTdcAsociadasCliente() {
        return tdcAsociadasCliente;
    }

    /**
     * ingresa Indica el listado de tdc asociadas al cliente.
     *
     * @param tdcAsociadasCliente listado de tdc asociadas al cliente.
     */
    public void setTdcAsociadasCliente(List<TarjetasCreditoDTO> tdcAsociadasCliente) {
        this.tdcAsociadasCliente = tdcAsociadasCliente;
    }

    /**
     * obtiene el listado de prestamos asociadas al cliente.
     * @return List < PrestamoDTO > listado de prestamos asociadas al cliente.
     */
    public List<PrestamoDTO> getPrestamosCliente() {
        return prestamosCliente;
    }

    /**
     * ingresa Indica el listado de prestamos asociadas al cliente.
     * @param prestamosCliente List  < PrestamoDTO > prestamosCliente listado de prestamos asociadas al cliente.
     */
    public void setPrestamosCliente(List<PrestamoDTO> prestamosCliente) {
        this.prestamosCliente = prestamosCliente;
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
     * @return the tddAsociadasCliente
     */
    public List<TarjetaDebitoDTO> getTddAsociadasCliente() {
        return tddAsociadasCliente;
    }

    /**
     * @param tddAsociadasCliente the tddAsociadasCliente to set
     */
    public void setTddAsociadasCliente(List<TarjetaDebitoDTO> tddAsociadasCliente) {
        this.tddAsociadasCliente = tddAsociadasCliente;
    }
    
    /**
     * retorna el limite de monto de operaciones propias en DELSUR
     * @return limite de monto de operaciones propias en DELSUR
     */    
    public BigDecimal getLimiteInternas() {
        return limiteInternas;
    }

    /**
     * asigna el limite de monto de operaciones propias en DELSUR
     * @param limiteInternas 
     */
    public void setLimiteInternas(BigDecimal limiteInternas) {
        this.limiteInternas = 
                limiteInternas;
    }

    /**
     * retorna el limite de monto de operaciones propias en otros bancos
     * @return limite de monto de operaciones propias en otros bancos
     */
    public BigDecimal getLimiteExternas() {
        return limiteExternas;
    }

    /**
     * asigna el limite de monto de operaciones propias en otros bancos
     * @param limiteExternas limite de monto de operaciones propias en otros bancos
     */
    public void setLimiteExternas(BigDecimal limiteExternas) {
        this.limiteExternas = limiteExternas;
    }

    /**
     * retorna el limite de monto de operaciones terceros en DELSUR
     * @return limite de monto de operaciones terceros en DELSUR
     */
    public BigDecimal getLimiteInternasTerc() {
        return limiteInternasTerc;
    }

    /**
     * asigna el limite de monto de operaciones terceros en DELSUR
     * @param limiteInternasTerc limite de monto de operaciones terceros en DELSUR
     */
    public void setLimiteInternasTerc(BigDecimal limiteInternasTerc) {
        this.limiteInternasTerc = limiteInternasTerc;
    }

    /**
     * retorna el limite de monto de operaciones terceros en otros bancos
     * @return limite de monto de operaciones terceros en otros bancos
     */
    public BigDecimal getLimiteExternasTerc() {
        return limiteExternasTerc;
    }

    /**
     * asigna el limite de monto de operaciones terceros en otros bancos
     * @param limiteExternasTerc limite de monto de operaciones terceros en otros bancos
     */
    public void setLimiteExternasTerc(BigDecimal limiteExternasTerc) {
        this.limiteExternasTerc = limiteExternasTerc;
    }

    public List<FideicomisoDTO> getFideicomisos() {
        return fideicomisos;
    }

    public void setFideicomisos(List<FideicomisoDTO> fideicomisos) {
        this.fideicomisos = fideicomisos;
    }
    
    

}
