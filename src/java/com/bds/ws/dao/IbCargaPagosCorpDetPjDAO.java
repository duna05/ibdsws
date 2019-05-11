/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbCargaPagosCorpDetPjDTO;
import com.bds.ws.dto.IbEstatusPagosPjDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.model.IbCargaPagosCorpDetPj;
import java.math.BigDecimal;

/**
 *
 * @author luis.perez
 */
public interface IbCargaPagosCorpDetPjDAO {

    /**
     * Metodo buscar el detalle de pagos corporativos Oracle 11
     *
     * @param idpago id del pago realizado
     * @param estatusCarga estatus de la carga
     * @param idCanal id de canal interno en IB
     * @param codigoCanal codigo de canal asignado por el CORE bancario
     * @return
     */
    public IbCargaPagosCorpDetPjDTO listarIbCargaPagosCorpDetPjDTO(BigDecimal idpago, Short estatusCarga, String idCanal, String codigoCanal);

    /**
     * Metodo para insertar una nueva cabecera de proceso de carga de pagos
     * corporativos Oracle 11
     *
     * @param ibCargaPagosCorpDetPj objeto completo de IbCargaPagosEspPjDTO
     * @param codigoCanal String del canal a utilizar
     * @return UtilDTO
     */
    public UtilDTO insertarIbCargaPagosCorpDetPj(IbCargaPagosCorpDetPj ibCargaPagosCorpDetPj, String codigoCanal);

    /**
     * Metodo para modificar una nueva cabecera de proceso de carga de pagos
     * corporativos Oracle 11
     *
     * @param idPago de tipo BigDecimal, identificador del pago a cambiar de
     * estatus
     * @param ibEstatusPagosPjDTO objeto completo de tipo IbEstatusPagosPjDTO
     * que establecera el nuevo estatus del pago
     * @param codigoCanal String
     * @return UtilDTO
     */
    public UtilDTO modificarEstatusIbCargaPagosCorpDetPj(Long idPago, IbEstatusPagosPjDTO ibEstatusPagosPjDTO, String codigoCanal);

}
