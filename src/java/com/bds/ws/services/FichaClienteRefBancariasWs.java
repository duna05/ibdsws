/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;


import com.bds.ws.dao.FichaClienteRefBancariasDAO;

import com.bds.ws.dto.FichaClienteRefBancariasDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author humberto.rojas
 */
@WebService(serviceName = "FichaClienteRefBancariasWs")
public class FichaClienteRefBancariasWs {
     @EJB
    private FichaClienteRefBancariasDAO fichaClienteRefBancariasDAO;
    
     
    @WebMethod(operationName = "consultarRefBancarias")
    public FichaClienteRefBancariasDTO consultarRefBancarias(@WebParam(name = "codigoCliente") String codigoCliente) {
        return fichaClienteRefBancariasDAO.consultarRefBancarias(codigoCliente);
    }
}
