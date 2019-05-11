/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.ActuacionesDAO;
import com.bds.ws.dao.CuentaDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.CabeceraEstadoCtaDTO;
import com.bds.ws.dto.ChequeDTO;
import com.bds.ws.dto.ChequeraDTO;
import com.bds.ws.dto.ClienteDTO;
import com.bds.ws.dto.CuentaDTO;
import com.bds.ws.dto.MovimientoCuentaDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.TransaccionesCuentasDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.exception.DAOException;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_PRODUCTO_CA;
import static com.bds.ws.util.BDSUtil.CODIGO_PRODUCTO_CC;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO_SP;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import static com.bds.ws.util.BDSUtil.JNDI_ORACLE_9;
import static com.bds.ws.util.BDSUtil.TEXTO_SIN_RESULTADOS_JPA;
import com.bds.ws.util.DAOUtil;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.NoResultException;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;

/**
 * Clase que implementa la interfaz CuentaDAO
 *
 * @author rony.rodriguez
 */
@Named("CuentaDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class CuentaDAOImpl extends DAOUtil implements CuentaDAO {

    @EJB
    public ActuacionesDAO actuaciones;

    /**
     * Log de sistema
     */
    private static final Logger logger = Logger.getLogger(CuentaDAOImpl.class.getName());

    /**
     * retorna los datos basicos de la cuenta mediante la cedula y el canal
     *
     * @param sCuenta String tipo de cuenta (Ahorro, Corriente)
     * @param iNumeroCuenta String numero de cuenta
     * @param iCodExtCanal String canal por el cual se realiza la consulta
     * @return CuentaDTO datos de la cuenta en core bancario
     */
    @Override
    public CuentaDTO obtenerDetalleCuenta(String sCuenta, String iNumeroCuenta, String iCodExtCanal) {
        CuentaDTO cuenta = new CuentaDTO();
        RespuestaDTO respuesta = new RespuestaDTO();

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, iCodExtCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            switch (sCuenta) {
                case CODIGO_PRODUCTO_CA:
                    respuesta = this.generarQuery("IB_K_CUENTAS_AHORRO", "IB_P_OBTENER_DETALLE", 3, iCodExtCanal);
                    break;
                case CODIGO_PRODUCTO_CC:
                    respuesta = this.generarQuery("IB_K_CUENTAS_CORRIENTE", "IB_P_OBTENER_DETALLE", 3, iCodExtCanal);
                    break;
                case CODIGO_PRODUCTO_ME:
                    respuesta = this.generarQuery("IB_K_CUENTAS_ME", "IB_P_OBTENER_DETALLE", 3, iCodExtCanal);
                    break;
            }

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setString(1, iNumeroCuenta);
            statement.setString(2, String.valueOf(iCodExtCanal));
            statement.registerOutParameter(3, OracleTypes.CURSOR);
            this.ejecuto = statement.execute();

            objResultSet = (ResultSet) statement.getObject(3);
            while (objResultSet.next()) {

                cuenta.setNumeroCuenta(iNumeroCuenta);
                cuenta.setCodigoTipoProducto(new String(objResultSet.getBytes("codigoTipoProducto"), CHARSET_ORACLE_9));
                cuenta.setNombreProducto(new String(objResultSet.getBytes("nombreProducto"), CHARSET_ORACLE_9));
                cuenta.setSiglasTipoMoneda(new String(objResultSet.getBytes("siglasTipoMoneda"), CHARSET_ORACLE_9));
                cuenta.setSaldoDisponible(objResultSet.getBigDecimal("saldoDisponible"));
                if (sCuenta.equalsIgnoreCase(CODIGO_PRODUCTO_CC)) {
                    cuenta.setCodAgencia(new String(objResultSet.getBytes("codigoAgencia"), CHARSET_ORACLE_9));
                }
                if (!sCuenta.equalsIgnoreCase(CODIGO_PRODUCTO_ME)) {
                    cuenta.setSaldoDiferido(objResultSet.getBigDecimal("saldoDiferido"));
                    cuenta.setSaldoBloqueado(objResultSet.getBigDecimal("saldobloqueado"));
                    cuenta.setSaldoTotal(objResultSet.getBigDecimal("saldoTotal"));
                }
            }
            cuenta.setRespuesta(respuesta);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN obtenerDetalleCuenta: ")
                    .append("CTA-").append(this.numeroCuentaFormato(iNumeroCuenta))
                    .append("-CH-").append(iCodExtCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
            cuenta.setRespuesta(respuesta);
        } finally {
            cuenta.setRespuesta(respuesta);
            this.cerrarConexion(iCodExtCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN obtenerDetalleCuenta: ")
//                    .append("CTA-").append(this.numeroCuentaFormato(iNumeroCuenta))
//                    .append("-CH-").append(iCodExtCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return cuenta;
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
    @Override
    public CuentaDTO listarMovimientos(String tipoCuenta, String numeroCuenta, String fechaIni, String fechaFin,
            String regIni, String regFin, String tipoOrdenFecha, String canal) {

        MovimientoCuentaDTO movimiento = null;
        List<MovimientoCuentaDTO> movimientos = new ArrayList<>();
        RespuestaDTO respuesta = new RespuestaDTO();
        CuentaDTO cuenta = new CuentaDTO();

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            switch (tipoCuenta) {
                case (CODIGO_PRODUCTO_CA): {
                    respuesta = this.generarQuery("IB_K_CUENTAS_AHORRO", "IB_P_OBTENER_MOVIMIENTOS", 8, canal);
                    break;
                }
                case (CODIGO_PRODUCTO_CC): {
                    respuesta = this.generarQuery("IB_K_CUENTAS_CORRIENTE", "IB_P_OBTENER_MOVIMIENTOS", 8, canal);
                    break;
                }
                case (CODIGO_PRODUCTO_ME): {
                    respuesta = this.generarQuery("IB_K_CUENTAS_ME", "IB_P_OBTENER_MOVIMIENTOS", 8, canal);
                    break;
                }
            }

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setString(1, numeroCuenta);
            if (fechaIni != null && !fechaIni.isEmpty() && !fechaIni.equalsIgnoreCase("null")) {
                statement.setDate(2, this.getSQLDate(fechaIni, FORMATO_FECHA_SIMPLE));
            } else {
                statement.setDate(2, null);
            }
            if (fechaFin != null && !fechaFin.isEmpty() && !fechaFin.equalsIgnoreCase("null")) {
                statement.setDate(3, this.getSQLDate(fechaFin, FORMATO_FECHA_SIMPLE));
            } else {
                statement.setDate(3, null);
            }
            statement.setInt(4, Integer.parseInt(regIni));
            statement.setInt(5, Integer.parseInt(regFin));
            statement.setString(6, tipoOrdenFecha);
            statement.setString(7, canal);
            statement.registerOutParameter(8, OracleTypes.CURSOR);

            this.ejecuto = statement.execute();

            objResultSet = (ResultSet) statement.getObject(8);
            while (objResultSet.next()) {
                movimiento = new MovimientoCuentaDTO();
                movimiento.setDescripcion(this.getCharSetOracle9(objResultSet, "descripcion"));
                movimiento.setFechaTransaccion(this.getUtilDate(objResultSet, "fechaTransaccion"));
                movimiento.setFechaTransaccionString(formatearFecha(this.getUtilDate(objResultSet, "fechaTransaccion"), FORMATO_FECHA_SIMPLE));
                if (this.objResultSet.getBigDecimal("numeroReferencia") != null) {
                    movimiento.setNumeroReferencia(this.objResultSet.getBigDecimal("numeroReferencia").toString());
                } else {
                    movimiento.setNumeroReferencia("");
                }
                if (this.objResultSet.getBigDecimal("secuenciaMovimiento") != null) {
                    movimiento.setSecuenciaMovimiento(this.objResultSet.getBigDecimal("secuenciaMovimiento").toString());
                } else {
                    movimiento.setSecuenciaMovimiento("");
                }
                if (this.getCharSetOracle9(objResultSet, "secuenciaExtMovimiento") != null) {
                    movimiento.setSecuenciaExtMovimiento(this.getCharSetOracle9(objResultSet, "secuenciaExtMovimiento"));
                } else {
                    movimiento.setSecuenciaExtMovimiento("");
                }
                if (tipoCuenta.equals(CODIGO_PRODUCTO_ME)) {
                    movimiento.setCreditosBs(this.objResultSet.getBigDecimal("creditosBs"));
                    movimiento.setCreditosDolar(this.objResultSet.getBigDecimal("creditosDolar"));
                    movimiento.setDebitosBs(this.objResultSet.getBigDecimal("debitosBs"));
                    movimiento.setDebitosDolar(this.objResultSet.getBigDecimal("debitosDolar"));
                    movimiento.setValorCambioTasa(this.objResultSet.getBigDecimal("valorCambioTasa"));
                } else {
                    movimiento.setIngreso(this.objResultSet.getBigDecimal("ingreso"));
                    movimiento.setEgreso(this.objResultSet.getBigDecimal("egreso"));
                }
                movimiento.setMonto(this.objResultSet.getBigDecimal("saldo"));
                movimientos.add(movimiento);
            }
            cuenta.setCodigoTipoProducto(tipoCuenta);
            cuenta.setMovimientos(movimientos);

            if (cuenta.getMovimientos() == null || cuenta.getMovimientos().isEmpty()) {
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);//revisar el log
            logger.error( new StringBuilder("ERROR DAO EN listarMovimientos: ")
                    .append("CTA-").append(this.numeroCuentaFormato(numeroCuenta))
                    .append("-CH-").append(canal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listarMovimientos: ")
                    .append("CTA-").append(this.numeroCuentaFormato(numeroCuenta))
                    .append("-CH-").append(canal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            cuenta.setRespuesta(respuesta);
            this.cerrarConexion(canal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN listarMovimientos: ")
//                    .append("CTA-").append(this.numeroCuentaFormato(numeroCuenta))
//                    .append("-CH-").append(canal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return cuenta;
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
    @Override
    public ClienteDTO listadoCuentasClientes(String codigoCliente, String canal) {
        ClienteDTO cliente = new ClienteDTO();
        List<CuentaDTO> cuentas = new ArrayList<>();
        RespuestaDTO respuesta = new RespuestaDTO();

        try {

            if (codigoCliente == null || codigoCliente.isEmpty() || codigoCliente.equalsIgnoreCase("")
                    || canal == null || canal.isEmpty() || canal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            /*Consulta del primer SP para cuentas de ahorros*/
            respuesta = this.generarQuery("IB_K_CLIENTES", "IB_P_OBTENER_RESUMEN_CA", 3, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setInt(1, Integer.valueOf(codigoCliente));
            statement.setString(2, canal);
            statement.registerOutParameter(3, OracleTypes.CURSOR);
            this.ejecuto = statement.execute();

            objResultSet = (ResultSet) statement.getObject(3);
            while (objResultSet.next()) {
                CuentaDTO cuenta = new CuentaDTO();
                cuenta.setNumeroCuenta(this.getCharSetOracle9(objResultSet, "numeroCuenta"));
                cuenta.setCodigoTipoProducto(this.getCharSetOracle9(objResultSet, "codigoTipoProducto"));
                cuenta.setNombreProducto(this.getCharSetOracle9(objResultSet, "nombreProducto"));
                cuenta.setSiglasTipoMoneda(this.getCharSetOracle9(objResultSet, "siglasTipoMoneda"));
                cuenta.setSaldoDisponible(this.objResultSet.getBigDecimal("saldoDisponible"));

                cuentas.add(cuenta);
                cliente.setCuentasAhorro(cuentas);
            }

            /*Consulta del primer SP para cuentas corrientes*/
            respuesta = this.generarQuery("IB_K_CLIENTES", "IB_P_OBTENER_RESUMEN_CC", 3, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setInt(1, Integer.valueOf(codigoCliente));
            statement.setString(2, canal);
            statement.registerOutParameter(3, OracleTypes.CURSOR);
            this.ejecuto = statement.execute();
            objResultSet = (ResultSet) statement.getObject(3);
            cuentas = new ArrayList<>();
            while (objResultSet.next()) {

                CuentaDTO cuenta = new CuentaDTO();
                cuenta.setNumeroCuenta(this.getCharSetOracle9(objResultSet, "numeroCuenta"));
                cuenta.setCodigoTipoProducto(this.getCharSetOracle9(objResultSet, "codigoTipoProducto"));
                cuenta.setNombreProducto(this.getCharSetOracle9(objResultSet, "nombreProducto"));
                cuenta.setSiglasTipoMoneda(this.getCharSetOracle9(objResultSet, "siglasTipoMoneda"));
                cuenta.setSaldoDisponible(this.objResultSet.getBigDecimal("saldoDisponible"));
                cuenta.setCodAgencia(this.getCharSetOracle9(objResultSet, "codigoAgencia"));

                cuentas.add(cuenta);
                cliente.setCuentasCorriente(cuentas);
            }

            cliente.setRespuesta(respuesta);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listadoCuentasClientes: ")
                    .append("USR-").append(codigoCliente)
                    .append("-CH-").append(canal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
            cliente.setRespuesta(respuesta);
        } finally {
            cliente.setRespuesta(respuesta);
            this.cerrarConexion(canal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN listadoCuentasClientes: ")
//                    .append("USR-").append(codigoCliente)
//                    .append("-CH-").append(canal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return cliente;
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
    @Override
    public List<CuentaDTO> listarCuentasActivasClientePj(String codigoClienteAbanks, String nroRif, String tipoCuenta, String idCanal, String codigoCanal) {
        List<CuentaDTO> cuentas = new ArrayList<>();
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            respuesta = conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            /*Consulta del primer SP para cuentas de ahorros*/
            respuesta = generarQuery("IB_K_CLIENTES", "IB_P_OBT_CUENTAS_CLIENTE", 6, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setString(1, nroRif.equals("") ? null : nroRif);
            statement.setInt(2, codigoClienteAbanks.equals("") ? null : Integer.valueOf(codigoClienteAbanks));
            statement.setString(3, tipoCuenta.equals("") ? null : tipoCuenta);
            statement.setString(4, codigoCanal);
            statement.registerOutParameter(5, OracleTypes.CURSOR);
            statement.registerOutParameter(6, OracleTypes.NUMBER);
            statement.execute();

            //RETORNA 1 EN EL CASO DE EXITO
            BigDecimal codSalida = (BigDecimal) statement.getObject(6);

            if (codSalida.intValue() == 1) {
                objResultSet = (ResultSet) statement.getObject(5);
                while (objResultSet.next()) {
                    CuentaDTO cuenta = new CuentaDTO();
                    cuenta.setNumeroCuenta10Digitos(objResultSet.getString("CUENTA10"));
                    cuenta.setNumeroCuenta(objResultSet.getString("CUENTA20"));
                    cuenta.setCodigoTipoProducto(objResultSet.getString("APLICACION"));
                    cuenta.setNombreProducto(getCharSetOracle9(objResultSet, "NOMBRE_PROD"));
                    cuenta.setSiglasTipoMoneda(getCharSetOracle9(objResultSet, "SIGLAS"));
                    cuenta.setSaldoDisponible(objResultSet.getBigDecimal("SALDODISPONIBLE"));
                    cuenta.setSaldoBloqueado(objResultSet.getBigDecimal("SALDOBLOQUEADO"));
                    cuenta.setSaldoDiferido(objResultSet.getBigDecimal("SALDODIFERIDO"));
                    cuenta.setSaldoTotal(objResultSet.getBigDecimal("SALDOTOTAL"));
                    cuenta.setCodAgencia(objResultSet.getString("CODIGOAGENCIA"));
                    cuentas.add(cuenta);
                }
            } else {
                //Ajuste por error al devolver cuentas sin chequeras activas y/o disponibles
                respuesta = actuaciones.obtenerDescripcionSalidaSP(codSalida.toString(), codigoCanal);
            }
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listadoCuentasClientes: ")
                    .append("USR-").append(codigoClienteAbanks)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            cerrarConexion(codigoCanal);
        }
        return cuentas;
    }

    /**
     * Método que obtiene el listado de cuentas de ahorro y corriente, activas y
     * canceladas, que posee un cliente, asociadas a una TDD en específico
     *
     * @param tdd
     * @param nombreCanal codigo de canal
     * @return ClienteDTO listado de cuentas de ahorro y corrientes que posee un
     * cliente
     */
    @Override
    public ClienteDTO listCuentasActivasCanceladas(String tdd, String nombreCanal) {
        ClienteDTO cliente = new ClienteDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        List<CuentaDTO> cuentasBCA = new ArrayList<>();
        List<CuentaDTO> cuentasBCC = new ArrayList<>();

        try {

            if (tdd == null || tdd.isEmpty() || tdd.equalsIgnoreCase("")
                    || nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, nombreCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_TDD", "IB_P_OBTENER_LISTADO_TDD", 3, nombreCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            //seteo de parametros de entrada y/o salida            
            statement.setString(1, tdd);
            statement.setString(2, nombreCanal);
            statement.registerOutParameter(3, OracleTypes.CURSOR);
            this.ejecuto = statement.execute();

            objResultSet = (ResultSet) statement.getObject(3);
            while (objResultSet.next()) {
                CuentaDTO cuenta = new CuentaDTO();

                switch (this.getCharSetOracle9(objResultSet, "codAplicacion")) {
                    case CODIGO_PRODUCTO_CA:
                        cuenta.setNumeroCuenta(this.getCharSetOracle9(objResultSet, "nroCuenta"));
                        cuenta.setCodigoTipoProducto(this.getCharSetOracle9(objResultSet, "codAplicacion"));
                        cuentasBCA.add(cuenta);
                        break;
                    case CODIGO_PRODUCTO_CC:
                        cuenta.setNumeroCuenta(this.getCharSetOracle9(objResultSet, "nroCuenta"));
                        cuenta.setCodigoTipoProducto(this.getCharSetOracle9(objResultSet, "codAplicacion"));
                        cuentasBCC.add(cuenta);
                        break;
                }

            }
            cliente.setCuentasAhorro(cuentasBCA);
            cliente.setCuentasCorriente(cuentasBCC);

            if (cuentasBCA.isEmpty() && cuentasBCC.isEmpty()) {
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);//revisar el log
            logger.error( new StringBuilder("ERROR DAO EN listCuentasActivasCanceladas: ")
                    .append("TDD-").append(tdd)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listCuentasActivasCanceladas: ")
                    .append("TDD-").append(tdd)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            cliente.setRespuesta(respuesta);
            this.cerrarConexion(nombreCanal);
        }

//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN listCuentasActivasCanceladas: ")
//                    .append("TDD-").append(tdd)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return cliente;
    }

    /**
     * Metodo que consulta el detalle para el movimiento de una cuenta
     *
     * @param secuenciamovimiento String secuencia del movimiento de la cuenta
     * @param canal codigo de canal
     * @return MovimientoCuentaDTO el movimiento con la informacion detallada
     */
    @Override
    public MovimientoCuentaDTO detalleMovimiento(String secuenciamovimiento, String canal) {
        MovimientoCuentaDTO movimientoCuentaDTO = new MovimientoCuentaDTO();
        RespuestaDTO respuesta = new RespuestaDTO();

        movimientoCuentaDTO.setSecuenciaExtMovimiento(secuenciamovimiento);
        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            /*Consulta del primer SP para cuentas de ahorros*/
            respuesta = this.generarQuery("IB_K_CUENTAS_BASE", "IB_P_OBT_DETALLE_MOVIMIENTO", 3, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setString(1, secuenciamovimiento);
            statement.setString(2, canal);
            statement.registerOutParameter(3, OracleTypes.CURSOR);
            this.ejecuto = statement.execute();

            objResultSet = (ResultSet) statement.getObject(3);
            while (objResultSet.next()) {
                movimientoCuentaDTO.setSecuenciaMovimiento(this.objResultSet.getBigDecimal("secuenciaMovimiento").toString());
                movimientoCuentaDTO.setNumeroReferencia(this.getCharSetOracle9(objResultSet, "numeroReferencia"));
                movimientoCuentaDTO.setCodigoAplicacion(this.getCharSetOracle9(objResultSet, "codigoAplicacion"));
                movimientoCuentaDTO.setTipoTransaccion(this.getCharSetOracle9(objResultSet, "tipoTransaccion"));
                movimientoCuentaDTO.setMonto(this.objResultSet.getBigDecimal("monto"));
                movimientoCuentaDTO.setFechaTransaccion(this.getUtilDate(objResultSet, "fechaTransaccion"));
                movimientoCuentaDTO.setFechaTransaccionString(formatearFecha(this.getUtilDate(objResultSet, "fechaTransaccion"), FORMATO_FECHA_COMPLETA));
                movimientoCuentaDTO.setDescripcion(this.getCharSetOracle9(objResultSet, "descripcion"));
                movimientoCuentaDTO.setDebitoCredito(this.getCharSetOracle9(objResultSet, "debitoCredito"));
                movimientoCuentaDTO.setNumeroCuentaOrigen(this.getCharSetOracle9(objResultSet, "numeroCuentaOrigen"));
                movimientoCuentaDTO.setNumeroCuentaDestino(this.getCharSetOracle9(objResultSet, "numeroCuentaDestino"));
                movimientoCuentaDTO.setCodigoAgencia(this.getCharSetOracle9(objResultSet, "codigoAgencia"));
                movimientoCuentaDTO.setNombreAgencia(this.getCharSetOracle9(objResultSet, "nombreAgencia"));
                movimientoCuentaDTO.setIdentificadorEmisor(this.getCharSetOracle9(objResultSet, "identificadorEmisor"));
                movimientoCuentaDTO.setNombreEmisor(this.getCharSetOracle9(objResultSet, "nombreEmisor"));
                movimientoCuentaDTO.setIdentificadorBeneficiario(this.getCharSetOracle9(objResultSet, "identificadorBeneficiario"));
                movimientoCuentaDTO.setNombreBeneficiario(this.getCharSetOracle9(objResultSet, "nombreBeneficiario"));
                movimientoCuentaDTO.setCanal(this.getCharSetOracle9(objResultSet, "canal"));
                movimientoCuentaDTO.setCodigoBancoOrigen(this.getCharSetOracle9(objResultSet, "codigoBancoOrigen"));
                movimientoCuentaDTO.setNombreBancoOrigen(this.getCharSetOracle9(objResultSet, "nombreBancoOrigen"));
                movimientoCuentaDTO.setCodigoBancoDestino(this.getCharSetOracle9(objResultSet, "codigoBancoDestino"));
                movimientoCuentaDTO.setNombreBancoDestino(this.getCharSetOracle9(objResultSet, "nombreBancoDestino"));
                movimientoCuentaDTO.setSiglasTipoOp(this.getCharSetOracle9(objResultSet, "siglasTipoOp"));
                movimientoCuentaDTO.setDescripcionTipoOp(this.getCharSetOracle9(objResultSet, "descripcionTipoOp"));
                movimientoCuentaDTO.setCodigoUsuario(this.getCharSetOracle9(objResultSet, "codigoUsuario"));
                movimientoCuentaDTO.setHora(this.getUtilDate(objResultSet, "hora"));
                movimientoCuentaDTO.setHoraString(formatearFecha(this.getUtilDate(objResultSet, "hora"), FORMATO_HORA_HHMMSS));
            }

            movimientoCuentaDTO.setRespuesta(respuesta);

            if (movimientoCuentaDTO.getMonto() == null && movimientoCuentaDTO.getFechaTransaccion() == null) {
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN detalleMovimiento: ")
                    .append("-CH-").append(canal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN detalleMovimiento: ")
                    .append("MOV-").append(secuenciamovimiento)
                    .append("-CH-").append(canal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
            movimientoCuentaDTO.setRespuesta(respuesta);
        } finally {
            movimientoCuentaDTO.setRespuesta(respuesta);
            this.cerrarConexion(canal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN detalleMovimiento: ")
//                    .append("MOV-").append(secuenciamovimiento)
//                    .append("-CH-").append(canal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return movimientoCuentaDTO;
    }

    /**
     * Obtiene las chequeras entregadas de una cuenta.
     *
     * @param numeroCuenta numero de cuenta
     * @param codigoCanal codigo del canal
     * @return CuentaDTO Listado de las chequeras entregadas
     */
    @Override
    public CuentaDTO listarChequerasEntregadas(String numeroCuenta, String codigoCanal) {

        ChequeraDTO chequera = null;
        List<ChequeraDTO> chequeras = new ArrayList<>();
        RespuestaDTO respuesta = new RespuestaDTO();
        CuentaDTO cuenta = new CuentaDTO();

        try {

            if (numeroCuenta == null || numeroCuenta.isEmpty() || numeroCuenta.equalsIgnoreCase("")
                    || codigoCanal == null || codigoCanal.isEmpty() || codigoCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_CUENTAS_CORRIENTE", "IB_P_OBTENER_CHEQ_ENTREGADAS", 4, codigoCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setString(1, numeroCuenta);
            statement.setString(2, codigoCanal);
            statement.registerOutParameter(3, OracleTypes.CURSOR);
            statement.registerOutParameter(4, OracleTypes.VARCHAR);

            this.ejecuto = statement.execute();

            //Ajuste por error al devolver cuentas sin chequeras activas y/o disponibles
            respuesta = actuaciones.obtenerDescripcionSalidaSP(new String(this.statement.getBytes(4), CHARSET_ORACLE_9), codigoCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            } else {
                if (respuesta.getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    objResultSet = (ResultSet) statement.getObject(3);
                    while (objResultSet.next()) {
                        chequera = new ChequeraDTO();

                        chequera.setNumeroPrimerCheque(String.valueOf(this.objResultSet.getInt("numero_primer_cheque")));
                        chequera.setNumeroUltimoCheque(String.valueOf(this.objResultSet.getInt("numero_ultimo_cheque")));
                        chequera.setCantidadCheques(String.valueOf(this.objResultSet.getInt("cantidad_cheques")));
                        chequera.setAgenciaOrigen(this.getCharSetOracle9(objResultSet, "nombre_agencia"));
                        chequera.setFechaEntrega(this.getUtilDate(objResultSet, "fecha_entrega"));

                        chequeras.add(chequera);
                    }

                    cuenta.setChequeras(chequeras);

                }
            }
        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN listarChequerasEntregadas: ")
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            cuenta.setRespuesta(respuesta);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listarChequerasEntregadas: ")
                    .append("CTA-").append(this.numeroCuentaFormato(numeroCuenta))
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            cuenta.setRespuesta(respuesta);
            this.cerrarConexion(codigoCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN listarChequerasEntregadas: ")
//                    .append("CTA-").append(this.numeroCuentaFormato(numeroCuenta))
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return cuenta;
    }

    /**
     * Obtiene el estado de los cheques de una chequera.
     *
     * @param numeroCuenta numero de la cuenta
     * @param numeroPrimerCheque numero del primer cheque
     * @param codigoCanal codigo del canal
     * @return ChequeraDTO Listado de los cheques entregados
     */
    @Override
    public ChequeraDTO listarCheques(String numeroCuenta, String numeroPrimerCheque, String codigoCanal) {

        ChequeraDTO chequera = new ChequeraDTO();
        List<ChequeDTO> cheques = new ArrayList<>();
        RespuestaDTO respuesta = new RespuestaDTO();
        ChequeDTO cheque = new ChequeDTO();

        try {

            if (numeroCuenta == null || numeroCuenta.isEmpty() || numeroCuenta.equalsIgnoreCase("")
                    || codigoCanal == null || codigoCanal.isEmpty() || codigoCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_CUENTAS_CORRIENTE", "IB_P_OBTENER_CHEQUES_CHEQ_ACT", 4, codigoCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setString(1, numeroCuenta);
            statement.setInt(2, Integer.parseInt(numeroPrimerCheque));
            statement.setString(3, codigoCanal);
            statement.registerOutParameter(4, OracleTypes.CURSOR);

            this.ejecuto = statement.execute();

            objResultSet = (ResultSet) statement.getObject(4);
            while (objResultSet.next()) {
                cheque = new ChequeDTO();

                cheque.setNumeroCheque(String.valueOf(this.objResultSet.getInt("numero_cheque")));
                cheque.setEstado(this.getCharSetOracle9(objResultSet, "estado"));
                cheque.setConformado(this.getCharSetOracle9(objResultSet, "conformado"));
                cheque.setTextoEstado(this.getCharSetOracle9(objResultSet, "texto_estado"));

                cheques.add(cheque);
            }

            chequera.setListCheque(cheques);

            if (cheques.isEmpty()) {
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN listarCheques: ")
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listarCheques: ")
                    .append("CTA-").append(this.numeroCuentaFormato(numeroCuenta))
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
        } finally {
            chequera.setRespuesta(respuesta);
            this.cerrarConexion(codigoCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN listarCheques: ")
//                    .append("CTA-").append(this.numeroCuentaFormato(numeroCuenta))
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return chequera;
    }

    /**
     * Realiza la solicitud de chequeras de una cuenta corriente.
     *
     * @param numeroCuenta numero de la cuenta
     * @param codigoCanal codigo del canal
     * @return ChequeraDTO Listado de chequeras por cliente
     */
    @Override
    public UtilDTO solicitarChequeras(String numeroCuenta, String tipoChequera, String cantidad, String identificacion, String retira, String agenciaRetira, String codigoCanal) {
        UtilDTO parametros = new UtilDTO();
        Map mapaParametros = new HashMap();
        RespuestaDTO respuesta = new RespuestaDTO();

        try {

            if (numeroCuenta == null || numeroCuenta.isEmpty() || numeroCuenta.equalsIgnoreCase("")
                    || tipoChequera == null || tipoChequera.isEmpty() || tipoChequera.equalsIgnoreCase("")
                    || cantidad == null || cantidad.isEmpty() || cantidad.equalsIgnoreCase("")
                    || agenciaRetira == null || agenciaRetira.isEmpty() || agenciaRetira.equalsIgnoreCase("")
                    || codigoCanal == null || codigoCanal.isEmpty() || codigoCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_CUENTAS_CORRIENTE", "IB_P_SOLICITAR_CHEQUERAS", 9, codigoCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setString(1, numeroCuenta);
            statement.setInt(2, Integer.parseInt(tipoChequera));
            statement.setInt(3, Integer.parseInt(cantidad));
            if ((identificacion != null) && (!identificacion.isEmpty())) {
                statement.setString(4, identificacion);
            } else {
                statement.setString(4, null);
            }
            if ((retira != null) && (!retira.isEmpty())) {
                statement.setString(5, retira);
            } else {
                statement.setString(5, null);
            }
            statement.setInt(6, Integer.parseInt(agenciaRetira));
            statement.setString(7, codigoCanal);
            statement.registerOutParameter(8, OracleTypes.VARCHAR);
            statement.registerOutParameter(9, OracleTypes.VARCHAR);

            this.ejecuto = statement.execute();

            respuesta = actuaciones.obtenerDescripcionSalidaSP(new String(this.statement.getBytes(9), CHARSET_ORACLE_9), codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
                throw new DAOException();
            } else if (respuesta.getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO)) {
                mapaParametros.put("numSolicitud", new String(this.statement.getBytes(8), CHARSET_ORACLE_9));
            }

            parametros.setResulados(mapaParametros);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN solicitarChequeras: ")
                    .append("CTA-").append(this.numeroCuentaFormato(numeroCuenta))
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
        } finally {
            parametros.setRespuesta(respuesta);
            this.cerrarConexion(codigoCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN solicitarChequeras: ")
//                    .append("CTA-").append(this.numeroCuentaFormato(numeroCuenta))
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        parametros.setRespuesta(respuesta);
        return parametros;
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
    @Override
    public UtilDTO suspenderChequeras(String numeroCuenta, String motivoSuspension, String numPrimerCheque, String numUltimoCheque, String listCheques, String nombreCanal) {
        UtilDTO utilDTO = new UtilDTO();
        Map resultado = new HashMap();
        RespuestaDTO respuesta = new RespuestaDTO();
        StringBuilder errorCheques = new StringBuilder();
        StringBuilder chequesSuspendidos = new StringBuilder();
        int cantSuspendidos = 0;

        try {

            if (numeroCuenta == null || numeroCuenta.isEmpty() || numeroCuenta.equalsIgnoreCase("")
                    || motivoSuspension == null || motivoSuspension.isEmpty() || motivoSuspension.equalsIgnoreCase("")
                    || nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")
                    || ((numPrimerCheque == null || numPrimerCheque.isEmpty() || numPrimerCheque.equalsIgnoreCase(""))
                    && (numUltimoCheque == null || numUltimoCheque.isEmpty() || numUltimoCheque.equalsIgnoreCase(""))
                    && (listCheques == null || listCheques.isEmpty() || listCheques.equalsIgnoreCase("")))) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, nombreCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_CUENTAS_CORRIENTE", "IB_P_SUSPENDER_CHEQUES", 8, nombreCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            //Si la suspención es por chequera
            if ((listCheques == null) || (listCheques.equalsIgnoreCase("")) || (listCheques.length() == 0)) {
                //seteo de parametros de entrada y/o salida
                statement.setString(1, numeroCuenta);
                statement.setInt(2, Integer.parseInt(motivoSuspension));
                statement.setInt(3, Integer.parseInt(numPrimerCheque));
                statement.setInt(4, Integer.parseInt(numUltimoCheque));
                statement.setString(5, nombreCanal);
                statement.registerOutParameter(6, OracleTypes.INTEGER);
                statement.registerOutParameter(7, OracleTypes.INTEGER);
                statement.registerOutParameter(8, OracleTypes.INTEGER);

                this.ejecuto = statement.execute();

                respuesta = actuaciones.obtenerDescripcionSalidaSP(String.valueOf(this.statement.getInt(8)), nombreCanal);

                if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new DAOException();
                } else {
                    if (respuesta.getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO)) {
                        resultado.put("nroSuspensiones", this.statement.getInt(6));
                        resultado.put("nroReferencia", this.statement.getInt(7));
                        utilDTO.setRespuesta(respuesta);
                    }
                }

            } else {//Si la suspención es por cheque

                String[] cheques = listCheques.split(",");

                for (String i : cheques) {

                    //seteo de parametros de entrada y/o salida
                    statement.setString(1, numeroCuenta);
                    statement.setInt(2, Integer.parseInt(motivoSuspension));
                    statement.setInt(3, Integer.parseInt(i));
                    statement.setInt(4, Integer.parseInt(i));
                    statement.setString(5, nombreCanal);
                    statement.registerOutParameter(6, OracleTypes.INTEGER);
                    statement.registerOutParameter(7, OracleTypes.INTEGER);
                    statement.registerOutParameter(8, OracleTypes.INTEGER);

                    this.ejecuto = statement.execute();

                    respuesta = actuaciones.obtenerDescripcionSalidaSP(String.valueOf(this.statement.getInt(8)), nombreCanal);

                    if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO) && respuesta.getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO)) {
                        resultado.put("nroSuspensiones", this.statement.getInt(6));
                        resultado.put("nroReferencia", this.statement.getInt(7));

                        if (chequesSuspendidos.length() > 0) {
                            chequesSuspendidos.append(",");
                        }
                        chequesSuspendidos.append(i);
                        cantSuspendidos++;

                    } else {
                        if (errorCheques.length() > 0) {
                            errorCheques.append(",");
                        }
                        errorCheques.append(i);
                    }
                }

                if (cantSuspendidos == 0) {
                    respuesta.setCodigoSP(CODIGO_EXCEPCION_GENERICA);
                    respuesta.setDescripcionSP(DESCRIPCION_RESPUESTA_FALLIDA);
                    respuesta.setTextoSP(errorCheques.toString());
                } else {
                    respuesta = new RespuestaDTO();
                    if (errorCheques.length() > 0) {
                        respuesta.setTextoSP(errorCheques.toString());
                    }
                }
                resultado.put("chequesSuspendidos", chequesSuspendidos.toString());
                resultado.put("errorCheques", errorCheques.toString());
            }
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN suspenderChequeras: ")
                    .append("CTA-").append(this.numeroCuentaFormato(numeroCuenta))
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
            utilDTO.setRespuesta(respuesta);
        } finally {
            utilDTO.setResulados(resultado);
            utilDTO.setRespuesta(respuesta);
            this.cerrarConexion(nombreCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN suspenderChequeras: ")
//                    .append("CTA-").append(this.numeroCuentaFormato(numeroCuenta))
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return utilDTO;
    }

    /**
     * Obtiene los saldos bloqueados de una cuenta
     *
     * @param tipoCuenta tipo de cuenta BCA, BCC o BDC
     * @param numeroCuenta numero de la cuenta
     * @param codigoCanal codigo del canal
     * @return listado de Saldos Bloqueados de una Cuenta
     */
    @Override
    public CuentaDTO listadoSaldoBloqueadoCuenta(String tipoCuenta, String numeroCuenta, String codigoCanal) {
        CuentaDTO cuenta = new CuentaDTO();
        List<MovimientoCuentaDTO> listMov = new ArrayList<>();
        RespuestaDTO respuesta = new RespuestaDTO();
        try {

            if (tipoCuenta == null || tipoCuenta.isEmpty() || tipoCuenta.equalsIgnoreCase("")
                    || numeroCuenta == null || numeroCuenta.isEmpty() || numeroCuenta.equalsIgnoreCase("")
                    || codigoCanal == null || codigoCanal.isEmpty() || codigoCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            switch (tipoCuenta) {
                case (CODIGO_PRODUCTO_CA): {
                    respuesta = this.generarQuery("IB_K_CUENTAS_AHORRO", "IB_P_OBTENER_SALDOS_BLOQUEADOS", 4, codigoCanal);
                    break;
                }
                case (CODIGO_PRODUCTO_CC): {
                    respuesta = this.generarQuery("IB_K_CUENTAS_CORRIENTE", "IB_P_OBTENER_SALDOS_BLOQUEADOS", 4, codigoCanal);
                    break;
                }
            }

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setString(1, numeroCuenta);
            statement.setString(2, codigoCanal);
            statement.registerOutParameter(3, OracleTypes.CURSOR);
            statement.registerOutParameter(4, OracleTypes.INTEGER);

            this.ejecuto = statement.execute();

            respuesta = actuaciones.obtenerDescripcionSalidaSP(String.valueOf(this.statement.getInt(4)), codigoCanal);

            if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO) && respuesta.getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO)) {
                objResultSet = (ResultSet) statement.getObject(3);
                while (objResultSet.next()) {
                    MovimientoCuentaDTO movimientoCuentaDTO = new MovimientoCuentaDTO();

                    movimientoCuentaDTO.setFechaBloqueo(formatearFecha(this.getUtilDate(objResultSet, "fechaBloqueo"), FORMATO_FECHA_SIMPLE));
                    movimientoCuentaDTO.setMonto(this.objResultSet.getBigDecimal("saldo") == null ? BigDecimal.ZERO : this.objResultSet.getBigDecimal("saldo"));

                    listMov.add(movimientoCuentaDTO);
                }

                cuenta.setListSaldoBloqueado(listMov);

                if (listMov.isEmpty()) {
                    throw new NoResultException();
                }
            }

        } catch (NoResultException d) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN listadoSaldoBloqueadoCuenta: ")
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(d.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listadoSaldoBloqueadoCuenta: ")
                    .append("CTA-").append(this.numeroCuentaFormato(numeroCuenta))
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            cuenta.setRespuesta(respuesta);
            this.cerrarConexion(codigoCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN listadoSaldoBloqueadoCuenta: ")
//                    .append("CTA-").append(this.numeroCuentaFormato(numeroCuenta))
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return cuenta;
    }

    /**
     * Obtiene los saldos bloqueados de una cuenta
     *
     * @param tipoCuenta tipo de cuenta BCA, BCC o BDC
     * @param numeroCuenta numero de la cuenta
     * @param codigoCanal codigo del canal
     * @return listado de Saldos Bloqueados de una Cuenta
     */
    @Override
    public CuentaDTO listadoSaldoDiferidoCuenta(String tipoCuenta, String numeroCuenta, String codigoCanal) {
        CuentaDTO cuenta = new CuentaDTO();
        List<MovimientoCuentaDTO> listMov = new ArrayList<>();
        RespuestaDTO respuesta = new RespuestaDTO();
        try {

            if (tipoCuenta == null || tipoCuenta.isEmpty() || tipoCuenta.equalsIgnoreCase("")
                    || numeroCuenta == null || numeroCuenta.isEmpty() || numeroCuenta.equalsIgnoreCase("")
                    || codigoCanal == null || codigoCanal.isEmpty() || codigoCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            switch (tipoCuenta) {
                case (CODIGO_PRODUCTO_CA): {
                    respuesta = this.generarQuery("IB_K_CUENTAS_AHORRO", "IB_P_OBTENER_SALDOS_DIFERIDOS", 4, codigoCanal);
                    break;
                }
                case (CODIGO_PRODUCTO_CC): {
                    respuesta = this.generarQuery("IB_K_CUENTAS_CORRIENTE", "IB_P_OBTENER_SALDOS_DIFERIDOS", 4, codigoCanal);
                    break;
                }
            }

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setString(1, numeroCuenta);
            statement.setString(2, codigoCanal);
            statement.registerOutParameter(3, OracleTypes.CURSOR);
            statement.registerOutParameter(4, OracleTypes.INTEGER);

            this.ejecuto = statement.execute();

            respuesta = actuaciones.obtenerDescripcionSalidaSP(String.valueOf(this.statement.getInt(4)), codigoCanal);

            if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO) && respuesta.getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO)) {
                objResultSet = (ResultSet) statement.getObject(3);
                while (objResultSet.next()) {
                    MovimientoCuentaDTO movimientoCuentaDTO = new MovimientoCuentaDTO();

                    movimientoCuentaDTO.setFechaReserva(formatearFecha(this.getUtilDate(objResultSet, "fechaReserva"), FORMATO_FECHA_SIMPLE));
                    movimientoCuentaDTO.setMonto(this.objResultSet.getBigDecimal("monto") == null ? BigDecimal.ZERO : this.objResultSet.getBigDecimal("monto"));

                    listMov.add(movimientoCuentaDTO);
                }

                cuenta.setListSaldoDiferido(listMov);

                if (listMov.isEmpty()) {
                    throw new NoResultException();
                }
            }

        } catch (NoResultException d) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN listadoSaldoDiferidoCuenta: ")
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(d.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listadoSaldoDiferidoCuenta: ")
                    .append("CTA-").append(this.numeroCuentaFormato(numeroCuenta))
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            cuenta.setRespuesta(respuesta);
            this.cerrarConexion(codigoCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN listadoSaldoDiferidoCuenta: ")
//                    .append("CTA-").append(this.numeroCuentaFormato(numeroCuenta))
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return cuenta;
    }

    @Override
    public CuentaDTO estadoCuenta(String tipoCuenta, String numeroCuenta, String fechaIni, String fechaFin, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        CuentaDTO cuenta = new CuentaDTO();
        try {

            if (tipoCuenta == null || tipoCuenta.isEmpty() || tipoCuenta.equalsIgnoreCase("")
                    || numeroCuenta == null || numeroCuenta.isEmpty() || numeroCuenta.equalsIgnoreCase("")
                    || fechaIni == null || fechaIni.isEmpty() || fechaIni.equalsIgnoreCase("")
                    || fechaFin == null || fechaFin.isEmpty() || fechaFin.equalsIgnoreCase("")
                    || codigoCanal == null || codigoCanal.isEmpty() || codigoCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            switch (tipoCuenta) {
                case (CODIGO_PRODUCTO_CA): {
                    respuesta = this.generarQuery("IB_K_CUENTAS_AHORRO", "IB_P_RESUMEN_ESTADO_CTA", 16, codigoCanal);
                    break;
                }
                case (CODIGO_PRODUCTO_CC): {
                    respuesta = this.generarQuery("IB_K_CUENTAS_CORRIENTE", "IB_P_RESUMEN_ESTADO_CTA", 16, codigoCanal);
                    break;
                }
            }

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            statement.setString(1, numeroCuenta);
            statement.setString(2, codigoCanal);
            statement.setDate(3, this.getSQLDate(fechaIni, FORMATO_FECHA_SIMPLE));
            statement.setDate(4, this.getSQLDate(fechaFin, FORMATO_FECHA_SIMPLE));
            statement.registerOutParameter(5, OracleTypes.CURSOR);
            statement.registerOutParameter(6, OracleTypes.NUMBER);
            statement.registerOutParameter(7, OracleTypes.NUMBER);
            statement.registerOutParameter(8, OracleTypes.NUMBER);
            statement.registerOutParameter(9, OracleTypes.NUMBER);
            statement.registerOutParameter(10, OracleTypes.NUMBER);
            statement.registerOutParameter(11, OracleTypes.NUMBER);
            statement.registerOutParameter(12, OracleTypes.NUMBER);
            statement.registerOutParameter(13, OracleTypes.NUMBER);
            statement.registerOutParameter(14, OracleTypes.NUMBER);
            statement.registerOutParameter(15, OracleTypes.NUMBER);
            statement.registerOutParameter(16, OracleTypes.NUMBER);

            this.ejecuto = statement.execute();
            objResultSet = (ResultSet) statement.getObject(5);
            CabeceraEstadoCtaDTO cabecera = new CabeceraEstadoCtaDTO();

            while (objResultSet.next()) {
                cabecera.setCodAgencia(this.objResultSet.getBigDecimal("CODIGO_AGENCIA"));
                cabecera.setNombre(this.getCharSetOracle9(objResultSet, "NOMBRE_CLIENTE"));
                cabecera.setDireccion(this.getCharSetOracle9(objResultSet, "DIRECCION"));

            }

            if (cabecera.getNombre().isEmpty() || cabecera.getNombre() == null || cabecera.getDireccion().isEmpty()
                    || cabecera.getDireccion() == null || cabecera.getCodAgencia() == null) {
                throw new NoResultException();
            }

            respuesta = actuaciones.obtenerDescripcionSalidaSP(String.valueOf(this.statement.getInt(16)), codigoCanal);

            if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO) && respuesta.getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO)) {
                cabecera.setCantDebitos(this.statement.getBigDecimal(7));
                cabecera.setCantCreditos(this.statement.getBigDecimal(6));
                cabecera.setCantDepositos(this.statement.getBigDecimal(8));
                cabecera.setCantCheques(this.statement.getBigDecimal(9));
                cabecera.setSaldoDebitos(this.statement.getBigDecimal(13));
                cabecera.setSaldoCreditos(this.statement.getBigDecimal(12));
                cabecera.setSaldoDepositos(this.statement.getBigDecimal(10));
                cabecera.setSaldoCheques(this.statement.getBigDecimal(11));
                cabecera.setSaldoInicial(this.statement.getBigDecimal(14));
                cabecera.setSaldoTotal(this.statement.getBigDecimal(15));

            } else {
                throw new NoResultException();
            }

            cuenta.setCabecera(cabecera);

        } catch (NoResultException d) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN estadoCuenta: ")
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(d.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN estadoCuenta: ")
                    .append("CTA-").append(this.numeroCuentaFormato(numeroCuenta))
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            cuenta.setRespuesta(respuesta);
            this.cerrarConexion(codigoCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN estadoCuenta: ")
//                    .append("CTA-").append(this.numeroCuentaFormato(numeroCuenta))
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }

        return cuenta;
    }

    public CuentaDTO listarMovimientosPorTransaccion(String tipoCuenta, String numeroCuenta, String fechaIni, String fechaFin, String regIni, String regFin, String tipoOrdenFecha, String canal, String codTransaccion) {

        MovimientoCuentaDTO movimiento = null;
        List<MovimientoCuentaDTO> movimientos = new ArrayList<>();
        RespuestaDTO respuesta = new RespuestaDTO();
        CuentaDTO cuenta = new CuentaDTO();

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_CUENTAS_BASE", "IB_P_OBT_MOV_X_TRX", 11, canal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setString(1, numeroCuenta);
            if (fechaIni != null && !fechaIni.isEmpty() && !fechaIni.equalsIgnoreCase("null")) {
                statement.setDate(2, this.getSQLDate(fechaIni, FORMATO_FECHA_SIMPLE));
            } else {
                statement.setDate(2, null);
            }
            if (fechaFin != null && !fechaFin.isEmpty() && !fechaFin.equalsIgnoreCase("null")) {
                statement.setDate(3, this.getSQLDate(fechaFin, FORMATO_FECHA_SIMPLE));
            } else {
                statement.setDate(3, null);
            }
            statement.setInt(4, Integer.parseInt(regIni));
            statement.setInt(5, Integer.parseInt(regFin));
            statement.setString(6, tipoCuenta);
            statement.setInt(7, Integer.parseInt(codTransaccion));
            statement.setString(8, tipoOrdenFecha);
            statement.setString(9, canal);
            statement.registerOutParameter(10, OracleTypes.INTEGER);
            statement.registerOutParameter(11, OracleTypes.CURSOR);

            this.ejecuto = statement.execute();

            objResultSet = (ResultSet) statement.getObject(11);

            //  respuesta = actuaciones.obtenerDescripcionSalidaSP(String.valueOf(this.statement.getInt(10)), canal);
            //  if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO) && respuesta.getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO)) {  
            while (objResultSet.next()) {
                movimiento = new MovimientoCuentaDTO();
                movimiento.setDescripcion(this.getCharSetOracle9(objResultSet, "descripcion"));
                movimiento.setFechaTransaccion(this.getUtilDate(objResultSet, "fechaTransaccion"));
                movimiento.setFechaTransaccionString(formatearFecha(this.getUtilDate(objResultSet, "fechaTransaccion"), FORMATO_FECHA_SIMPLE));
                if (this.objResultSet.getBigDecimal("numeroReferencia") != null) {
                    movimiento.setNumeroReferencia(this.objResultSet.getBigDecimal("numeroReferencia").toString());
                } else {
                    movimiento.setNumeroReferencia("");
                }
                if (this.objResultSet.getBigDecimal("secuenciaMovimiento") != null) {
                    movimiento.setSecuenciaMovimiento(this.objResultSet.getBigDecimal("secuenciaMovimiento").toString());
                } else {
                    movimiento.setSecuenciaMovimiento("");
                }
                if (this.getCharSetOracle9(objResultSet, "secuenciaExtMovimiento") != null) {
                    movimiento.setSecuenciaExtMovimiento(this.getCharSetOracle9(objResultSet, "secuenciaExtMovimiento"));
                } else {
                    movimiento.setSecuenciaExtMovimiento("");

                }
                if (tipoCuenta.equals(CODIGO_PRODUCTO_ME)) {
                    movimiento.setCreditosBs(this.objResultSet.getBigDecimal("creditosBs"));
                    movimiento.setCreditosDolar(this.objResultSet.getBigDecimal("creditosDolar"));
                    movimiento.setDebitosBs(this.objResultSet.getBigDecimal("debitosBs"));
                    movimiento.setDebitosDolar(this.objResultSet.getBigDecimal("debitosDolar"));
                    movimiento.setValorCambioTasa(this.objResultSet.getBigDecimal("valorCambioTasa"));
                } else {
                    movimiento.setIngreso(this.objResultSet.getBigDecimal("ingreso"));
                    movimiento.setEgreso(this.objResultSet.getBigDecimal("egreso"));
                }
                movimientos.add(movimiento);
            }

            // }
            cuenta.setCodigoTipoProducto(tipoCuenta);
            cuenta.setMovimientos(movimientos);

            if (cuenta.getMovimientos() == null || cuenta.getMovimientos().isEmpty()) {
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);//revisar el log
            logger.error( new StringBuilder("ERROR DAO EN listarMovimientos: ")
                    .append("CTA-").append(this.numeroCuentaFormato(numeroCuenta))
                    .append("-CH-").append(canal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listarMovimientos: ")
                    .append("CTA-").append(this.numeroCuentaFormato(numeroCuenta))
                    .append("-CH-").append(canal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            cuenta.setRespuesta(respuesta);
            this.cerrarConexion(canal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN listarMovimientos: ")
//                    .append("CTA-").append(this.numeroCuentaFormato(numeroCuenta))
//                    .append("-CH-").append(canal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return cuenta;

    }

    /**
     * Método que obtiene el listado de transacciones asociadas a los
     * movimientos en las cuentas
     *
     * @param tipoCuenta tipo de Cuenta
     * @param nombreCanal nombre del Canal
     * @return TransaccionesCuentasDTO listado de transacciones asociadas a los
     * movimientos
     */
    @Override
    public TransaccionesCuentasDTO listadoTransaccionesCuentas(String tipoCuenta, String nombreCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        List<TransaccionesCuentasDTO> transacciones = new ArrayList<>();
        TransaccionesCuentasDTO trans = null;
        TransaccionesCuentasDTO transDTO = new TransaccionesCuentasDTO();

        try {

            if (tipoCuenta == null || tipoCuenta.isEmpty() || tipoCuenta.equalsIgnoreCase("")
                    || nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, nombreCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            switch (tipoCuenta) {
                case (CODIGO_PRODUCTO_CA): {
                    respuesta = this.generarQuery("IB_K_CUENTAS_AHORRO", "IB_P_LISTA_TRX", 3, nombreCanal);
                    break;
                }
                case (CODIGO_PRODUCTO_CC): {
                    respuesta = this.generarQuery("IB_K_CUENTAS_CORRIENTE", "IB_P_LISTA_TRX", 3, nombreCanal);
                    break;
                }
            }

            statement.setString(1, nombreCanal);
            statement.registerOutParameter(2, OracleTypes.CURSOR);
            statement.registerOutParameter(3, OracleTypes.NUMBER);
            this.ejecuto = statement.execute();
            objResultSet = (ResultSet) statement.getObject(2);
            while (objResultSet.next()) {
                trans = new TransaccionesCuentasDTO();
                trans.setCodigoTransaccion(this.objResultSet.getString("codigo_tipo_transaccion"));
                trans.setDescripcionTransaccion(this.getCharSetOracle9(objResultSet, "descripcion"));

                transacciones.add(trans);
            }

            transDTO.setListadoTransacciones(transacciones);

            if (transacciones.isEmpty()) {
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);//revisar el log
            logger.error( new StringBuilder("ERROR DAO EN listadoTransaccionesCuentas: ")
                    .append("TCTA-").append(tipoCuenta)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listadoTransaccionesCuentas: ")
                    .append("TCTA-").append(tipoCuenta)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log            
        } finally {
            transDTO.setRespuesta(respuesta);
            this.cerrarConexion(nombreCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN listadoTransaccionesCuentas: ")
//                    .append("TCTA-").append(tipoCuenta)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }

        return transDTO;
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
    @Override
    public UtilDTO validarCuentaEmpresaCliente(String nroCuenta, Character tipoRif, String rif, String idCanal, String codigoCanal) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        String rifCompleto = tipoRif + rif;
        Map resultado = new HashMap();

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_AUTOGESTION", "IB_P_VALIDA_CTA_RIF", 7, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            //seteo de parametros de entrada y/o salida
            statement.setString(1, rifCompleto);
            statement.setString(2, nroCuenta);
            statement.setString(3, codigoCanal);
            statement.registerOutParameter(4, OracleTypes.INTEGER); //CodCliente
            statement.registerOutParameter(5, OracleTypes.VARCHAR); //RazonSocial
            statement.registerOutParameter(6, OracleTypes.INTEGER); //CodSalida
            statement.registerOutParameter(7, OracleTypes.CHAR); //v_Salida
            this.ejecuto = statement.execute();
            //RETORNA 1 SI LA CUENTA T
            Integer codSalida = (Integer) statement.getObject(6);
            //RETORNA V SI LA CUENTA ESTA ACTIVA EN CASO CONTRARIO RETORNA F
            String vSalida = (String) statement.getObject(7);

            if (!codSalida.equals(1) || vSalida.trim().equalsIgnoreCase("F")) {
                resultado.put(VALIDO, false);
            } else {
                //CODIGO DEL CLIENTE (CODIGO DE LA EMPRESA EN EL CORE)
                Integer codCliente = (Integer) statement.getObject(4);
                //RAZON SOCIAL (NOMBRE DE LA EMPRESA EN EL CORE)
                String nombreEmpresa = new String(this.statement.getBytes(5), CHARSET_ORACLE_9);
                resultado.put(NOMBRE_EMPRESA, nombreEmpresa);
                resultado.put(CODIGO_EMPRESA, codCliente);
                resultado.put(VALIDO, true);
            }
            utilDTO.setResulados(resultado);
            utilDTO.setRespuesta(respuesta);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN validarCuentaCliente: ")
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);

            logger.error( new StringBuilder("ERROR EN SP: IB_K_AUTOGESTION.IB_P_VALIDA_CTA_RIF ")
                    .append("Parametros SP: rif: ").append(rif).append(" nroCuenta: ").append(nroCuenta)
                    .append(" codigoCanal: ").append(codigoCanal)
                    .toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
            utilDTO.setRespuesta(respuesta);
        } finally {
            utilDTO.setRespuesta(respuesta);
            this.cerrarConexion(codigoCanal);
        }
        return utilDTO;
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
    @Override
    public UtilDTO obtenerSaldoDisponibleCta(String nroCuenta, String idCanal, String codigoCanal) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        Map resultado = new HashMap();

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_CUENTAS_BASE", "IB_P_OBT_SALDO_CTA", 4, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            //seteo de parametros de entrada y/o salida
            statement.setString(1, nroCuenta);
            statement.setString(2, codigoCanal);
            statement.registerOutParameter(3, OracleTypes.NUMBER); //onSaldo
            statement.registerOutParameter(4, OracleTypes.INTEGER); //onCodSalida
            this.ejecuto = statement.execute();

            //Ajuste por error al devolver cuentas sin chequeras activas y/o disponibles
            respuesta = actuaciones.obtenerDescripcionSalidaSP(((Integer) statement.getObject(4)).toString(), codigoCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                resultado.put("saldo", "No Disponible");
                utilDTO.setResulados(resultado);
                throw new DAOException();
            } else {
                if (respuesta.getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    resultado.put("saldo", formatearMonto((BigDecimal) statement.getObject(3)));
                }
            }
            utilDTO.setResulados(resultado);
            utilDTO.setRespuesta(respuesta);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN obtenerSaldoDisponibleCta: ")
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
            utilDTO.setRespuesta(respuesta);
        } finally {
            utilDTO.setRespuesta(respuesta);
            this.cerrarConexion(codigoCanal);
        }
        return utilDTO;
    }

    /**
     * Obtiene el estado de los cheques utilizados de una chequera.
     *
     * @param numeroCuenta numero de la cuenta
     * @param numeroPrimerCheque numero del primer cheque
     * @param codigoCanal codigo del canal
     * @return ChequeraDTO Listado de los cheques entregados
     */
    @Override
    public ChequeraDTO listarChequesChequera(String numeroCuenta, String numeroPrimerCheque, String codigoCanal) {

        ChequeraDTO chequera = new ChequeraDTO();
        List<ChequeDTO> cheques = new ArrayList<>();
        RespuestaDTO respuesta = new RespuestaDTO();
        ChequeDTO cheque;

        try {

            if (numeroCuenta == null || numeroCuenta.isEmpty() || numeroCuenta.equalsIgnoreCase("")
                    || codigoCanal == null || codigoCanal.isEmpty() || codigoCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_CUENTAS_CORRIENTE", "IB_P_OBTENER_CHEQUES_CHEQUERA", 5, codigoCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setString(1, numeroCuenta);
            statement.setInt(2, Integer.parseInt(numeroPrimerCheque));
            statement.setString(3, codigoCanal);
            statement.registerOutParameter(4, OracleTypes.VARCHAR);
            statement.registerOutParameter(5, OracleTypes.CURSOR);

            this.ejecuto = statement.execute();

            String codSalida = new String(this.statement.getBytes(4), CHARSET_ORACLE_9);

            if (!codSalida.isEmpty() || !codSalida.equalsIgnoreCase("")) {
                respuesta = actuaciones.obtenerDescripcionSalidaSP(codSalida, codigoCanal);
                if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO) || !respuesta.getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new DAOException();
                } else {

                    objResultSet = (ResultSet) statement.getObject(5);
                    while (objResultSet.next()) {
                        cheque = new ChequeDTO();

                        cheque.setNumeroCheque(String.valueOf(this.objResultSet.getInt("numero_cheque")));
                        cheque.setEstado(this.getCharSetOracle9(objResultSet, "estado"));
                        cheque.setConformado(this.getCharSetOracle9(objResultSet, "conformado"));
                        cheque.setTextoEstado(this.getCharSetOracle9(objResultSet, "texto_estado"));

                        cheques.add(cheque);
                    }
                    chequera.setListCheque(cheques);

                    if (cheques.isEmpty()) {
                        throw new NoResultException();
                    }
                }
            }

        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN listarChequesChequera: ")
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listarChequesChequera: ")
                    .append("CTA-").append(this.numeroCuentaFormato(numeroCuenta))
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
        } finally {
            chequera.setRespuesta(respuesta);
            this.cerrarConexion(codigoCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN listarChequesChequera: ")
//                    .append("CTA-").append(this.numeroCuentaFormato(numeroCuenta))
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return chequera;
    }

    @Override
    public String obtenerCtaDelSur20DigitosSP(String ctaBeneficiario, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        String cuenta = "";

        try {
            respuesta = conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            /*Consulta del primer SP para cuentas de ahorros*/
            respuesta = generarQuery("IB_K_GENERALES_PJ", "IB_P_OBT_CTA_DELSUR_DIG20", 4, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            //Setting de los parametros de entrada y salida
            statement.setLong(1, Long.parseLong(ctaBeneficiario));
            statement.setString(2, codigoCanal);
            statement.registerOutParameter(3, OracleTypes.VARCHAR);
            statement.registerOutParameter(4, OracleTypes.INTEGER);
            this.ejecuto = statement.execute();

            Integer vCtaCliente = (Integer) statement.getObject(4);

            if (!vCtaCliente.equals(1)) {
                cuenta = "";
            } else {
                cuenta = (String) statement.getObject(3);
            }

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN obtenerCtaDelSur20DigitosSP: ")
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            cerrarConexion(codigoCanal);
        }

        return cuenta;
    }

    /**
     * Metodo para validar que el numero de cuenta introducido exista y
     * pertenesca al benefeciario asignado
     *
     * @param nroCuenta String snumero de cuenta a validar
     * @param identBeneficiario String identificacion del benefeciario
     * @param iCodExtCanal String condigo del canal para realizar la consulta
     * @return UtilDTO indica si la operacion se realizo de manera exitosa o no
     * y contiene el dato de respuesta en el map con key perteneseCta, es una
     * tipo String
     */
    @Override
    public UtilDTO validarCtaBeneficiario(String nroCuenta, String identBeneficiario, String iCodExtCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        Map resultados = new HashMap();
        UtilDTO utilDTO = new UtilDTO();

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, iCodExtCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_PAGOS_GENERALES_PJ", "IB_P_VALIDAR_IDENTF_CUENTA", 5, iCodExtCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setString(1, nroCuenta);
            statement.setString(2, identBeneficiario);
            statement.setString(3, iCodExtCanal);
            statement.setInt(4, OracleTypes.INTEGER);
            statement.registerOutParameter(5, OracleTypes.VARCHAR);

            this.ejecuto = statement.execute();

            resultados.put("perteneseCta", statement.getString(5));
            utilDTO.setResulados(resultados);
            utilDTO.setRespuesta(respuesta);
//            logger.error( new StringBuilder("EXITO DAO EN validarCtaBeneficiario: ")
//                    .append("CTA-").append("")
//                    .append("-CH-").append(iCodExtCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
            respuesta.setCodigo(CODIGO_RESPUESTA_EXITOSO);
            respuesta.setCodigoSP(CODIGO_RESPUESTA_EXITOSO_SP);
            utilDTO.setRespuesta(respuesta);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN validarCtaBeneficiario: ")
                    .append("CTA-").append("")
                    .append("-CH-").append(iCodExtCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
            utilDTO.setRespuesta(respuesta);
        } finally {
            this.cerrarConexion(iCodExtCanal);
        }

        return utilDTO;
    }

}
