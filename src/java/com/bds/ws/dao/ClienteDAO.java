/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.ClienteDTO;
import com.bds.ws.dto.PosicionConsolidadaDTO;
import com.bds.ws.dto.UtilDTO;

/**
 * Interfaz de cliente
 * @author cesar.mujica
 */
public interface ClienteDAO {

    /**
     * retorna los datos basicos del cliente mediante el codigo y el canal
     *
     * @param iCodigoCliente codigo del cliente
     * @param canal canal por el cual se realiza la consulta
     * @param codigoCanal Codigo de canal por el cual se realiza la consulta
     * @return ClienteDTO datos del cliente en core bancario
     */
    public ClienteDTO consultarDatosCliente(String iCodigoCliente, String canal,String codigoCanal);

    /**
     * retorna los datos de los productos del cliente
     *
     * @param codigoCliente codigo del cliente
     * @param canal canal por el cual se realiza la consulta
     * @return PosicionConsolidadaDTO datos de productos de cliente en core
     * bancario
     */
    public PosicionConsolidadaDTO consultarPosicionConsolidadaCliente(String codigoCliente, String canal);

    /**
     * Actualiza los el telefono movil y el email del cliente
     *
     * @param codigoCliente codigo del cliente
     * @param celular numero del telefono celular a actualizar
     * @param codigoCanal codigo del canal
     * @param email email a actualizar
     * @return UtilDTO
     */
    public UtilDTO actualizarDatosContacto(String codigoCliente, String celular, String email, String codigoCanal);

    /**
     * Realiza la validacion de la clave con la ficha del cliente.
     *
     * @param codigoCliente codigo del cliente
     * @param claveCifrada clave cifrada
     * @param codigoCanal codigo del canal
     * @return UtilDTO retorna la validacion de la clave
     */
    public UtilDTO validarClaveFondo(String codigoCliente, String claveCifrada, String codigoCanal);

    /**
     * Obtiene los datos de un cliente del Internet Banking. Que valide la existencia del cliente.
     *
     * @param codigoCliente codigo del cliente
     * @param codigoCanal nombre del canal
     * @return UtilDTO retorna la validacion de la cuenta
     */
    public UtilDTO validarCliente(String codigoCliente, String codigoCanal);
    
    /**
     * Metodo para validar el rif de un cliente
     * @param rif rif 'v1234567890'
     * @param codigoCanal nombre del canal
     * @return retorna 'S' para casos validos, 'N' en caso contrario.
     */
    public UtilDTO validarRif (String rif, String codigoCanal);

    /**
     * Metodo para Valida cliente con Identificacion cliente y No. De cuenta
     * @param identificacion identificacion del cliente se debe incluir letra ej: v123456
     * @param nroCuenta numero de cuenta del cliente
     * @param codigoCanal nombre del canal
     * @return retorna 'V' para casos validos, 'F' en caso contrario.
     */
    public UtilDTO validarIdentificacionCuenta (String identificacion, String nroCuenta, String codigoCanal);
    
    /**
     * Metodo para validar y obtener un codigo de usuario existente por numero de cedula
     * @param identificacion identificacion del cliente se debe incluir letra ej: v123456
     * @param codigoCanal nombre del canal
     * @return en caso de existir retorna el parametro codUsuario en el mapa con el valor correspondiente
     */
    public UtilDTO obtenerCodCliente (String identificacion, String codigoCanal);
    
    /**
     * valida que un producto pertenezca a un cliente
     *
     * @param codigoCliente codigo del cliente
     * @param nroProducto 
     * @param codigoCanal nombre del canal
     * @return UtilDTO valido -> true en caso de existir el producto, false en
     * caso contrario
     */
    public UtilDTO validarProductoCliente(String codigoCliente, String nroProducto, String codigoCanal);
}
