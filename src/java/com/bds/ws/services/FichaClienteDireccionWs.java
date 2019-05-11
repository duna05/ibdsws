/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.FichaClienteDireccionDAO;
import com.bds.ws.dto.FichaClienteDireccionDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author humberto.rojas
 */
@WebService(serviceName = "FichaClienteDireccionWs")
public class FichaClienteDireccionWs {
    @EJB
    private FichaClienteDireccionDAO fichaClienteDireccionDAO;
     
     
    @WebMethod(operationName = "consultarDatosDireccionCliente")
    public FichaClienteDireccionDTO consultarDatosDireccionCliente(@WebParam(name = "codigoCliente") String codigoCliente) {
        return fichaClienteDireccionDAO.consultarDatosDireccionCliente(codigoCliente);
    } 
}
