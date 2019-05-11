/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.bds.ws.dao.FichaClienteRefComercialesUpdateDAO;
import com.bds.ws.dto.UtilDTO;

/**
 *
 * @author humberto.rojas
 */
@WebService(serviceName = "FichaClienteRefComercialesUpdateWs")
public class FichaClienteRefComercialesUpdateWs {
    
    @EJB
    private FichaClienteRefComercialesUpdateDAO fichaClienteRefComercialesUpdateDAO;
    
    
    @WebMethod(operationName = "actualizarRefComerciales")
    public UtilDTO actualizarRefComerciales(@WebParam(name = "codigoCliente") String codigoCliente) {
        return fichaClienteRefComercialesUpdateDAO.actualizarRefComerciales(codigoCliente);
    }
}
