/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbCtasEmpresaUsuarioPjDAO;
import com.bds.ws.dto.IbCtaEmpresaPjDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author cesar.mujica
 */
@WebService(serviceName = "IbCtasEmpresaUsuarioPjWS")
public class IbCtasEmpresaUsuarioPjWS {

    @EJB
    private IbCtasEmpresaUsuarioPjDAO ejbRef;

    /**
     * Metodo que se +encarga de consultar las cuentas asociadas a una empresa y un usario
     * Oracle 11++
     *
     *
     * @param idUsuario id del usuario en IB
     * @param idEmpresa
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return Objeto de Respuesta con el resultado de la transaccion.
     */
    @WebMethod(operationName = "obtenerIbCtasEmpresaUsuarioPj")
    public IbCtaEmpresaPjDTO obtenerIbCtasEmpresaUsuarioPj(@WebParam(name = "idUsuario") String idUsuario, @WebParam(name = "idEmpresa") String idEmpresa, @WebParam(name = "idCanal") String idCanal, @WebParam(name = "codigoCanal") String codigoCanal) {
        return ejbRef.obtenerIbCtasEmpresaUsuarioPj(idUsuario, idEmpresa, idCanal, codigoCanal);
    }     
    
}
