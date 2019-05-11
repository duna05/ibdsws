/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.util.BDSUtil;
import java.io.Serializable;

/**
 * clase para validar los estatus de respuestas de los servicios y/o
 * procedimientos almacenados
 *
 * @author cesar.mujica
 */
public class RespuestaDTO extends BDSUtil implements Serializable {

    //Atributos que funcionan para el manejo de errores interno.
    //codigo de respuesta de status, 000 representa estatus exitoso
    private String codigo;

    //breve descripcion relativa al codigo
    private String descripcion;
;        //Codigo arrojado por SP invocado.
    private String descripcionSP;   //Siglas del codigo devuelto.
    //Atributos que describen la respuesta de determinados SP    
    private String codigoSP;        //Codigo arrojado por SP invocado.
    private String textoSP;         //Descripcion del codigo de salida. 

    public RespuestaDTO() {
        this.codigo = CODIGO_RESPUESTA_EXITOSO;
        this.descripcion = DESCRIPCION_RESPUESTA_EXITOSO;
        this.codigoSP = CODIGO_RESPUESTA_EXITOSO;
        this.descripcionSP = DESCRIPCION_RESPUESTA_EXITOSO_SP;
        this.textoSP = "";
    }

    /**
     * Descripcion del codigo de salida.
     *
     * @return String Descripcion del codigo de salida.
     */
    public String getTextoSP() {
        return textoSP;
    }

    /**
     * Descripcion del codigo de salida.
     *
     * @param textoSP String Descripcion del codigo de salida.
     */
    public void setTextoSP(String textoSP) {
        this.textoSP = textoSP;
    }

    /**
     * obtiene Codigo arrojado por SP invocado
     *
     * @return String Codigo arrojado por SP invocado
     */
    public String getCodigoSP() {
        return codigoSP;
    }

    /**
     * ingresa Codigo arrojado por SP invocado
     *
     * @param codigoSP String Codigo arrojado por SP invocado
     */
    public void setCodigoSP(String codigoSP) {
        this.codigoSP = codigoSP;
    }

    /**
     * obtiene Siglas del codigo devuelto.
     *
     * @return String Siglas del codigo devuelto.
     */
    public String getDescripcionSP() {
        return descripcionSP;
    }

    /**
     * ingresa Siglas del codigo devuelto.
     *
     * @param descripcionSP String Siglas del codigo devuelto.
     */
    public void setDescripcionSP(String descripcionSP) {
        this.descripcionSP = descripcionSP;
    }

    /**
     * retorna el codigo de respuesta de status, 000 representa estatus exitoso
     *
     * @return String codigo de respuesta de status
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * 
     * @param codigo String codigo de respuesta de status
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
        if (!this.codigo.equals(CODIGO_RESPUESTA_EXITOSO)) {
            this.descripcion = DESCRIPCION_RESPUESTA_FALLIDA;
        }
    }

    /**
     * retorna una breve descripcion relativa al codigo
     *
     * @return String descripcion de respuesta de status
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * inserta una breve descripcion relativa al codigo 
     * @param descripcion String descripcion de respuesta de status
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
