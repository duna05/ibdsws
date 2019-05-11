/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbCargaPagosEspDetPjDTO;
import com.bds.ws.dto.IbEstatusPagosPjDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.model.IbCargaPagosEspDetPj;
import java.math.BigDecimal;

/**
 *
 * @author robinson rodriguez
 */
public interface IbCargaPagosEspDetPjDAO {

    /**
     * Metodo buscar el detalle de pagos especiales Oracle 11
     *
     * @param idpago id del pago realizado
     * @param estatusCarga estatus de la carga
     * @param idCanal id de canal interno en IB
     * @param codigoCanal codigo de canal asignado por el CORE bancario
     * @return
     */
    public IbCargaPagosEspDetPjDTO listarIbCargaPagosEspDetPjDTO(BigDecimal idpago, Short estatusCarga, String idCanal, String codigoCanal);

    /**
     * Metodo para insertar una nueva cabecera de proceso de carga de pagos
     * especiales Oracle 11
     *
     * @param detalle objeto completo de IbCargaPagosEspPjDTO
     * @param codigoCanal String del canal a utilizar
     * @return UtilDTO
     */
    public UtilDTO insertarIbCargaPagosEspPj(IbCargaPagosEspDetPj detalle, String codigoCanal);

    /**
     * Metodo para modificar una nueva cabecera de proceso de carga de pagos
     * especiales Oracle 11
     *
     * @param idPago de tipo BigDecimal, identificador del pago a cambiar de
     * estatus
     * @param ibEstatusPagosPjDTO objeto completo de tipo IbEstatusPagosPjDTO
     * que establecera el nuevo estatus del pago
     * @param codigoCanal String
     * @return UtilDTO
     */
    public UtilDTO modificarEstatusIbCargaPagosEspDetPj(Long idPago, IbEstatusPagosPjDTO ibEstatusPagosPjDTO, String codigoCanal);
}
