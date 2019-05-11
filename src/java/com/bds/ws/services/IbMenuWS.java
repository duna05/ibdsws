/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbMenuDAO;
import com.bds.ws.dto.IbModulosDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Clase de servicio web para menu
 * @author rony.rodriguez
 */
@WebService(serviceName = "IbMenuWS")
public class IbMenuWS {

    @EJB
    private IbMenuDAO ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    /**
     * Metodo que consulta el menu por usuario.
     * @param idUsuario String id del usuario
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return IbModulosDTO con modulos y transacciones
     */
    @WebMethod(operationName = "consultarModulosPorUsuario")
    public IbModulosDTO consultarModulosPorUsuario(@WebParam(name = "idUsuario") String idUsuario, @WebParam(name = "idCanal") String idCanal, @WebParam(name = "nombreCanal") String nombreCanal) {
        return ejbRef.consultarModulosPorUsuario(idUsuario, idCanal, nombreCanal);
    }

    /**
     * Método para obtener todos los módulos y transacciones asociadas a un canal en específico
     * esten activos o inactivos, visibles o no visibles
     * @author wilmer.rondon
     * @param idCanal canal del cual se desea obtener los modulos y las transacciones
     * @param nombreCanal nombre del canal para efectos de los logs
     * @return IbModulosDTO
     */
    @WebMethod(operationName = "obtenerTodosModulosYTransacciones")
    public IbModulosDTO obtenerTodosModulosYTransacciones(@WebParam(name = "idCanal") String idCanal, @WebParam(name = "nombreCanal") String nombreCanal) {
        return ejbRef.obtenerTodosModulosYTransacciones(idCanal, nombreCanal);
    }
}
