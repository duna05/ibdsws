/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.ReferenciaDAO;
import com.bds.ws.dto.ReferenciaDTO;
import com.bds.ws.dto.UtilDTO;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author rony.rodriguez
 */
@WebService(serviceName = "ReferenciaWS")
public class ReferenciaWS {

    @EJB
    private ReferenciaDAO referenciaDAO;

    /**
     * Obtiene los datos de las referencias bancarias.
     *
     * @param codigoCliente codigo del cliente
     * @param tipoRef tipo de referencia
     * @param nroCuenta numero de cuenta
     * @param nombreCanal codigo de canal
     * @return ReferenciaDTO Datos de la referencia
     */
    @WebMethod(operationName = "obtenerDatosReferencias")
    public ReferenciaDTO obtenerDatosReferencias(
            @WebParam(name = "codigoCliente") String codigoCliente, @WebParam(name = "tipoRef") String tipoRef, @WebParam(name = "nroCuenta") String nroCuenta, @WebParam(name = "destinatario") String destinatario, @WebParam(name = "nombreCanal") String nombreCanal
    ) {
        return referenciaDAO.obtenerDatosReferencias(codigoCliente, tipoRef, nroCuenta, destinatario, nombreCanal);
    }

    /**
     * Obtiene la confirmacion de la referencia bancaria.
     *
     * @param codigoCliente Codigo del cliente.
     * @param destinatario A quien va dirigida la referencia.
     * @param nombreCanal Codigo (extendido) del canal desde el cual es llamado
     * el procedimiento.
     * @return UtilDTO -> (Numero de referencia, Firma que se estampa en la
     * referencia)
     */
    @WebMethod(operationName = "confirmarReferencia")
    public UtilDTO confirmarReferencia(
            @WebParam(name = "codigoCliente") String codigoCliente, @WebParam(name = "destinatario") String destinatario, @WebParam(name = "nombreCanal") String nombreCanal
    ) {
        return referenciaDAO.confirmarReferencia(codigoCliente, destinatario, nombreCanal);
    }

    /**
     * Obtiene las referencias de una TDC
     * @param codigoCliente codigo del cliente
     * @param nroTarjeta
     * @param destinatario destinatario a quien va dirigida la tdc
     * @param codigoCanal codigo del canal
     * @return ReferenciaDTO retorna los datos de la refeneia
     */
    @WebMethod(operationName = "obtenerReferenciaTDC")
    public ReferenciaDTO obtenerReferenciaTDC(@WebParam(name = "codigoCliente") String codigoCliente, @WebParam(name = "nroTarjeta") String nroTarjeta, @WebParam(name = "destinatario") String destinatario, @WebParam(name = "codigoCanal") String codigoCanal) {
        return referenciaDAO.obtenerReferenciaTDC(codigoCliente, nroTarjeta, destinatario, codigoCanal);
    }
}
