/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.FichaClienteRefTarjetaUpdateDAO;
import com.bds.ws.dto.UtilDTO;
import javax.ejb.EJB;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author Accusys Technology
 */
@WebService(serviceName = "FichaClienteRefTarjetaUpdateWs")
public class FichaClienteRefTarjetaUpdateWs {
     @EJB
    private FichaClienteRefTarjetaUpdateDAO fichaClienteRefTarjetaUpdateDAO;
    
    public UtilDTO actualizarRefTarjeta(@WebParam(name = "codigoCliente") String codigoCliente){
        return fichaClienteRefTarjetaUpdateDAO.actualizarRefTarjeta(codigoCliente);
    }
}
