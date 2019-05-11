/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.ClienteDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.TarjetaDebitoDTO;
import com.bds.ws.dto.UtilDTO;

/**
 * Interfaz DAO para tardejas de debito
 * @author rony.rodriguez
 */
public interface TddDAO {

    /**
     * Bloquea una tarjeta de debito por intentos fallidos ya sean de acceso o
     * de validacion de OTP.
     *
     * @param tarjetaDebito numero de tarjeta de debito
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO indica si el proceso se realizo exitosamente o si ocurrio algun error
     */
    public RespuestaDTO bloquearTDD(String tarjetaDebito, String nombreCanal);

    /**
     * Obtiene los datos de una TDD
     * @param codigoCliente codigo del cliente
     * @param codigoCanal nombre del canal
     * @return Informacion de una tarjeta de debito
     */    
    public TarjetaDebitoDTO obtenerDatosTDD(String codigoCliente, String codigoCanal);    

    /**
     * Metodo que valida que el instrumento pertenece al cliente.
     *
     * @param codigoCliente codigo del cliente
     * @param numeroTarjeta numero de la tarjeta
     * @param codigoCanal codigo del canal
     * @return UtilDTO detalle de la tarjeta de un cliente
     */
    public UtilDTO obtenerClienteTarjeta(String codigoCliente, String numeroTarjeta, String codigoCanal);

    /**
     * Obtiene la lista TDD activas y canceladas del cliente
     *
     * @param codigoCliente codigo del cliente
     * @param codigoCanal nombre del canal
     * @return TarjetaDebitoDTO
     */
    public TarjetaDebitoDTO obtenerListadoTDDCliente(String codigoCliente, String codigoCanal);
    
    /**
     * Valida que los instrumentos TDD, Fecha Vcto y PIN pertenece al cliente.
     * @param numeroTarjeta numero de la tarjeta de debito del cliente  
     * @param pinCifrado PIN cifrado de la Tarjeta.
     * @param fechaVencimiento Fecha de Vencimiento de la Tarjeta.
     * @param codigoCanal codigo del canal
     * @return UtilDTO
     */
    public UtilDTO validarTDD (String numeroTarjeta,String pinCifrado,String fechaVencimiento,String codigoCanal);
}
