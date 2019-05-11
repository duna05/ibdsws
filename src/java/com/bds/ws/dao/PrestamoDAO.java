/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.ClienteDTO;
import com.bds.ws.dto.PrestamoDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;

/**
 * Interfaz DAO para prestamos
 * @author rony.rodriguez
 */
public interface PrestamoDAO {

    /**
     * retorna los prestamos de un cliente
     *
     * @param iCodigoCliente String Codigo del cliente que solicita el resumen.
     * @param iCodExtCanal String Codigo (extendido) del canal desde el cual es
     * llamado el procedimiento.
     * @return ClienteDTO cliente con los prestamos asociados.
     */
    public ClienteDTO listadoPrestamosCliente(String iCodigoCliente, String iCodExtCanal);
    
    /**
     * retorna los prestamos de un cliente sólo para Amortización de Préstamos
     *
     * @param iCodigoCliente String Codigo del cliente que solicita el resumen.
     * @param iCodExtCanal String Codigo (extendido) del canal desde el cual es
     * llamado el procedimiento.
     * @return ClienteDTO cliente con los prestamos asociados.
     */
    public ClienteDTO listadoPrestamosClienteAP(String iCodigoCliente, String iCodExtCanal);

    /**
     * retorna los datos basicos de la cuenta mediante la cedula y el canal en
     *
     * @param iNumeroPrestamo String Numero del prestamo
     * @param iCodExtCanal String Codigo (extendido) del canal desde el cual es
     * llamado el procedimiento.
     * @return PrestamoDTO datos del prestamo.
     */
    public PrestamoDTO obtenerDetalle(String iNumeroPrestamo, String iCodExtCanal);

    /**
     * retorna los pagos para el prestamo seleccionado
     *
     * @param iNumeroPrestamo String numero de cuenta
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
    public PrestamoDTO listadoPagosPrestamo(String iNumeroPrestamo, String fechaIni, String fechaFin,
            String regIni, String regFin, String tipoOrdenFecha, String canal);

    /**
     * Obtiene el saldo exigible de un prestamo.
     *
     * @param iNumeroPrestamo numero del prestamo
     * @param codigoCanal codigo del canal
     * @return PrestamoDTO los datos del prestamos
     */
    public PrestamoDTO obtenerSaldoExigible(String iNumeroPrestamo, String codigoCanal);

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
    public UtilDTO aplicarPagoPrestamo(String iNumeroPrestamo, String ivNumeroCuenta, String inValorTransaccion, String ivCodigoUsuario, String ivDescripcionMovimiento, String codigoCanal);

    /**
     * Obtiene los movimientos de la amortizacion de un prestamo.
     * @param numeroPrestamo numero del prestamo
     * @param nombreCanal nombre del canal
     * @return PrestamoDTO con listado de movimientos de la amortizacion de un prestamo.
     */
    public PrestamoDTO obtenerAmortizMov(String numeroPrestamo, String nombreCanal);
    
    /**
     * Obtiene el detalle de la amortizacion de un prestamo.
     * 
     * @param codigoCliente codigo del cliente
     * @param codigoCanal codigo del canal
     * @return PrestamoDTO retorna os datos de la amortizacion
     */
    public PrestamoDTO obtenerAmortizDet(String codigoCliente, String codigoCanal);

}
