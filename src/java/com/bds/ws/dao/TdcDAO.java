/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.ClienteDTO;
import com.bds.ws.dto.TarjetasCreditoDTO;
import com.bds.ws.dto.UtilDTO;

/**
 * Interfaz DAO para TDC
 * @author renseld.lugo
 */
public interface TdcDAO {

    /**
     * Retorna el listado de tarjetas de credito de un cliente consultando por
     * numero de cedula.
     *
     * @param iNroCedula numero de cedula del cliente.
     * @param iCodCanal Codigo del canal desde el cual es llamado el
     * procedimiento.
     * @return ClienteDAO objeto cliente con lista de tcd asociadas al mismo.
     */
    public ClienteDTO listadoTdcPorCliente(String iNroCedula, String iCodCanal);

    /**
     * retorna los movimientos para la TDC seleccionada por parametros
     *
     * @param numeroTarjeta String Numero de Tarjeta a la cual se le van a
     * obtener los movimientos.
     * @param canal String Codigo del canal desde el cual es llamado el
     * procedimiento.
     * @param nroRegistros Nro maximo de registros que debe contener el listado.
     * @return TarjetasCreditoDTO la TDC con los movimientos encontrados
     */
    public TarjetasCreditoDTO listadoMovimientosTDC(String numeroTarjeta, String canal, String nroRegistros);

    /**
     * Retorna el detalle de una TDC especifica.
     *
     * @param numeroTarjeta String con el numero de tarjeta
     * @param canal String Codigo del canal desde el cual es llamado el
     * procedimiento.
     * @return TarjetasCreditoDTO el detalle de una TDC especifica.
     */
    public TarjetasCreditoDTO obtenerDetalleTDC(String numeroTarjeta, String canal);

    /**
     * Metodo que obtiene el listado de TDC propias por cliente
     *
     * @param nroCedula String Numero de CI del cliente
     * @param nombreCanal String Nombre del canal
     * @return ClienteDTO -> con List< TarjetasCreditoDTO > listado de tarjetas de credito propias
     */
    public ClienteDTO listadoTdcPropias(String nroCedula, String nombreCanal);

    /**
     * Metodo que valida que el instrumento pertenece al cliente.
     *
     * @param codigoCliente codigo del cliente
     * @param numeroTarjeta numero de tarjeta de credito
     * @param codigoCanal codigo de canal
     * @return UtilDTO los datos de la TDC de un cliente.
     */
    public UtilDTO obtenerClienteTarjeta(String codigoCliente, String numeroTarjeta, String codigoCanal);
    
    /**
     * Retorna los movimientos para la TDC seleccionada por mes
     *
     * @param numeroTarjeta String Numero de Tarjeta a la cual se le van a
     * obtener los movimientos.
     * @param mes
     * @param anno
     * @param codigoCanal String Codigo del canal desde el cual es llamado el
     * procedimiento.
     * @return TarjetasCreditoDTO la TDC con los movimientos encontrados.
     */
    public TarjetasCreditoDTO listadoMovimientosTDCMes(String numeroTarjeta, int mes, int anno, String codigoCanal);

}
