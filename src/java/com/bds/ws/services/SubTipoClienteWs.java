/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;
import com.bds.ws.dao.SubtipoClienteDAO;
import com.bds.ws.dto.SubtipoClienteDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author alejandro.flores
 */
@WebService(serviceName = "SubTipoClienteWs")
public class SubTipoClienteWs {
     @EJB
    private SubtipoClienteDAO subtipoClienteDAO;
     
     
    @WebMethod(operationName = "consultarSubtipoCliente")
    public SubtipoClienteDTO consultarSubtipoCliente() {
        return subtipoClienteDAO.consultarSubtipoCliente();
    }
}
