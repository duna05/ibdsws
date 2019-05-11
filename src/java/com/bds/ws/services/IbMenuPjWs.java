/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbMenuPjDAO;
import com.bds.ws.dto.IbServiciosPjDTO;
import com.bds.ws.dto.UtilDTO;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author luis.perez
 */
@WebService(serviceName = "IbMenuPjWs")
public class IbMenuPjWs {

    @EJB
    IbMenuPjDAO ibMenuPjDAO;

    /**
     * Metodo que consulta el menu por usuario persona juridica
     *
     * @param idUsuario String id del usuario
     * @param idEmpresa id de la Empresa en IB
     * @param estatus estatus del modulo A = activo, I = inactivo
     * @param visible visible o no 1 = visible, 0 = no visible
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param codigoCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return IbModulosDTO con modulos y transacciones
     */
    @WebMethod(operationName = "consultarModulosServPorEmpresaUsuarioPj")
    public UtilDTO consultarModulosServPorEmpresaUsuarioPj(
            @WebParam(name = "idUsuario") String idUsuario,
            @WebParam(name = "idEmpresa") String idEmpresa,
            @WebParam(name = "estatus") Character estatus,
            @WebParam(name = "visible") Character visible,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "nombreCanal") String codigoCanal) {
        return ibMenuPjDAO.consultarModulosServPorEmpresaUsuarioPj(idUsuario, idEmpresa, estatus, visible, idCanal, codigoCanal);
    }
    
    /**
     * Metodo que consulta las urls permitidas para un servicio
     *
     * @param idUsuario String id del usuario
     * @param idEmpresa id de la Empresa en IB
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param codigoCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return List<String> con las urls permitidas
     */
    @WebMethod(operationName = "consultarUrlPorServicioUsuario")
    public List<String> consultarUrlPorServicioUsuario(
            @WebParam(name = "idUsuario") String idUsuario,
            @WebParam(name = "idEmpresa") String idEmpresa,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return ibMenuPjDAO.consultarUrlPorServicioUsuario(idUsuario, idEmpresa, idCanal, codigoCanal);
    }

    @WebMethod(operationName = "IbServiciosPjDTOP")
    public IbServiciosPjDTO IbModulosPjDTO(@WebParam(name = "prueba") String prueba) {
        return null;
    }
}
