/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbCargaNominaDetallePjDTO;
import com.bds.ws.dto.IbEstatusPagosPjDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.model.IbCargaNominaDetallePj;
import java.math.BigDecimal;

/**
 *
 * @author juan.vasquez
 */
public interface IbCargaNominaDetallePjDAO {
    
    
    /**
     * Metodo para insertar una nueva cabecera de proceso de nomina
     * Oracle 11
     *
     * @param ibCargaNominaDetallePj objeto completo de IbCargaNominaDetallePj
     * @param codigoCanal codigo del canal
     * @return UtilDTO de ser exitoso la transaccion retorna el valor en el mapa 
     * resultados con el key ibCargaNominaDetallePj
     */
    public UtilDTO insertarIbCargaNominaDetallePj(IbCargaNominaDetallePj ibCargaNominaDetallePj, String codigoCanal);
    /**
     * Metodo para insertar una nueva cabecera de proceso de nomina
     * Oracle 11
     *
     * @param ibCargaNominaDetallePjDTO objeto completo de IbCargaNominaDetallePjDTO
     * @param codigoCanal codigo del canal
     * @return UtilDTO
     */
    public UtilDTO insertarIbCargaNominaDetallePj(IbCargaNominaDetallePjDTO ibCargaNominaDetallePjDTO, String codigoCanal);
    /**
     * Metodo para insertar una nueva cabecera de proceso de nomina
     * Oracle 11
     *
     * @param idPago
     * @param idPagoDetalle de tipo BigDecimal, identificador del pago a cambiar de estatus
     * @param ibEstatusPagosPjDTO objeto completo de tipo IbEstatusPagosPjDTO que establecera
     * el nuevo estatus del pago
     * @param codigoCanal String
     * @return UtilDTO
     */
    public UtilDTO modificarEstatusIbCargaNominaDetallePj(BigDecimal idPago, IbEstatusPagosPjDTO ibEstatusPagosPjDTO, String codigoCanal);
    
        /**
     * Metodo para listar los detalles de una cabecera
     * Oracle 11
     *
     * @param idpago identificador unico del pago
     * @param codigoCanal codigo del canal
     * @return IbCargaNominaDetallePjDTO
     */
    public IbCargaNominaDetallePjDTO listarIbCargaNominaDetallePj(BigDecimal idpago,Short estatusCarga, String codigoCanal);

}
