/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;

/**
 * Interfaz DAO para pagos y transferencias
 * @author juan.faneite
 */
public interface IbPagosYTransferenciasDAO {

    /**
     * Metodo para registrar las transacciones pendientes
     *
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @param idUsuario String Usuario creador de la transaccion
     * @param tipo String Tipo de transaccion
     * @param estatus String Estatus de la transaccion "1" EJECUTADO "0"
     * PENDIENTE
     * @param lote String Numero de lote
     * @param fechaHoraValor String Fecha y hora en que se ejecutara
     * latransaccion
     * @param monto String Monto de la transaccion
     * @param aprobacionesRequeridas String Cantidad de aprobaciones necesarias
     * para ejecutar la transaccion
     * @param tipoRif String Tipo rif de la empresa
     * @param rif String Numero de rif de la empresa
     * @param cuentaOrigen String Cuenta de origen de los fondos
     * @param cuentaDestino String Cuenta destino de los fondos
     * @param descripcion String Descripcion de la transaccion
     * @param tipoIdBeneficiario String tipo de documento de identidad del
     * beneficiaro C:cedula P:pasaporte y J:juridico
     * @param idBeneficiario String Identificador del beneficiario
     * @param nombreBeneficiario String Nombre del beneficiario
     * @param emailBeneficiario String e-mail del beneficiario
     * @param tipoCarga String Tipo de carga de la transaccion
     * @param fechaEjecucion String FECHA EN LA QUE SE EJECUTO LA TRANSACCION
     * @return RespuestaDTO indica si la operacion se realizo de manera exitosa o no
     */
    public RespuestaDTO registrarTransaccionesPendientes(String idCanal, String nombreCanal, String idUsuario, String tipo, String estatus, String lote,
            String fechaHoraValor, String monto, String aprobacionesRequeridas, String tipoRif, String rif, String cuentaOrigen,
            String cuentaDestino, String descripcion, String tipoIdBeneficiario, String idBeneficiario, String nombreBeneficiario, String emailBeneficiario,
            String tipoCarga, String fechaEjecucion);

}
