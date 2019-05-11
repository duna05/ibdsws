/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbCargaPagosEspDetPjDAO;
import com.bds.ws.dto.IbCargaPagosEspDetPjDTO;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author robinson rodriguez
 */
@WebService(serviceName = "IbCargaPagosEspDetPjWs")
public class IbCargaPagosEspDetPjWs {
    
    @EJB
    private IbCargaPagosEspDetPjDAO ibCargaPagosEspDetPjDAO;
    
           
     /**
     * Metodo buscar el detalle de pagos especiales
     * Oracle 11
     *
     * @param idpago id del pago a buscar
     * @param estatusCarga
     * @param idCanal id del canal interno en IB
     * @param codigoCanal codigo de canal asignado por el CORE banario
     * @return IbRegistrosMasivosPjDTO
     */
    
    
    @WebMethod(operationName = "listarIbCargaPagosEspDetPjDTO")
    public IbCargaPagosEspDetPjDTO listarIbCargaPagosEspDetPjDTO(@WebParam(name = "idpago")       BigDecimal idpago, 
                                                                 @WebParam(name = "estatusCarga") Short estatusCarga,   
                                                                 @WebParam(name = "idCanal")     String       idCanal, 
                                                                 @WebParam(name = "codigoCanal")     String       codigoCanal ){
        return ibCargaPagosEspDetPjDAO.listarIbCargaPagosEspDetPjDTO(idpago,estatusCarga, idCanal, codigoCanal);
    }
}
