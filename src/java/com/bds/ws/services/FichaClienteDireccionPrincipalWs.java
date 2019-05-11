/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.FichaClienteDireccionPrincipalDAO;
import com.bds.ws.dto.FichaClienteDireccionPrincipalDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author alejandro.flores
 */
@WebService(serviceName = "FichaClienteDireccionPrincipalWs")
public class FichaClienteDireccionPrincipalWs {
    @EJB
    private FichaClienteDireccionPrincipalDAO fichaClienteDireccionPrincipalDAO;
     
     
    @WebMethod(operationName = "consultarDatosDireccionCliente")
    public FichaClienteDireccionPrincipalDTO consultarDatosDirPpalCliente(@WebParam(name = "codigoCliente") String codigoCliente) {
        return fichaClienteDireccionPrincipalDAO.consultarDatosDirPpalCliente(codigoCliente);
    } 
}

