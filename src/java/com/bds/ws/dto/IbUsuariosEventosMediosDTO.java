/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbUsuariosEventosMedios;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author juan.faneite
 */
public class IbUsuariosEventosMediosDTO implements Serializable{
    
    private IbUsuariosEventosMedios usuarioEventoMedio;
    private List<IbUsuariosEventosMedios> usuarioEventosMedios;
    private RespuestaDTO respuesta;

    /**
     * 
     * @return modelo Ib_Usuarios_Eventos_Medios
     */
    public IbUsuariosEventosMedios getUsuarioEventoMedio() {
        return usuarioEventoMedio;
    }

    /**
     * 
     * @param usuarioEventoMedio modelo Ib_Usuarios_Eventos_Medios
     */
    public void setUsuarioEventoMedio(IbUsuariosEventosMedios usuarioEventoMedio) {
        this.usuarioEventoMedio = usuarioEventoMedio;
    }

    /**
     * 
     * @return lista de modelo Ib_Usuarios_Eventos_Medios
     */
    public List<IbUsuariosEventosMedios> getUsuarioEventosMedios() {
        return usuarioEventosMedios;
    }

    /**
     * 
     * @param usuarioEventosMedios lista de modelo Ib_Usuarios_Eventos_Medios
     */
    public void setUsuarioEventosMedios(List<IbUsuariosEventosMedios> usuarioEventosMedios) {
        this.usuarioEventosMedios = usuarioEventosMedios;
    }

    /**
     * 
     * @return respuesta
     */
    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    /**
     * 
     * @param respuesta respuesta
     */
    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }
    
    
}
