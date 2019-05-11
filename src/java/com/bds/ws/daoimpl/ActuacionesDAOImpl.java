/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.ActuacionesDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.exception.DAOException;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import static com.bds.ws.util.BDSUtil.JNDI_ORACLE_9;
import com.bds.ws.util.DAOUtil;
import java.util.Date;
import javax.ejb.Stateless;
import javax.inject.Named;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;

/**
 * Clase que implementa ActuacionesDAO
 * @author juan.faneite
 */
@Named("ActuacionesDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class ActuacionesDAOImpl extends DAOUtil implements ActuacionesDAO {

    /**
     * Log de sistema
     */
    private static final Logger logger = Logger.getLogger(ActuacionesDAOImpl.class.getName());

    /**
     * Obtiene las siglas y la descripcion de un codigo de salida, dado su ID.
     *
     * @param id String id ID del codigo de salida devuelto por algun
     * procedimiento.
     * @param canal String canal Codigo (extendido) del canal desde el cual es
     * llamado el procedimiento.
     * @return RespuestaDTO indica si el metodo se realizo de manera exitosa o no
     */
    @Override
    public RespuestaDTO obtenerDescripcionSalidaSP(String id, String canal) {
        RespuestaDTO respuesta = new RespuestaDTO();

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("SS_ACTUACIONES", "obtenerEstatusOP", 4, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setInt(1, Integer.parseInt(id));
            statement.setString(2, canal);           //Nota: en el sp el parametro de entrada es int
            statement.registerOutParameter(3, OracleTypes.VARCHAR);
            statement.registerOutParameter(4, OracleTypes.VARCHAR);
            this.ejecuto = statement.execute();

            if (!new String(this.statement.getBytes(3), CHARSET_ORACLE_9).equals("ok")) {
                respuesta.setCodigoSP(id);
                respuesta.setDescripcionSP(new String(this.statement.getBytes(3), CHARSET_ORACLE_9));
                respuesta.setTextoSP(new String(this.statement.getBytes(4), CHARSET_ORACLE_9));
            } else {
                respuesta.setCodigoSP(CODIGO_RESPUESTA_EXITOSO);
                respuesta.setDescripcionSP(DESCRIPCION_RESPUESTA_EXITOSO);
            }

        } catch (DAOException d) {
            this.cerrarConexion(canal);
            return respuesta;
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN obtenerDescripcionSalidaSP: ").append("ID-").append(id)
                    .append("-CH-").append(canal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            this.cerrarConexion(canal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN obtenerDescripcionSalidaSP: ").append("ID-").append(id)
//                    .append("-CH-").append(canal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return respuesta;
    }

}
