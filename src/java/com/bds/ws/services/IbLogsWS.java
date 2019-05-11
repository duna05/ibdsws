/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbLogsDAO;
import com.bds.ws.dto.IbLogsDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Clase de servicio web para logs
 * @author juan.faneite
 */
@WebService(serviceName = "IbLogsWS")
public class IbLogsWS {
    @EJB
    private IbLogsDAO ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    /**
     * Metodo para obtener el listado de moviemientos del log del usuario
     *
     * @param idUsuario id del usuario
     * @param fechaDesde fecha desde si viene null se tomaran los ultimos registros
     * @param fechaHasta fecha hasta si viene null se tomaran los ultimos registros
     * @param nroMesesAtras cantidad de meses atras a colsultar
     * @param idTransaccion número de la transacción por la que se realizará el filtro
     * @param idCanal id del canal a filtrar
     * @param nombreCanal nombre del canal
     * @return IbLogsDTO -> List < IbLogs > listado de moviemientos del log
     */
    @WebMethod(operationName = "listadoHistoricoCliente")
    public IbLogsDTO listadoHistoricoCliente(@WebParam(name = "idUsuario") String idUsuario, @WebParam(name = "fechaDesde") String fechaDesde, @WebParam(name = "fechaHasta") String fechaHasta, @WebParam(name = "nroMesesAtras") int nroMesesAtras, @WebParam(name = "idTransaccion") String idTransaccion, @WebParam(name = "idCanal") String idCanal, @WebParam(name = "nombreCanal") String nombreCanal) {
        return ejbRef.listadoHistoricoCliente(idUsuario, fechaDesde, fechaHasta, nroMesesAtras, idTransaccion, idCanal, nombreCanal);
    }
    
}
