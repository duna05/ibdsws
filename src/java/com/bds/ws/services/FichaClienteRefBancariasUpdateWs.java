/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.FichaClienteRefBancariasUpdateDAO;
import com.bds.ws.dto.UtilDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author audra.zapata
 */
@WebService(serviceName = "FichaClienteRefBancariasUpdateWs")
public class FichaClienteRefBancariasUpdateWs {
    
    @EJB
    private FichaClienteRefBancariasUpdateDAO fichaClienteRefBancariasUpdateDAO;
    
    @WebMethod(operationName = "actualizarDatosRefBancarias")
    public UtilDTO actualizarDatosRefBancarias(@WebParam(name = "codigoCliente") String codigoCliente) {
        return fichaClienteRefBancariasUpdateDAO.actualizarDatosRefBancarias(codigoCliente);
    }
}
