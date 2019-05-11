/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import java.io.Serializable;

/**
 * Maneja todo lo relacionado con las claves OTP (One Time Password).
 *
 * @author juan.faneite
 */
public class OtpDTO implements Serializable {

    private String ovOTP;           //Valor del OTP generado.
    private int intentosMaximosPermitidos; //Numero de intentos maximos permitidos para el OTP
    private int intentosRealizados; //Numero de intentos realizados para la validacion del OTP
    private String onCodSalida;     //Codigo de salida indicando si el OTP fue generado.
    private RespuestaDTO respuesta;

    /**
     * retorna Valor del OTP generado.
     *
     * @return String ovOTP Valor del OTP generado.
     */
    public String getOvOTP() {
        return ovOTP;
    }

    /**
     * ingresa Valor del OTP generado.
     * @param ovOTP String ovOTP Valor del OTP generado.
     */
    public void setOvOTP(String ovOTP) {
        this.ovOTP = ovOTP;
    }

    /**
     * retorna Codigo de salida indicando si el OTP fue generado.
     *
     * @return String onCodSalida Codigo de salida indicando si el OTP fue
     * generado.
     */
    public String getOnCodSalida() {
        return onCodSalida;
    }

    /**
     * ingresa Codigo de salida indicando si el OTP fue generado.
     * @param onCodSalida String onCodSalida Codigo de salida indicando si el OTP fue generado.
     */
    public void setOnCodSalida(String onCodSalida) {
        this.onCodSalida = onCodSalida;
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

    public int getIntentosMaximosPermitidos() {
        return intentosMaximosPermitidos;
    }

    public void setIntentosMaximosPermitidos(int intentosMaximosPermitidos) {
        this.intentosMaximosPermitidos = intentosMaximosPermitidos;
    }

    public int getIntentosRealizados() {
        return intentosRealizados;
    }

    public void setIntentosRealizados(int intentosRealizados) {
        this.intentosRealizados = intentosRealizados;
    }
}
