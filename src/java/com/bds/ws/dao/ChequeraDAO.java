/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.ChequeraDTO;
import com.bds.ws.dto.CuentaDTO;
import com.bds.ws.dto.EstadoSolicitudChequeraDTO;
import com.bds.ws.dto.UtilDTO;
import java.math.BigDecimal;

/**
 *
 * @author robinson.rodriguez
 */
public interface ChequeraDAO {

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
    public UtilDTO solicitarChequeraPJ(String numeroCuenta, String tipoChequera, String cantidad, String identificacion, String retira, String agenciaRetira, String idCanal, String codigoCanal, BigDecimal idEmpresa, String nombreEmpresa, String emailEmpresa, String nombreUsuario);

    /**
     *
     * @param nroCuenta numero de cuenta a ser consultado
     * @param iCodExtCanal canal por el cual se realiza la consulta
     * @return EstadoSolicitudChequeraDTO
     */
    public EstadoSolicitudChequeraDTO obtenerEstadoSolicitudChequera(String nroCuenta, String iCodExtCanal);

    /**
     * Obtiene las chequeras entregadas de una cuenta.
     *
     * @param numeroCuenta numero de la cuenta
     * @param codigoCanal codigo del canal
     * @return CuentaDTO listado de chequeras entraadas a una cuenta
     */
    public CuentaDTO listarChequeraEntregada(String numeroCuenta, String codigoCanal);

    /**
     * Obtiene el estado de los cheques de una chequera.
     *
     * @param numeroCuenta numero de la cuenta
     * @param numeroPrimerCheque numero del primer cheque
     * @param codigoCanal codigo del canal
     * @return ChequeraDTO lista los cheques de una chequera
     */
    public ChequeraDTO listarCheque(String numeroCuenta, String numeroPrimerCheque, String codigoCanal);
    
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
    public UtilDTO suspenderChequera(String numeroCuenta, String motivoSuspension, String numPrimerCheque, String numUltimoCheque, String listCheques, String nombreCanal);

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
    public UtilDTO solicitarChequeraPN(String numeroCuenta, String tipoChequera, String cantidad, String identificacion, String retira, String agenciaRetira, String codigoCanal);

}
