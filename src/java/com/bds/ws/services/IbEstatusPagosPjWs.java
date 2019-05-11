/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbEstatusPagosPjDAO;
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
@WebService(serviceName = "IbEstatusPagosPjWs")
public class IbEstatusPagosPjWs {
    
    @EJB
    private IbEstatusPagosPjDAO ibEstatusPagosPjDAO;
    
 /**
     * Metodo para insertar un estatus de pagos nuevo
     * Oracle 11
     *
     * @param ibEstatusPagosPjDTO objeto completo de IbEstatusPagosPjDTO
     * @return UtilDTO
     */
    @WebMethod(operationName = "insertarIbEstatusPagosPj")
    public UtilDTO insertarIbEstatusPagosPj(@WebParam(name = "ibEstatusPagosPj")IbEstatusPagosPjDTO ibEstatusPagosPjDTO){
        return this.ibEstatusPagosPjDAO.insertarIbEstatusPagosPj(ibEstatusPagosPjDTO);
    }
    /**
     * Metodo para insertar un estatus de pagos nuevo
     * Oracle 11
     *
     * @param nombre tipo String, nombre con el cual se identificara el estatus
     * @param descripcion tipo String, breve texto explicativo del estatus a crear
     * @return UtilDTO
     */
    @WebMethod(operationName = "insertarIbEstatusPago")
    public UtilDTO insertarIbEstatusPagos(@WebParam(name = "nombre")String nombre, @WebParam(name = "descripcion")String descripcion){
        return this.ibEstatusPagosPjDAO.insertarIbEstatusPagosPj(nombre, descripcion);
    }
     /**
     * Metodo para modificar un estatus de pagos ya creado
     * Oracle 11
     *
     * @param ibEstatusPagosPj objeto completo de IbEstatusPagosPj a 
     * modificar previamente consultado
     * @return UtilDTO
     */
    @WebMethod(operationName = "modificarIbEstatusPagosPj")
    public UtilDTO modificarIbEstatusPagosPj(@WebParam(name = "ibEstatusPagosPj")IbEstatusPagosPjDTO ibEstatusPagosPjDTO){
        return this.ibEstatusPagosPjDAO.modificarIbEstatusPagosPj(ibEstatusPagosPjDTO);
    }
    /**
     * Metodo para consultar un estatus de pagos por numero de identificador
     * Oracle 11
     *
     * @param ibEstatusPagosPj objeto completo de IbEstatusPagosPj a 
     * modificar previamente consultado
     * @return UtilDTO
     */
    @WebMethod(operationName = "consultarIbEstatusPagosPj")
    public UtilDTO consultarIbEstatusPagosPj(@WebParam(name = "idEstatus")BigDecimal idEstatus){
        return this.ibEstatusPagosPjDAO.consultarIbEstatusPagosPjById(idEstatus);
    }
    
    /**
     * Metodo para consultar un estatus de pagos por numero de identificador
     * Oracle 11
     *
     * @param ibEstatusPagosPj objeto completo de IbEstatusPagosPj a 
     * modificar previamente consultado
     * @return UtilDTO
     */
    @WebMethod(operationName = "buscarIbEstatusPagosPjDTO")
    public IbEstatusPagosPjDTO buscarIbEstatusPagosPjDTO(@WebParam(name = "idEstatus")BigDecimal idEstatus){
        //Modificar metodo
        return new IbEstatusPagosPjDTO();
    }
}
