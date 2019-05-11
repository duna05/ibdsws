/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbErroresCargaPagosCorpDetPjDAO;
import com.bds.ws.dto.IbErroresCargaPagosCorpDetPjDTO;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author christian.gutierrez
 */
@WebService(serviceName = "IbErroresCargaPagosCorpDetPjWs")
public class IbErroresCargaPagosCorpDetPjWs {

    @EJB
    private IbErroresCargaPagosCorpDetPjDAO ibErrCargaPagosCorpDetPjDAO;
            
    
     /**
     * Metodo buscar el detalle de error de carga en pagos especiales
     * Oracle 11
     *
     * @param idPago número del pago a consultar
     * @param idCanal id del canal interno en IB
     * @param codigoCanal codigo de canal asignado por el CORE banario
     * @return IbRegistrosMasivosPjDTO
     */

    @WebMethod(operationName = "listarErrCargaPagosCorpDetPj")
    public IbErroresCargaPagosCorpDetPjDTO listarErrCargaPagosCorpDetPj(
            @WebParam(name = "idPago") BigDecimal idPago,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return this.ibErrCargaPagosCorpDetPjDAO.listarErrCargaPagosCorpDetPj(idPago, idCanal, codigoCanal);
    }

}
