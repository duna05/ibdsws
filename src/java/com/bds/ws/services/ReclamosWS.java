/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.ReclamosDAO;
import com.bds.ws.dto.CuentaDTO;
import com.bds.ws.dto.ReclamoDTO;
import com.bds.ws.dto.RespuestaDTO;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author cesar.mujica
 */
@WebService(serviceName = "ReclamosWS")
public class ReclamosWS {
    
     @EJB
    private ReclamosDAO reclamosDAO;

    /**
     * Obtiene el listado de reclamos asociados a las TDD
     *
     * @param codigoCanal codigo del canal
     * @return ClienteDTO  obtiene el listado de TDD del cliente
     */
    @WebMethod(operationName = "obtenerListadoReclamosTDD")
    public ReclamoDTO obtenerListadoReclamosTDD(@WebParam(name = "codigoCanal") String codigoCanal) {
        return reclamosDAO.obtenerListadoReclamos(codigoCanal);
    }
    
    /**
     * Graba en la tabla de solicitud los campos del reclamo.
     * @param codigoCliente codigo del cliente
     * @param codigoReclamo codigo del reclamo
     * @param secuenciaExtMovimiento Este valor es devuelto en el campo secuenciaExtMovimiento de algunos cursores de movimientos.
     * @param observacion Observación del reclamo suministrada por el cliente que la realiza.
     * @param codigoCanal codigo del canal 
     * @return RespuestaDTO
     */
    @WebMethod(operationName = "insertarReclamoClienteTDD")
    public ReclamoDTO insertarReclamoClienteTDD(@WebParam(name = "codigoCliente") String codigoCliente, @WebParam(name = "codigoReclamo") String codigoReclamo, @WebParam(name = "secuenciaExtMovimiento") String secuenciaExtMovimiento, @WebParam(name = "observacion") String observacion, @WebParam(name = "codigoCanal") String codigoCanal) {
        return reclamosDAO.insertarReclamoCliente(codigoCliente,codigoReclamo,secuenciaExtMovimiento,observacion,codigoCanal);
    }
    
    
    /**
     * Listado De Reclamos X Cliente
     * @param codigoCliente codigo del cliente
     * @param codigoCanal codigo del canal 
     * @return RespuestaDTO
     */
    @WebMethod(operationName = "listadoReclamosPorCliente")
    public ReclamoDTO listadoReclamosPorCliente(@WebParam(name = "codigoCliente") String codigoCliente, @WebParam(name = "codigoCanal") String codigoCanal) {
        return reclamosDAO.listadoReclamosPorCliente(codigoCliente,codigoCanal);
    }
    
    /**
     * retorna los movimientos para la cuenta seleccionada
     *
     * @param tipoCuenta String tipo de cuenta (BCC, BCA, BME) -> ver constantes
     * de tipos producto
     * @param numeroCuenta String numero de cuenta
     * @param fechaIni String fecha de incio del filtro
     * @param fechaFin String fecha de fin del filtro
     * @param regIni String registro de Inicio para la paginacion
     * @param regFin String registro de fin para la paginacion
     * @param tipoOrdenFecha String tipo de Orden por Fecha: ASC(por defecto),
     * DESC
     * @param canal canal por el cual se realiza la consulta
     * @return CuentaDTO la cuenta con los movimientos de la cuenta
     * seleccionada(solo vienen los datos de los movimientos)
     */
    @WebMethod(operationName = "listadoMovimientosCuenta")
    public CuentaDTO listadoMovimientosCuenta(@WebParam(name = "tipoCuenta") String tipoCuenta, @WebParam(name = "numeroCuenta") String numeroCuenta,
            @WebParam(name = "fechaIni") String fechaIni, @WebParam(name = "fechaFin") String fechaFin, @WebParam(name = "regIni") String regIni,
            @WebParam(name = "regFin") String regFin, @WebParam(name = "tipoOrdenFecha") String tipoOrdenFecha, @WebParam(name = "canal") String canal) {

        return reclamosDAO.listarMovimientos(tipoCuenta, numeroCuenta, fechaIni, fechaFin, regIni, regFin, tipoOrdenFecha, canal);
    }
}
