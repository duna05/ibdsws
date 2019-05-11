/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.RespuestaDTO;

/**
 * Interfaz para SMS
 * @author cesar.mujica
 */
public interface SMSDAO {

    /**
     * Metodo que se encarga de realizar un envio de SMS
     *
     * @param numeroCelular numero del destinatario
     * @param mensajeTexto texto del mensaje a enviar
     * @param idCanal identificador del canal
     * @return RespuestaDTO objeto con el estatus de la operacion
     */
    public RespuestaDTO enviarSMS(String numeroCelular, String mensajeTexto, String idCanal);

}
