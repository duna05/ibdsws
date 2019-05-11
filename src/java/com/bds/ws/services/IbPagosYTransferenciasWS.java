/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbPagosYTransferenciasDAO;
import com.bds.ws.dto.RespuestaDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Clase de servicio web para pagos y transferencias
 * @author juan.faneite
 */
@WebService(serviceName = "IbPagosYTransferenciasWS")
public class IbPagosYTransferenciasWS {

    @EJB
    private IbPagosYTransferenciasDAO ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

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
    @WebMethod(operationName = "registrarTransaccionesPendientes")
    public RespuestaDTO registrarTransaccionesPendientes(@WebParam(name = "idCanal") String idCanal, @WebParam(name = "nombreCanal") String nombreCanal, @WebParam(name = "idUsuario") String idUsuario, @WebParam(name = "tipo") String tipo, @WebParam(name = "estatus") String estatus, @WebParam(name = "lote") String lote, @WebParam(name = "fechaHoraValor") String fechaHoraValor, @WebParam(name = "monto") String monto, @WebParam(name = "aprobacionesRequeridas") String aprobacionesRequeridas, @WebParam(name = "tipoRif") String tipoRif, @WebParam(name = "rif") String rif, @WebParam(name = "cuentaOrigen") String cuentaOrigen, @WebParam(name = "cuentaDestino") String cuentaDestino, @WebParam(name = "descripcion") String descripcion, @WebParam(name = "tipoIdBeneficiario") String tipoIdBeneficiario, @WebParam(name = "idBeneficiario") String idBeneficiario, @WebParam(name = "nombreBeneficiario") String nombreBeneficiario, @WebParam(name = "emailBeneficiario") String emailBeneficiario, @WebParam(name = "tipoCarga") String tipoCarga, @WebParam(name = "fechaEjecucion") String fechaEjecucion) {
        return ejbRef.registrarTransaccionesPendientes(idCanal, nombreCanal, idUsuario, tipo, estatus, lote, fechaHoraValor, monto, aprobacionesRequeridas, tipoRif, rif, cuentaOrigen, cuentaDestino, descripcion, tipoIdBeneficiario, idBeneficiario, nombreBeneficiario, emailBeneficiario, tipoCarga, fechaEjecucion);
    }

}
