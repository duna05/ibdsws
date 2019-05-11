/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.EmpresasDAO;
import com.bds.ws.dto.UtilDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author luis.perez
 */
@WebService(serviceName = "EmpresasWs")
public class EmpresasWs {
    @EJB
    EmpresasDAO empresasDAO;

    /**
     * Metodo que se encarga de buscar el nombre y el codigo de la empresa por un RIF en el CORE del banco
     *
     * @param nroCuenta numero de cuenta de la empresa
     * @param tipoRif tipo de rif J, G, etc. 
     * @param rif     rif de la empresa
     * @param idCanal id de canal interno en IB
     * @param codigoCanal codigo de canal en el CORE bancario
     * @return UtilDTO
     */
    @WebMethod(operationName = "buscarNombreCodEmpresaByRif")
    public UtilDTO buscarNombreCodEmpresaByRif(@WebParam(name = "nroCuenta") String nroCuenta,
                                            @WebParam(name = "tipoRif")     Character tipoRif, 
                                            @WebParam(name = "rif")         String rif, 
                                            @WebParam(name = "idCanal")     String idCanal, 
                                            @WebParam(name = "codigoCanal") String codigoCanal) {
        return empresasDAO.buscarNombreCodEmpresaByRif(nroCuenta,tipoRif,rif, idCanal, codigoCanal);
    } 
}
