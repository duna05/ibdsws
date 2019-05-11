/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbErroresPjDAO;
import com.bds.ws.dto.IbErroresPjDTO;
import com.bds.ws.dto.UtilDTO;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author juan.vasquez
 */
@WebService(serviceName = "IbErroresCargaPjWs")
public class IbErroresCargaPjWs {
    
    @EJB
    private IbErroresPjDAO ibErroresCargaPjDAO;
    
     /**
     * Metodo para insertar un nuevo error relacionado a la carga de nomina
     * Oracle 11
     *
     * @param ibErroresCargaPjDTO objeto completo de IbErroresPjDTO
     * @return UtilDTO
     */
    @WebMethod(operationName = "insertarIbErroresCargaPj")
    public UtilDTO insertarIbErroresCargaPj(@WebParam(name = "ibErroresCargaPj")IbErroresPjDTO ibErroresCargaPjDTO){
        return this.ibErroresCargaPjDAO.insertarIbErroresCargaPj(ibErroresCargaPjDTO);
    }
    
     /**
     * Metodo para consultar un error relacionado a la carga de nomina
     * Oracle 11
     *
     * @param idError BigDecimal, identificador del error a buscar
     * @return UtilDTO
     */
    @WebMethod(operationName = "consultarIbErroresCargaPjById")
    public UtilDTO consultarIbErroresCargaPjById(@WebParam(name = "idError")BigDecimal idError){
        return this.consultarIbErroresCargaPjById(idError);
    }
    
     /**
     * Metodo para consultar un error relacionado a la carga de nomina
     * Oracle 11
     *
     * @param idError BigDecimal, identificador del error a buscar
     * @return IbErroresPjDTO
     */
    @WebMethod(operationName = "buscarIbErroresCargaPjDTO")
    public IbErroresPjDTO buscarIbErroresCargaPjDTO(@WebParam(name = "idError")BigDecimal idError){
        return new IbErroresPjDTO();
    }
}
