/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbLogsPjDAO;
import com.bds.ws.dto.IbLogsPjDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Clase de servicio web para logs
 * @author luis.perez
 */
@WebService(serviceName = "IbLogsPjWs")
public class IbLogsPjWs {
    @EJB
    private IbLogsPjDAO ibLogsPjDAO;

    /**
     * Metodo para obtener el listado de moviemientos del log del usuario
     *
     * @param idUsuario id del usuario
     * @param idEmpresa
     * @param fechaDesde fecha desde si viene null se tomaran los ultimos registros
     * @param fechaHasta fecha hasta si viene null se tomaran los ultimos registros
     * @param idServicio id del servicio por la que se realizará el filtro
     * @param idCanal id del canal a filtrar
     * @param codigoCanal
     * @return IbLogsDTO -> List < IbLogsPj > listado de moviemientos del log
     */
    @WebMethod(operationName = "listadoHistoricoUsuarioEmpresaPj")
    public IbLogsPjDTO listadoHistoricoUsuarioEmpresaPj(
            @WebParam(name = "idUsuario") String idUsuario, 
            @WebParam(name = "idEmpresa") String idEmpresa,
            @WebParam(name = "fechaDesde") String fechaDesde, 
            @WebParam(name = "fechaHasta") String fechaHasta, 
            @WebParam(name = "idServicio") String idServicio, 
            @WebParam(name = "idCanal") String idCanal, 
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return ibLogsPjDAO.listadoHistoricoUsuarioEmpresaPj(idUsuario,idEmpresa, fechaDesde, fechaHasta, idServicio, idCanal, codigoCanal);
    }   
}
