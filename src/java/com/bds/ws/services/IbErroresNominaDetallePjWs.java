/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbErroresNominaDetallePjDAO;
import com.bds.ws.dto.IbErroresNominaDetallePjDTO;
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
@WebService(serviceName = "IbErroresNominaDetallePjWs")
public class IbErroresNominaDetallePjWs {
    
    @EJB
    private IbErroresNominaDetallePjDAO ibErroresNominaDetallePjDAO;
    
     /**
     * Metodo para insertar un nuevo error relacionado a la carga de nomina
     * Oracle 11
     *
     * @param ibErroresNominaPjDTO objeto completo de IbErroresNominaPjDTO
     * @return UtilDTO
     */
    @WebMethod(operationName = "insertarIbErroresNominaDetallePj")
    public UtilDTO insertarIbErroresNominaDetallePj(@WebParam(name = "ibErroresNominaDetallePj")IbErroresNominaDetallePjDTO ibErroresNominaDetallePjDTO){
        return this.ibErroresNominaDetallePjDAO.insertarIbErroresNominaDetallePj(ibErroresNominaDetallePjDTO);
    }
    
     /**
     * Metodo para consultar un error relacionado a la carga de nomina
     * Oracle 11
     *
     * @param idError BigDecimal, identificador del error a buscar
     * @return UtilDTO
     */
    @WebMethod(operationName = "consultarIbErroresNominaDetallePjById")
    public UtilDTO consultarIbErroresNominaDetallePjById(@WebParam(name = "idError")BigDecimal idError){
        return this.ibErroresNominaDetallePjDAO.consultarIbErroresNominaDetallePjById(idError);
    }
    
     /**
     * Metodo para consultar un error relacionado a la carga de nomina
     * Oracle 11
     *
     * @param idPago BigDecimal, identificador del error a buscar
     * @return UtilDTO
     */
    @WebMethod(operationName = "consultarIbErroresNominaDetallePjByPago")
    public UtilDTO consultarIbErroresNominaDetallePjByPago(@WebParam(name = "idPago")BigDecimal idPago){
        return this.consultarIbErroresNominaDetallePjByPago(idPago);
    }
    
     /**
     * Metodo para consultar un error relacionado a la carga de nomina
     * Oracle 11
     *
     * @param idPago BigDecimal, identificador del error a buscar
     * @return IbErroresNominaDetallePjDTO
     */
    @WebMethod(operationName = "buscarIbErroresNominaDetallePjDTO")
    public IbErroresNominaDetallePjDTO buscarIbErroresNominaDetallePjDTO(@WebParam(name = "idError")BigDecimal idError){
        return new IbErroresNominaDetallePjDTO();
    }
    
     /**
     * Metodo para listar los errores relacionados a la carga de nomina
     * Oracle 11
     *
     * @param idPago BigDecimal, identificador del pago que posee la lista de errorres
     * @return IbErroresNominaDetallePjDTO
     */
    @WebMethod(operationName = "listarCargaNominaErroresByPago")
    public IbErroresNominaDetallePjDTO listarCargaNominaErroresByPago(
            @WebParam(name = "idPago")BigDecimal idPago,
            @WebParam(name = "codigoCanal")String codigoCanal           
    ){
        return this.ibErroresNominaDetallePjDAO.listarCargaNominaErroresByPago(idPago, codigoCanal);
    }    
}
