/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import java.util.Map;

/**
 * Clase UtilDTO
 * @author renseld.lugo
 */
public class UtilDTO {

    private Map resulados;            // resultados de consulta a datos especificos.
    private RespuestaDTO respuesta;  // Maneja la respuesta de la operacion que se realiza

    /**
     * retorna mapa con resultados de la consulta de un dato en especifico.
     *
     * @return Map resultados de consulta a datos especificos.
     */
    public Map getResulados() {
        return resulados;
    }

    /**
     * asigna Mapa con resultados de la consulta de un dato en especifico.
     * @param resulados resultados de consulta a datos especificos.
     */
    public void setResulados(Map resulados) {
        this.resulados = resulados;
    }

    /**
     * retorna la respuesta de la operacion que se realiza.
     *
     * @return RespuestaDTO respuesta de la operacion que se realiza.
     */
    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    /**
     * asigna objeto RespuestaDTO con el estatus de la transaccion.
     * @param respuesta objeto respuesta.
     */
    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }

}
