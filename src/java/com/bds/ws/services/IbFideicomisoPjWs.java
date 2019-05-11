/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbFideicomisoPjDAO;
import com.bds.ws.dto.IbCargaArchivoDTO;
import com.bds.ws.dto.IbErroresCargaPjDTO;
import com.bds.ws.dto.IbEstatusPagosPjDTO;
import com.bds.ws.dto.IbFideicomisoPjDTO;
import com.bds.ws.dto.UtilDTO;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author luis.perez
 */
@WebService(serviceName = "IbFideicomisoPjWs")
public class IbFideicomisoPjWs {

    @EJB
    private IbFideicomisoPjDAO ibFideicomisoPjDAO;

    /**
     * Metodo para modifica el estatus de la cabecera y el detalle Oracle 11 del
     * fideicomiso
     *
     * @param idFideicomisoPj de tipo long, identificador del registro a cambiar de estatus
     * @param ibEstatusPagosPjDTO objeto completo de tipo IbEstatusPagosPjDTO
     * que establecera el nuevo estatus del pago
     * @param idEmpresa BigDecimal id de la empresa de sesion
     * @param idCanal String id del canal a utilizar
     * @param codigoCanal String
     * @return UtilDTO
     */
    @WebMethod(operationName = "modificarEstatusIbFideicomisoPj")
    public UtilDTO modificarEstatusIbFideicomisoPj(@WebParam(name = "id") long idFideicomisoPj,
            @WebParam(name = "ibEstatusPagosPj") IbEstatusPagosPjDTO ibEstatusPagosPjDTO,
            @WebParam(name = "idEmpresa") BigDecimal idEmpresa,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        
        return ibFideicomisoPjDAO.modificarEstatusIbFideicomisoPj(idFideicomisoPj, ibEstatusPagosPjDTO, idEmpresa, idCanal, codigoCanal);
    }

    /**
     * Metodo procesarArchivoFideicomisoPj procesa el archivo de fideicomiso
     * Oracle 11
     *
     * @param ibCargaArchivoDTO
     * @return List<IbErroresCargaPjDTO>
     */
    @WebMethod(operationName = "procesarArchivoFideicomisoPj")
    public UtilDTO procesarArchivoFideicomisoPj(
            @WebParam(name = "ibCargaArchivoDTO") IbCargaArchivoDTO ibCargaArchivoDTO) {
        UtilDTO utilDTO = ibFideicomisoPjDAO.procesarArchivoPj(ibCargaArchivoDTO);

        return utilDTO;
    }

    /**
     * Metodo ibErroresCargaPjDTO requerido para cargar la clase
     * IbErroresCargaPjDTO Oracle 11
     *
     * @return 
     */
    @WebMethod(operationName = "ibErroresCargaFideicomisoPjDTO")
    public IbErroresCargaPjDTO ibErroresCargaFideicomisoPjDTO() {return new IbErroresCargaPjDTO();}

    @WebMethod(operationName = "listarIbFideicomisoPjDTO")
    public IbFideicomisoPjDTO listarIbFideicomisoPjDTO(
            @WebParam(name = "idfideicomiso") BigDecimal idfideicomiso,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return ibFideicomisoPjDAO.listarIbFideicomisoPj(idfideicomiso, idCanal, codigoCanal);
    }
    
    /**
     * Metodo eliminarIbFideicomisoPjEstatusCeroPj para eliminar registros con
       estatus cero Oracle 11
     *
     * @param id id de pago interno en IB
     * @param idCanal id de canal interno en IB
     * @param codigoCanal codigo de canal en el CORE Bancario
     * @return
     */
    @WebMethod(operationName = "eliminarIbFideicomisoPjEstatusCeroPj")
    public UtilDTO eliminarIbFideicomisoPjEstatusCeroPj(
            @WebParam(name = "idPago")      Long   id,
            @WebParam(name = "idCanal")     String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal
    ) {
        return ibFideicomisoPjDAO.eliminarIbFideicomisoPjEstatusCeroPj(id, idCanal, codigoCanal);
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
    @WebMethod(operationName = "listarIbCargaFideicomisoAutorizarPjDTO")
    public IbFideicomisoPjDTO listarIbCargaFideicomisoAutorizarPjDTO(
                @WebParam(name = "cdClienteAbanks")BigDecimal cdClienteAbanks, 
                @WebParam(name = "idUsuarioAurorizado") Long idUsuarioAurorizado,
                @WebParam(name = "idServicio") Long idServicio,
                @WebParam(name = "idCanal")String idCanal,
                @WebParam(name = "codigoCanal") String codigoCanal){
        return this.ibFideicomisoPjDAO.listarIbCargaFideicomisoAutorizarPj(cdClienteAbanks, idUsuarioAurorizado,idServicio, idCanal,  codigoCanal);
    }
}
