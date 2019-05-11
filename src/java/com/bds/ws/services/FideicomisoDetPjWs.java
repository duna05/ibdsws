/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.FideicomisoDetPjDAO;
import com.bds.ws.dto.FideicomisoDetPjDTO;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author robinson.rodriguez
 */
@WebService(serviceName = "FideicomisoDetPjWs")
public class FideicomisoDetPjWs {

   @EJB
    private FideicomisoDetPjDAO fideicomisoDetPjDAO;

    /**
     * Metodo para listar los detalles de la consulta fideicomiso Oracle 11
     *
     * @param cdClienteAbanks codigo del cliente en el core bancario
     * @param cdUsuario Usuario del sistema
     * @param idFideicomiso id del fideicomiso
     * @param fechaHora Fecha y hora de la consulta del detalle
     * @param idCanal id del canal a utilizar
     * @param codigoCanal id canal utilizado
     * @return IbFideicomisoPjDTO
     */
    @WebMethod(operationName = "listarFideicomisoDetPjDTO")
    public FideicomisoDetPjDTO listarFideicomisoDetPjDTO(@WebParam(name = "cdClienteAbanks") String cdClienteAbanks,
            @WebParam(name = "cdUsuario") String cdUsuario,
            @WebParam(name = "idFideicomiso") String idFideicomiso,
            @WebParam(name = "fechaHora") String fechaHora,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return fideicomisoDetPjDAO.listarFideicomisoDetPj(cdClienteAbanks, cdUsuario, idFideicomiso, fechaHora, idCanal, codigoCanal);
    }
}
