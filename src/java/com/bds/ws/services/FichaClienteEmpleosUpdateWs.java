/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.FichaClienteEmpleosUpdateDAO;
import com.bds.ws.dto.UtilDTO;
import javax.jws.WebParam;
import javax.ejb.EJB;
import javax.jws.WebService;

/**
 *
 * @author audra.zapata
 */
@WebService(serviceName = "FichaClienteEmpleosUpdateWs")
public class FichaClienteEmpleosUpdateWs {
    
    @EJB
    private FichaClienteEmpleosUpdateDAO fichaClienteEmpleosUpdateDAO;
    
    public UtilDTO actualizarDatosEmpleos(@WebParam(name = "codigoCliente") String codigoCliente, @WebParam(name = "rif") String rif,@WebParam(name = "ramo") String ramo, @WebParam(name = "direccion") String direccion, @WebParam(name = "telefono") String telefono, @WebParam(name = "secuencia") String secuencia){
       return fichaClienteEmpleosUpdateDAO.actualizarDatosEmpleos(codigoCliente, rif, ramo, direccion, telefono, secuencia);
    }
    
}
