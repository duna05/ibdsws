/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.FichaClienteComplementarioDAO;
import com.bds.ws.dto.FichaClienteComplementarioDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author humberto.rojas
 */
@WebService(serviceName = "FichaClienteComplementarioWs")
public class FichaClienteComplementarioWs {
     @EJB
    private FichaClienteComplementarioDAO fichaClienteComplementarioDAO;
     
     
    @WebMethod(operationName = "consultarDatosComplementariosCliente")
    public FichaClienteComplementarioDTO consultarDatosComplementariosCliente(@WebParam(name = "codigoCliente") String codigoCliente) {
        return fichaClienteComplementarioDAO.consultarDatosComplementariosCliente(codigoCliente);
    }
}
