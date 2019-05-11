/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbErroresNominaPjDAO;
import com.bds.ws.dto.IbErroresNominaPjDTO;
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
@WebService(serviceName = "IbErroresNominaPjWs")
public class IbErroresNominaPjWs {
    
    @EJB
    private IbErroresNominaPjDAO ibErroresNominaPjDAO;

      /**
     * Metodo para insertar un nuevo error relacionado a la carga de nomina
     * Oracle 11
     *
     * @param ibErroresNominaPjDTO objeto completo de IbErroresNominaPjDTO
     * @return UtilDTO
     */
    @WebMethod(operationName = "insertarIbErroresNominaPj")
    public UtilDTO insertarIbErroresNominaPj(@WebParam(name = "ibErroresNominaPj")IbErroresNominaPjDTO ibErroresNominaPjDTO){
        return this.ibErroresNominaPjDAO.insertarIbErroresNominaPj(ibErroresNominaPjDTO);
    }
    
     /**
     * Metodo para consultar un error relacionado a la carga de nomina
     * Oracle 11
     *
     * @param idError BigDecimal, identificador del error a buscar
     * @return UtilDTO
     */
    @WebMethod(operationName = "consultarIbErroresNominaPjById")
    public UtilDTO consultarIbErroresNominaPjById(@WebParam(name = "idError")BigDecimal idError){
        return this.ibErroresNominaPjDAO.consultarIbErroresNominaPjById(idError);
    }
    
     /**
     * Metodo para consultar un error relacionado a la carga de nomina
     * Oracle 11
     *
     * @param idPago BigDecimal, identificador del error a buscar
     * @return UtilDTO
     */
    @WebMethod(operationName = "consultarIbErroresNominaPjByPago")
    public UtilDTO consultarIbErroresNominaPjByPago(@WebParam(name = "idPago")BigDecimal idPago){
        return this.ibErroresNominaPjDAO.consultarIbErroresNominaPjByPago(idPago);
    }
    
     /**
     * Metodo para consultar un error relacionado a la carga de nomina
     * Oracle 11
     *
     * @param idPago BigDecimal, identificador del error a buscar
     * @return IbErroresNominaPjDTO
     */
    @WebMethod(operationName = "buscarIbErroresNominaPjDTO")
    public IbErroresNominaPjDTO buscarIbErroresNominaPjDTO(@WebParam(name = "idPago")BigDecimal idPago){
        return new IbErroresNominaPjDTO();
    }
}
