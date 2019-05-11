/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import java.io.Serializable;

/**
 * Clase EMailDTO
 * @author cesar.mujica
 */
public class EMailDTO implements Serializable {

    private String remitente;       //Remitente del correo.
    private String destinatario;    //Destinatario del correo.
    private String asunto;          //Asunto del correo.
    private String texto;           //Texto del correo.
    private String canal;           //Codigo (extendido) del canal desde el cual es llamado el procedimiento.
    RespuestaDTO respuesta;         //manejo de respuesta

    /**
     * retorna Remitente del correo.
     *
     * @return String Remitente del correo.
     */
    public String getRemitente() {
        return remitente;
    }

    /**
     * asigna Remitente del correo.
     * @param remitente String Remitente del correo.
     */
    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    /**
     * retorna Destinatario del correo.
     *
     * @return String Destinatario del correo.
     */
    public String getDestinatario() {
        return destinatario;
    }

    /**
     * asigna Destinatario del correo.
     * @param destinatario String Destinatario del correo.
     */
    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    /**
     * retorna Asunto del correo.
     *
     * @return String Asunto del correo.
     */
    public String getAsunto() {
        return asunto;
    }

    /**
     * asigna Asunto del correo.
     * @param asunto String Asunto del correo.
     */
    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    /**
     * retorna Texto del correo.
     *
     * @return String Texto del correo.
     */
    public String getTexto() {
        return texto;
    }

    /**
     * asigna Texto del correo.
     * @param texto String Texto del correo.
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * retorna Codigo (extendido) del canal desde el cual es llamado el
     * procedimiento.
     *
     * @return String Codigo (extendido) del canal desde el cual es llamado el
     * procedimiento.
     */
    public String getCanal() {
        return canal;
    }

    /**
     * asigna Codigo (extendido) del canal desde el cual es llamado el procedimiento.
     * @param canal String Codigo (extendido) del canal desde el cual es llamado el procedimiento.
     */
    public void setCanal(String canal) {
        this.canal = canal;
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
