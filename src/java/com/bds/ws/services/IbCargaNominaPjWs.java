/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbCargaNominaPjDAO;
import com.bds.ws.dto.IbCargaArchivoDTO;
import com.bds.ws.dto.IbCargaNominaDetallePjDTO;
import com.bds.ws.dto.IbCargaNominaPjDTO;
import com.bds.ws.dto.IbErroresCargaPjDTO;
import com.bds.ws.dto.IbEstatusPagosPjDTO;
import com.bds.ws.dto.UtilDTO;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author juan.vasquez
 */
@WebService(serviceName = "IbCargaNominaPjWs")
public class IbCargaNominaPjWs {
    
    @EJB
    private IbCargaNominaPjDAO ibCargaNominaPjDAO;
    
   
     /**
     * Metodo para insertar una nueva cabecera de proceso de nomina
     * Oracle 11
     *
     * @param ibCargaNominaPjDTO objeto completo de IbCargaNominaPjDTO
     * @param nominaDetalleDTO IbCargaNominaDetallePjDTO
     * @return UtilDTO
     */
    @WebMethod(operationName = "insertarIbCargaNominaPj")
    public UtilDTO insertarIbCargaNominaPj(@WebParam(name = "ibCargaNominaPjDTO")IbCargaNominaPjDTO ibCargaNominaPjDTO, 
            @WebParam(name = "nominaDetalleDTO") IbCargaNominaDetallePjDTO nominaDetalleDTO, @WebParam(name = "idCanal") String idCanal, @WebParam(name = "codigoCanal") String codigoCanal){
        return this.ibCargaNominaPjDAO.insertarIbCargaNominaPj(ibCargaNominaPjDTO, nominaDetalleDTO, idCanal, codigoCanal);
    }
    
    
     /**
     * Metodo para insertar una nueva cabecera de proceso de nomina
     * Oracle 11
     *
     * @param idEstatus
     * @param ibCargaNominaPjDTO objeto completo de IbCargaNominaPjDTO
     * @param nominaDetalleDTO IbCargaNominaDetallePjDTO
     * @return UtilDTO
     */
    @WebMethod(operationName = "buscarEstatusNominaPj")
    public UtilDTO buscarEstatusNominaPj(@WebParam(name = "idEstatus")String  idEstatus){
        return this.ibCargaNominaPjDAO.buscarEstatusNomina(idEstatus);
    }
     
    /**
     * Metodo para modifica el estatus de la cabecera y el detalle
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
    @WebMethod(operationName = "modificarEstatusIbCargaNominaPj")
    public UtilDTO modificarEstatusIbCargaNominaPj(@WebParam(name = "idPago")BigDecimal idPago, 
                                                    @WebParam(name = "ibEstatusPagosPj")IbEstatusPagosPjDTO ibEstatusPagosPjDTO,
                                                    @WebParam(name = "idEmpresa")BigDecimal idEmpresa,
                                                    @WebParam(name = "idCanal")String idCanal,
                                                    @WebParam(name = "codigoCanal") String codigoCanal){
        return this.ibCargaNominaPjDAO.modificarEstatusIbCargaNominaPj(idPago,ibEstatusPagosPjDTO, idEmpresa, idCanal, codigoCanal);
    }
    
     /**
     * Metodo buscar una nueva cabecera de proceso de nomina
     * Oracle 11
     *
     * @param idPago de tipo BigDecimal, identificador del pago a cambiar de estatus
     * el nuevo estatus del pago
     * @return 
     */
    @WebMethod(operationName = "buscarIbCargaNominaPjDTO")
    public IbCargaNominaPjDTO buscarIbCargaNominaPjDTO(@WebParam(name = "idPago")BigDecimal idPago){
        return new IbCargaNominaPjDTO();
    }
    
     /**
     * Metodo buscar una nueva cabecera de proceso de nomina
     * Oracle 11
     *
     * @param cdClienteAbanks de tipo BigDecimal, identificador del pago a cambiar de estatus
     * el nuevo estatus del pago
     * @param estatusCarga estatus de la carga
     * @param idCanal String id del canal a utilizar
     * @param codigoCanal String del canal a utilizar
     * @return 
     */
    @WebMethod(operationName = "listarIbCargaNominaPjDTO")
    public IbCargaNominaPjDTO listarIbCargaNominaPjDTO(
                @WebParam(name = "cdClienteAbanks")BigDecimal cdClienteAbanks, 
                @WebParam(name = "estatusCarga") Short estatusCarga,
                @WebParam(name = "idCanal")String idCanal,
                @WebParam(name = "codigoCanal") String codigoCanal){
        return this.ibCargaNominaPjDAO.listarIbCargaNominaPj(cdClienteAbanks, estatusCarga, idCanal,  codigoCanal);
    }
    
      /**
     * Metodo buscar una nueva cabecera de proceso de nomina
     * Oracle 11
     *
     * @param cdClienteAbanks de tipo BigDecimal, identificador del pago a cambiar de estatus
     * el nuevo estatus del pago
     * @param estatusCarga estatus de la carga
     * @param idCanal String id del canal a utilizar
     * @param codigoCanal String del canal a utilizar
     * @return 
     */
    @WebMethod(operationName = "listarIbCargaNominaAutorizarPjDTO")
    public IbCargaNominaPjDTO listarIbCargaNominaAutorizarPjDTO(
                @WebParam(name = "cdClienteAbanks")BigDecimal cdClienteAbanks, 
                @WebParam(name = "idUsuarioAurorizado") Long idUsuarioAurorizado,
                @WebParam(name = "idServicio") Long idServicio,
                @WebParam(name = "idCanal")String idCanal,
                @WebParam(name = "codigoCanal") String codigoCanal){
        return this.ibCargaNominaPjDAO.listarIbCargaNominaAutorizarPj(cdClienteAbanks, idUsuarioAurorizado,idServicio, idCanal,  codigoCanal);
    }
    
    
     /**
     * Metodo procesarArchivoNominaPj una nueva cabecera de proceso de nomina
     * Oracle 11
     *
     * @param ibCargaArchivoDTO 
     * @return  List<IbErroresCargaPjDTO>
     */
    @WebMethod(operationName = "procesarArchivoNominaPj")   
    public UtilDTO procesarArchivoNominaPj(
                @WebParam(name = "ibCargaArchivoDTO") IbCargaArchivoDTO ibCargaArchivoDTO
        ){
        return this.ibCargaNominaPjDAO.procesarArchivoNominaPj(ibCargaArchivoDTO); 
    }
    
     /**
     * Metodo ibErroresCargaPjDTO requerido 
     *    para cargar la clase IbErroresCargaPjDTO
     * Oracle 11
     *
     * @return 
     */
    @WebMethod(operationName = "ibErroresCargaMasivaPjDTO")   
    public IbErroresCargaPjDTO ibErroresCargaMasivaPjDTO(){return new IbErroresCargaPjDTO();}
    
    /**
     * Metodo eliminarCargaMasivaEstatusCeroPj
     *    para eliminar registros con estatus cero
     * Oracle 11
     *
     * @param idPago        id de pago interno en IB
     * @param codigoCanal   codigo de canal en el CORE Bancario
     * @return 
     */
    @WebMethod(operationName = "eliminarCargaMasivaEstatusCeroPj")
    public UtilDTO eliminarCargaMasivaEstatusCeroPj(
                @WebParam(name = "idPago") BigDecimal idPago,
                @WebParam(name = "codigoCanal") String codigoCanal
                ){
        return this.ibCargaNominaPjDAO.eliminarCargaMasivaEstatusCeroPj(idPago, codigoCanal);
    }
    
     /**
     * Metodo buscar una nueva cabecera de proceso de nomina
     * Oracle 11
     *
     * @param clienteAbanks de tipo BigDecimal, identificador del pago a cambiar de estatus
     * el nuevo estatus del pago
     * @param idPago estatus de la carga
     * @param fechaInicio String id del canal a utilizar
     * @param fechaFin String del canal a utilizar
     * @return 
     */
    @WebMethod(operationName = "listarIbCargaNominaAdminPjDTO")
    public IbCargaNominaPjDTO listarIbCargaNominaAdminPjDTO(
                @WebParam(name = "clienteAbanks") BigDecimal clienteAbanks,
                @WebParam(name = "idPago") Long idPago,
                @WebParam(name = "fechaInicio") Date fechaInicio,
                @WebParam(name = "fechaFin") Date fechaFin){
        return this.ibCargaNominaPjDAO.listarCargaNominaPjAdmin(clienteAbanks, idPago, fechaInicio, fechaFin);
    }
}
