/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbPregDesafioUsuario;
import java.io.Serializable;
import java.util.List;

/**
 * objeto que retorna la relacion entre un usuario y sus preguntas
 * @author cesar.mujica
 */
public class IbPregDesafioUsuarioDTO implements Serializable{
    private IbPregDesafioUsuario ibPregDesafioUsuario;          
    private List<IbPregDesafioUsuario> ibPregDesafioUsuarios;
    private RespuestaDTO respuesta;

    /**
     * retorna el objeto que relaciona un usuario a una pregunta
     * @return IbPregDesafioUsuario
     */
    public IbPregDesafioUsuario getIbPregDesafioUsuario() {
        return ibPregDesafioUsuario;
    }

    /**
     * asigna el objeto que relaciona un usuario a una pregunta
     * @param ibPregDesafioUsuario objeto que relaciona un usuario a una pregunta
     */
    public void setIbPregDesafioUsuario(IbPregDesafioUsuario ibPregDesafioUsuario) {
        this.ibPregDesafioUsuario = ibPregDesafioUsuario;
    }

    /**
     * retorna la lista de objetos que relaciona un usuario a una pregunta
     * @return List < IbPregDesafioUsuario >
     */
    public List<IbPregDesafioUsuario> getIbPregDesafioUsuarios() {
        return ibPregDesafioUsuarios;
    }

    /**
     * asigna la lista de objetos que relaciona un usuario a una pregunta
     * @param ibPregDesafioUsuarios lista de objetos que relaciona un usuario a una pregunta
     */
    public void setIbPregDesafioUsuarios(List<IbPregDesafioUsuario> ibPregDesafioUsuarios) {
        this.ibPregDesafioUsuarios = ibPregDesafioUsuarios;
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
