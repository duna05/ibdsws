/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.ActuacionesDAO;
import com.bds.ws.dao.EMailDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.EMailDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.exception.DAOException;
import com.bds.ws.util.DAOUtil;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;

/**
 * Clase que implementa la interfaz EMailDAO
 * @author cesar.mujica
 */
@Named("EMailDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class EMailDAOImpl extends DAOUtil implements EMailDAO {

    /**
     * Log de sistema
     */
    private static final Logger logger = Logger.getLogger(EMailDAOImpl.class.getName());

    @EJB
    public ActuacionesDAO actuaciones;

    /**
     * Procesa el envio de Email con los parametros especificados
     *
     * @param remitente String Remitente del correo.
     * @param destinatario String Destinatario del correo.
     * @param asunto String Asunto del correo.
     * @param texto String Texto del correo.
     * @param canal Codigo (extendido) del canal desde el cual es llamado el
     * procedimiento.
     * @return EMailDTO la cuenta con los movimientos de la cuenta
     * seleccionada(solo vienen los datos de los movimientos)
     */
    @Override
    public EMailDTO enviarEmail(String remitente, String destinatario, String asunto, String texto, String canal) {
        EMailDTO email = new EMailDTO();
        RespuestaDTO respuesta = new RespuestaDTO();

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_MSG_ELECTRONICOS", "IB_P_ENVIAR_EMAIL", 6, canal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setString(1, remitente);
            statement.setString(2, destinatario);
            statement.setString(3, asunto);
            statement.setString(4, texto);
            statement.setString(5, canal);
            statement.registerOutParameter(6, OracleTypes.VARCHAR);
            this.ejecuto = statement.execute();

            respuesta.setCodigoSP(new String(this.statement.getBytes(6), CHARSET_ORACLE_9));
            respuesta = actuaciones.obtenerDescripcionSalidaSP(respuesta.getCodigoSP(), canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN enviarEmail: ")
                    .append("destinatario-").append(destinatario)
                    .append("-CH-").append(canal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            email.setRespuesta(respuesta);
            this.cerrarConexion(canal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.log(Level.INFO, new StringBuilder("EXITO DAO EN enviarEmail: ")
//                    .append("destinatario-").append(destinatario)
//                    .append("-CH-").append(canal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return email;
    }
    /**
     * Procesa el envio de Email con los parametros especificados
     *
     * @param remitente String Remitente del correo.
     * @param destinatario String Destinatario del correo.
     * @param asunto String Asunto del correo.
     * @param texto String Texto del correo.
     * @param canal Codigo (extendido) del canal desde el cual es llamado el
     * procedimiento.
     * @return EMailDTO la cuenta con los movimientos de la cuenta
     * seleccionada(solo vienen los datos de los movimientos)
     */
    @Override
    public EMailDTO enviarEmailOTP(String inCodigoCliente, String CodExtCanal, String ivOTP) {
        EMailDTO email = new EMailDTO();
        RespuestaDTO respuesta = new RespuestaDTO();

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, CodExtCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_MSG_ELECTRONICOS", "IB_P_CORREO_OTP", 4, CodExtCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setString(1, inCodigoCliente);
            statement.setString(2, CodExtCanal);
            statement.setString(3, ivOTP);
            statement.registerOutParameter(4, OracleTypes.VARCHAR);
            this.ejecuto = statement.execute();

            respuesta.setCodigoSP(new String(this.statement.getBytes(4), CHARSET_ORACLE_9));
            respuesta = actuaciones.obtenerDescripcionSalidaSP(respuesta.getCodigoSP(), CodExtCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN enviarEmail: ")
                    .append("CodigoCliente-").append(inCodigoCliente)
                    .append("-CH-").append(CodExtCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            email.setRespuesta(respuesta);
            this.cerrarConexion(CodExtCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.log(Level.INFO, new StringBuilder("EXITO DAO EN enviarEmail: ")
//                    .append("CodigoCliente-").append(inCodigoCliente)
//                    .append("-CH-").append(CodExtCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return email;
    }
        
    /**
     * Procesa el envio de Email con los parametros especificados
     *
     * @param codigoEmpresa siempre se envia el valor 1
     * @param codigoPlantilla codigo de la plantilla
     * @param destinatario String Destinatario del correo.
     * @param parametrosCorreo parametros requeridos para la plantilla
     * @param idCanal codigo del canal interno en ib
     * @param codigoCanal codigo del canal interno en el CORE
     * @return EMailDTO la cuenta con los movimientos de la cuenta
     * seleccionada(solo vienen los datos de los movimientos)
     */
    @Override
    public EMailDTO enviarEmailPlantilla(String codigoEmpresa, String codigoPlantilla, String destinatario, String parametrosCorreo, String idCanal, String codigoCanal) {
        EMailDTO email = new EMailDTO();
        RespuestaDTO respuesta = new RespuestaDTO();

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_TOOLS", "IB_P_ENV_MAIL", 7, codigoCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setInt(1, Integer.parseInt(codigoEmpresa));       //SIEMPRE SE LE ENVIA EL VALOR 1
            statement.setInt(2, Integer.parseInt(codigoPlantilla));     //CODIGO DE PLANTILLA
            statement.setString(3, destinatario);                       //DESTINATARIO DE CORREO
            statement.setString(4, parametrosCorreo);                   //PARAMETROS DE CORREO
            statement.setInt(5, Integer.parseInt("0"));                 //DEBUG 0=Apagado, 1=Encendido
            statement.registerOutParameter(6, OracleTypes.VARCHAR); //MENSAJE ERROR
            statement.registerOutParameter(7, OracleTypes.VARCHAR); //ONSALIDA
            this.ejecuto = statement.execute();
            
            respuesta.setCodigoSP(new String(this.statement.getBytes(7), CHARSET_ORACLE_9));
            
            /*
            respuesta = actuaciones.obtenerDescripcionSalidaSP(respuesta.getCodigoSP(), canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            */        
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN enviarEmail: ")
                    .append("destinatario-").append(destinatario)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            email.setRespuesta(respuesta);
            this.cerrarConexion(codigoCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.log(Level.INFO, new StringBuilder("EXITO DAO EN enviarEmail: ")
//                    .append("destinatario-").append(destinatario)
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return email;
    }
    
    /**
     * Procesa el envio de Email con los parametros especificados
     *
     * @param codigoPlantilla codigo de la plantilla
     * @param destinatario String Destinatario del correo.
     * @param parametrosCorreo parametros requeridos para la plantilla
     * @param idCanal codigo del canal interno en ib
     * @param codigoCanal codigo del canal interno en el CORE
     * @return EMailDTO la cuenta con los movimientos de la cuenta
     * seleccionada(solo vienen los datos de los movimientos)
     */
    @Override
    public RespuestaDTO enviarEmailPN(String codigoPlantilla, String destinatario, String parametrosCorreo, String idCanal, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        String codigoTabla = "1"; //codigo solicitado por tabla de email

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_MSG_ELECTRONICOS", "IB_P_ENVIAR_EMAIL_PN", 7, codigoCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setInt(1, Integer.parseInt(codigoTabla));       //SIEMPRE SE LE ENVIA EL VALOR 1
            statement.setInt(2, Integer.parseInt(codigoPlantilla));     //CODIGO DE PLANTILLA
            statement.setString(3, destinatario);                       //DESTINATARIO DE CORREO
            statement.setString(4, parametrosCorreo);                   //PARAMETROS DE CORREO
            statement.setInt(5, Integer.parseInt("0"));                 //DEBUG 0=Apagado, 1=Encendido
            statement.registerOutParameter(6, OracleTypes.VARCHAR); //MENSAJE ERROR
            statement.registerOutParameter(7, OracleTypes.VARCHAR); //ONSALIDA
            this.ejecuto = statement.execute();
            
            respuesta.setCodigoSP(new String(this.statement.getBytes(7), CHARSET_ORACLE_9));
            
            
            respuesta = actuaciones.obtenerDescripcionSalidaSP(respuesta.getCodigoSP(), codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
                   
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN enviarEmailPN: ")
                    .append("destinatario-").append(destinatario)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            this.cerrarConexion(codigoCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.log(Level.INFO, new StringBuilder("EXITO DAO EN enviarEmailPN: ")
//                    .append("destinatario-").append(destinatario)
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return respuesta;
    }
}
