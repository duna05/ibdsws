/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbEventosNotificacionDTO;

/**
 *
 * @author juan.faneite
 */
public interface IbEventosNotificacionDAO {
    
    /**
     * Metodo que obtiene el listado de eventos
     * @param codigoCanal codigo del canal para su registro en el log
     * @return IbEventosNotificacionDTO con listado
     */
    public IbEventosNotificacionDTO listaEventos (String codigoCanal);
}
