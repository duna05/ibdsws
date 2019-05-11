/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;
import com.bds.ws.dao.FichaClienteRefTarjetaDAO;
import com.bds.ws.dto.FichaClienteRefTarjetaDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
/**
 *
 * @author alejandro.flores
 */
@WebService(serviceName = "FichaClienteRefTarjetaWs")
public class FichaClienteRefTarjetaWs {
    @EJB
    private FichaClienteRefTarjetaDAO fichaClienteRefTarjetaDAO;
    
    @WebMethod(operationName = "consultarRefTarjeta")
    public  FichaClienteRefTarjetaDTO consultarRefTarjeta(@WebParam(name = "codigoCliente") String codigoCliente) {
        return fichaClienteRefTarjetaDAO.consultarRefTarjeta(codigoCliente);
    }
}
