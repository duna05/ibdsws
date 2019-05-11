/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.ActuacionesDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dao.ReclamosDAO;
import com.bds.ws.dto.CuentaDTO;
import com.bds.ws.dto.MovimientoCuentaDTO;
import com.bds.ws.dto.ReclamoDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.exception.DAOException;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_PRODUCTO_CA;
import static com.bds.ws.util.BDSUtil.CODIGO_PRODUCTO_CC;
import static com.bds.ws.util.BDSUtil.CODIGO_PRODUCTO_ME;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import static com.bds.ws.util.BDSUtil.FORMATO_FECHA_SIMPLE;
import static com.bds.ws.util.BDSUtil.JNDI_ORACLE_9;
import static com.bds.ws.util.BDSUtil.TEXTO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.formatearFecha;
import com.bds.ws.util.DAOUtil;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.NoResultException;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;

/**
 *
 * @author cesar.mujica
 */
@Named("ReclamoDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class ReclamosDAOImpl extends DAOUtil implements ReclamosDAO {

    @EJB
    private ActuacionesDAO actuacionesDAO;

    /**
     * Log de sistema
     */
    private static final Logger logger = Logger.getLogger(ReclamosDAOImpl.class.getName());

    /**
     * Obtiene el listado de reclamos asociados a las TDD
     *
     * @param codigoCanal codigo del canal
     * @return ClienteDTO obtiene el listado de TDD del cliente
     */
    @Override
    public ReclamoDTO obtenerListadoReclamos(String codigoCanal) {
        ReclamoDTO reclamo = new ReclamoDTO();
        reclamo.setReclamos(new ArrayList<ReclamoDTO>());
        RespuestaDTO respuesta = new RespuestaDTO();

        try {

            if (codigoCanal == null || codigoCanal.isEmpty() || codigoCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_TDD", "IB_P_LISTADO_TIPOS_RECLAMO_TDD", 2, codigoCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            statement.setString(1, codigoCanal);
            statement.registerOutParameter(2, OracleTypes.CURSOR);
            this.ejecuto = statement.execute();

            objResultSet = (ResultSet) statement.getObject(2);
            while (objResultSet.next()) {
                ReclamoDTO reclamoTemp = new ReclamoDTO();

                reclamoTemp.setCodigoReclamo(String.valueOf(this.objResultSet.getBigDecimal("codigoReclamo")));
                reclamoTemp.setNombreReclamo(this.getCharSetOracle9(objResultSet, "descripcion"));

                reclamo.getReclamos().add(reclamoTemp);
            }
            
            if(reclamo.getReclamos().isEmpty()){                
                throw new NoResultException();
            }

            reclamo.setRespuesta(respuesta);

        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN obtenerListadoReclamos: ")
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN obtenerListadoReclamos: ")
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
        } finally {
            reclamo.setRespuesta(respuesta);
            this.cerrarConexion(codigoCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN obtenerListadoReclamos: ")
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }        
        return reclamo;
    }

    /**
     * Graba en la tabla de solicitud los campos del reclamo.
     *
     * @param codigoCliente codigo del cliente
     * @param codigoReclamo codigo del reclamo
     * @param secuenciaExtMovimiento Este valor es devuelto en el campo
     * secuenciaExtMovimiento de algunos cursores de movimientos.
     * @param observacion Observación del reclamo suministrada por el cliente
     * que la realiza.
     * @param codigoCanal codigo del canal
     * @return RespuestaDTO
     */
    @Override
    public ReclamoDTO insertarReclamoCliente(String codigoCliente, String codigoReclamo, String secuenciaExtMovimiento, String observacion, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        ReclamoDTO reclamo = new ReclamoDTO();
        try {

            if (codigoCliente == null || codigoCliente.isEmpty() || codigoCliente.equalsIgnoreCase("")
                    || codigoReclamo == null || codigoReclamo.isEmpty() || codigoReclamo.equalsIgnoreCase("")                    
                    || observacion == null || observacion.isEmpty() || observacion.equalsIgnoreCase("")
                    || codigoCanal == null || codigoCanal.isEmpty() || codigoCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_TDD", "IB_P_GRABAR_RECLAMOS_TDD", 7, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setBigDecimal(1, new BigDecimal(codigoCliente));
            statement.setBigDecimal(2, new BigDecimal(codigoReclamo));
            statement.setString(3, secuenciaExtMovimiento);
            statement.setString(4, observacion);
            statement.setString(5, codigoCanal);
            statement.registerOutParameter(6, OracleTypes.INTEGER);
            statement.registerOutParameter(7, OracleTypes.INTEGER);
            this.ejecuto = statement.execute();
            reclamo.setNumeroSolicitud(String.valueOf(this.statement.getInt(6)));
            respuesta.setCodigoSP(String.valueOf(this.statement.getInt(7)));
            
            respuesta = actuacionesDAO.obtenerDescripcionSalidaSP(respuesta.getCodigoSP(), codigoCanal);

            
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO GENERICO EN insertarReclamoCliente: ").append("USR-").append(codigoCliente)
                    .append("-CH-").append(codigoCanal).append("-DT-").append(new Date()).append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log            
        } finally {
            reclamo.setRespuesta(respuesta);
            this.cerrarConexion(codigoCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN insertarReclamoCliente: ").append("USR-").append(codigoCliente)
//                    .append("-CH-").append(codigoCanal).append("-DT-").append(new Date()).append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        
        
        return reclamo;
    }

    /**
     * Listado De Reclamos X Cliente
     *
     * @param codigoCliente codigo del cliente
     * @param codigoCanal codigo del canal
     * @return RespuestaDTO
     */
    @Override
    public ReclamoDTO listadoReclamosPorCliente(String codigoCliente, String codigoCanal) {
        ReclamoDTO reclamo = new ReclamoDTO();
        reclamo.setReclamos(new ArrayList<ReclamoDTO>());
        RespuestaDTO respuesta = new RespuestaDTO();

        try {

            if (codigoCliente == null || codigoCliente.isEmpty() || codigoCliente.equalsIgnoreCase("")
                    || codigoCanal == null || codigoCanal.isEmpty() || codigoCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_TDD", "IB_P_RECLAMOS_CLIENTE_TDD", 3, codigoCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            statement.setInt(1, Integer.parseInt(codigoCliente));
            statement.setString(2, codigoCanal);
            statement.registerOutParameter(3, OracleTypes.CURSOR);
            this.ejecuto = statement.execute();

            objResultSet = (ResultSet) statement.getObject(3);
            while (objResultSet.next()) {
                ReclamoDTO reclamoTemp = new ReclamoDTO();

                reclamoTemp.setNumeroSolicitud(this.objResultSet.getBigDecimal("NRO_SOLICITUD").toString());
                reclamoTemp.setEstatus(this.getCharSetOracle9(objResultSet, "ESTATUS_SOLUCION"));
                reclamoTemp.setFechaSolicitud(this.getUtilDate(objResultSet, "FECHA_SOLICITUD"));
                reclamoTemp.setFechaSolucion(this.getUtilDate(objResultSet, "FECHA_SOLUCION_RECLAMO"));
                reclamoTemp.setMontoReclamo(this.objResultSet.getBigDecimal("MONTO_RECLAMO"));
                reclamoTemp.setNombreReclamo(this.getCharSetOracle9(objResultSet, "DESCRIPCION"));

                reclamo.getReclamos().add(reclamoTemp);
            }

            if (reclamo.getReclamos().isEmpty()) {
                throw new NoResultException();
            }            

        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN listadoReclamosPorCliente: ")
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listadoReclamosPorCliente: ")
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
        } finally {
            reclamo.setRespuesta(respuesta);
            this.cerrarConexion(codigoCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN listadoReclamosPorCliente: ")
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return reclamo;
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
                    respuesta = this.generarQuery("IB_K_CUENTAS_AHORRO", "IB_P_OBTENER_MOVIMIENTOS_RE", 8, canal);
                    break;
                }
                case (CODIGO_PRODUCTO_CC): {
                    respuesta = this.generarQuery("IB_K_CUENTAS_CORRIENTE", "IB_P_OBTENER_MOVIMIENTOS_RE", 8, canal);
                    break;
                }
                case (CODIGO_PRODUCTO_ME): {
                    respuesta = this.generarQuery("IB_K_CUENTAS_ME", "IB_P_OBTENER_MOVIMIENTOS_RE", 8, canal);
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

}
