/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.ActuacionesDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dao.PreguntasAutenticacionDAO;
import com.bds.ws.dto.PreguntaAutenticacionDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.exception.DAOException;
import com.bds.ws.facade.IbPreguntasAutenticacionFacade;
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
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.NoResultException;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;

/**
 * Clase que implementa la interfaz PreguntasAutenticacionDAO
 *
 * @author juan.faneite
 */
@Named("PreguntasAutenticacionDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class PreguntasAutenticacionDAOImpl extends DAOUtil implements PreguntasAutenticacionDAO {

    @EJB
    public ActuacionesDAO actuaciones;
    @EJB
    IbPreguntasAutenticacionFacade preguntasAutenticacionFacade;

    /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(PreguntasAutenticacionDAOImpl.class.getName());

    /**
     * Metodo para obtener las preguntas de autenticacion de un cliente (core
     * bancario)
     *
     * @param tdd tarjeta de debito del cliente
     * @param nroCta numero de cuenta del cliente
     * @param nroPreguntas numero de preguntas a mostrar
     * @param nombreCanal nombre del canal
     * @return PreguntaAutenticacionDTO -> con listado de preguntas de
     * autenticacion
     */
    @Override
    public PreguntaAutenticacionDTO listPDAporCliente(String tdd, String nroCta, int nroPreguntas, String nombreCanal) {
        PreguntaAutenticacionDTO preguntasAutenticacionDTO = new PreguntaAutenticacionDTO();
        preguntasAutenticacionDTO.setPreguntasAutenticacion(new ArrayList<PreguntaAutenticacionDTO>());
        RespuestaDTO respuesta = new RespuestaDTO();
        try {

            if (tdd == null || tdd.isEmpty() || tdd.equalsIgnoreCase("")
                    || nroCta == null || nroCta.isEmpty() || nroCta.equalsIgnoreCase("")
                    || Integer.toString(nroPreguntas) == null || Integer.toString(nroPreguntas).isEmpty() || Integer.toString(nroPreguntas).equalsIgnoreCase("")
                    || nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")) {
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, nombreCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_TDD", "IB_P_GENERAR_PDD", 6, nombreCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            statement.setString(1, tdd);
            statement.setString(2, nroCta);
            if (nroPreguntas != 0) {
                statement.setInt(3, nroPreguntas);
            } else {
                statement.setBigDecimal(3, null);
            }
            statement.setString(4, nombreCanal);
            statement.registerOutParameter(5, OracleTypes.CURSOR);
            statement.registerOutParameter(6, OracleTypes.NUMBER);
            this.ejecuto = statement.execute();

            respuesta.setCodigoSP(String.valueOf(this.statement.getInt(6)));

            respuesta = actuaciones.obtenerDescripcionSalidaSP(respuesta.getCodigoSP(), nombreCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            } else {
                if (respuesta.getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO)) {

                    objResultSet = (ResultSet) statement.getObject(5);
                    while (objResultSet.next()) {
                        PreguntaAutenticacionDTO preguntasAutenticacionDTOTemp = new PreguntaAutenticacionDTO();

                        preguntasAutenticacionDTOTemp.setCodigoPregunta(objResultSet.getBigDecimal("CODIGO_PREGUNTA").toString());
                        preguntasAutenticacionDTOTemp.setCodigoTipoPregunta(objResultSet.getBigDecimal("CODIGO_TIPO_PREGUNTA").toString());
                        preguntasAutenticacionDTOTemp.setDescripcion(this.getCharSetOracle9(objResultSet, "DESCRIPCION"));
                        preguntasAutenticacionDTOTemp.setLongitudDato(objResultSet.getBigDecimal("LONGITUD_DE_DATO").toString());
                        preguntasAutenticacionDTOTemp.setNroOrdenamiento(objResultSet.getBigDecimal("ORDEN_PREGUNTA").toString());
                        preguntasAutenticacionDTOTemp.setTipoDato(this.getCharSetOracle9(objResultSet, "TIPO_DE_DATO"));

                        preguntasAutenticacionDTO.getPreguntasAutenticacion().add(preguntasAutenticacionDTOTemp);
                    }

                    if (preguntasAutenticacionDTO.getPreguntasAutenticacion().isEmpty()) {
                        throw new NoResultException();
                    }
                }

            }

        } catch (NoResultException d) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN listPDAporCliente: ")
                    .append("-TDD-").append(tdd)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(d.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listPDAporCliente: ")
                    .append("-TDD-").append(tdd)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
        } finally {
            preguntasAutenticacionDTO.setRespuesta(respuesta);
            this.cerrarConexion(nombreCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN listPDAporCliente: ")
//                    .append("-TDD-").append(tdd)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return preguntasAutenticacionDTO;
    }

    /**
     * Metodo para verificar las respuestas de autenticacion
     *
     * @param tdd tarjeta de debito del cliente
     * @param nroCta numero de cuenta del cliente
     * @param rafaga cadena con las preguntas y respuestas a validar ej: < codigoPregunta >< separador
     * > < codigoPregunta >< separador >< codigoPregunta >< separador >
     * @param separador separador que utilizara en la rafaga, si este es null se
     * tomará guion "-" como separador
     * @param nombreCanal nombre del canal
     * @return UtilDTO con respuesta valida o no para las respuestas
     */
    @Override
    public UtilDTO validarPDAporCliente(String tdd, String nroCta, String rafaga, String separador, String nombreCanal) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        Map resultados = new HashMap();

        try {

            if (tdd == null || tdd.isEmpty() || tdd.equalsIgnoreCase("")
                    || nroCta == null || nroCta.isEmpty() || nroCta.equalsIgnoreCase("")
                    || rafaga == null || rafaga.isEmpty() || rafaga.equalsIgnoreCase("")
                    || separador == null || separador.isEmpty() || separador.equalsIgnoreCase("")
                    || nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")) {
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, nombreCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_TDD", "IB_P_VALIDAR_RESPUESTAS_PDD", 6, nombreCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            statement.setString(1, tdd);
            statement.setString(2, nroCta);
            statement.setString(3, rafaga);
            statement.setString(4, separador);
            statement.setString(5, nombreCanal);
            statement.registerOutParameter(6, OracleTypes.NUMBER);
            this.ejecuto = statement.execute();

            String codSalida = this.statement.getBigDecimal(6).toString();

            respuesta = actuaciones.obtenerDescripcionSalidaSP(codSalida, nombreCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            } else {
                if (respuesta.getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO)) {                   
                    resultados.put("respuesta", true);
                }
            }
            utilDTO.setResulados(resultados);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN validarPDAporCliente: ")
                    .append("-TDD-").append(tdd)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
        } finally {
            utilDTO.setRespuesta(respuesta);
            this.cerrarConexion(nombreCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN validarPDAporCliente: ")
//                    .append("-TDD-").append(tdd)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return utilDTO;
    }
}
