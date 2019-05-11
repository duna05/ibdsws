/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.TddDAO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.TarjetaDebitoDTO;
import com.bds.ws.dto.UtilDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Clase donde se encuentran los servicios para operaciones con Tarjetas de Debito
 * @author rony.rodriguez
 */
@WebService(serviceName = "TddWS")
public class TddWS {

    @EJB
    private TddDAO tddDao;

    /**
     * Bloquea una tarjeta de debito por intentos fallidos ya sean de acceso o de validacion de OTP.
     * @param tarjetaDebito numero de tarjeta de debito
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO indica si el proceso se realizo exitosamente o si ocurrio algun error
     */
    @WebMethod(operationName = "bloquearTDD")
    public RespuestaDTO bloquearTDD(@WebParam(name = "tarjetaDebito") String tarjetaDebito, @WebParam(name = "nombreCanal") String nombreCanal) {
        return tddDao.bloquearTDD(tarjetaDebito, nombreCanal);
    }

    /**
     * Obtiene los datos de una TDD
     * @param codigoCliente codigo del cliente
     * @param codigoCanal nombre del canal
     * @return Informacion de una tarjeta de debito
     */
    @WebMethod(operationName = "obtenerDatosTDD")
    public TarjetaDebitoDTO obtenerDatosTDD(@WebParam(name = "codigoCliente") String codigoCliente, @WebParam(name = "codigoCanal") String codigoCanal) {
        return tddDao.obtenerDatosTDD(codigoCliente, codigoCanal);
    }

    /**
     * Metodo que valida que el instrumento pertenece al cliente.
     *
     * @param codigoCliente codigo del cliente
     * @param numeroTarjeta numero de la tarjeta
     * @param codigoCanal codigo del canal
     * @return UtilDTO detalle de la tarjeta de un cliente
     */
    @WebMethod(operationName = "obtenerClienteTarjeta")
    public UtilDTO obtenerClienteTarjeta(@WebParam(name = "codigoCliente") String codigoCliente, @WebParam(name = "numeroTarjeta") String numeroTarjeta, @WebParam(name = "codigoCanal") String codigoCanal) {
        return tddDao.obtenerClienteTarjeta(codigoCliente, numeroTarjeta, codigoCanal);
    }

    /**
     * Obtiene la lista TDD activas y canceladas del cliente
     *
     * @param codigoCliente codigo del cliente
     * @param codigoCanal codigo del canal
     * @return ClienteDTO  obtiene el listado de TDD del cliente
     */
    @WebMethod(operationName = "obtenerListadoTDDCliente")
    public TarjetaDebitoDTO obtenerListadoTDDCliente(@WebParam(name = "codigoCliente") String codigoCliente, @WebParam(name = "codigoCanal") String codigoCanal) {
        return tddDao.obtenerListadoTDDCliente(codigoCliente, codigoCanal);
    }
    
    /**
     * Valida que los instrumentos TDD, Fecha Vcto y PIN pertenece al cliente.
     * @param numeroTarjeta numero de la tarjeta de debito del cliente  
     * @param pinCifrado PIN cifrado de la Tarjeta.
     * @param fechaVencimiento Fecha de Vencimiento de la Tarjeta.
     * @param codigoCanal codigo del canal
     * @return UtilDTO
     */
    @WebMethod(operationName = "validarTDD")
    public UtilDTO validarTDD(@WebParam(name = "numeroTarjeta") String numeroTarjeta, @WebParam(name = "pinCifrado") String pinCifrado, @WebParam(name = "fechaVencimiento") String fechaVencimiento, @WebParam(name = "codigoCanal") String codigoCanal) {
        return tddDao.validarTDD(numeroTarjeta,pinCifrado,fechaVencimiento,codigoCanal);
    }

}
