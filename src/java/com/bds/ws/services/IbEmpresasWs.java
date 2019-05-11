/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbEmpresasDAO;
import com.bds.ws.dto.IbEmpresasDTO;
import com.bds.ws.dto.UtilDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author luis.perez
 */
@WebService(serviceName = "IbEmpresasWs")
public class IbEmpresasWs {
    @EJB
    private IbEmpresasDAO empresasDAO;
    
    /**
     * Realiza la validacion de la empresa
     *
     * @param tipoRif tipo de persona, natural o juridica
     * @param rif numero de rif
     * @param idCanal id de canal interno
     * @param codigoCanal codigo de canal en el CORE
     * @return UtilDTO
     */
    public UtilDTO validarEmpresa(@WebParam(name = "tipoRif") Character tipoRif, @WebParam(name = "rif") String rif, @WebParam(name = "idCanal") String idCanal, @WebParam(name = "codigoCanal") String codigoCanal) {
        return empresasDAO.validarEmpresa(tipoRif, rif, idCanal,codigoCanal);
    }
    
    /**
     * Metodo que se encarga de registrar los datos de una emprea en BD
     * Oracle 11++
     *
     *
     * @param nroDoc numero de documento de indentificacion
     * @param tipoDoc tipo de documento del usuario
     * @param nroCuenta numero de cuenta del cliente
     * @param codigoEmpresa codigo de la empresa en el core
     * @param nombre nombre de la empresa
     * @param tipoAcceso centralizado o centralizado
     * @param estatus    activa, en proceso de afiliacion, inactiva
     * @param idCanal    id de canal interno en ib
     * @param codigoCanal codigo de canal en el CORE
     * @return Objeto de Respuesta con el resultado de la transaccion.
     */
    @WebMethod(operationName = "insertarDatosEmpresa")
    public UtilDTO insertarDatosEmpresaPj(@WebParam(name = "nroDoc") String nroDoc,
            @WebParam(name = "tipoDoc") Character tipoDoc,
            @WebParam(name = "nroCuenta") String nroCuenta,
            @WebParam(name = "codigoEmpresa") Long codigoEmpresa,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "tipoAcceso") Character tipoAcceso,
            @WebParam(name = "estatus")    Character estatus,
            @WebParam(name = "idCanal")     String   idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {

        return empresasDAO.insertarDatosEmpresa(nroDoc,tipoDoc,nroCuenta,codigoEmpresa,nombre,tipoAcceso,estatus, idCanal, codigoCanal);
    }
    
    /**
     * Metodo que se +encarga de consultar los datos asociados a una empresa
     * Oracle 11++
     *
     *
     * @param idUsuario id del usuario en IB
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return Objeto de Respuesta con el resultado de la transaccion.
     */
    @WebMethod(operationName = "consultarDatosEmpresaUsuario")
    public UtilDTO consultarDatosEmpresaUsuario(@WebParam(name = "idUsuario") String idUsuario,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return empresasDAO.consultarDatosEmpresaUsuario(idUsuario, idCanal, codigoCanal);
    }
    
    @WebMethod(operationName = "pruebaEmpresaaaa")
    public IbEmpresasDTO pruebaEmpresaaaa(
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        
        return null;
    }
}
