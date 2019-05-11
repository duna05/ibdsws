/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;
import com.bds.ws.dao.FichaClienteRefPersonalDAO;
import com.bds.ws.dto.FichaClienteRefPersonalDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
/**
 *
 * @author alejandro.flores
 */
@WebService(serviceName = "FichaClienteRefPersonalWs")
public class FichaClienteRefPersonalWs {
    @EJB
    private FichaClienteRefPersonalDAO fichaClienteRefPersonalDAO;
    
    @WebMethod(operationName = "consultarRefPersonal")
    public  FichaClienteRefPersonalDTO consultarRefPersonal(@WebParam(name = "codigoCliente") String codigoCliente) {
        return fichaClienteRefPersonalDAO.consultarRefPersonal(codigoCliente);
    }
}
