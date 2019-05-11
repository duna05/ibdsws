/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbServiEmpreUsuariosPjDAO;
import com.bds.ws.dto.IbServiEmpreUsuariosPjDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author luis.perez
 */
@WebService(serviceName = "IbServiEmpreUsuariosPjWs")
public class IbServiEmpreUsuariosPjWs {
    @EJB
    IbServiEmpreUsuariosPjDAO ibServiEmpreUsuariosPjDAO;

    /**
     * *
     * Metodo que retorna una lista de IbServiEmpreUsuariosPj
     *
     * @param idUsuario id del usuario en IB
     * @param idEmpresa id de la empresa en IB
     * @param idCanal id de canal interno en IB
     * @param codigoCanal codigo de canal en el CORE Bancario
     * @return List IbServiEmpreUsuariosPjDTO
     *
     */
    @WebMethod(operationName = "consultarIbServiEmpreUsuariosPj")
    public IbServiEmpreUsuariosPjDTO consultarIbServiEmpreUsuariosPj(
            @WebParam(name = "idUsuario")   String idUsuario,
            @WebParam(name = "idEmpresa")   String idEmpresa,
            @WebParam(name = "idCanal")     String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return ibServiEmpreUsuariosPjDAO.consultarIbServiEmpreUsuariosPj(idUsuario, idEmpresa, idCanal, codigoCanal);
    }
}
