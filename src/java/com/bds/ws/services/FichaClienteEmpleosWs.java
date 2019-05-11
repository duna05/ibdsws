/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;


import com.bds.ws.dao.FichaClienteEmpleosDAO;

import com.bds.ws.dto.FichaClienteEmpleosDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
/**
 *
 * @author humberto.rojas
 */
@WebService(serviceName = "FichaClienteEmpleosWs")
public class FichaClienteEmpleosWs {
    @EJB
    private FichaClienteEmpleosDAO fichaClienteEmpleosDAO;
    
    @WebMethod(operationName = "consultarEmpleos")
    public FichaClienteEmpleosDTO consultarEmpleos(@WebParam(name = "codigoCliente") String codigoCliente) {
        return fichaClienteEmpleosDAO.consultarEmpleos(codigoCliente);
    }
    
}
