/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.ClienteDAO;
import com.bds.ws.dto.ClienteDTO;
import com.bds.ws.dto.PosicionConsolidadaDTO;
import com.bds.ws.dto.UtilDTO;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 * Servicio Web que se encarga de las consultas de productos de clientes en Oracle9
 * @author cesar.mujica
 */
@WebService(serviceName = "ClienteWs")
public class ClienteWs {

    @EJB
    private ClienteDAO clienteDAO;

    /**
     * consulta de los productos de la posicion consolidada del cliente de Oracle9
     * @param codigoCliente identificador clave de usuario en Oracle9
     * @param canal codigo extendido del canal
     * @return un objeto con los listados de productos del usuario 
     */
    @WebMethod(operationName = "posicionConsolidadaCliente")
    public PosicionConsolidadaDTO posicionConsolidadaCliente(@WebParam(name = "codigoCliente") String codigoCliente, @WebParam(name = "canal") String canal) {
        return clienteDAO.consultarPosicionConsolidadaCliente(codigoCliente, canal);
    }

    /**
     * retorna los datos basicos del cliente mediante el codigo y el canal
     *
     * @param codigoCliente codigo del cliente
     * @param canal canal por el cual se realiza la consulta
     * @param codigoCanal nombre del canal
     * @return ClienteDTO datos del cliente en core bancario
     */
    @WebMethod(operationName = "datosCliente")
    public ClienteDTO datosCliente(@WebParam(name = "codigoCliente") String codigoCliente, @WebParam(name = "canal") String canal,@WebParam(name = "codigoCanal") String codigoCanal) {
        return clienteDAO.consultarDatosCliente(codigoCliente, canal,codigoCanal);
    }

    /**
     * Obtiene los datos de un cliente del Internet Banking. Que valide la existencia del cliente.
     *
     * @param codigoCliente codigo del cliente
     * @param codigoCanal nombre del canal
     * @return UtilDTO retorna la validacion de la cuenta
     */
    @WebMethod(operationName = "validarCliente")
    public UtilDTO validarCliente(@WebParam(name = "codigoCliente") String codigoCliente, @WebParam(name = "codigoCanal") String codigoCanal) {
        return clienteDAO.validarCliente(codigoCliente, codigoCanal);
    }

    /**
     * Realiza la validacion de la clave con la ficha del cliente.
     *
     * @param codigoCliente codigo del cliente
     * @param claveCifrada clave cifrada
     * @param codigoCanal codigo del canal
     * @return UtilDTO retorna la validacion de la clave
     */
    @WebMethod(operationName = "validarClaveFondo")
    public UtilDTO validarClaveFondo(@WebParam(name = "codigoCliente") String codigoCliente, @WebParam(name = "claveCifrada") String claveCifrada, @WebParam(name = "codigoCanal") String codigoCanal) {
        return clienteDAO.validarClaveFondo(codigoCliente, claveCifrada, codigoCanal);
    }
    
    /**
     * Actualiza los el telefono movil y el email del cliente
     *
     * @param codigoCliente codigo del cliente
     * @param celular numero del telefono celular a actualizar
     * @param codigoCanal codigo del canal
     * @param email email a actualizar
     * @return UtilDTO
     */
    @WebMethod(operationName = "actualizarDatosContacto")
    public UtilDTO actualizarDatosContacto(@WebParam(name = "codigoCliente") String codigoCliente, @WebParam(name = "celular") String celular,  @WebParam(name = "email") String email, @WebParam(name = "codigoCanal") String codigoCanal) {
        return clienteDAO.actualizarDatosContacto( codigoCliente,  celular,  email, codigoCanal);
    }
    
    /**
     * Metodo para validar el rif de un cliente
     * @param rif rif 'v1234567890'
     * @param codigoCanal nombre del canal
     * @return retorna 'S' para casos validos, 'N' en caso contrario.
     */
    @WebMethod(operationName = "validarRif")
    public UtilDTO validarRif(@WebParam(name = "rif") String rif, @WebParam(name = "codigoCanal") String codigoCanal) {
        return clienteDAO.validarRif(rif, codigoCanal);
    }
    
    
    /**
     * Metodo para Valida cliente con Identificacion cliente y No. De cuenta
     * @param identificacion identificacion del cliente se debe incluir letra ej: v123456
     * @param nroCuenta numero de cuenta del cliente
     * @param codigoCanal nombre del canal
     * @return retorna 'V' para casos validos, 'F' en caso contrario.
     */
    @WebMethod(operationName = "validarIdentificacionCuenta")
    public UtilDTO validarIdentificacionCuenta(@WebParam(name = "identificacion") String identificacion, @WebParam(name = "nroCuenta") String nroCuenta, @WebParam(name = "codigoCanal") String codigoCanal) {
        return clienteDAO.validarIdentificacionCuenta(identificacion, nroCuenta, codigoCanal);
    }
    
    /**
     * Metodo para validar y obtener un codigo de usuario existente por numero de cedula
     * @param identificacion identificacion del cliente se debe incluir letra ej: v123456
     * @param codigoCanal nombre del canal
     * @return en caso de existir retorna el parametro codUsuario en el mapa con el valor correspondiente
     */
    @WebMethod(operationName = "obtenerCodCliente")
    public UtilDTO obtenerCodCliente (@WebParam(name = "identificacion")String identificacion, @WebParam(name = "codigoCanal") String codigoCanal){
        return clienteDAO.obtenerCodCliente(identificacion, codigoCanal);
    }
    
    
    /**
     * valida que un producto pertenezca a un cliente
     *
     * @param codigoCliente codigo del cliente
     * @param nroProducto 
     * @param codigoCanal nombre del canal
     * @return UtilDTO valido -> true en caso de existir el producto, false en
     * caso contrario
     */
    @WebMethod(operationName = "validarProductoCliente")
    public UtilDTO validarProductoCliente(@WebParam(name = "codigoCliente") String codigoCliente, @WebParam(name = "nroProducto") String nroProducto, @WebParam(name = "codigoCanal") String codigoCanal) {
        return clienteDAO.validarProductoCliente(codigoCliente, nroProducto, codigoCanal);
    }
}
