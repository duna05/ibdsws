/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.RespuestaDTO;

/**
 * Clase interfaz DAO para bitacora
 * @author juan.faneite
 */
public interface IBBitacoraDAO {

    /**
     * Registro de bitacora Todos los campos son obligatorios.
     *
     * @param cuentaOrigen String Numero de cuenta origen
     * @param cuentaDestino String Numero de cuenta/TDC/Servicio destino
     * @param monto String Monto de la transaccion
     * @param referencia String Numero de referencia de la transaccion
     * @param descripcion String Descripcion dada por el usuario a la
     * transaccion
     * @param ip String Direccion ip del usuario
     * @param userAgent String Identificacion del navegador utilizado por el
     * usuario
     * @param errorOperacion String Canal de ejecucion de la transaccion
     * @param nombreBeneficiario String Nombre del beneficiario
     * @param tipoRif String Tipo de RIF de una empresa
     * @param rifCedula String Numero de RIF de una empresa
     * @param fechaHoraTx String Fecha y hora para ejecutar la transaccion
     * @param fechaHoraJob String Fecha y hora de ejecucion del job
     * @param idUsuario String Referencia foranea al usuario creador de la
     * transaccion
     * @param idTransaccion String Identificador de la transaccion realizada
     * @param idAfiliacion String Referencia foranea al beneficiario
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return RespuestaDTO indica si la operacion se realizo de manera exitosa o no
     */
    public RespuestaDTO registroDeBitacora(String cuentaOrigen, String cuentaDestino, String monto, String referencia, String descripcion, String ip,
            String userAgent, String errorOperacion, String nombreBeneficiario, String tipoRif, String rifCedula,
            String fechaHoraTx, String fechaHoraJob, String idCanal, String nombreCanal, String idUsuario, String idTransaccion, String idAfiliacion);
    
    /**
     * Registro de bitacora PJ Todos los campos son obligatorios.
     *
     * @param cuentaOrigen String Numero de cuenta origen
     * @param cuentaDestino String Numero de cuenta/TDC/Servicio destino
     * @param monto String Monto de la transaccion
     * @param referencia String Numero de referencia de la transaccion
     * @param descripcion String Descripcion dada por el usuario a la
     * transaccion
     * @param ip String Direccion ip del usuario
     * @param userAgent String Identificacion del navegador utilizado por el
     * usuario
     * @param errorOperacion String Canal de ejecucion de la transaccion
     * @param nombreBeneficiario String Nombre del beneficiario
     * @param tipoRif String Tipo de RIF de una empresa
     * @param rifCedula String Numero de RIF de una empresa
     * @param fechaHoraTx String Fecha y hora para ejecutar la transaccion
     * @param fechaHoraJob String Fecha y hora de ejecucion del job
     * @param idUsuario String Referencia foranea al usuario creador de la
     * transaccion
     * @param idAfiliacion String Referencia foranea al beneficiario
     * @param idEmpresa codigo de la empresa en IB
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreServicio nombre del servicio en IB
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return RespuestaDTO indica si la operacion se realizo de manera exitosa
     * o no
     */
    public RespuestaDTO registroDeBitacoraPj(String cuentaOrigen, String cuentaDestino, String monto, String referencia, String descripcion, String ip,
            String userAgent, String errorOperacion, String nombreBeneficiario, String tipoRif, String rifCedula,
            String fechaHoraTx, String fechaHoraJob, String idCanal, String nombreCanal, String idUsuario, String idEmpresa, String nombreServicio, String idAfiliacion);
}
