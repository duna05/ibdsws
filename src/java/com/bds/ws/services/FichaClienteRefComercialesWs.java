/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;


import com.bds.ws.dao.FichaClienteRefComercialesDAO;
import com.bds.ws.dto.FichaClienteRefComercialesDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author humberto.rojas
 */
@WebService(serviceName = "FichaClienteRefComercialesWs")
public class FichaClienteRefComercialesWs {
     @EJB
    private FichaClienteRefComercialesDAO fichaClienteRefComercialesDAO;
     
     
    @WebMethod(operationName = "consultarRefComerciales")
    public FichaClienteRefComercialesDTO consultarRefComerciales(@WebParam(name = "codigoCliente") String codigoCliente) {
        return fichaClienteRefComercialesDAO.consultarRefComerciales(codigoCliente);
    }
}
