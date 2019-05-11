/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbEventosNotificacionDAO;
import com.bds.ws.dao.IbMediosNotificacionDAO;
import com.bds.ws.dao.IbUsuariosEventosMediosDAO;
import com.bds.ws.dto.IbEventosNotificacionDTO;
import com.bds.ws.dto.IbMediosNotificacionDTO;
import com.bds.ws.dto.RespuestaDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author juan.faneite
 */
@WebService(serviceName = "IbUsuariosEventosMediosWS")
public class IbUsuariosEventosMediosWS {
    @EJB
    private IbUsuariosEventosMediosDAO ibUsuariosEventosMediosDAO;
    
    @EJB
    private IbEventosNotificacionDAO ibEventosNotificacionDAO;
    
    @EJB
    private IbMediosNotificacionDAO ibMediosNotificacionDAO;

    /**
     * Metodo que sustituye el listado de Eventos y medios asociados a un usuario
     * @param idUsuario
     * @param idEvento
     * @param idMedio
     * @param montoMin
     * @param codigoCanal codigo del canal
     * @return RespuestaDTO
     */
    @WebMethod(operationName = "agregarUsuarioEventosMedios")
    public RespuestaDTO agregarUsuarioEventosMedios(@WebParam(name = "idUsuario") String idUsuario, @WebParam(name = "idEvento") String idEvento, @WebParam(name = "idMedio") String idMedio, @WebParam(name = "montoMin") String montoMin, @WebParam(name = "codigoCanal") String codigoCanal) {        
        return ibUsuariosEventosMediosDAO.agregarUsuarioEventosMedios(idUsuario, idEvento, idMedio, montoMin, codigoCanal);
    }
    
    /**
     * Metodo que obtiene el listado de eventos
     * @param codigoCanal codigo del canal para su registro en el log
     * @return IbEventosNotificacionDTO con listado
     */
    @WebMethod(operationName = "listaEventos")
    public IbEventosNotificacionDTO listaEventos(@WebParam(name = "codigoCanal") String codigoCanal) {
        return ibEventosNotificacionDAO.listaEventos(codigoCanal);
    }
    
    /**
     * Metodo que obtiene el listado de medios
     * @param codigoCanal codigo del canal para su registro en el log
     * @return IbMediosNotificacionDTO con listado
     */
    @WebMethod(operationName = "listaMedios")
    public IbMediosNotificacionDTO listaMedios(@WebParam(name = "codigoCanal") String codigoCanal) {
        return ibMediosNotificacionDAO.listaMedios(codigoCanal);
    }
    
}
