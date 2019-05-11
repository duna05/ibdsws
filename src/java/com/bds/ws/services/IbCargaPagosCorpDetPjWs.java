/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbCargaPagosCorpDetPjDAO;
import com.bds.ws.dto.IbCargaPagosCorpDetPjDTO;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author luis.perez
 */
@WebService(serviceName = "IbCargaPagosCorpDetPjWs")
public class IbCargaPagosCorpDetPjWs {

    @EJB
    private IbCargaPagosCorpDetPjDAO ibCargaPagosCorpDetPjDAO;

    /**
     * Metodo buscar el detalle de pagos corporativos Oracle 11
     *
     * @param idpago id del pago a buscar
     * @param estatusCarga
     * @param idCanal id del canal interno en IB
     * @param codigoCanal codigo de canal asignado por el CORE banario
     * @return IbRegistrosMasivosPjDTO
     */
    @WebMethod(operationName = "listarIbCargaPagosCorpDetPjDTO")
    public IbCargaPagosCorpDetPjDTO listarIbCargaPagosCorpDetPjDTO(@WebParam(name = "idpago") BigDecimal idpago,
            @WebParam(name = "estatusCarga") Short estatusCarga,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return ibCargaPagosCorpDetPjDAO.listarIbCargaPagosCorpDetPjDTO(idpago, estatusCarga, idCanal, codigoCanal);
    }
}
