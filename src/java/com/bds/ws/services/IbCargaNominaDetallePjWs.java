/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbCargaNominaDetallePjDAO;
import com.bds.ws.dto.IbCargaNominaDetallePjDTO;
import com.bds.ws.dto.IbEstatusPagosPjDTO;
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
@WebService(serviceName = "IbCargaNominaDetallePjWs")
public class IbCargaNominaDetallePjWs {
    
    @EJB
    private IbCargaNominaDetallePjDAO ibCargaNominaDetallePjDAO;
    
        /**
     * Metodo para insertar una nueva cabecera de proceso de nomina
     * Oracle 11
     *
     * @param ibCargaNominaDetallePjDTO objeto completo de IbCargaNominaDetallePjDTO
     * @return UtilDTO
     */
    @WebMethod(operationName = "insertarIbCargaNominaDetallePj")
    public UtilDTO insertarIbCargaNominaDetallePj(@WebParam(name = "ibCargaNominaDetallePj")IbCargaNominaDetallePjDTO ibCargaNominaDetallePjDTO, @WebParam(name ="codigoCanal") String codigoCanal){
        return this.ibCargaNominaDetallePjDAO.insertarIbCargaNominaDetallePj(ibCargaNominaDetallePjDTO, codigoCanal);
    }
    /**
     * Metodo para insertar una nueva cabecera de proceso de nomina
     * Oracle 11
     *
     * @param idPagoDetalle de tipo BigDecimal, identificador del pago a cambiar de estatus
     * @param ibEstatusPagosPjDTO objeto completo de tipo IbEstatusPagosPjDTO que establecera
     * el nuevo estatus del pago
     * @return UtilDTO
     */
    @WebMethod(operationName = "modificarEstatusIbCargaNominaDetallePj")
    public UtilDTO modificarEstatusIbCargaNominaDetallePj(@WebParam(name = "idPagoDetalle")BigDecimal idPagoDetalle,@WebParam(name = "ibEstatusPagosPj") IbEstatusPagosPjDTO ibEstatusPagosPjDTO){
        return this.modificarEstatusIbCargaNominaDetallePj(idPagoDetalle, ibEstatusPagosPjDTO);
    }
    
    /**
     * Metodo para buscar un detalle de una nomina precargada
     * Oracle 11
     *
     * @param idPagoDetalle de tipo BigDecimal, identificador del pago a cambiar de estatus
     * el nuevo estatus del pago
     * @return IbCargaNominaDetallePjDTO
     */
    @WebMethod(operationName = "buscarIbCargaNominaDetallePjDTO")
    public IbCargaNominaDetallePjDTO buscarIbCargaNominaDetallePjDTO(@WebParam(name = "idDetalle")BigDecimal idDetalle){
        //modificar metodo
        return new IbCargaNominaDetallePjDTO();
    }
    
         /**
     * Metodo lista eldetalle de una cabecera de proceso de nomina
     * Oracle 11
     *
     * @param idpago de tipo BigDecimal, identificador del pago 
     * @param estatusCarga id del estatus de la carga
     * @param codigoCanal de tipo String, codigo de canal
     * el nuevo estatus del pago
     * @return 
     */
    @WebMethod(operationName = "listarIbCargaNominaDetallePjDTO")
    public IbCargaNominaDetallePjDTO listarIbCargaNominaDetallePjDTO(
            @WebParam(name = "idpago")BigDecimal idpago,
            @WebParam(name = "estatusCarga") Short estatusCarga,
            @WebParam(name = "codigoCanal")String codigoCanal){
        
        return this.ibCargaNominaDetallePjDAO.listarIbCargaNominaDetallePj(idpago,estatusCarga, codigoCanal);
    }
}
