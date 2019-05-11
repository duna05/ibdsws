/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;
import com.bds.ws.dao.IbAprobacionesServiciosPjDAO;
import com.bds.ws.dto.IbAprobacionesServiciosPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author luis.perez
 */
@WebService(serviceName = "IbAprobacionesServiciosPjWs")
public class IbAprobacionesServiciosPjWs {
   
    @EJB
    IbAprobacionesServiciosPjDAO  ibAprobacionesServiciosPjDAO;
    /**
     * Lista las aprobaciones por servicio para una empresa
     *
     * Parametros 
     * 
     * @param idEmpresa codigo interno de la empresa en IB
     * @param idCanal   codigo del canal interno en IB
     * @param codigoCanal nombre del canal en el CORE
     *
     * @return UtilDTO
     */
    @WebMethod(operationName = "consultarAprobacionesEmpresaServiciosPj")
    public IbAprobacionesServiciosPjDTO consultarAprobacionesEmpresaServiciosPj(
            @WebParam(name = "idEmpresa") String idEmpresa,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal
    ) {
        return ibAprobacionesServiciosPjDAO.consultarAprobacionesEmpresaServiciosPj(idEmpresa,idCanal, codigoCanal);        
    }
    
    /**
     * Inserta la cantidad de aprobadores por servicio para una empresa
     *
     * Parametros
     *
     * @param ibAprobacionesServiciosPjDTO lista de servicios con su cantidad de aprobadores
     * @param idCanal codigo del canal interno en IB
     * @param codigoCanal nombre del canal en el CORE
     *
     * @return UtilDTO
     */
    @WebMethod(operationName = "insertarAprobacionesEmpresaServiciosPj")
    public RespuestaDTO insertarAprobacionesEmpresaServiciosPj(
            @WebParam(name = "ibAprobacionesServiciosPjDTO") IbAprobacionesServiciosPjDTO ibAprobacionesServiciosPjDTO,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal
    ) {
        return ibAprobacionesServiciosPjDAO.insertarAprobacionesEmpresaServiciosPj(ibAprobacionesServiciosPjDTO, idCanal, codigoCanal);
    }

    @WebMethod(operationName = "consultarByEmpresa")
    public IbAprobacionesServiciosPjDTO consultarByEmpresa(
            @WebParam(name = "cdClienteAbanks") BigDecimal cdClienteAbanks,
            @WebParam(name = "servicio") String servicio,
            @WebParam(name = "idCanal") String idCanal
    ) {
        return ibAprobacionesServiciosPjDAO.consultarByEmpresa(cdClienteAbanks, servicio, idCanal);
    }
    
}
