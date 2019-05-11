/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbUsuariosCanales;
import java.io.Serializable;
import java.util.List;

/**
 * Clase IbUsuariosCanalesDTO
 * @author renseld.lugo
 */
public class IbUsuariosCanalesDTO implements Serializable {

    private IbUsuariosCanales ibUsuarioCanal;          //Objeto IbUsuariosCanales
    private List<IbUsuariosCanales> ibUsuariosCanales;  //Lista de IbUsuariosCanales
    private RespuestaDTO respuesta;                  //Objeto RespuestaDTO para el manejo de estatus de transacciones

    /**
     * retorna Objeto IbUsuariosCanales
     *
     * @return Objeto IbUsuariosCanales (Para consultas de un solo usuario)
     */
    public IbUsuariosCanales getIbUsuarioCanal() {
        return ibUsuarioCanal;
    }

    /**
     * asigna un objeto IbUsuariosCanales.
     * @param ibUsuarioCanal Objeto IbUsuariosCanales.
     */
    public void setIbUsuarioCanal(IbUsuariosCanales ibUsuarioCanal) {
        this.ibUsuarioCanal = ibUsuarioCanal;
    }

    /**
     * @return List < IbUsuariosCanales > (Para consultas de una lista de usuarios)
     */
    public List<IbUsuariosCanales> getIbUsuariosCanales() {
        return ibUsuariosCanales;
    }

    /**
     * asigna lista de tipo ibUsuariosCanales.
     * @param ibUsuariosCanales List < IbUsuariosCanales > ibUsuariosCanales.
     */
    public void setIbUsuariosCanales(List<IbUsuariosCanales> ibUsuariosCanales) {
        this.ibUsuariosCanales = ibUsuariosCanales;
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
