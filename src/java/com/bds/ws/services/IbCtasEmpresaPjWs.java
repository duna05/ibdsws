/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbCtasEmpresaPjDAO;
import com.bds.ws.dto.CuentaDTO;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author luis.perez
 */
@WebService(serviceName = "IbCtasEmpresaPjWs")
public class IbCtasEmpresaPjWs {
    @EJB
    private IbCtasEmpresaPjDAO ibCtasEmpresaPjDAO;
    
    /**
     * Metodo que obtiene el listado de cuentas de ahorro y corrientes una empresa
     * en relacion a un usuario
     *
     * @param idUsuario id del usuario en IB
     * @param idEmpresa id de la empresa en IB
     * @param idCanal id del canal interno en ib
     * @param codigoCanal codigo de canal asignado en el core bancario
     * @return ClienteDTO listado de cuentas de ahorro y corrientes que posee un
     * cliente
     */
    @WebMethod(operationName = "listarCuentasEmpresaUsuarioPj")
    public List<CuentaDTO> listarCuentasEmpresaUsuarioPj(
            @WebParam(name = "idUsuario")   String idUsuario,
            @WebParam(name = "idEmpresa")   String idEmpresa,
            @WebParam(name = "idCanal")     String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return ibCtasEmpresaPjDAO.listarCuentasEmpresaUsuarioPj(idUsuario, idEmpresa, idCanal, codigoCanal);
    }
}
