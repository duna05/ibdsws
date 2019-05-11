/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbCargaArchivoDTO;
import com.bds.ws.dto.IbCargaPagosCorpDetPjDTO;
import com.bds.ws.dto.IbCargaPagosCorpPjDTO;
import com.bds.ws.dto.IbEstatusPagosPjDTO;
import com.bds.ws.dto.UtilDTO;
import java.math.BigDecimal;
import java.util.Date;

/**ertert
 *
 * @author luis.perez
 */
public interface IbCargaPagosCorpPjDAO {

    /**
     * Metodo para listar las cabeceras del proceso de carga de pagos corporativos Oracle 11
     *
     * @param cdClienteAbanks codigo del cliente en el core bancario
     * @param estatusCarga estatus en el que se encuentran los registros
     * @param idCanal String id del canal a utilizar
     * @param codigoCanal String
     * @return IbCargaPagosEspPjDTO
     */
    public IbCargaPagosCorpPjDTO listarIbCargaPagosCorpPj(BigDecimal cdClienteAbanks, Short estatusCarga, String idCanal, String codigoCanal);

    /**
     * Metodo para insertar una nueva cabecera de proceso de carga de pagos corporativos Oracle 11
     *
     * @param ibCargaPagosCorpPjDTO objeto completo de IbCargaPagosCorpPjDTO
     * @param ibCargaPagosEspDetPjDTO IbCargaPagosEspDetPjDTO
     * @param idCanal String id del canal a utilizar
     * @param codigoCanal String del canal a utilizar
     * @return UtilDTO
     */
    public UtilDTO insertarIbCargaPagosCorpPj(IbCargaPagosCorpPjDTO ibCargaPagosCorpPjDTO, IbCargaPagosCorpDetPjDTO ibCargaPagosEspDetPjDTO, String idCanal, String codigoCanal);

    /**
     * Metodo para modificar una nueva cabecera de proceso de carga de pagos corporativos Oracle 11
     *
     * @param idPago de tipo BigDecimal, identificador del pago a cambiar de estatus
     * @param ibEstatusPagosPjDTO objeto completo de tipo IbEstatusPagosPjDTO que establecera el nuevo estatus del pago
     * @param idEmpresa BigDecimal id de la empresa de sesion
     * @param idCanal String id del canal a utilizar
     * @param codigoCanal String
     * @return UtilDTO
     */
    public UtilDTO modificarEstatusIbCargaPagosCorpPj(Long idPago, IbEstatusPagosPjDTO ibEstatusPagosPjDTO, BigDecimal idEmpresa, String idCanal, String codigoCanal);

    /**
     * Metodo para leer de la interfaz el archivo de carga de pagos corporativos Oracle 11
     *
     * @param ibCargaArchivoDTO el cual posee codigoUsuario cdClienteAbanks :
     * codigo abanks filename nombre del archivo de nomina enviado data data en
     * bytes del archivo idCanal String id del canal a utilizar codigoCanal
     * String del canal a utilizar
     * @return UtilDTO
     */
    public UtilDTO procesarArchivoPagosCorpPj(IbCargaArchivoDTO ibCargaArchivoDTO);
    
        /**
     * Metodo para leer  de la interfaz el archivo de pagos corporativos
     * Oracle 11
     *
     * @param idPago        id de pago interno en IB
     * @param codigoCanal   codigo de canal en el CORE Bancario
     * @return 
     */    
    
    public UtilDTO eliminarCargaMasivaEstatusCeroPj(BigDecimal idPago, String codigoCanal);
    
    /**
     * Lista los pagos corporativos que el usuario puede autorizar
     * @param cdClienteAbanks
     * @param idUsuarioAurorizado
     * @param idServicio
     * @param idCanal
     * @param codigoCanal
     * @return 
     */
    public IbCargaPagosCorpPjDTO listarIbCargaPagosCorpAutorizarPj(BigDecimal cdClienteAbanks, Long idUsuarioAurorizado,Long idServicio, String idCanal, String codigoCanal);
    
    /**
     * Lista los pagos corporativos que el usuario puede autorizar
     * @param cdClienteAbanks
     * @param idpago
     * @param fechaInicio
     * @param fechaFin
     * @param idCanal
     * @param codigoCanal
     * @return 
     */
    public IbCargaPagosCorpPjDTO listarIbCargaPagosCorpAdminPj(BigDecimal clienteAbanks,Long idPago,Date fechaInicio, Date fechaFin);
}
