/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbCargaBeneficiariosPjDAO;
import com.bds.ws.dto.IbCargaArchivoDTO;
import com.bds.ws.dto.IbCargaBeneficiarioManualDTO;
import com.bds.ws.dto.IbCargaBeneficiarioManualModificarDTO;
import com.bds.ws.dto.IbCargaBeneficiariosPjDTO;
import com.bds.ws.dto.IbErroresCargaPjDTO;
import com.bds.ws.dto.IbEstatusPagosPjDTO;
import com.bds.ws.dto.UtilDTO;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author robinson.rodriguez
 */
@WebService(serviceName = "IbCargaBeneficiariosPjWs")
public class IbCargaBeneficiariosPjWs {

    @EJB
    private IbCargaBeneficiariosPjDAO ibCargaBeneficiariosPjDAO;

    /**
     * Metodo buscar una nueva cabecera de registros masivos de beneficiario
     * Oracle 11
     *
     * @param idEmpresa id de la empresa
     * @param estatusCarga estatus en el que se encuentran los registros
     * @param idCanal String id del canal a utilizar
     * @param codigoCanal String
     * @return IbCargaBeneficiariosPjDTO
     */
    @WebMethod(operationName = "listarIbCargaBeneficiariosPj")
    public IbCargaBeneficiariosPjDTO listarIbCargaBeneficiariosPj(@WebParam(name = "idEmpresa") BigDecimal idEmpresa,
            @WebParam(name = "estatusCarga") Short estatusCarga,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return ibCargaBeneficiariosPjDAO.listarIbCargaBeneficiariosPj(idEmpresa, estatusCarga, idCanal, codigoCanal);
    }

    /**
     * Metodo para modifica el estatus de la cabecera y el detalle Oracle 11
     *
     * @param idCargaBeneficiario id de carga beneficiario
     * @param ibEstatusPagosPjDTO estatus de la carga beneficiario
     * @param idEmpresa id de la empresa
     * @param idCanal String id del canal a utilizar
     * @param codigoCanal String del canal a utilizar
     * @return UtilDTO
     */
    @WebMethod(operationName = "modificarEstatusIbCargaBeneficiariosPj")
    public UtilDTO modificarEstatusIbCargaBeneficiariosPj(@WebParam(name = "idCargaBeneficiario") Long idCargaBeneficiario,
            @WebParam(name = "ibEstatusPagosPj") IbEstatusPagosPjDTO ibEstatusPagosPjDTO,
            @WebParam(name = "idEmpresa") BigDecimal idEmpresa,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {

        return this.ibCargaBeneficiariosPjDAO.modificarEstatusIbCargaBeneficiariosPj(idCargaBeneficiario, ibEstatusPagosPjDTO, idEmpresa, idCanal, codigoCanal);
    }

    /**
     * Metodo procesarArchivoPagosEspPj procesa el archivo de beneficiario
     * Oracle 11
     *
     * @param ibCargaArchivoDTO
     * @return List<IbErroresCargaPjDTO>
     */
    @WebMethod(operationName = "procesarArchivoIbCargaBeneficiariosPj")
    public UtilDTO procesarArchivoIbCargaBeneficiariosPj(
            @WebParam(name = "ibCargaArchivoDTO") IbCargaArchivoDTO ibCargaArchivoDTO
    ) {
        return this.ibCargaBeneficiariosPjDAO.procesarArchivoIbCargaBeneficiariosPj(ibCargaArchivoDTO);
    }

    /**
     * Metodo ibErroresCargaPjDTO requerido para cargar la clase
     * IbErroresCargaPjDTO Oracle 11
     *
     * @return IbErroresCargaPjDTO
     */
    @WebMethod(operationName = "ibErroresCargaMasivaPjDTO")
    public IbErroresCargaPjDTO ibErroresCargaMasivaPjDTO() {
        return new IbErroresCargaPjDTO();
    }

    /**
     * Metodo buscar una nueva cabecera de proceso de nomina Oracle 11
     *
     * @param idCargaBeneficiario de tipo Long, identificador del pago a cambiar
     * de estatus el nuevo estatus del pago
     * @return
     */
    @WebMethod(operationName = "buscarIbCargaBeneficiariosPjDTO")
    public IbCargaBeneficiariosPjDTO buscarIbCargaBeneficiariosPjDTO(@WebParam(name = "idCargaBeneficiario") Long idCargaBeneficiario) {
        return new IbCargaBeneficiariosPjDTO();
    }

    @WebMethod(operationName = "eliminarIbCargaBeneficiariosPjEstatusCero")
    public UtilDTO eliminarIbCargaBeneficiariosPjEstatusCero(
            @WebParam(name = "idCargaBeneficiario") Long idCargaBeneficiario,
            @WebParam(name = "codigoCanal") String codigoCanal
    ) {
        return this.ibCargaBeneficiariosPjDAO.eliminarIbCargaBeneficiariosPjEstatusCero(idCargaBeneficiario, codigoCanal);
    }

    /**
     * Metodo procesarRegistroManualIbCargaBeneficiariosPj procesa el registro
     * manual del beneficiario Oracle 11
     *
     * @param ibCargaBeneficiarioManualDTO
     * @return UtilDTO
     */
    @WebMethod(operationName = "procesarRegistroManualIbCargaBeneficiariosPj")
    public UtilDTO procesarRegistroManualIbCargaBeneficiariosPj(
            @WebParam(name = "ibCargaBeneficiarioManualDTO") IbCargaBeneficiarioManualDTO ibCargaBeneficiarioManualDTO
    ) {
        return this.ibCargaBeneficiariosPjDAO.procesarRegistroManualIbCargaBeneficiariosPj(ibCargaBeneficiarioManualDTO);
    }

    /**
     * Metodo procesarRegistroManualModificarIbCargaBeneficiariosPj procesa el
     * registro manual del beneficiario Oracle 11
     *
     * @param ibCargaBeneficiarioManualModificarDTO
     * @return UtilDTO
     */
    @WebMethod(operationName = "procesarRegistroManualModificarIbCargaBeneficiariosPj")
    public UtilDTO procesarRegistroManualModificarIbCargaBeneficiariosPj(
            @WebParam(name = "IbCargaBeneficiarioManualModificarDTO") IbCargaBeneficiarioManualModificarDTO ibCargaBeneficiarioManualModificarDTO,
            @WebParam(name = "canal") String canal,
            @WebParam(name = "codigoCanal") String codigoCanal
    ) {
        return this.ibCargaBeneficiariosPjDAO.procesarRegistroManualModificarIbCargaBeneficiariosPj(ibCargaBeneficiarioManualModificarDTO, canal, codigoCanal);
    }
    
    /**
     * Metodo buscar una nueva cabecera de registros masivos de beneficiario
     * Oracle 11
     *
     * @param idEmpresa id de la empresa
     * @param estatusCarga estatus en el que se encuentran los registros
     * @param idCanal String id del canal a utilizar
     * @param codigoCanal String
     * @return IbCargaBeneficiariosPjDTO
     */
    @WebMethod(operationName = "listarIbCargaBeneficiariosAutorizarPj")
    public IbCargaBeneficiariosPjDTO listarIbCargaBeneficiariosAutorizarPj(
            @WebParam(name = "idEmpresa") BigDecimal idEmpresa,
            @WebParam(name = "idUsuarioAurorizado") Long idUsuarioAurorizado,
            @WebParam(name = "idServicio") Long idServicio,
            @WebParam(name = "StatusAConsultar") String StatusAConsultar,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return ibCargaBeneficiariosPjDAO.listarIbCargaBeneficiariosAutorizarPj(idEmpresa, idUsuarioAurorizado, idServicio, StatusAConsultar, idCanal, codigoCanal);

    }
    
    

}
