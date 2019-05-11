/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.ReferenciaDTO;
import com.bds.ws.dto.UtilDTO;

/**
 * Interfaz de referencia
 * @author juan.faneite
 */
public interface ReferenciaDAO {

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
    public UtilDTO confirmarReferencia(String codigoCliente, String destinatario, String nombreCanal);

    /**
     * Obtiene los datos de las referencias bancarias.
     *
     * @param codigoCliente codigo del cliente
     * @param tipoRef tipo de referencia
     * @param nroCuenta numero de cuenta
     * @param nombreCanal codigo de canal
     * @param destinatario codigo de canal
     * @return ReferenciaDTO Datos de la referencia
     */
    public ReferenciaDTO obtenerDatosReferencias(String codigoCliente, String tipoRef, String nroCuenta, String destinatario, String nombreCanal);

    /**
     * Obtiene las referencias de una TDC
     * @param codigoCliente codigo del cliente
     * @param nroTarjeta
     * @param destinatario destinatario a quien va dirigida la tdc
     * @param codigoCanal codigo del canal
     * @return ReferenciaDTO retorna los datos de la refeneia
     */
    public ReferenciaDTO obtenerReferenciaTDC(String codigoCliente, String nroTarjeta, String destinatario, String codigoCanal);

}
