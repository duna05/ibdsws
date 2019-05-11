/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbMensajes;
import java.io.Serializable;
import java.util.List;

/**
 * Clase usada para almacenar datos referentes a
 * objetos de tipo IbMensajes y listas de objetos IbMensajes,
 * además, del estatus de la respuesta de la transacción realizada para
 * obtener los datos almacenados en el DTO
 * @author cesar.mujica
 */
public class IbMensajesDTO implements Serializable{
    private IbMensajes ibMensaje;//objeto de tipo IbMensajes
    private List<IbMensajes> ibMensajes;//Lista de objetos de tipo IbMensajes
    private RespuestaDTO respuesta;

    public IbMensajes getIbMensaje() {
        return ibMensaje;
    }

    public void setIbMensaje(IbMensajes ibMensaje) {
        this.ibMensaje = ibMensaje;
    }

    public List<IbMensajes> getIbMensajes() {
        return ibMensajes;
    }

    public void setIbMensajes(List<IbMensajes> ibMensajes) {
        this.ibMensajes = ibMensajes;
    }

    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaDTO respuestaDTO) {
        this.respuesta = respuestaDTO;
    }
    
}
