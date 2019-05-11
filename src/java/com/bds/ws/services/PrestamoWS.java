/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.PrestamoDAO;
import com.bds.ws.dto.ClienteDTO;
import com.bds.ws.dto.PrestamoDTO;
import com.bds.ws.dto.UtilDTO;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 * Clase de servicios web relacionados a prestamos
 * @author rony.rodriguez
 */
@WebService(serviceName = "PrestamoWS")
public class PrestamoWS {

    @EJB
    private PrestamoDAO prestamoDAO;

    /**
     * retorna los prestamos de un cliente 
     * @param codigoCliente String Codigo del cliente que solicita el resumen.
     * @param canal String Codigo (extendido) del canal desde el cual es llamado el
     * procedimiento.
     * @return ClienteDTO cliente con los prestamos asociados.
     */  
    @WebMethod(operationName = "listadoPrestamosCliente")
    public ClienteDTO listadoPrestamosCliente(
            @WebParam(name = "codigoCliente") String codigoCliente,
            @WebParam(name = "canal") String canal) {
        return prestamoDAO.listadoPrestamosCliente(codigoCliente, canal);
    }
    
    /**
     * retorna los prestamos de un cliente sólo para Amortización de Préstamos
     * @param codigoCliente String Codigo del cliente que solicita el resumen.
     * @param canal String Codigo (extendido) del canal desde el cual es llamado el
     * procedimiento.
     * @return ClienteDTO cliente con los prestamos asociados.
     */  
    @WebMethod(operationName = "listadoPrestamosClienteAP")
    public ClienteDTO listadoPrestamosClienteAP(
            @WebParam(name = "codigoCliente") String codigoCliente,
            @WebParam(name = "canal") String canal) {
        return prestamoDAO.listadoPrestamosClienteAP(codigoCliente, canal);
    }

    /**
     * retorna los datos basicos del prestamo
     * @param numeroPrestamo String Numero del prestamo
     * @param canal String Codigo (extendido) del canal desde el cual es llamado el
     * procedimiento.
     * @return PrestamoDTO datos del prestamo.
     */
    @WebMethod(operationName = "detallePrestamo")
    public PrestamoDTO detallePrestamo(
            @WebParam(name = "numeroPrestamo") String numeroPrestamo,
            @WebParam(name = "canal") String canal) {
        return prestamoDAO.obtenerDetalle(numeroPrestamo, canal);
    }
    
    /**
    * retorna los pagos para el prestamo seleccionado
    * @param numeroPrestamo String numero de cuenta
    * @param fechaIni String fecha de incio del filtro
    * @param fechaFin String fecha de fin del filtro
    * @param regIni String registro de Inicio para la paginacion
    * @param regFin String registro de fin para la paginacion
    * @param tipoOrdenFecha String tipo de Orden por Fecha: ASC(por defecto), DESC
    * @param canal canal por el cual se realiza la consulta
    * @return  CuentaDTO la cuenta con los movimientos de la cuenta seleccionada(solo vienen los datos de los movimientos)
    */
    @WebMethod(operationName = "listadoPagosPrestamo")
    public PrestamoDTO listadoPagosPrestamo(@WebParam(name = "numeroPrestamo") String numeroPrestamo,
            @WebParam(name = "fechaIni") String fechaIni, @WebParam(name = "fechaFin") String fechaFin, @WebParam(name = "regIni") String regIni,
            @WebParam(name = "regFin") String regFin, @WebParam(name = "tipoOrdenFecha") String tipoOrdenFecha,
            @WebParam(name = "canal") String canal) {
        return prestamoDAO.listadoPagosPrestamo(numeroPrestamo, fechaIni, fechaFin, regIni, regFin, tipoOrdenFecha, canal);
    }

    /**
     * Obtiene el saldo exigible de un prestamo.
     *
     * @param iNumeroPrestamo numero del prestamo
     * @param codigoCanal codigo del canal
     * @return PrestamoDTO los datos del prestamos
     */
    @WebMethod(operationName = "obtenerSaldoExigible")
    public PrestamoDTO obtenerSaldoExigible(@WebParam(name = "iNumeroPrestamo") String iNumeroPrestamo, @WebParam(name = "codigoCanal") String codigoCanal) {
        return prestamoDAO.obtenerSaldoExigible(iNumeroPrestamo, codigoCanal);
    }

    /**
     * Realiza el pago del prestamo.
     *
     * @param iNumeroPrestamo numero del prestamo
     * @param ivNumeroCuenta numero de la cuenta
     * @param inValorTransaccion monto de la transaccion
     * @param ivCodigoUsuario codigo del usuario
     * @param ivDescripcionMovimiento descripcion del movimiento
     * @param codigoCanal codigo del canal
     * @return PrestamoDTO Los datos de un prestamos especifico
     */
    @WebMethod(operationName = "aplicarPagoPrestamo")
    public UtilDTO aplicarPagoPrestamo(@WebParam(name = "iNumeroPrestamo") String iNumeroPrestamo, @WebParam(name = "ivNumeroCuenta") String ivNumeroCuenta, @WebParam(name = "inValorTransaccion") String inValorTransaccion, @WebParam(name = "ivCodigoUsuario") String ivCodigoUsuario, @WebParam(name = "ivDescripcionMovimiento") String ivDescripcionMovimiento, @WebParam(name = "codigoCanal") String codigoCanal) {
        return prestamoDAO.aplicarPagoPrestamo(iNumeroPrestamo, ivNumeroCuenta, inValorTransaccion, ivCodigoUsuario, ivDescripcionMovimiento, codigoCanal);
    }

    /**
     * Obtiene los movimientos de la amortizacion de un prestamo.
     * @param numeroPrestamo numero del prestamo
     * @param nombreCanal nombre del canal
     * @return PrestamoDTO con listado de movimientos de la amortizacion de un prestamo.
     */
    @WebMethod(operationName = "obtenerAmortizMov")
    public PrestamoDTO obtenerAmortizMov(
            @WebParam(name = "numeroPrestamo") String numeroPrestamo,
            @WebParam(name = "nombreCanal") String nombreCanal) {
        return prestamoDAO.obtenerAmortizMov(numeroPrestamo, nombreCanal);
    }
    
    /**
     * Obtiene el detalle de la amortizacion de un prestamo.
     * 
     * @param codigoCliente codigo del cliente
     * @param codigoCanal codigo del canal
     * @return PrestamoDTO retorna os datos de la amortizacion
     */
    @WebMethod(operationName = "obtenerAmortizDet")
    public PrestamoDTO obtenerAmortizDet(
            @WebParam(name = "codigoCliente") String codigoCliente,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return prestamoDAO.obtenerAmortizDet(codigoCliente, codigoCanal);
    }
}
