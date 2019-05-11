/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.ChequeraDTO;
import com.bds.ws.dto.ClienteDTO;
import com.bds.ws.dto.CuentaDTO;
import com.bds.ws.dto.MovimientoCuentaDTO;
import com.bds.ws.dto.TransaccionesCuentasDTO;
import com.bds.ws.dto.UtilDTO;
import java.util.List;

/**
 * Interfaz de cuenta
 *
 * @author rony.rodriguez
 */
public interface CuentaDAO {

    /**
     * retorna los datos basicos de la cuenta mediante la cedula y el canal
     *
     * @param sCuenta String tipo de cuenta (Ahorro, Corriente)
     * @param iNumeroCuenta String numero de cuenta
     * @param iCodExtCanal String canal por el cual se realiza la consulta
     * @return CuentaDTO datos de la cuenta en core bancario
     */
    public CuentaDTO obtenerDetalleCuenta(String sCuenta, String iNumeroCuenta, String iCodExtCanal);

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
    public CuentaDTO listarMovimientos(String tipoCuenta, String numeroCuenta, String fechaIni, String fechaFin,
            String regIni, String regFin, String tipoOrdenFecha, String canal);

    /**
     * Metodo que obtiene el listado de cuentas de ahorro y corrientes que posee
     * un cliente
     *
     * @param codigoCliente codigo del cliente
     * @param canal codigo de canal
     * @return ClienteDTO listado de cuentas de ahorro y corrientes que posee un
     * cliente
     */
    public ClienteDTO listadoCuentasClientes(String codigoCliente, String canal);

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
    public List<CuentaDTO> listarCuentasActivasClientePj(String codigoClienteAbanks, String nroRif, String tipoCuenta, String idCanal, String codigoCanal);

    /**
     * Método que obtiene el listado de cuentas de ahorro y corriente, activas y
     * canceladas, que posee un cliente, asociadas a una TDD en específico
     *
     * @param tdd
     * @param nombreCanal codigo de canal
     * @return ClienteDTO listado de cuentas de ahorro y corrientes que posee un
     * cliente
     */
    public ClienteDTO listCuentasActivasCanceladas(String tdd, String nombreCanal);

    /**
     * Metodo que consulta el detalle para el movimiento de una cuenta
     *
     * @param secuenciamovimiento String secuencia del movimiento de la cuenta
     * @param canal codigo de canal
     * @return MovimientoCuentaDTO el movimiento con la informacion detallada
     */
    public MovimientoCuentaDTO detalleMovimiento(String secuenciamovimiento, String canal);

    /**
     * Obtiene las chequeras entregadas de una cuenta.
     *
     * @param numeroCuenta numero de la cuenta
     * @param codigoCanal codigo del canal
     * @return CuentaDTO listado de chequeras entraadas a una cuenta
     */
    public CuentaDTO listarChequerasEntregadas(String numeroCuenta, String codigoCanal);

    /**
     * Obtiene el estado de los cheques de una chequera.
     *
     * @param numeroCuenta numero de la cuenta
     * @param numeroPrimerCheque numero del primer cheque
     * @param codigoCanal codigo del canal
     * @return ChequeraDTO lista los cheques de una chequera
     */
    public ChequeraDTO listarCheques(String numeroCuenta, String numeroPrimerCheque, String codigoCanal);

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
    public UtilDTO solicitarChequeras(String numeroCuenta, String tipoChequera, String cantidad, String identificacion, String retira, String agenciaRetira, String codigoCanal);

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
    public UtilDTO suspenderChequeras(String numeroCuenta, String motivoSuspension, String numPrimerCheque, String numUltimoCheque, String listCheques, String nombreCanal);

    /**
     * Obtiene los saldos bloqueados de una cuenta
     *
     * @param tipoCuenta tipo de cuenta BCA o BCC
     * @param numeroCuenta numero de la cuenta
     * @param codigoCanal codigo del canal
     * @return listado de Saldos Bloqueados de una Cuenta
     */
    public CuentaDTO listadoSaldoBloqueadoCuenta(String tipoCuenta, String numeroCuenta, String codigoCanal);

    /**
     * Obtiene los saldos bloqueados de una cuenta
     *
     * @param tipoCuenta tipo de cuenta BCA o BCC
     * @param numeroCuenta numero de la cuenta
     * @param codigoCanal codigo del canal
     * @return listado de Saldos Bloqueados de una Cuenta
     */
    public CuentaDTO listadoSaldoDiferidoCuenta(String tipoCuenta, String numeroCuenta, String codigoCanal);

    /**
     * Obtiene el estado de cuenta.
     *
     * @param tipoCuenta tipo de cuenta BCA o BCC
     * @param numeroCuenta numero de la cuenta
     * @param fechaIni fecha inicial
     * @param fechaFin fecha final
     * @param codigoCanal codigo del canal
     * @return estado de cuenta.
     */
    public CuentaDTO estadoCuenta(String tipoCuenta, String numeroCuenta, String fechaIni, String fechaFin, String codigoCanal);

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
    public CuentaDTO listarMovimientosPorTransaccion(String tipoCuenta, String numeroCuenta, String fechaIni, String fechaFin,
            String regIni, String regFin, String tipoOrdenFecha, String canal, String codTransaccion);

    /**
     * retorna las transacciones para la cuenta seleccionada
     *
     * @param tipoCuenta String tipo de cuenta (BCC, BCA, BME) -> ver constantes
     * de tipos producto
     * @param canal canal por el cual se realiza la consulta
     * @return CuentaDTO la cuenta con los movimientos de la cuenta
     * seleccionada(solo vienen los datos de los movimientos)
     */
    public TransaccionesCuentasDTO listadoTransaccionesCuentas(String tipoCuenta, String canal);

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
    public UtilDTO validarCuentaEmpresaCliente(String nroCuenta, Character tipoRif, String rif, String idCanal, String codigoCanal);

    /**
     * Método retorna el saldo disponible de una cta
     *
     * @param nroCuenta numero de cta de 20 digitos
     * @param idCanal
     * @param codigoCanal
     *
     * @return UtilDTO
     */
    public UtilDTO obtenerSaldoDisponibleCta(String nroCuenta, String idCanal, String codigoCanal);

    /* 
    * Obtiene el estado de los cheques utilizados de una chequera.
     *
     * @param numeroCuenta numero de la cuenta
     * @param numeroPrimerCheque numero del primer cheque
     * @param codigoCanal codigo del canal
     * @return ChequeraDTO Listado de los cheques entregados
     */
    public ChequeraDTO listarChequesChequera(String numeroCuenta, String numeroPrimerCheque, String codigoCanal);

    /**
     *
     * @param ctaBeneficiario id del beneficiario a consultar
     * @param codigoCanal String canal Codigo (extendido) del canal desde el
     * cual es llamado el procedimiento.
     * @return
     */
    public String obtenerCtaDelSur20DigitosSP(String ctaBeneficiario, String codigoCanal);

    /**
     *
     * @param nroCuenta
     * @param identBeneficiario
     * @param iCodExtCanal
     * @return
     */
    public UtilDTO validarCtaBeneficiario(String nroCuenta, String identBeneficiario, String iCodExtCanal);

}
