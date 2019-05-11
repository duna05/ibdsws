/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.FideicomisoPjDAO;
import com.bds.ws.dto.FideicomisoPjDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author robinson.rodriguez
 */
@WebService(serviceName = "FideicomisoPjWs")
public class FideicomisoPjWs {

    @EJB
    private FideicomisoPjDAO fideicomisoPjDAO;

    /**
     * Metodo para listar las cabeceras de la consulta fideicomiso Oracle 11
     *
     * @param cdClienteAbanks codigo del cliente en el core bancario
     * @param idFideicomiso id del fideicomiso
     * @param idIndicadorTxt indicador del archivo
     * @param idCanal id del canal a utilizar
     * @param codigoCanal id canal utilizado
     * @return IbFideicomisoPjDTO
     */
    @WebMethod(operationName = "listarFideicomisoPjDTO")
    public FideicomisoPjDTO listarFideicomisoPjDTO(@WebParam(name = "cdClienteAbanks") String cdClienteAbanks,
            @WebParam(name = "idFideicomiso") String idFideicomiso,
            @WebParam(name = "idIndicadorTxt") String idIndicadorTxt,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return fideicomisoPjDAO.listarFideicomisoPj(cdClienteAbanks,idFideicomiso, idIndicadorTxt, idCanal, codigoCanal);
    }

}