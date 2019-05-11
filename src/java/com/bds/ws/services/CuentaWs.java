/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.CuentaDAO;
import com.bds.ws.dto.ChequeraDTO;
import com.bds.ws.dto.ClienteDTO;
import com.bds.ws.dto.CuentaDTO;
import com.bds.ws.dto.MovimientoCuentaDTO;
import com.bds.ws.dto.TransaccionesCuentasDTO;
import com.bds.ws.dto.UtilDTO;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author rony.rodriguez
 */
@WebService(serviceName = "CuentaWs")
public class CuentaWs {

    @EJB
    private CuentaDAO cuentaDAO;

    /**
     * retorna los datos basicos de la cuenta mediante la cedula y el canal
     *
     * @param tipoCuenta String tipo de cuenta (Ahorro, Corriente)
     * @param numeroCuenta String numero de cuenta
     * @param canal String canal por el cual se realiza la consulta
     * @return CuentaDTO datos de la cuenta en core bancario
     */
    @WebMethod(operationName = "detalleCuenta")
    public CuentaDTO detalleCuenta(@WebParam(name = "tipoCuenta") String tipoCuenta, @WebParam(name = "numeroCuenta") String numeroCuenta, @WebParam(name = "canal") String canal) {
        return cuentaDAO.obtenerDetalleCuenta(tipoCuenta, numeroCuenta, canal);
    }

    /**
     * Metodo que obtiene el listado de cuentas de ahorro y corrientes que posee
     * un cliente
     *
     * @param codigoCliente codigo del cliente
     * @param canal codigo de canal
     * @return ClienteDTO listado de cuentas de ahorro y corrientes que posee un
     * cliente
     */
    @WebMethod(operationName = "listadoCuentasCliente")
    public ClienteDTO listadoCuentasCliente(@WebParam(name = "codigoCliente") String codigoCliente, @WebParam(name = "canal") String canal) {
        return cuentaDAO.listadoCuentasClientes(codigoCliente, canal);
    }

    /**
     * Metodo que obtiene el listado de cuentas de ahorro y corrientes que posee
     * un cliente
     *
     * @param codigoClienteAbanks codigo del cliente en el core banario
     * @param nroRif rif del cliente
     * @param tipoCuenta tipo de cuenta ahorro o corriente
     * @param idCanal id del canal interno en ib
     * @param codigoCanal codigo de canal asignado en el core bancario
     * @return ClienteDTO listado de cuentas de ahorro y corrientes que posee un
     * cliente
     */
    @WebMethod(operationName = "listarCuentasActivasClientePj")
    public List<CuentaDTO> listarCuentasActivasClientePj(@WebParam(name = "codigoClienteAbanks") String codigoClienteAbanks,
            @WebParam(name = "nroRif") String nroRif,
            @WebParam(name = "tipoCuenta") String tipoCuenta,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return cuentaDAO.listarCuentasActivasClientePj(codigoClienteAbanks, nroRif, tipoCuenta, idCanal, codigoCanal);
    }

    /**
     * Método que obtiene el listado de cuentas de ahorro y corriente, activas y
     * canceladas, que posee un cliente, asociadas a una TDD en específico
     *
     * @param tdd tarjeta de débito
     * @param nombreCanal codigo de canal
     * @return ClienteDTO listado de cuentas de ahorro y corrientes que posee un
     * cliente
     */
    @WebMethod(operationName = "listCuentasActivasCanceladas")
    public ClienteDTO listCuentasActivasCanceladas(@WebParam(name = "tdd") String tdd, @WebParam(name = "nombreCanal") String nombreCanal) {
        return cuentaDAO.listCuentasActivasCanceladas(tdd, nombreCanal);
    }

    /**
     * retorna los movimientos para la cuenta seleccionada
     *
     * @param tipoCuenta String tipo de cuenta (BCC, BCA, BME) -> ver constantes
     * de tipos producto
     * @param numeroCuenta String numero de cuenta
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
    @WebMethod(operationName = "listadoMovimientosCuenta")
    public CuentaDTO listadoMovimientosCuenta(@WebParam(name = "tipoCuenta") String tipoCuenta, @WebParam(name = "numeroCuenta") String numeroCuenta,
            @WebParam(name = "fechaIni") String fechaIni, @WebParam(name = "fechaFin") String fechaFin, @WebParam(name = "regIni") String regIni,
            @WebParam(name = "regFin") String regFin, @WebParam(name = "tipoOrdenFecha") String tipoOrdenFecha, @WebParam(name = "canal") String canal) {

        return cuentaDAO.listarMovimientos(tipoCuenta, numeroCuenta, fechaIni, fechaFin, regIni, regFin, tipoOrdenFecha, canal);
    }

    /**
     * Metodo que consulta el detalle para el movimiento de una cuenta
     *
     * @param secuenciaExtMovimiento String secuencia del movimiento de la
     * cuenta
     * @param canal codigo de canal
     * @return MovimientoCuentaDTO el movimiento con la informacion detallada
     */
    @WebMethod(operationName = "detalleMovimientoCuenta")
    public MovimientoCuentaDTO detalleMovimientoCuenta(@WebParam(name = "secuenciaExtMovimiento") String secuenciaExtMovimiento, @WebParam(name = "canal") String canal) {
        return cuentaDAO.detalleMovimiento(secuenciaExtMovimiento, canal);
    }

    /**
     * Obtiene las chequeras entregadas de una cuenta.
     *
     * @param numeroCuenta numero de la cuenta
     * @param codigoCanal codigo del canal
     * @return CuentaDTO listado de chequeras entraadas a una cuenta
     */
    @WebMethod(operationName = "listarChequerasEntregadas")
    public CuentaDTO listarChequerasEntregadas(@WebParam(name = "numeroCuenta") String numeroCuenta, @WebParam(name = "codigoCanal") String codigoCanal) {
        return cuentaDAO.listarChequerasEntregadas(numeroCuenta, codigoCanal);
    }

    /**
     * Obtiene el estado de los cheques de una chequera.
     *
     * @param numeroCuenta numero de la cuenta
     * @param numeroPrimerCheque numero del primer cheque
     * @param codigoCanal codigo del canal
     * @return ChequeraDTO lista los cheques de una chequera
     */
    @WebMethod(operationName = "listarCheques")
    public ChequeraDTO listarCheques(@WebParam(name = "numeroCuenta") String numeroCuenta, @WebParam(name = "numeroPrimerCheque") String numeroPrimerCheque, @WebParam(name = "codigoCanal") String codigoCanal) {
        return cuentaDAO.listarCheques(numeroCuenta, numeroPrimerCheque, codigoCanal);
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
    @WebMethod(operationName = "solicitarChequeras")
    public UtilDTO solicitarChequeras(@WebParam(name = "numeroCuenta") String numeroCuenta, @WebParam(name = "tipoChequera") String tipoChequera, @WebParam(name = "cantidad") String cantidad, @WebParam(name = "identificacion") String identificacion, @WebParam(name = "retira") String retira, @WebParam(name = "agenciaRetira") String agenciaRetira, @WebParam(name = "codigoCanal") String codigoCanal) {
        return cuentaDAO.solicitarChequeras(numeroCuenta, tipoChequera, cantidad, identificacion, retira, agenciaRetira, codigoCanal);
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
    @WebMethod(operationName = "suspenderChequeras")
    public UtilDTO suspenderChequeras(@WebParam(name = "numeroCuenta") String numeroCuenta, @WebParam(name = "motivoSuspension") String motivoSuspension, @WebParam(name = "numPrimerCheque") String numPrimerCheque, @WebParam(name = "numUltimoCheque") String numUltimoCheque, @WebParam(name = "listCheques") String listCheques, @WebParam(name = "nombreCanal") String nombreCanal) {
        return cuentaDAO.suspenderChequeras(numeroCuenta, motivoSuspension, numPrimerCheque, numUltimoCheque, listCheques, nombreCanal);
    }

    /**
     * Obtiene los saldos bloqueados de una cuenta
     *
     * @param tipoCuenta tipo de cuenta BCA o BCC
     * @param numeroCuenta numero de la cuenta
     * @param codigoCanal codigo del canal
     * @return listado de Saldos Bloqueados de una Cuenta
     */
    @WebMethod(operationName = "listadoSaldoBloqueadoCuenta")
    public CuentaDTO listadoSaldoBloqueadoCuenta(@WebParam(name = "tipoCuenta") String tipoCuenta, @WebParam(name = "numeroCuenta") String numeroCuenta, @WebParam(name = "codigoCanal") String codigoCanal) {
        return cuentaDAO.listadoSaldoBloqueadoCuenta(tipoCuenta, numeroCuenta, codigoCanal);
    }

    /**
     * Obtiene los saldos bloqueados de una cuenta
     *
     * @param tipoCuenta tipo de cuenta BCA o BCC
     * @param numeroCuenta numero de la cuenta
     * @param codigoCanal codigo del canal
     * @return listado de Saldos Bloqueados de una Cuenta
     */
    @WebMethod(operationName = "listadoSaldoDiferidoCuenta")
    public CuentaDTO listadoSaldoDiferidoCuenta(@WebParam(name = "tipoCuenta") String tipoCuenta, @WebParam(name = "numeroCuenta") String numeroCuenta, @WebParam(name = "codigoCanal") String codigoCanal) {
        return cuentaDAO.listadoSaldoDiferidoCuenta(tipoCuenta, numeroCuenta, codigoCanal);
    }

    /**
     * Obtiene el estado de cuenta (Cabecera)
     *
     * @param tipoCuenta tipo de cuenta BCA o BCC
     * @param numeroCuenta numero de la cuenta
     * @param fechaIni fecha inicial
     * @param fechaFin fecha final
     * @param codigoCanal codigo del canal
     * @return listado de Saldos Bloqueados de una Cuenta
     */
    @WebMethod(operationName = "estadoCuenta")
    public CuentaDTO estadoCuenta(@WebParam(name = "tipoCuenta") String tipoCuenta, @WebParam(name = "numeroCuenta") String numeroCuenta, @WebParam(name = "fechaIni") String fechaIni, @WebParam(name = "fechaFin") String fechaFin, @WebParam(name = "codigoCanal") String codigoCanal) {
        return cuentaDAO.estadoCuenta(tipoCuenta, numeroCuenta, fechaIni, fechaFin, codigoCanal);
    }

    /**
     * retorna los movimientos para la cuenta seleccionada
     *
     * @param tipoCuenta String tipo de cuenta (BCC, BCA, BME) -> ver constantes
     * de tipos producto
     * @param numeroCuenta String numero de cuenta
     * @param fechaIni String fecha de incio del filtro
     * @param fechaFin String fecha de fin del filtro
     * @param regIni String registro de Inicio para la paginacion
     * @param regFin String registro de fin para la paginacion
     * @param tipoOrdenFecha String tipo de Orden por Fecha: ASC(por defecto),
     * DESC
     * @param canal canal por el cual se realiza la consulta
     * @param codTransaccion Codigo de transaccion
     * @return CuentaDTO la cuenta con los movimientos de la cuenta
     * seleccionada(solo vienen los datos de los movimientos)
     */
    @WebMethod(operationName = "listarMovimientosPorTransaccion")
    public CuentaDTO listarMovimientosPorTransaccion(@WebParam(name = "tipoCuenta") String tipoCuenta, @WebParam(name = "numeroCuenta") String numeroCuenta,
            @WebParam(name = "fechaIni") String fechaIni, @WebParam(name = "fechaFin") String fechaFin, @WebParam(name = "regIni") String regIni,
            @WebParam(name = "regFin") String regFin, @WebParam(name = "tipoOrdenFecha") String tipoOrdenFecha, @WebParam(name = "canal") String canal, @WebParam(name = "codTransaccion") String codTransaccion) {

        return cuentaDAO.listarMovimientosPorTransaccion(tipoCuenta, numeroCuenta, fechaIni, fechaFin, regIni, regFin, tipoOrdenFecha, canal, codTransaccion);
    }

    /**
     * Método que obtiene el listado de transacciones para el filtrado de
     * movimientos
     *
     * @param tipoCuenta
     * @param nombreCanal codigo de canal
     * @return TransaccionesCuentasDTO listado de transacciones para consulta de
     * movimientos de cuentas corrinte y ahorro
     */
    @WebMethod(operationName = "listadoTransaccionesCuentas")
    public TransaccionesCuentasDTO listadoTransaccionesCuentas(@WebParam(name = "tipoCuenta") String tipoCuenta, @WebParam(name = "nombreCanal") String nombreCanal) {
        return cuentaDAO.listadoTransaccionesCuentas(tipoCuenta, nombreCanal);
    }

    /**
     * Método valida si una empresa esta activa y si el numero de cuenta
     * pertecene a la misma movimientos
     *
     * @param nroCuenta
     * @param tipoRif
     * @param rif
     * @param idCanal
     * @param codigoCanal
     *
     * @return UtilDTO
     */
    @WebMethod(operationName = "validarCuentaEmpresaCliente")
    public UtilDTO validarCuentaEmpresaCliente(@WebParam(name = "nroCuenta") String nroCuenta, @WebParam(name = "tipoRif") Character tipoRif, @WebParam(name = "rif") String rif, @WebParam(name = "idCanal") String idCanal, @WebParam(name = "codigoCanal") String codigoCanal) {
        return cuentaDAO.validarCuentaEmpresaCliente(nroCuenta, tipoRif, rif, idCanal, codigoCanal);
    }

    /**
     * Método retorna el saldo disponible de una cta
     *
     * @param nroCuenta numero de cta de 20 digitos
     * @param idCanal
     * @param codigoCanal
     *
     * @return UtilDTO
     */
    @WebMethod(operationName = "obtenerSaldoDisponibleCta")
    public UtilDTO obtenerSaldoDisponibleCta(@WebParam(name = "nroCuenta") String nroCuenta, @WebParam(name = "idCanal") String idCanal, @WebParam(name = "codigoCanal") String codigoCanal) {
        return cuentaDAO.obtenerSaldoDisponibleCta(nroCuenta, idCanal, codigoCanal);
    }

    /**
     * Obtiene el estado de los cheques de una chequera.
     *
     * @param numeroCuenta numero de la cuenta
     * @param numeroPrimerCheque numero del primer cheque
     * @param codigoCanal codigo del canal
     * @return ChequeraDTO lista los cheques de una chequera
     */
    @WebMethod(operationName = "listarChequesChequera")
    public ChequeraDTO listarChequesChequera(@WebParam(name = "numeroCuenta") String numeroCuenta, @WebParam(name = "numeroPrimerCheque") String numeroPrimerCheque, @WebParam(name = "codigoCanal") String codigoCanal) {
        return cuentaDAO.listarChequesChequera(numeroCuenta, numeroPrimerCheque, codigoCanal);
    }
    
    /**
     * Metodo para validar que el numero de cuenta introducido exista y pertenesca 
     * al benefeciario asignado
     *
     * @param nroCuenta String snumero de cuenta a validar
     * @param identBeneficiario String identificacion del benefeciario
     * @param iCodExtCanal String condigo del canal para realizar la consulta
     * @return UtilDTO indica si la operacion se realizo de manera exitosa o no y contiene el dato de respuesta
     * en el map con key perteneseCta, es una tipo String
     */
    
    @WebMethod(operationName = "validarCtaBeneficiario")
    public UtilDTO validarCtaBeneficiario(
            @WebParam(name = "nroCuenta") String nroCuenta,
            @WebParam(name = "identBeneficiario") String identBeneficiario,
            @WebParam(name = "iCodExtCanal") String iCodExtCanal){
        return this.cuentaDAO.validarCtaBeneficiario(nroCuenta, identBeneficiario, iCodExtCanal);
    }
    
}
