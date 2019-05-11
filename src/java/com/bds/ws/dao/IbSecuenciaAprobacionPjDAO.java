/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbSecuenciaAprobacionPjDTO;
import com.bds.ws.dto.UtilDTO;
import java.math.BigDecimal;

/**
 *
 * @author juan.vasquez
 */
public interface IbSecuenciaAprobacionPjDAO {
     /**
     * Metodo para insertar una nueva secuencia de aprobacion
     * Oracle 11
     *
     * @param idPago tipo BigDecimal, identificador de IbCargaNominaPj
     * @param idUsuarioPj tipo BigDecimal, identificador de IbUsuariosPj
     * @return UtilDTO
     */    
    public UtilDTO insertarSecuenciaDeAprobacion(BigDecimal idServicio,Long idTransaccion , BigDecimal idUsuarioPj);
     /**
     * Metodo para insertar una nueva secuencia de aprobacion
     * Oracle 11
     *
     * @param ibSecuenciaAprobacionPjDTO objeto completo de IbSecuenciaAprobacionPjDTO
     * @return UtilDTO
     */    
    public UtilDTO insertarSecuenciaDeAprobacion(IbSecuenciaAprobacionPjDTO ibSecuenciaAprobacionPjDTO);
    
       /**
     * Metodo para insertar una nueva secuencia de aprobacion
     * Oracle 11
     *
     * @param idPago tipo BigDecimal, identificador de IbCargaNominaPj
     * @param idUsuarioPj tipo BigDecimal, identificador de IbUsuariosPj
     * @return UtilDTO
     */    
    public UtilDTO buscarAprobacionesMultiples(BigDecimal idServicio,Long idTransaccion);
     
}
