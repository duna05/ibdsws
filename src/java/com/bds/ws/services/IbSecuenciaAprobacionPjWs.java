/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbSecuenciaAprobacionPjDAO;
import com.bds.ws.dto.IbCargaNominaPjDTO;
import com.bds.ws.dto.IbSecuenciaAprobacionPjDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.model.IbSecuenciaAprobacionPj;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author juan.vasquez
 */
@WebService(serviceName = "IbSecuenciaAprobacionPjWs")
public class IbSecuenciaAprobacionPjWs {
    
    @EJB
    private IbSecuenciaAprobacionPjDAO ibSecuenciaAprobacionPjDAO;
   
     /**
     * Metodo para busqueda de una secuencia de aprobacion
     * Oracle 11
     *
     * @param id tipo BigDecimal, identificador de la secuencia
     * @return UtilDTO
     */  
    @WebMethod(operationName = "buscarSecuenciaDeAprobacion")
    public IbSecuenciaAprobacionPj buscarSecuenciaDeAprobacion(@WebParam(name = "id")BigDecimal id){
        //modificar metodo o eliminar
        return new IbSecuenciaAprobacionPj();
    }
    
     /**
     * Metodo para busqueda de una secuencia de aprobacion
     * Oracle 11
     *
     * @param id tipo BigDecimal, identificador de la secuencia
     * @return UtilDTO
     */  
    @WebMethod(operationName = "buscarSecuenciaDeAprobacionDTO")
    public IbSecuenciaAprobacionPjDTO buscarSecuenciaDeAprobacionDTO(@WebParam(name = "id")BigDecimal id){
        //modificar metodo o eliminar
        return new IbSecuenciaAprobacionPjDTO();
    }
    /**
     * Metodo para insertar una nueva secuencia de aprobacion
     * Oracle 11
     *
     * @param idPago tipo BigDecimal, identificador de IbCargaNominaPj
     * @param idUsuarioPj tipo BigDecimal, identificador de IbUsuariosPj
     * @return UtilDTO
     */    
    @WebMethod(operationName = "insertarSecuenciaDeAprobacionPj")
    public UtilDTO insertarSecuenciaDeAprobacionPj(@WebParam(name = "idServicio") BigDecimal idServicio, @WebParam(name = "idTransaccion" )Long idTransaccion, @WebParam(name = "idUsuarioPj" )BigDecimal idUsuarioPj){
        return this.ibSecuenciaAprobacionPjDAO.insertarSecuenciaDeAprobacion(idServicio, idTransaccion ,  idUsuarioPj);
                                                          
    }
     /**
     * Metodo para insertar una nueva secuencia de aprobacion
     * Oracle 11
     *
     * @param ibSecuenciaAprobacionPj objeto completo de IbSecuenciaAprobacionPj
     * @return UtilDTO
     */    
    @WebMethod(operationName = "insertarSecuenciaDeAprobacion")
    public UtilDTO insertarSecuenciaDeAprobacion(@WebParam(name = "ibSecuenciaAprobacionPj")IbSecuenciaAprobacionPjDTO ibSecuenciaAprobacionPjDTO){
        return this.ibSecuenciaAprobacionPjDAO.insertarSecuenciaDeAprobacion(ibSecuenciaAprobacionPjDTO);
    }
     /**
     * Metodo de busqueda segun el id del pago 
     * Oracle 11
     *
     * @param idPago tipo BigDecimal, identificador de IbCargaNominaPj
     * @return UtilDTO
     */
    @WebMethod(operationName = "listaDeSecDeAprobPorPago")
    public UtilDTO listaDeSecDeAprobPorPago(@WebParam(name = "idPago")BigDecimal idPago){
       return  null;
    }
    /**
     * Metodo de busqueda segun el id del pago 
     * Oracle 11
     *
     * @param pago tipo IbCargaNominaPj, objeto completo de IbCargaNominaPj
     * @return UtilDTO
     */
    @WebMethod(operationName = "listaDeSecDeAprobPorPagoDTO")
    public UtilDTO listaDeSecDeAprobPorPagoDTO(@WebParam(name = "pago")IbCargaNominaPjDTO pago){
        return  null;
        //return this.ibSecuenciaAprobacionPjDAO.listaDeSecuenciaDeAprobacionPorPago(pago);
    }
    
}
