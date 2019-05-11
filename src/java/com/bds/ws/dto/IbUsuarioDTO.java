/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbUsuarios;
import java.util.List;

/**
 * Clase IbUsuarioDTO
 * @author cesar.mujica
 */
public class IbUsuarioDTO {

    private IbUsuarios usuario;         //objeto para retornar los datos de un usuario 
    private List<IbUsuarios> usuarios;  //objeto para retornar los datos de una lista usuarios
    private RespuestaDTO respuesta;     //objeto para manejar el estatus de las respuestas

    /**
     * retorna los datos de un usuario
     *
     * @return usuario
     */
    public IbUsuarios getUsuario() {
        return usuario;
    }

    /**
     * asigna los datos de un usuario
     *
     * @param usuario usuario
     */
    public void setUsuario(IbUsuarios usuario) {
        this.usuario = usuario;
    }

    /**
     * retorna los datos de una lista usuarios
     *
     * @return usuarios
     */
    public List<IbUsuarios> getUsuarios() {
        return usuarios;
    }

    /**
     * asigna los datos de una lista usuarios
     *
     * @param usuarios usuarios
     */
    public void setUsuarios(List<IbUsuarios> usuarios) {
        this.usuarios = usuarios;
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
