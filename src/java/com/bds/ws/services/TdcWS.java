/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.TdcDAO;
import com.bds.ws.dto.ClienteDTO;
import com.bds.ws.dto.TarjetasCreditoDTO;
import com.bds.ws.dto.UtilDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Clase donde se encuentran los servicios web relacionados con las tarjetas de credito
 * @author rony.rodriguez
 */
@WebService(serviceName = "TdcWS")
public class TdcWS {

    @EJB
    private TdcDAO tdcDao;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    /**
     * Retorna el listado de tarjetas de credito de un cliente consultando por
     * numero de cedula.
     *
     * @param iNroCedula numero de cedula del cliente.
     * @param iCodCanal Codigo del canal desde el cual es llamado el
     * procedimiento.
     * @return ClienteDAO objeto cliente con lista de tcd asociadas al mismo.
     */
    @WebMethod(operationName = "listadoTdcPorCliente")
    public ClienteDTO listadoTdcPorCliente(@WebParam(name = "iNroCedula") String iNroCedula, @WebParam(name = "iCodCanal") String iCodCanal) {
        return tdcDao.listadoTdcPorCliente(iNroCedula, iCodCanal);
    }

    /**
     * retorna los movimientos para la TDC seleccionada por parametros
     *
     * @param numeroTDC String Numero de Tarjeta a la cual se le van a
     * obtener los movimientos.
     * @param canal String Codigo del canal desde el cual es llamado el
     * procedimiento.
     * @param nroRegistros Nro maximo de registros que debe contener el listado.
     * @return TarjetasCreditoDTO la TDC con los movimientos encontrados
     */
    @WebMethod(operationName = "listadoMovimientosTDC")
    public TarjetasCreditoDTO listadoMovimientosTDC(@WebParam(name = "numeroTDC") String numeroTDC, @WebParam(name = "canal") String canal, @WebParam(name = "nroRegistros") String nroRegistros) {
        return tdcDao.listadoMovimientosTDC(numeroTDC, canal, nroRegistros);
    }

    /**
     * Retorna el detalle de una TDC especifica.
     *
     * @param numeroTarjeta String con el numero de tarjeta
     * @param canal String Codigo del canal desde el cual es llamado el
     * procedimiento.
     * @return TarjetasCreditoDTO el detalle de una TDC especifica.
     */
    @WebMethod(operationName = "obtenerDetalleTDC")
    public TarjetasCreditoDTO obtenerDetalleTDC(@WebParam(name = "numeroTarjeta") String numeroTarjeta, @WebParam(name = "canal") String canal) {
        return tdcDao.obtenerDetalleTDC(numeroTarjeta, canal);
    }

    /**
     * Metodo que obtiene el listado de TDC propias por cliente
     * @param nroCedula String Numero de CI del cliente
     * @param nombreCanal String Nombre del canal
     * @return ClienteDTO -> con List< TarjetasCreditoDTO > listado de tarjetas de credito propias
     */
    @WebMethod(operationName = "listadoTdcPropias")
    public ClienteDTO listadoTdcPropias(@WebParam(name = "nroCedula") String nroCedula, @WebParam(name = "nombreCanal") String nombreCanal) {
        return tdcDao.listadoTdcPropias(nroCedula, nombreCanal);
    }

    /**
     * Metodo que valida que el instrumento pertenece al cliente.
     *
     * @param codigoCliente codigo del cliente
     * @param numeroTarjeta numero de tarjeta de credito
     * @param codigoCanal codigo de canal
     * @return UtilDTO los datos de la TDC de un cliente.
     */
    @WebMethod(operationName = "obtenerClienteTarjeta")
    public UtilDTO obtenerClienteTarjeta(@WebParam(name = "codigoCliente") String codigoCliente, @WebParam(name = "numeroTarjeta") String numeroTarjeta, @WebParam(name = "codigoCanal") String codigoCanal) {
        return tdcDao.obtenerClienteTarjeta(codigoCliente, numeroTarjeta, codigoCanal);
    }
    
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
    @WebMethod(operationName = "listadoMovimientosTDCMes")
    public TarjetasCreditoDTO listadoMovimientosTDCMes(@WebParam(name = "numeroTarjeta") String numeroTarjeta, @WebParam(name = "mes") int mes, @WebParam(name = "anno") int anno, @WebParam(name = "codigoCanal") String codigoCanal) {
        return tdcDao.listadoMovimientosTDCMes(numeroTarjeta, mes, anno, codigoCanal);
    }
}
