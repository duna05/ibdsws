/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.FichaClienteUpdateDAO;
import com.bds.ws.dto.FichaClienteDTO;
import com.bds.ws.dto.FichaClienteUpdateDTO;
import com.bds.ws.dto.UtilDTO;
import javax.jws.WebService;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Accusys Technology
 */

@WebService(serviceName = "FichaClienteUpdateWs")
public class FichaClienteUpdateWs {
    
    @EJB
    private FichaClienteUpdateDAO fichaClienteUpdateDAO;
    
    @WebMethod(operationName = "insertUpdateFichaCliente")
    public UtilDTO insertUpdateFichaCliente(@WebParam(name = "codigoCliente") String codigoCliente) {
        return fichaClienteUpdateDAO.insertUpdateFichaCliente(codigoCliente);        
    }
    
  
}
