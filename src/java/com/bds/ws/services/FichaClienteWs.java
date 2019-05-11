/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.FichaClienteDAO;
import com.bds.ws.dto.FichaClienteDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author humberto.rojas
 */
@WebService(serviceName = "FichaClienteWs")
public class FichaClienteWs {
     @EJB
    private FichaClienteDAO fichaClienteDAO;
     
     
    @WebMethod(operationName = "consultarDatosCliente")
    public FichaClienteDTO consultarDatosCliente(@WebParam(name = "codigoCliente") String codigoCliente) {
        return fichaClienteDAO.consultarDatosCliente(codigoCliente);
    }
}
