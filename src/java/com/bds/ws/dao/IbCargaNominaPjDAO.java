/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbCargaArchivoDTO;
import com.bds.ws.dto.IbCargaNominaDetallePjDTO;
import com.bds.ws.dto.IbCargaNominaPjDTO;
import com.bds.ws.dto.IbEstatusPagosPjDTO;
import com.bds.ws.dto.UtilDTO;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author juan.vasquez
 */
public interface IbCargaNominaPjDAO {
    
     /**
     * Metodo para insertar una nueva cabecera de proceso de nomina
     * Oracle 11
     *
     * @param ibCargaNominaPjDTO objeto completo de IbCargaNominaPjDTO
     * @param nominaDetalleDTO IbCargaNominaDetallePjDTO
     * @param idCanal String id del canal a utilizar
     * @param codigoCanal String del canal a utilizar    
     * @return UtilDTO
     */
    public UtilDTO insertarIbCargaNominaPj(IbCargaNominaPjDTO ibCargaNominaPjDTO, IbCargaNominaDetallePjDTO nominaDetalleDTO, String idCanal, String codigoCanal) ;
    /**
     * Metodo para busca los estatus del proceso de nomina
     * Oracle 11
     *
     * @param idEstatus
     * @return UtilDTO
     */
    public UtilDTO buscarEstatusNomina(String idEstatus);
    /**
     * Metodo para insertar una nueva cabecera de proceso de nomina
     * Oracle 11
     *
     * @param idPago de tipo BigDecimal, identificador del pago a cambiar de estatus
     * @param ibEstatusPagosPjDTO objeto completo de tipo IbEstatusPagosPjDTO que establecera
     * el nuevo estatus del pago
     * @param idEmpresa BigDecimal id de la empresa de sesion
     * @param idCanal String id del canal a utilizar
     * @param codigoCanal String
     * @return UtilDTO
     */
    public UtilDTO modificarEstatusIbCargaNominaPj(BigDecimal idPago, IbEstatusPagosPjDTO ibEstatusPagosPjDTO, BigDecimal idEmpresa, String idCanal, String codigoCanal);


    /**
     * Metodo para consulta las cabeceras de proceso de nomina
     * Oracle 11
     *
     * @return IbCargaNominaPjDTO
     */
    public IbCargaNominaPjDTO consultaIbCargaNominaPj();
    
    
    
    

    /**
     * Metodo para consulta las detalles y error de proceso de nomina
     * Oracle 11
     *
     * @return IbCargaNominaDetallePjDTO
     */
    public IbCargaNominaDetallePjDTO consultaIbCargaNominaDetallePj();

    /**
     * Metodo para listar las cabeceras del proceso de nomina
     * Oracle 11
     *
     * @param cdClienteAbanks codigo del cliente en el core bancario
     * @param estatusCarga estatus en el que se encuentran los registros
     * @param idCanal String id del canal a utilizar
     * @param codigoCanal String
     * @return IbCargaNominaPjDTO
     */    
    public IbCargaNominaPjDTO listarIbCargaNominaPj(BigDecimal cdClienteAbanks, Short estatusCarga, String idCanal, String codigoCanal);
    /**
     * Metodo para listar las cabeceras del proceso de nomina
     * Oracle 11
     * @param cdClienteAbanks codigo del cliente en el core bancario
     * @param idUsuarioAurorizado
     * @param idServicio
     * @param idCanal String id del canal a utilizar
     * @param codigoCanal String
     * @return IbCargaNominaPjDTO
     */  
    public IbCargaNominaPjDTO listarIbCargaNominaAutorizarPj(BigDecimal cdClienteAbanks, Long idUsuarioAurorizado,Long idServicio, String idCanal, String codigoCanal);
    
    /**
     * Metodo para leer  de la interfaz el archivo de nomina
     * Oracle 11
     *
     * @param ibCargaArchivoDto el cual posee 
     *    codigoUsuario
     *    cdClienteAbanks : codigo abanks 
     *    filename  nombre del archivo de nomina enviado
     *    data  data en bytes del archivo
     *    idCanal String id del canal a utilizar
     *    codigoCanal String del canal a utilizar
     * @return List< IbErroresCargaPjDTO >:  Lista de lineas con problemas y sus respectivos errores
     */    
    
    public UtilDTO procesarArchivoNominaPj(IbCargaArchivoDTO ibCargaArchivoDto);
    

    /**
     * Metodo para leer  de la interfaz el archivo de nomina
     * Oracle 11
     *
     * @param idPago        id de pago interno en IB
     * @param codigoCanal   codigo de canal en el CORE Bancario
     * @return 
     */    
    
    public UtilDTO eliminarCargaMasivaEstatusCeroPj(BigDecimal idPago, String codigoCanal);
    /**
     * Metodo para leer  de la interfaz el consulta nomina corporativa desde el administrador
     * Oracle 11
     * @param idPago        id de pago interno en IB
     * @param clienteAbanks   codigo de canal en el CORE Bancario
     * * @param fechaInicio  
     * * @param fechaFin  
     * @return 
     */ 
    public IbCargaNominaPjDTO listarCargaNominaPjAdmin(BigDecimal clienteAbanks,Long idPago,Date fechaInicio, Date fechaFin);
}
