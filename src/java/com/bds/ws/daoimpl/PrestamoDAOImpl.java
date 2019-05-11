/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.ActuacionesDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dao.PrestamoDAO;
import com.bds.ws.dto.ClienteDTO;
import com.bds.ws.dto.MovimientoPrestamoDTO;
import com.bds.ws.dto.PrestamoDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.exception.DAOException;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.JNDI_ORACLE_9;
import com.bds.ws.util.DAOUtil;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
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
 * Clase que implementa la interfaz PrestamoDAO
 *
 * @author rony.rodriguez
 */
@Named("PrestamoDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class PrestamoDAOImpl extends DAOUtil implements PrestamoDAO {

    @EJB
    private ActuacionesDAO actuacionesDAO;

    /**
     * Log de sistema
     */
    private static final Logger logger = Logger.getLogger(PrestamoDAOImpl.class.getName());

    /**
     * retorna los prestamos de un cliente
     *
     * @param iCodigoCliente String Codigo del cliente que solicita el resumen.
     * @param iCodExtCanal String Codigo (extendido) del canal desde el cual es
     * llamado el procedimiento.
     * @return ClienteDTO cliente con los prestamos asociados.
     */
    @Override
    public ClienteDTO listadoPrestamosCliente(String iCodigoCliente, String iCodExtCanal) {
        PrestamoDTO prestamo = null;
        RespuestaDTO respuesta = new RespuestaDTO();
        List<PrestamoDTO> prestamos = new ArrayList<>();
        ClienteDTO cliente = new ClienteDTO();

        try {

            if (iCodigoCliente == null || iCodigoCliente.isEmpty() || iCodigoCliente.equalsIgnoreCase("")
                    || iCodExtCanal == null || iCodExtCanal.isEmpty() || iCodExtCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, iCodExtCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_CLIENTES", "IB_P_OBTENER_RESUMEN_PR", 3, iCodExtCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setInt(1, Integer.parseInt(iCodigoCliente));
            statement.setString(2, String.valueOf(iCodExtCanal));
            statement.registerOutParameter(3, OracleTypes.CURSOR);
            this.ejecuto = statement.execute();

            objResultSet = (ResultSet) statement.getObject(3);
            while (objResultSet.next()) {
                prestamo = new PrestamoDTO();
                prestamo.setNumeroPrestamo(getCharSetOracle9(objResultSet, "numeroPrestamo"));
                prestamo.setCodigoTipoProducto(getCharSetOracle9(objResultSet, "codigoTipoProducto"));
                prestamo.setNombreProducto(getCharSetOracle9(objResultSet, "nombreProducto"));
                prestamo.setSiglasTipoMoneda(getCharSetOracle9(objResultSet, "siglasTipoMoneda"));
                prestamo.setSaldoPrestamo(objResultSet.getBigDecimal("saldoPrestamo"));
                prestamos.add(prestamo);

            }
            cliente.setPrestamosCliente(prestamos);
            if (prestamos.isEmpty()) {                
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN listadoPrestamosCliente: ")
                    .append("USR-").append(iCodigoCliente)
                    .append("-CH-").append(iCodExtCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listadoPrestamosCliente: ")
                    .append("USR-").append(iCodigoCliente)
                    .append("-CH-").append(iCodExtCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            cliente.setRespuesta(respuesta);
            this.cerrarConexion(iCodExtCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN listadoPrestamosCliente: ")
//                    .append("USR-").append(iCodigoCliente)
//                    .append("-CH-").append(iCodExtCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return cliente;
    }
    /**
     * retorna los prestamos de un cliente sólo para amortización de préstamos
     *
     * @param iCodigoCliente String Codigo del cliente que solicita el resumen.
     * @param iCodExtCanal String Codigo (extendido) del canal desde el cual es
     * llamado el procedimiento.
     * @return ClienteDTO cliente con los prestamos asociados.
     */
    @Override
    public ClienteDTO listadoPrestamosClienteAP(String iCodigoCliente, String iCodExtCanal) {
        PrestamoDTO prestamo = null;
        RespuestaDTO respuesta = new RespuestaDTO();
        List<PrestamoDTO> prestamos = new ArrayList<>();
        ClienteDTO cliente = new ClienteDTO();

        try {

            if (iCodigoCliente == null || iCodigoCliente.isEmpty() || iCodigoCliente.equalsIgnoreCase("")
                    || iCodExtCanal == null || iCodExtCanal.isEmpty() || iCodExtCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, iCodExtCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_PRESTAMOS", "IB_P_OBTENER_RESUMEN_PR_PAGO", 3, iCodExtCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setInt(1, Integer.parseInt(iCodigoCliente));
            statement.setString(2, String.valueOf(iCodExtCanal));
            statement.registerOutParameter(3, OracleTypes.CURSOR);
            this.ejecuto = statement.execute();

            objResultSet = (ResultSet) statement.getObject(3);
            while (objResultSet.next()) {
                prestamo = new PrestamoDTO();
                prestamo.setNumeroPrestamo(getCharSetOracle9(objResultSet, "numeroPrestamo"));
                prestamo.setCodigoTipoProducto(getCharSetOracle9(objResultSet, "codigoTipoProducto"));
                prestamo.setNombreProducto(getCharSetOracle9(objResultSet, "nombreProducto"));
                prestamo.setSiglasTipoMoneda(getCharSetOracle9(objResultSet, "siglasTipoMoneda"));
                prestamo.setSaldoPrestamo(objResultSet.getBigDecimal("saldoPrestamo"));
                prestamos.add(prestamo);

            }
            cliente.setPrestamosCliente(prestamos);
            if (prestamos.isEmpty()) {
                respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
                throw new NoResultException();
            }

        } catch (NoResultException | DAOException e) {
            logger.error( new StringBuilder("ERROR DAO EN listadoPrestamosClienteAP: ")
                    .append("USR-").append(iCodigoCliente)
                    .append("-CH-").append(iCodExtCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listadoPrestamosClienteAP: ")
                    .append("USR-").append(iCodigoCliente)
                    .append("-CH-").append(iCodExtCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            this.cerrarConexion(iCodExtCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN listadoPrestamosClienteAP: ")
//                    .append("USR-").append(iCodigoCliente)
//                    .append("-CH-").append(iCodExtCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        cliente.setRespuesta(respuesta);
        return cliente;
    }

    /**
     * retorna los datos basicos de la cuenta mediante la cedula y el canal en
     *
     * @param iNumeroPrestamo String Numero del prestamo
     * @param iCodExtCanal String Codigo (extendido) del canal desde el cual es
     * llamado el procedimiento.
     * @return PrestamoDTO datos del prestamo.
     */
    @Override
    public PrestamoDTO obtenerDetalle(String iNumeroPrestamo, String iCodExtCanal) {
        PrestamoDTO prestamo = new PrestamoDTO();
        RespuestaDTO respuesta = new RespuestaDTO();

        try {

            if (iNumeroPrestamo == null || iNumeroPrestamo.isEmpty() || iNumeroPrestamo.equalsIgnoreCase("")
                    || iCodExtCanal == null || iCodExtCanal.isEmpty() || iCodExtCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, iCodExtCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_PRESTAMOS", "IB_P_OBTENER_DETALLE", 4, iCodExtCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setString(1, iNumeroPrestamo);
            statement.setString(2, iCodExtCanal);
            statement.registerOutParameter(3, OracleTypes.CURSOR);

            statement.registerOutParameter(4, OracleTypes.INTEGER);
            this.ejecuto = statement.execute();

            respuesta.setCodigoSP(String.valueOf(this.statement.getInt(4)));

            respuesta = actuacionesDAO.obtenerDescripcionSalidaSP(respuesta.getCodigoSP(), iCodExtCanal);

            if (respuesta.getCodigo().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO) && respuesta.getCodigoSP().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO)) {

                objResultSet = (ResultSet) statement.getObject(3);
                while (objResultSet.next()) {
                    prestamo = new PrestamoDTO();
                    prestamo.setNumeroPrestamo(iNumeroPrestamo);
                    prestamo.setCodigoTipoProducto(getCharSetOracle9(objResultSet, "codigoTipoProducto"));
                    prestamo.setNombreProducto(getCharSetOracle9(objResultSet, "nombreProducto"));
                    prestamo.setFechaLiquidacion(this.getUtilDate(objResultSet, "fechaLiquidacion"));
                    prestamo.setFechaVencimiento(this.getUtilDate(objResultSet, "fechaVencimiento"));
                    prestamo.setFechaProximoPago(this.getUtilDate(objResultSet, "fechaProximoPago"));
                    prestamo.setMontoAprobado(objResultSet.getBigDecimal("montoAprobado"));
                    prestamo.setTasa(objResultSet.getBigDecimal("tasa"));
                    prestamo.setMontoCuota(objResultSet.getBigDecimal("montoCuota"));
                    prestamo.setSaldoActual(objResultSet.getBigDecimal("saldoActual"));
                    prestamo.setCantidadCuotasPagadas(objResultSet.getBigDecimal("cantidadCuotasPagadas"));

                }
                if (prestamo.getNumeroPrestamo() == null) {
                    throw new NoResultException();
                }

            }

        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN obtenerDetalle: ")
                    .append("NPR-").append(this.numeroCuentaFormato(iNumeroPrestamo))
                    .append("-CH-").append(iCodExtCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN obtenerDetalle: ")
                    .append("NPR-").append(this.numeroCuentaFormato(iNumeroPrestamo))
                    .append("-CH-").append(iCodExtCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            prestamo.setRespuesta(respuesta);
            this.cerrarConexion(iCodExtCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN obtenerDetalle: ")
//                    .append("NPR-").append(this.numeroCuentaFormato(iNumeroPrestamo))
//                    .append("-CH-").append(iCodExtCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return prestamo;
    }

    /**
     * retorna los pagos para el prestamo seleccionado
     *
     * @param iNumeroPrestamo String numero de cuenta
     * @param fechaIni String fecha de incio del filtro
     * @param fechaFin String fecha de fin del filtro
     * @param regIni String registro de Inicio para la paginacion
     * @param regFin String registro de fin para la paginacion
     * @param tipoOrdenFecha String tipo de Orden por Fecha: ASC(por defecto),
     * DESC
     * @param canal canal por el cual se realiza la consulta
     * @return Prestamo el prestamo con los pagos (solo vienen los datos de los
     * pagos)
     */
    @Override
    public PrestamoDTO listadoPagosPrestamo(String iNumeroPrestamo, String fechaIni, String fechaFin,
            String regIni, String regFin, String tipoOrdenFecha, String canal) {
        PrestamoDTO prestamo = new PrestamoDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        MovimientoPrestamoDTO movimiento = null;
        List<MovimientoPrestamoDTO> movimientos = new ArrayList<>();
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        prestamo.setNumeroPrestamo(iNumeroPrestamo);

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_PRESTAMOS", "IB_P_OBTENER_MOVIMIENTOS", 8, canal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            
            statement.setString(1, iNumeroPrestamo);
            if (fechaIni != null && !fechaIni.isEmpty()) {
                statement.setDate(2, this.getSQLDate(fechaIni, FORMATO_FECHA_SIMPLE));
            } else {
                statement.setDate(2, null);
            }
            if (fechaFin != null && !fechaFin.isEmpty()) {
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

                movimiento = new MovimientoPrestamoDTO();
                movimiento.setNumeroCuota(objResultSet.getBigDecimal("numeroCuota"));
                movimiento.setFechaVencimiento(this.getUtilDate(objResultSet, "fechaVencimiento"));
                movimiento.setFechaDePago(this.getUtilDate(objResultSet, "fechaDePago"));
                movimiento.setCapitalPagado(objResultSet.getBigDecimal("capitalPagado"));
                movimiento.setInteresesPagado(objResultSet.getBigDecimal("interesesPagado"));
                movimiento.setMoraPagada(objResultSet.getBigDecimal("moraPagada"));
                movimiento.setSeguroPagado(objResultSet.getBigDecimal("seguroPagado"));
                movimiento.setCapital(objResultSet.getBigDecimal("Capital"));
                movimiento.setIntereses(objResultSet.getBigDecimal("Intereses"));
                movimiento.setMora(objResultSet.getBigDecimal("Mora"));
                movimiento.setSeguro(objResultSet.getBigDecimal("Seguro"));
                                
                movimientos.add(movimiento);
            }
            prestamo.setMovimientos(movimientos);
            if (movimientos.isEmpty()) {
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN listadoPagosPrestamo: ")
                    .append("NPR-").append(this.numeroCuentaFormato(iNumeroPrestamo))
                    .append("-CH-").append(canal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listadoPagosPrestamo: ")
                    .append("NPR-").append(this.numeroCuentaFormato(iNumeroPrestamo))
                    .append("-CH-").append(canal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
        } finally {
            prestamo.setRespuesta(respuesta);
            this.cerrarConexion(canal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN listadoPagosPrestamo: ")
//                    .append("NPR-").append(this.numeroCuentaFormato(iNumeroPrestamo))
//                    .append("-CH-").append(canal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return prestamo;
    }

    /**
     * Obtiene los movimientos de la amortizacion de un prestamo.
     *
     * @param numeroPrestamo numero del prestamo
     * @param nombreCanal nombre del canal
     * @return PrestamoDTO contiene el saldo exigible del prestamo
     */
    @Override
    public PrestamoDTO obtenerSaldoExigible(String numeroPrestamo, String nombreCanal) {
        PrestamoDTO prestamo = new PrestamoDTO();
        RespuestaDTO respuesta = new RespuestaDTO();

        prestamo.setNumeroPrestamo(numeroPrestamo);

        try {

            if (numeroPrestamo == null || numeroPrestamo.isEmpty() || numeroPrestamo.equalsIgnoreCase("")
                    || nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, nombreCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_PRESTAMOS", "IB_P_SALDO_EXIGIBLE", 3, nombreCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            statement.setString(1, numeroPrestamo);
            statement.setString(2, nombreCanal);
            statement.registerOutParameter(3, OracleTypes.CURSOR);

            this.ejecuto = statement.execute();

            objResultSet = (ResultSet) statement.getObject(3);
            while (objResultSet.next()) {
                prestamo.setSaldoExigible(objResultSet.getBigDecimal("saldoExigible"));
            }

            if (prestamo.getSaldoExigible() == null) {
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN obtenerSaldoExigible: ")
                    .append("NPR-").append(this.numeroCuentaFormato(numeroPrestamo))
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN obtenerSaldoExigible: ")
                    .append("NPR-").append(this.numeroCuentaFormato(numeroPrestamo))
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
        } finally {
            prestamo.setRespuesta(respuesta);
            this.cerrarConexion(nombreCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN obtenerSaldoExigible: ")
//                    .append("NPR-").append(this.numeroCuentaFormato(numeroPrestamo))
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return prestamo;
    }

    /**
     * Realiza el pago del prestamo.
     *
     * @param iNumeroPrestamo numero de prestamo
     * @param ivNumeroCuenta numero de cuenta
     * @param inValorTransaccion numero de valor
     * @param ivCodigoUsuario numero de codigo
     * @param ivDescripcionMovimiento numero de descripcion
     * @param codigoCanal codigo de canal
     * @return PrestamoDTO Contiene los datos para apliacar el pago.
     */
    @Override
    public UtilDTO aplicarPagoPrestamo(String iNumeroPrestamo, String ivNumeroCuenta, String inValorTransaccion, String ivCodigoUsuario, String ivDescripcionMovimiento, String codigoCanal) {

        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        Map resultados = new HashMap();

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_PRESTAMOS", "IB_P_APLICAR_PAGO_A_PRESTAMO", 8, codigoCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            statement.setString(1, iNumeroPrestamo);
            statement.setString(2, ivNumeroCuenta);
            statement.setBigDecimal(3, new BigDecimal(inValorTransaccion));
            statement.setString(4, ivCodigoUsuario);
            statement.setString(5, ivDescripcionMovimiento);
            statement.setString(6, codigoCanal);
            statement.registerOutParameter(7, OracleTypes.NUMBER);
            statement.registerOutParameter(8, OracleTypes.INTEGER);
            this.ejecuto = statement.execute();

            respuesta = actuacionesDAO.obtenerDescripcionSalidaSP(String.valueOf(this.statement.getInt(8)), codigoCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            } else {
                if (respuesta.getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    resultados.put("secMovimiento", String.valueOf(this.statement.getInt(7)));
                    utilDTO.setResulados(resultados);
                }
            }

        } catch (DAOException d) {
            logger.error( new StringBuilder("ERROR DAO EN aplicarPagoPrestamo: ")
                    .append("NPR-").append(this.numeroCuentaFormato(iNumeroPrestamo))
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(d.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN aplicarPagoPrestamo: ")
                    .append("NPR-").append(this.numeroCuentaFormato(iNumeroPrestamo))
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
        } finally {
            utilDTO.setRespuesta(respuesta);
            this.cerrarConexion(codigoCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN aplicarPagoPrestamo: ")
//                    .append("NPR-").append(this.numeroCuentaFormato(iNumeroPrestamo))
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return utilDTO;
    }

    /**
     * Obtiene los movimientos de la amortizacion de un prestamo.
     *
     * @param numeroPrestamo numero de prestamo
     * @param nombreCanal nombre del canal
     * @return PrestamoDTO listado de amorizaciones de los movimientos
     */
    @Override
    public PrestamoDTO obtenerAmortizMov(String numeroPrestamo, String nombreCanal) {
        PrestamoDTO prestamo = new PrestamoDTO();
        RespuestaDTO respuesta = new RespuestaDTO();

        prestamo.setNumeroPrestamo(numeroPrestamo);

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, nombreCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_PRESTAMOS", "IB_P_OBTENER_AMORTIZ_MOVI", 3, nombreCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            statement.setString(1, numeroPrestamo);
            statement.setString(2, nombreCanal);
            statement.registerOutParameter(3, OracleTypes.CURSOR);

            this.ejecuto = statement.execute();

            objResultSet = (ResultSet) statement.getObject(3);
            while (objResultSet.next()) {

                prestamo.setCodigoEstadoCartera(getCharSetOracle9(objResultSet, "codigoEstadoCartera"));
                prestamo.setTasaTotal(objResultSet.getBigDecimal("tasaTotal"));
                prestamo.setTipoTasa(getCharSetOracle9(objResultSet, "tipoTasa"));
                prestamo.setMontoCuota(objResultSet.getBigDecimal("valorCuota"));
                prestamo.setCuotaIntereses(getCharSetOracle9(objResultSet, "cuotaIntereses"));
                prestamo.setTipoLiquidacionMes(getCharSetOracle9(objResultSet, "tipoLiquidacionMes"));
                prestamo.setTipoLiquidacionAnio(getCharSetOracle9(objResultSet, "tipoLiquidacionAnio"));
                prestamo.setTipoFrecuenciaInteres(objResultSet.getBigDecimal("tipoFrecuenciaInteres"));
                prestamo.setFechaPagoInteres(this.getUtilDate(objResultSet, "fechaPagoInteres"));
                prestamo.setFrecuenciaInteres(objResultSet.getBigDecimal("FrecuenciaInteres"));
                prestamo.setFechaPrimerPagoInteres(this.getUtilDate(objResultSet, "fechaPrimerPagoInteres"));
                prestamo.setDiaPagoInteres(objResultSet.getBigDecimal("diaPagoInteres"));
                prestamo.setFechaProximoPagoInteres(this.getUtilDate(objResultSet, "fechaProximoPagoInteres"));
                prestamo.setFechaCuotaAnteriorInteres(this.getUtilDate(objResultSet, "fechaCuotaAnteriorInteres"));
                prestamo.setInteresesVencidos(getCharSetOracle9(objResultSet, "interesesVencidos"));
                prestamo.setFechaVencimiento(this.getUtilDate(objResultSet, "fechaVencimiento"));
                prestamo.setSaldoExigible(objResultSet.getBigDecimal("valor"));

            }
            if (prestamo.getNumeroPrestamo().isEmpty()) {
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN obtenerAmortizMov: ")
                    .append("NPR-").append(this.numeroCuentaFormato(numeroPrestamo))
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN obtenerAmortizMov: ")
                    .append("NPR-").append(this.numeroCuentaFormato(numeroPrestamo))
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
        } finally {
            prestamo.setRespuesta(respuesta);
            this.cerrarConexion(nombreCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN obtenerAmortizMov: ")
//                    .append("NPR-").append(this.numeroCuentaFormato(numeroPrestamo))
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return prestamo;
    }

    /**
     * Obtiene el detalle de la amortizacion de un prestamo.
     *
     * @param codigoCliente codigo del cliente
     * @param codigoCanal nombre del canal
     * @return PrestamoDTO contiene el detalle de la amortizacion
     */
    @Override
    public PrestamoDTO obtenerAmortizDet(String codigoCliente, String codigoCanal) {
        PrestamoDTO prestamo = new PrestamoDTO();
        RespuestaDTO respuesta = new RespuestaDTO();

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_PRESTAMOS", "IB_P_OBTENER_AMORTIZ_DETALLE", 3, codigoCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            statement.setInt(1, Integer.parseInt(codigoCliente));
            statement.setString(2, codigoCanal);
            statement.registerOutParameter(3, OracleTypes.CURSOR);

            this.ejecuto = statement.execute();

            objResultSet = (ResultSet) statement.getObject(3);
            while (objResultSet.next()) {

                prestamo.setNumeroPrestamo(getCharSetOracle9(objResultSet, "numeroPrestamo"));
                prestamo.setNombreProducto(getCharSetOracle9(objResultSet, "descripcion"));
                prestamo.setCodigoEstadoCartera(getCharSetOracle9(objResultSet, "codigoEstadoCartera"));
                prestamo.setTasaTotal(objResultSet.getBigDecimal("tasaTotal"));
                prestamo.setTipoTasa(getCharSetOracle9(objResultSet, "tipoTasa"));
                prestamo.setMontoCuota(objResultSet.getBigDecimal("valorCuota"));
                prestamo.setCuotaIntereses(getCharSetOracle9(objResultSet, "cuotaIntereses"));
                prestamo.setTipoLiquidacionMes(getCharSetOracle9(objResultSet, "tipoLiquidacionMes"));
                prestamo.setTipoLiquidacionAnio(getCharSetOracle9(objResultSet, "tipoLiquidacionAnio"));
                prestamo.setTipoFrecuenciaInteres(objResultSet.getBigDecimal("tipoFrecuenciaInteres"));
                prestamo.setFrecuenciaInteres(objResultSet.getBigDecimal("FrecuenciaInteres"));
                prestamo.setFechaPagoInteres(this.getUtilDate(objResultSet, "fechaPagoInteres"));
                prestamo.setFechaPrimerPagoInteres(this.getUtilDate(objResultSet, "fechaPrimerPagoInteres"));
                prestamo.setDiaPagoInteres(objResultSet.getBigDecimal("diaPagoInteres"));
                prestamo.setFechaProximoPagoInteres(this.getUtilDate(objResultSet, "fechaProximoPagoInteres"));
                prestamo.setFechaCuotaAnteriorInteres(this.getUtilDate(objResultSet, "fechaCuotaAnteriorInteres"));
                prestamo.setInteresesVencidos(getCharSetOracle9(objResultSet, "interesesVencidos"));
                prestamo.setFechaVencimiento(this.getUtilDate(objResultSet, "fechaVencimiento"));
                prestamo.setClaveProducto(objResultSet.getBigDecimal("claveProducto"));
                prestamo.setSaldoCapital(objResultSet.getBigDecimal("saldoCapital"));
                prestamo.setInteres(objResultSet.getBigDecimal("interes"));
                prestamo.setMora(objResultSet.getBigDecimal("mora"));
                prestamo.setGastos(objResultSet.getBigDecimal("gastos"));
                prestamo.setSeguro(objResultSet.getBigDecimal("seguro"));

            }
            if (prestamo.getNumeroPrestamo().isEmpty()) {
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN obtenerAmortizDet: ")
                    .append("-CC-").append(codigoCliente)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN obtenerAmortizDet: ")
                    .append("-CC-").append(codigoCliente)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
        } finally {
            prestamo.setRespuesta(respuesta);
            this.cerrarConexion(codigoCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN obtenerAmortizDet: ")
//                    .append("-CC-").append(codigoCliente)
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return prestamo;
    }

}
