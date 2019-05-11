/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbCargaPagosEspPjDAO;
import com.bds.ws.dto.IbCargaArchivoDTO;
import com.bds.ws.dto.IbCargaPagosEspPjDTO;
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
 * @author robinson rodriguez
 */
@WebService(serviceName = "IbCargaPagosEspPjWs")
public class IbCargaPagosEspPjWs {

    @EJB
    private IbCargaPagosEspPjDAO ibCargaPagosEspPjDAO;

    /**
     * Metodo buscar una nueva cabecera de registros masivos de pagos especiales
     * Oracle 11
     *
     * @param cdClienteAbanks de tipo BigDecimal, identificador del pago a
     * cambiar de estatus el nuevo estatus del pago
     * @param estatusCarga id del estatus de la carga
     * @param idCanal id del canal interno en IB
     * @param codigoCanal codigo de canal asignado por el CORE banario
     * @return IbRegistrosMasivosPjDTO
     */
    @WebMethod(operationName = "listarIbCargaPagosEspPjDTO")
    public IbCargaPagosEspPjDTO listarIbCargaPagosEspPjDTO(
            @WebParam(name = "cdClienteAbanks") BigDecimal cdClienteAbanks,
            @WebParam(name = "estatusCarga") Short estatusCarga,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return ibCargaPagosEspPjDAO.listarIbCargaPagosEspPj(cdClienteAbanks, estatusCarga, idCanal, codigoCanal);
    }

    /**
     * Metodo para modifica el estatus de la cabecera y el detalle Oracle 11
     *
     * @param idPago de tipo long, identificador del pago a cambiar de estatus
     * @param ibEstatusPagosPjDTO objeto completo de tipo IbEstatusPagosPjDTO
     * que establecera el nuevo estatus del pago
     * @param idEmpresa BigDecimal id de la empresa de sesion
     * @param idCanal String id del canal a utilizar
     * @param codigoCanal String
     * @return UtilDTO
     */
    @WebMethod(operationName = "modificarEstatusIbCargaPagosEspecialesPj")
    public UtilDTO modificarEstatusIbCargaPagosEspecialesPj(@WebParam(name = "idPago") Long idPago,
            @WebParam(name = "ibEstatusPagosPj") IbEstatusPagosPjDTO ibEstatusPagosPjDTO,
            @WebParam(name = "idEmpresa") BigDecimal idEmpresa,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {

        return this.ibCargaPagosEspPjDAO.modificarEstatusIbCargaPagosEspPj(idPago, ibEstatusPagosPjDTO, idEmpresa, idCanal, codigoCanal);
    }

    /**
     * Metodo procesarArchivoPagosEspPj procesa el archivo de pagos especiales
     * Oracle 11
     * @param ibCargaArchivoDTO
     * @return UtilDTO
     */
    @WebMethod(operationName = "procesarArchivoPagosEspecialesPj")
    public UtilDTO procesarArchivoPagosEspecialesPj(
            @WebParam(name = "ibCargaArchivoDTO") IbCargaArchivoDTO ibCargaArchivoDTO
    ) {
        return this.ibCargaPagosEspPjDAO.procesarArchivoPagosEspecialesPj(ibCargaArchivoDTO);
    }

    /**
     * Metodo ibErroresCargaPjDTO requerido para cargar la clase
     * IbErroresCargaPjDTO Oracle 11
     *
     * @return IbErroresCargaPjDTO
     */
    @WebMethod(operationName = "ibErroresCargaPagosEspPjDTO")
    public IbErroresCargaPjDTO ibErroresCargaPagosEspPjDTO() {return new IbErroresCargaPjDTO();}

    /**
     * Metodo buscar una nueva cabecera de proceso de nomina Oracle 11
     *
     * @param idPago de tipo BigDecimal, identificador del pago a cambiar de
     * estatus el nuevo estatus del pago
     * @return
     */
    @WebMethod(operationName = "buscarIbCargaPagosEspPjDTO")
    public IbCargaPagosEspPjDTO buscarIbCargaPagosEspPjDTO(
            @WebParam(name = "clienteAbanks") BigDecimal clienteAbanks,
            @WebParam(name = "idPago") Long idPago,
            @WebParam(name = "fechaInicio") Date fechaInicio,
            @WebParam(name = "fechaFin") Date fechaFin) {
        return this.ibCargaPagosEspPjDAO.buscarIbCargaPorParametrosPagosEspPj(clienteAbanks, idPago, fechaInicio, fechaFin);
    }
    
    
    /**
     * Devuelve el listado autorizado para un usuario
     * @param cdClienteAbanks
     * @param idUsuarioAurorizado
     * @param idServicio
     * @param idCanal
     * @param codigoCanal
     * @return 
     */
    @WebMethod(operationName = "listarIbCargaPagosEspAutorizarPjDTO")
    public IbCargaPagosEspPjDTO listarIbCargaPagosEspAutorizarPjDTO(
                @WebParam(name = "cdClienteAbanks")BigDecimal cdClienteAbanks, 
                @WebParam(name = "idUsuarioAurorizado") Long idUsuarioAurorizado,
                @WebParam(name = "idServicio") Long idServicio,
                @WebParam(name = "idCanal")String idCanal,
                @WebParam(name = "codigoCanal") String codigoCanal){
        return this.ibCargaPagosEspPjDAO.listarIbCargaPagosEspAutorizarPj(cdClienteAbanks, idUsuarioAurorizado,idServicio, idCanal,  codigoCanal);
    }
}
