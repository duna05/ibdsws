/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.ActuacionesDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dao.NotificationServiceType.ServiceType;
import com.bds.ws.dao.ReferenciaDAO;
import com.bds.ws.dto.ReferenciaDTO;
import com.bds.ws.dto.ReferenciaDetalleDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.exception.DAOException;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import static com.bds.ws.util.BDSUtil.JNDI_ORACLE_9;
import com.bds.ws.util.DAOUtil;
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
 * Clase que implementa la interfaz ReferenciaDAO
 *
 * @author juan.faneite
 */
@Named("ReferenciaDAO")
@Stateless
@NotificationServiceType(ServiceType.SERVICES)
public class ReferenciaDAOImpl extends DAOUtil implements ReferenciaDAO {

    /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(ReferenciaDAOImpl.class.getName());

    @EJB
    private ActuacionesDAO actuacionesDAO;
    
    /**
     * Obtiene la confirmacion de la referencia bancaria.
     *
     * @param codigoCliente Codigo del cliente.
     * @param destinatario A quien va dirigida la referencia.
     * @param nombreCanal Codigo (extendido) del canal desde el cual es llamado
     * el procedimiento.
     * @return UtilDTO -> (Numero de referencia, Firma que se estampa en la
     * referencia)
     */
    @Override
    public UtilDTO confirmarReferencia(String codigoCliente, String destinatario, String nombreCanal) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        Map resultados = new HashMap();
        try {

            if (codigoCliente == null || codigoCliente.isEmpty() || codigoCliente.equalsIgnoreCase("")
                    || destinatario == null || destinatario.isEmpty() || destinatario.equalsIgnoreCase("")
                    || nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, nombreCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_REFERENCIAS", "IB_P_CONFIRMAR_REFERENCIA", 5, nombreCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setInt(1, Integer.parseInt(codigoCliente));
            statement.setString(2, destinatario);
            statement.setString(3, nombreCanal);
            statement.registerOutParameter(4, OracleTypes.VARCHAR);
            statement.registerOutParameter(5, OracleTypes.BLOB);
            this.ejecuto = statement.execute();

            resultados.put("referencia", new String(this.statement.getBytes(4), CHARSET_ORACLE_9));
            resultados.put("firma", this.statement.getBlob(5));

            utilDTO.setResulados(resultados);
            utilDTO.setRespuesta(respuesta);

        } catch (NoResultException d) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN consultarDatosCliente: ")
                    .append("USR-").append(codigoCliente)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(d.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN consultarDatosCliente: ")
                    .append("USR-").append(codigoCliente)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            utilDTO.setRespuesta(respuesta);
            this.cerrarConexion(nombreCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN consultarDatosCliente: ")
//                    .append("USR-").append(codigoCliente)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return utilDTO;
    }

    /**
     * Obtiene los datos de las referencias bancarias.
     *
     * @param codigoCliente codigo del cliente
     * @param tipoRef tipo de referencia
     * @param nroCuenta numero de cuenta
     * @param nombreCanal codigo de canal
     * @return ReferenciaDTO Datos de la referencia
     */
    @Override
    public ReferenciaDTO obtenerDatosReferencias(String codigoCliente, String tipoRef, String nroCuenta, String destinatario, String nombreCanal) {
        ReferenciaDTO referencia = new ReferenciaDTO();
        List<ReferenciaDetalleDTO> listRefCta = new ArrayList<>();

        RespuestaDTO respuesta = new RespuestaDTO();
        int numParametros = 9;

        try {

            if (codigoCliente == null || codigoCliente.isEmpty() || codigoCliente.equalsIgnoreCase("")
                    || tipoRef == null || tipoRef.isEmpty() || tipoRef.equalsIgnoreCase("")
                    || nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            if (tipoRef.equalsIgnoreCase("U")) {
                if (nroCuenta == null || nroCuenta.isEmpty() || nroCuenta.equalsIgnoreCase("")) {
                    respuesta.setCodigo("DAO008");
                    throw new DAOException("DAO008");
                }
            } else {
                nroCuenta = null;
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, nombreCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_REFERENCIAS", "IB_P_OBTENER_DATOS_REFERENCIA", numParametros, nombreCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            //seteo de parametros de entrada y/o salida            
            statement.setInt(1, Integer.parseInt(codigoCliente));
            statement.setString(2, tipoRef);
            statement.setString(3, nroCuenta);
            statement.setString(4, nombreCanal);
            statement.setString(5, destinatario);
            statement.registerOutParameter(6, OracleTypes.CURSOR);
            statement.registerOutParameter(7, OracleTypes.VARCHAR);
            statement.registerOutParameter(8, OracleTypes.BLOB);
            statement.registerOutParameter(9, OracleTypes.INTEGER);
            this.ejecuto = statement.execute();

            respuesta.setCodigoSP(String.valueOf(this.statement.getInt(9)));

            respuesta = actuacionesDAO.obtenerDescripcionSalidaSP(respuesta.getCodigoSP(), nombreCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            } else {
                if (respuesta.getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    objResultSet = (ResultSet) statement.getObject(6);
                    while (objResultSet.next()) {

                        ReferenciaDetalleDTO refCta = new ReferenciaDetalleDTO();

                        refCta.setNumeroCuenta(this.getCharSetOracle9(objResultSet, "numero_cuenta"));
                        refCta.setTipoCuenta(this.getCharSetOracle9(objResultSet, "tipo_cuenta"));
                        refCta.setRegionPertenece(this.getCharSetOracle9(objResultSet, "nombre_region"));
                        refCta.setFechaInicio(this.getUtilDate(objResultSet, "fecha_inicio"));
                        refCta.setSaldo(this.objResultSet.getBigDecimal("saldo"));
                        refCta.setCifrasReferencia(this.getCharSetOracle9(objResultSet, "cifras_referencia"));
                        refCta.setNroReferencia(new String(this.statement.getBytes(7), CHARSET_ORACLE_9));
                        refCta.setFirma(this.statement.getBytes(8));
                        listRefCta.add(refCta);
                        //referencia.getReferenciasCuentas().add(refCta);
                    }

                    if (listRefCta.isEmpty()) {
                        throw new NoResultException();
                    }
                    referencia.setReferenciasCuentas(listRefCta);
                }
            }

        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN obtenerDatosReferencias: ")
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN obtenerDatosReferencias: ")
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            referencia.setRespuesta(respuesta);
            this.cerrarConexion(nombreCanal);
        }

//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN obtenerDatosReferencias: ")
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return referencia;
    }

    /**
     * Obtiene las referencias de una TDC
     *
     * @param codigoCliente codigo del cliente
     * @param nroTarjeta
     * @param destinatario destinatario a quien va dirigida la tdc
     * @param nombreCanal codigo del canal
     * @return ReferenciaDTO retorna los datos de la refeneia
     */
    @Override
    public ReferenciaDTO obtenerReferenciaTDC(String codigoCliente, String nroTarjeta, String destinatario, String nombreCanal) {
        ReferenciaDTO referencia = new ReferenciaDTO();
        List<ReferenciaDetalleDTO> listRefCta = new ArrayList<>();

        RespuestaDTO respuesta = new RespuestaDTO();
        int numParametros = 8;

        try {

            if (codigoCliente == null || codigoCliente.isEmpty() || codigoCliente.equalsIgnoreCase("")
                    || nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, nombreCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_REFERENCIAS", "IB_P_OBTENER_REF_TDC", numParametros, nombreCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            //seteo de parametros de entrada y/o salida            
            statement.setInt(1, Integer.parseInt(codigoCliente));
            statement.setString(2, nroTarjeta);
            statement.setString(3, nombreCanal);
            statement.setString(4, destinatario);
            statement.registerOutParameter(5, OracleTypes.CURSOR);
            statement.registerOutParameter(6, OracleTypes.VARCHAR); //--numero de referencia
            statement.registerOutParameter(7, OracleTypes.INTEGER); //--firma
            statement.registerOutParameter(8, OracleTypes.BLOB); //--firma            
            this.ejecuto = statement.execute();

            respuesta.setCodigoSP(String.valueOf(this.statement.getInt(7)));

            respuesta = actuacionesDAO.obtenerDescripcionSalidaSP(respuesta.getCodigoSP(), nombreCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            } else {
                if (respuesta.getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    objResultSet = (ResultSet) statement.getObject(5);
                    while (objResultSet.next()) {

                        ReferenciaDetalleDTO refCta = new ReferenciaDetalleDTO();

                        refCta.setNumeroCuenta(this.getCharSetOracle9(objResultSet, "numero_cuenta"));
                        refCta.setNombreCliente(this.getCharSetOracle9(objResultSet, "nombre_cliente"));
                        refCta.setTipoCuenta(this.getCharSetOracle9(objResultSet, "tipo_cuenta"));
                        refCta.setRegionPertenece(this.objResultSet.getBigDecimal("region_pertenece")!=null?this.objResultSet.getBigDecimal("region_pertenece").toString():"");
                        refCta.setNombrePertenece(this.getCharSetOracle9(objResultSet, "nombre_region"));
                        refCta.setFechaInicio(this.getUtilDate(objResultSet, "fecha_inicio"));
                        refCta.setSaldo(this.objResultSet.getBigDecimal("saldo"));
                        refCta.setCifrasReferencia(this.getCharSetOracle9(objResultSet, "cifras_referencia"));
                        refCta.setNroReferencia(new String(this.statement.getBytes(6), CHARSET_ORACLE_9));
                        refCta.setFirma(this.statement.getBytes(8));
                        listRefCta.add(refCta);
                        //referencia.getReferenciasCuentas().add(refCta);
                    }
                    if (listRefCta.isEmpty()) {
                        throw new NoResultException();
                    }
                    referencia.setReferenciasCuentas(listRefCta);
                }                
            }

        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN obtenerReferenciaTDC: ")
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN obtenerReferenciaTDC: ")
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            referencia.setRespuesta(respuesta);
            this.cerrarConexion(nombreCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN obtenerReferenciaTDC: ")
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }

        return referencia;
    }

}
