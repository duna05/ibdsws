/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbModulos;
import java.io.Serializable;
import java.util.List;

/**
 * Clase IbModulosDTO
 * @author renseld.lugo
 */
public class IbModulosDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private IbModulos ibModulo;             //Objeto de tipo IbModulos para retornar un solo parametro
    private List<IbModulos> ibModulos;      //Lisa de objetos IbModulos para retornar varios parametros 
    private RespuestaDTO respuesta;      //objeto de tipo RespuestaDTO para retornar el resultado de la transaccion

    /**
     * retorna Objeto de tipo IbModulos con los datos de un solo modulo y su
     * lista de transacciones.
     *
     * @return IbModulos.
     */
    public IbModulos getIbModulo() {
        return ibModulo;
    }

    /**
     * asigna Objeto de tipo IbModulos con los datos de un solo modulo y su
     * lista de transacciones.
     *
     * @param ibModulo IbModulos.
     */
    public void setIbModulo(IbModulos ibModulo) {
        this.ibModulo = ibModulo;
    }

    /**
     * retorna Una lista de objetos IbModulos con los datos de varios modulos y
     * su lista de transacciones.
     *
     * @return Collection < IbModulos >
     */
    public List<IbModulos> getIbModulos() {
        return ibModulos;
    }

    /**
     * asigna Una lista de objetos IbModulos con los datos de varios modulos y
     * su lista de transacciones
     *
     * @param ibModulos Collection < IbModulos >.
     */
    public void setIbModulos(List<IbModulos> ibModulos) {
        this.ibModulos = ibModulos;
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
