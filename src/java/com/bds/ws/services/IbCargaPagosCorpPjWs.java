
package com.bds.ws.services;

import com.bds.ws.dao.IbCargaPagosCorpPjDAO;
import com.bds.ws.dto.IbCargaArchivoDTO;
import com.bds.ws.dto.IbCargaPagosCorpDetPjDTO;
import com.bds.ws.dto.IbCargaPagosCorpPjDTO;
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
 * @author luis.perez
 */
@WebService(serviceName = "IbCargaPagosCorpPjWs")
public class IbCargaPagosCorpPjWs {

    @EJB
    private IbCargaPagosCorpPjDAO ibCargaPagosCorpPjDAO;
    
    
    /**
     * Metodo para insertar un pago coporativo manual Oracle 11
     *
     * @param ibCargaPagosCorpPjDTO
     * @param ibCargaPagosCorpDetPjDTO
     * @param idCanal
     * @param codigoCanal
     * @return UtilDTO
     */
    @WebMethod(operationName = "insertarIbCargaPagosCorpPj")
    public UtilDTO insertarIbCargaPagosCorpPj(
            @WebParam(name = "ibCargaPagosCorpPjDTO")    IbCargaPagosCorpPjDTO    ibCargaPagosCorpPjDTO,
            @WebParam(name = "IbCargaPagosCorpDetPjDTO") IbCargaPagosCorpDetPjDTO ibCargaPagosCorpDetPjDTO, 
            @WebParam(name = "idCanal")     String idCanal, 
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return ibCargaPagosCorpPjDAO.insertarIbCargaPagosCorpPj(ibCargaPagosCorpPjDTO, ibCargaPagosCorpDetPjDTO, idCanal, codigoCanal);
    }
    
    /**
     * Metodo buscar una nueva cabecera de registros masivos de pagos corporativos
     * Oracle 11
     *
     * @param cdClienteAbanks de tipo BigDecimal, identificador del pago a
     * cambiar de estatus el nuevo estatus del pago
     * @param estatusCarga id del estatus de la carga
     * @param idCanal id del canal interno en IB
     * @param codigoCanal codigo de canal asignado por el CORE banario
     * @return IbRegistrosMasivosPjDTO
     */
    @WebMethod(operationName = "listarIbCargaPagosCorpPjDTO")
    public IbCargaPagosCorpPjDTO listarIbCargaPagosCorpPjDTO(@WebParam(name = "cdClienteAbanks") BigDecimal cdClienteAbanks,
            @WebParam(name = "estatusCarga") Short estatusCarga,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return  ibCargaPagosCorpPjDAO.listarIbCargaPagosCorpPj(cdClienteAbanks, estatusCarga, idCanal, codigoCanal);
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
    @WebMethod(operationName = "modificarEstatusIbCargaPagosCorpPj")
    public UtilDTO modificarEstatusIbCargaPagosCorpPj(@WebParam(name = "idPago") Long idPago,
            @WebParam(name = "ibEstatusPagosPj") IbEstatusPagosPjDTO ibEstatusPagosPjDTO,
            @WebParam(name = "idEmpresa") BigDecimal idEmpresa,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return ibCargaPagosCorpPjDAO.modificarEstatusIbCargaPagosCorpPj(idPago, ibEstatusPagosPjDTO, idEmpresa, idCanal, codigoCanal);
    }

    /**
     * Metodo procesarArchivoPagosEspPj procesa el archivo de pagos corporativos
     * Oracle 11
     * @param ibCargaArchivoDTO
     * @return UtilDTO
     */
    @WebMethod(operationName = "procesarArchivoPagosCorpPj")
    public UtilDTO procesarArchivoPagosCorpPj(
            @WebParam(name = "ibCargaArchivoDTO") IbCargaArchivoDTO ibCargaArchivoDTO
    ) {
        return this.ibCargaPagosCorpPjDAO.procesarArchivoPagosCorpPj(ibCargaArchivoDTO);
    }

    /**
     * Metodo ibErroresCargaPjDTO requerido para cargar la clase
     * IbErroresCargaPjDTO Oracle 11
     *
     * @return IbErroresCargaPjDTO
     */
    @WebMethod(operationName = "ibErroresCargaPagosCorpPjDTO")
    public IbErroresCargaPjDTO ibErroresCargaPagosCorpPjDTO() {return new IbErroresCargaPjDTO();}

    /**
     * Metodo buscar una nueva cabecera de proceso de nomina Oracle 11
     *
     * @param idPago de tipo BigDecimal, identificador del pago a cambiar de
     * estatus el nuevo estatus del pago
     * @return
     */
    @WebMethod(operationName = "buscarIbCargaPagosCorpPjDTO")
    public IbCargaPagosCorpPjDTO buscarIbCargaPagosCorpPjDTO(@WebParam(name = "idPago") BigDecimal idPago) {
        return new IbCargaPagosCorpPjDTO();
    }
    
        /**
     * Metodo eliminarCargaMasivaEstatusCeroPj
     *    para eliminar registros con estatus cero
     * Oracle 11
     *
     * @param idPago        id de pago interno en IB
     * @param codigoCanal   codigo de canal en el CORE Bancario
     * @return 
     */
    @WebMethod(operationName = "eliminarCargaMasivaPagosCorpEstatusCeroPj")
    public UtilDTO eliminarCargaMasivaPagosCorpEstatusCeroPj(
                @WebParam(name = "idPago") BigDecimal idPago,
                @WebParam(name = "codigoCanal") String codigoCanal
                ){
        return this.ibCargaPagosCorpPjDAO.eliminarCargaMasivaEstatusCeroPj(idPago, codigoCanal);
    }
    
    /**
     * Lista los pagos corporativos que el usuario puede autorizar
     * @param cdClienteAbanks
     * @param idUsuarioAurorizado
     * @param idServicio
     * @param idCanal
     * @param codigoCanal
     * @return 
     */
    @WebMethod(operationName = "listarIbCargaPagosCorpAutorizarPjDTO")
    public IbCargaPagosCorpPjDTO listarIbCargaPagosCorpAutorizarPjDTO(
                @WebParam(name = "cdClienteAbanks")BigDecimal cdClienteAbanks, 
                @WebParam(name = "idUsuarioAurorizado") Long idUsuarioAurorizado,
                @WebParam(name = "idServicio") Long idServicio,
                @WebParam(name = "idCanal")String idCanal,
                @WebParam(name = "codigoCanal") String codigoCanal){
        return this.ibCargaPagosCorpPjDAO.listarIbCargaPagosCorpAutorizarPj(cdClienteAbanks, idUsuarioAurorizado,idServicio, idCanal,  codigoCanal);
    }
    
     /**
     * Lista los pagos corporativos que el usuario puede autorizar
     * @param cdClienteAbanks
     * @param idUsuarioAurorizado
     * @param idServicio
     * @param idCanal
     * @param codigoCanal
     * @return 
     */
    @WebMethod(operationName = "listarIbCargaPagosCorpAdminPjDTO")
    public IbCargaPagosCorpPjDTO listarIbCargaPagosCorpAdminPjDTO(
                @WebParam(name = "clienteAbanks") BigDecimal clienteAbanks,
                @WebParam(name = "idPago") Long idPago,
                @WebParam(name = "fechaInicio") Date fechaInicio,
                @WebParam(name = "fechaFin") Date fechaFin){
        return this.ibCargaPagosCorpPjDAO.listarIbCargaPagosCorpAdminPj(clienteAbanks, idPago, fechaInicio, fechaFin);
    }
}
