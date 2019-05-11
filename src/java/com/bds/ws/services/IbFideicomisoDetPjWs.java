/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbFideicomisoDetPjDAO;
import com.bds.ws.dto.IbFideicomisoDetPjDTO;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author robinson.rodriguez
 */
@WebService(serviceName = "IbFideicomisoDetPjWs")
public class IbFideicomisoDetPjWs {

    @EJB
    private IbFideicomisoDetPjDAO ibFideicomisoDetPjDAO;

    /**
     * Metodo buscar el detalle de pagos especiales Oracle 11
     *
     * @param idfideicomiso id del fideicomiso a buscar
     * @param estatusCarga
     * @param idCanal id del canal interno en IB
     * @param codigoCanal codigo de canal asignado por el CORE banario
     * @return IbRegistrosMasivosPjDTO
     */
    @WebMethod(operationName = "listarIbFideicomisoDetPjWsDTO")

    public IbFideicomisoDetPjDTO listarIbFideicomisoDetPjWsDTO(
            @WebParam(name = "idfideicomiso") BigDecimal idfideicomiso,
            @WebParam(name = "estatusCarga") Short estatusCarga,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return ibFideicomisoDetPjDAO.listarIbFideicomisoDetPjDTO(idfideicomiso, estatusCarga, idCanal, codigoCanal);
    }
}
