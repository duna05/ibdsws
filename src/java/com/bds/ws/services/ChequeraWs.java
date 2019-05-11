/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.ChequeraDAO;
import com.bds.ws.dto.ChequeraDTO;
import com.bds.ws.dto.CuentaDTO;
import com.bds.ws.dto.EstadoSolicitudChequeraDTO;
import com.bds.ws.dto.UtilDTO;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author robinson.rodriguez
 */
@WebService(serviceName = "ChequeraWs")
public class ChequeraWs {

    @EJB
    private ChequeraDAO chequeraDAO;

    /**
     * Realiza la solicitud de chequeras de una cuenta corriente.
     *
     * @param numeroCuenta numero de la cuenta
     * @param tipoChequera tipo de chequera
     * @param cantidad cantidad de chequeras
     * @param identificacion identificacion del usuario quien la retita
     * @param retira quien retira
     * @param agenciaRetira agencia donde se retira la chequera
     * @param codigoCanal codigo del canal
     * @return ChequeraDTO datos de retiro de chequera
     */
    @WebMethod(operationName = "solicitarChequeraPJ")
    public UtilDTO solicitarChequeraPJ(@WebParam(name = "numeroCuenta") String numeroCuenta, @WebParam(name = "tipoChequera") String tipoChequera, @WebParam(name = "cantidad") String cantidad, @WebParam(name = "identificacion") String identificacion, @WebParam(name = "retira") String retira, @WebParam(name = "agenciaRetira") String agenciaRetira, @WebParam(name = "idCanal") String idCanal, @WebParam(name = "codigoCanal") String codigoCanal, @WebParam(name = "idEmpresa") BigDecimal idEmpresa, @WebParam(name = "nombreEmpresa") String nombreEmpresa, @WebParam(name = "emailEmpresa") String emailEmpresa, @WebParam(name = "nombreUsuario") String nombreUsuario) {
        return chequeraDAO.solicitarChequeraPJ(numeroCuenta, tipoChequera, cantidad, identificacion, retira, agenciaRetira, idCanal, codigoCanal, idEmpresa, nombreEmpresa, emailEmpresa, nombreUsuario);
    }

    /**
     * Metodo para validar el estatus de la solicitud chequera realizados por el
     * cliente
     *
     * @param nroCuenta String numero de cuenta a validar
     * @param iCodExtCanal String condigo del canal para realizar la consulta
     * @return UtilDTO indica si la operacion se realizo de manera exitosa o no
     * y contiene el dato de respuesta en el map con key
     * estadoSolicitudChequera, es una tipo String
     */
    @WebMethod(operationName = "obtenerEstadoSolicitudChequera")
    public EstadoSolicitudChequeraDTO obtenerEstadoSolicitudChequera(
            @WebParam(name = "nroCuenta") String nroCuenta,
            @WebParam(name = "iCodExtCanal") String iCodExtCanal) {
        return this.chequeraDAO.obtenerEstadoSolicitudChequera(nroCuenta, iCodExtCanal);
    }

    /**
     * Obtiene las chequeras entregadas de una cuenta.
     *
     * @param numeroCuenta numero de la cuenta
     * @param codigoCanal codigo del canal
     * @return CuentaDTO listado de chequeras entraadas a una cuenta
     */
    @WebMethod(operationName = "listarChequeraEntregada")
    public CuentaDTO listarChequeraEntregada(@WebParam(name = "numeroCuenta") String numeroCuenta, @WebParam(name = "codigoCanal") String codigoCanal) {
        return chequeraDAO.listarChequeraEntregada(numeroCuenta, codigoCanal);
    }

    /**
     * Obtiene el estado de los cheques de una chequera.
     *
     * @param numeroCuenta numero de la cuenta
     * @param numeroPrimerCheque numero del primer cheque
     * @param codigoCanal codigo del canal
     * @return ChequeraDTO lista los cheques de una chequera
     */
    @WebMethod(operationName = "listarCheque")
    public ChequeraDTO listarCheque(@WebParam(name = "numeroCuenta") String numeroCuenta, @WebParam(name = "numeroPrimerCheque") String numeroPrimerCheque, @WebParam(name = "codigoCanal") String codigoCanal) {
        return chequeraDAO.listarCheque(numeroCuenta, numeroPrimerCheque, codigoCanal);
    }

    /**
     * Realiza la suspension de un cheque o varios cheque de una cuenta
     * corriente.
     *
     * @param numeroCuenta numero de la cuenta
     * @param motivoSuspension motivo de la suspension
     * @param numPrimerCheque numero del primer cheque
     * @param numUltimoCheque numero del ultimo cheque
     * @param listCheques lista de cheques a ser suspendidos
     * @param nombreCanal nombre del canal
     * @return Número de cheques que se lograron suspender, Numero de referencia
     * de la suspensión, Código de estatus de la operación indicando si la
     * solicitud fue realizada correctamente.
     */
    @WebMethod(operationName = "suspenderChequera")
    public UtilDTO suspenderChequera(@WebParam(name = "numeroCuenta") String numeroCuenta, @WebParam(name = "motivoSuspension") String motivoSuspension, @WebParam(name = "numPrimerCheque") String numPrimerCheque, @WebParam(name = "numUltimoCheque") String numUltimoCheque, @WebParam(name = "listCheques") String listCheques, @WebParam(name = "nombreCanal") String nombreCanal) {
        return chequeraDAO.suspenderChequera(numeroCuenta, motivoSuspension, numPrimerCheque, numUltimoCheque, listCheques, nombreCanal);
    }
    
        /**
     * Realiza la solicitud de chequeras de una cuenta corriente.
     *
     * @param numeroCuenta numero de la cuenta
     * @param tipoChequera tipo de chequera
     * @param cantidad cantidad de chequeras
     * @param identificacion identificacion del usuario quien la retita
     * @param retira quien retira
     * @param agenciaRetira agencia donde se retira la chequera
     * @param codigoCanal codigo del canal
     * @return ChequeraDTO datos de retiro de chequera
     */
    @WebMethod(operationName = "solicitarChequeraPN")
    public UtilDTO solicitarChequeraPN(@WebParam(name = "numeroCuenta") String numeroCuenta, @WebParam(name = "tipoChequera") String tipoChequera, @WebParam(name = "cantidad") String cantidad, @WebParam(name = "identificacion") String identificacion, @WebParam(name = "retira") String retira, @WebParam(name = "agenciaRetira") String agenciaRetira, @WebParam(name = "codigoCanal") String codigoCanal) {
        return chequeraDAO.solicitarChequeraPN(numeroCuenta, tipoChequera, cantidad, identificacion, retira, agenciaRetira, codigoCanal);
    }

}
