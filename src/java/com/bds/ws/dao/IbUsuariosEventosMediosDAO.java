/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.RespuestaDTO;

/**
 *
 * @author juan.faneite
 */
public interface IbUsuariosEventosMediosDAO {
    
    /**
     * Metodo que sustituye el listado de Eventos y medios asociados a un usuario
     * @param idUsuario
     * @param idEvento
     * @param idMedio
     * @param montoMin
     * @param codigoCanal codigo del canal
     * @return RespuestaDTO
     */
    public RespuestaDTO agregarUsuarioEventosMedios(String idUsuario, String idEvento, String idMedio, String montoMin, String codigoCanal);
    
}
