/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbClavesOperacionesEspecialesDAO;
import com.bds.ws.dto.UtilDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author ledwin.belen
 */
@WebService(serviceName = "IbClavesOperacionesEspecialesWs")
public class IbClavesOperacionesEspecialesWs {

    @EJB
    IbClavesOperacionesEspecialesDAO ibClavesOperacionesEspecialesDAO;

    /**
     * Métod para validar la clave de operaciones especiales porporcionada por
     * el usuario
     *
     * @param idUsuario
     * @param claveOP
     * @param idCanal
     * @param codigoCanal
     * @return
     */
    @WebMethod(operationName = "validarClaveOP")
    public UtilDTO validarClaveOP(
            @WebParam(name = "idUsuario") String idUsuario,
            @WebParam(name = "claveOP") String claveOP,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return ibClavesOperacionesEspecialesDAO.validarClaveOP(idUsuario, claveOP, idCanal, codigoCanal);
    }

    /**
     * Método para crear o modificar la clave de operaciones especiales
     *
     * @param idUsuario
     * @param claveOP
     * @param idUsuarioCarga
     * @param sw
     * @param idCanal
     * @param codigoCanal
     * @return
     */
    @WebMethod(operationName = "insertarActualizarClaveOP")
    public UtilDTO insertarActualizarClaveOP(
            @WebParam(name = "idUsuario") String idUsuario,
            @WebParam(name = "claveOP") String claveOP,
            @WebParam(name = "idUsuarioCarga") String idUsuarioCarga,
            @WebParam(name = "sw") boolean sw,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return ibClavesOperacionesEspecialesDAO.insertarActualizarClaveOP(idUsuario, claveOP, idUsuarioCarga, sw, idCanal, codigoCanal);
    }

    /**
     * Valida que el usuario tenga un registro de las claves de operaciones especiales
     * @param idUsuario
     * @param idCanal
     * @param codigoCanal
     * @return 
     */
    @WebMethod(operationName = "validarExisteClaveOperacionesEspeciales")
    public boolean validarExisteClaveOperacionesEspeciales(
            @WebParam(name = "idUsuario") String idUsuario,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return ibClavesOperacionesEspecialesDAO.validarExisteClaveOperacionesEspeciales(idUsuario, idCanal, codigoCanal);
    }
}
