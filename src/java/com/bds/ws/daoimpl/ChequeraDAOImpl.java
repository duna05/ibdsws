/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.ActuacionesDAO;
import com.bds.ws.dao.ChequeraDAO;
import com.bds.ws.dao.EMailDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.ChequeDTO;
import com.bds.ws.dto.ChequeraDTO;
import com.bds.ws.dto.CuentaDTO;
import com.bds.ws.dto.EstadoSolicitudChequeraDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.enumerated.EstatusPlantillasEmail;
import com.bds.ws.exception.DAOException;
import static com.bds.ws.util.BDSUtil.CHARSET_ORACLE_9;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import static com.bds.ws.util.BDSUtil.FORMATO_FECHA_SIMPLE;
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
 *
 * @author robinson.rodriguez
 */
@Named("ChequeraDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class ChequeraDAOImpl extends DAOUtil implements ChequeraDAO {

    @EJB
    public ActuacionesDAO actuaciones;

    @EJB
    private EMailDAO emailDAO;

    /**
     * Log de sistema
     */
    private static final Logger logger = Logger.getLogger(ChequeraDAOImpl.class.getName());

    @Override
    public UtilDTO solicitarChequeraPJ(String numeroCuenta, String tipoChequera, String cantidad, String identificacion, String retira, String agenciaRetira, String idCanal, String codigoCanal, BigDecimal idEmpresa, String nombreEmpresa, String emailEmpresa, String nombreUsuario) {
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
        try {
            enviarCorreo(idEmpresa, nombreEmpresa, emailEmpresa, nombreUsuario, idCanal, codigoCanal);
        } catch (Exception ex) {
            Logger.getLogger(ChequeraDAOImpl.class.getName()).log(null, ex);
        }
        return parametros;

    }

    @Override
    public EstadoSolicitudChequeraDTO obtenerEstadoSolicitudChequera(String nroCuenta, String iCodExtCanal) {

        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        String auxNumCuenta;
        EstadoSolicitudChequeraDTO consulta;
        EstadoSolicitudChequeraDTO consultaFinal = new EstadoSolicitudChequeraDTO();
        List<EstadoSolicitudChequeraDTO> consultaList = new ArrayList<>();

        try {

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, iCodExtCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("CC_K_CHEQUERAS", "CC_P_DATOS_SOLICITUD", 3, iCodExtCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            auxNumCuenta = nroCuenta.substring(10);

            //seteo de parametros de entrada y/o salida
            statement.setLong(1, Long.valueOf(auxNumCuenta.trim()));
            statement.registerOutParameter(2, OracleTypes.CURSOR);
            statement.registerOutParameter(3, OracleTypes.VARCHAR);

            this.ejecuto = statement.execute();

            objResultSet = (ResultSet) statement.getObject(2);

            while (objResultSet.next()) {

                consulta = new EstadoSolicitudChequeraDTO();

                Date fechaSolicitudDate = this.objResultSet.getDate("fecha_solicitud");

                String fechaSolicitudStr = this.formatearFecha(fechaSolicitudDate, FORMATO_FECHA_SIMPLE);

                consulta.setFechaSolicitud(fechaSolicitudStr);
                consulta.setTipoChequera(this.objResultSet.getString("tipo_chequera"));
                consulta.setEstadoSolicitud(this.objResultSet.getString("estado"));
                consulta.setAgenciaRetiro(this.objResultSet.getString("agencia_retiro"));

                consultaList.add(consulta);

            }

            consultaFinal.setEstadoSolicitudChequeraDTOList(consultaList);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN obtenerEstadoSolicitudChequera: ")
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

        return consultaFinal;
    }

    @Override
    public CuentaDTO listarChequeraEntregada(String numeroCuenta, String codigoCanal) {

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

    @Override
    public ChequeraDTO listarCheque(String numeroCuenta, String numeroPrimerCheque, String codigoCanal) {

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
            logger.error( new StringBuilder("ERROR DAO EN listarCheque: ")
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
//            logger.error( new StringBuilder("EXITO DAO EN listarCheque: ")
//                    .append("CTA-").append(this.numeroCuentaFormato(numeroCuenta))
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return chequera;
    }

    @Override
    public UtilDTO suspenderChequera(String numeroCuenta, String motivoSuspension, String numPrimerCheque, String numUltimoCheque, String listCheques, String nombreCanal) {
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
            logger.error( new StringBuilder("ERROR DAO EN suspenderChequera: ")
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
//            logger.error( new StringBuilder("EXITO DAO EN suspenderChequera: ")
//                    .append("CTA-").append(this.numeroCuentaFormato(numeroCuenta))
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return utilDTO;

    }
    
    @Override
    public UtilDTO solicitarChequeraPN(String numeroCuenta, String tipoChequera, String cantidad, String identificacion, String retira, String agenciaRetira, String codigoCanal) {
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


    public void enviarCorreo(BigDecimal idEmpresa,String nombreEmpresa, String emailEmpresa, String nombreUsuario, String idCanal, String codigoCanal) throws Exception {

        String codigoEmpresa = EstatusPlantillasEmail.CODIGO_EMPRESA_PLANTILLA.getDescripcion();

        String codigoPlantilla = EstatusPlantillasEmail.NOMINA_PENDIENTE_AUTORIZAR.getDescripcion();

        String parametrosCorreo = "";

        parametrosCorreo = nombreEmpresa + nombreUsuario;

        emailDAO.enviarEmailPlantilla(codigoEmpresa, codigoPlantilla, emailEmpresa, parametrosCorreo, idCanal, codigoCanal);
    }

}
