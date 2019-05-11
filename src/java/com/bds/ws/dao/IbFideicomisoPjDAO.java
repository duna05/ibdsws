/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbCargaArchivoDTO;
import com.bds.ws.dto.IbEstatusPagosPjDTO;
import com.bds.ws.dto.IbFideicomisoDetPjDTO;
import com.bds.ws.dto.IbFideicomisoPjDTO;
import com.bds.ws.dto.UtilDTO;
import java.math.BigDecimal;

/**
 *
 * @author robinson.rodriguez
 */
public interface IbFideicomisoPjDAO {

    /**
     * Metodo para listar las cabeceras de la consulta fideicomiso
     * Oracle 11
     *
     * @param cdClienteAbanks codigo del cliente en el core bancario
     * @param idCanal String id del canal a utilizar
     * @param codigoCanal String
     * @return IbFideicomisoPjDTO
     */
    public IbFideicomisoPjDTO listarIbFideicomisoPj(BigDecimal cdClienteAbanks, String idCanal, String codigoCanal);
    
    /**
     * Metodo procesarArchivoFideicomisoPj procesa el archivo de fideicomiso
     * Oracle 11
     *
     * @param ibCargaArchivoDTO
     * @return List<IbErroresCargaPjDTO>
     */
    public UtilDTO procesarArchivoPj(IbCargaArchivoDTO ibCargaArchivoDto); 
    
    public UtilDTO insertarIbFideicomisoPj(IbFideicomisoPjDTO ibFideicomisoPjDTO, IbFideicomisoDetPjDTO ibFideicomisoDetPjDTO, String idCanal, String codigoCanal);
    
    /**
     * Metodo para modificar una nueva cabecera de proceso de carga de pagos
     * especiales Oracle 11
     *
     * @param idFideicomisoPj id del beneficiario
     * @param ibEstatusPagosPjDTO objeto completo de tipo IbEstatusPagosPjDTO
     * que establecera el nuevo estatus del pago
     * @param idEmpresa BigDecimal id de la empresa de sesion
     * @param idCanal String id del canal a utilizar
     * @param codigoCanal String
     * @return UtilDTO
     */
    public UtilDTO modificarEstatusIbFideicomisoPj(Long idFideicomisoPj, IbEstatusPagosPjDTO ibEstatusPagosPjDTO, BigDecimal idEmpresa, String idCanal, String codigoCanal);

    /**
     * Metodo eliminarIbFideicomisoPjEstatusCeroPj para eliminar registros con
       estatus cero Oracle 11
     *
     * @param id id de pago interno en IB
     * @param idCanal id de canal interno en IB
     * @param codigoCanal codigo de canal en el CORE Bancario
     * @return
     */
    public UtilDTO eliminarIbFideicomisoPjEstatusCeroPj(Long id,String idCanal,String codigoCanal);
    
    /**
     * Devuelve el listado autorizado para un usuario
     * @param cdClienteAbanks
     * @param idUsuarioAurorizado
     * @param idServicio
     * @param idCanal
     * @param codigoCanal
     * @return 
     */
    public IbFideicomisoPjDTO listarIbCargaFideicomisoAutorizarPj(BigDecimal cdClienteAbanks, Long idUsuarioAurorizado,Long idServicio, String idCanal, String codigoCanal);
}    