/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbServiciosPerfilesPjDAO;
import com.bds.ws.dto.IbServiciosPjDTO;
import com.bds.ws.dto.UtilDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author luis.perez
 */
@WebService(serviceName = "IbServiciosPerfilesPjWs")
public class IbServiciosPerfilesPjWs {
    @EJB
    IbServiciosPerfilesPjDAO ibServiciosPerfilesPjDAO;

    /**
     * Metodo que consulta el menu por usuario persona juridica
     *
     * @param idPerfil id de perfil del usuario
     * @param estatus  estatus del modulo A = activo, I = inactivo
     * @param idCanal  String idCanal por el cual se ejecuta la transaccion.
     * @param codigoCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return IbModulosDTO con modulos y transacciones
     */
    @WebMethod(operationName = "consultarModulosServiciosPerfilesPj")
    public UtilDTO consultarModulosServiciosPerfilesPj(
            @WebParam(name = "idPerfil")    String idPerfil,
            @WebParam(name = "estatus")     Character estatus,
            @WebParam(name = "idCanal")     String idCanal,
            @WebParam(name = "nombreCanal") String codigoCanal) {
        return ibServiciosPerfilesPjDAO.consultarServiciosPerfilesPj(idPerfil, estatus, idCanal, codigoCanal);
    }
    
    @WebMethod(operationName = "consultarModulosServiciosPerfilesPjIbServiciosPjDTOP")
    public IbServiciosPjDTO consultarModulosServiciosPerfilesPjIbServiciosPjDTOP(@WebParam(name = "prueba") String prueba) {
        return null;
    }
}
