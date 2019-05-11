/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.ActuacionesDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dao.OtpDAO;
import com.bds.ws.dto.OtpDTO;
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
 * Clase que implementa la interfaz OtpDAO
 * @author juan.faneite
 */
@Named("OtpDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class OtpDAOImpl extends DAOUtil implements OtpDAO {

    /**
     * Log de sistema
     */
    private static final Logger logger = Logger.getLogger(OtpDAOImpl.class.getName());

    @EJB
    private ActuacionesDAO actuacionesDAO;

    /**
     * Genera un nuevo OTP el cual es asignado al cliente asociado a una tarjeta de debito.
     * Adicionalmente, el OTP es enviado por correo electronico al cliente, solo en caso de que, 
     * tanto el canal, como el cliente esten configurados para dicho envio.
     * @param inCodigoCliente String codigo de cliente al cual se le va a generar el OTP.
     * @param canal String canal Codigo (extendido) del canal desde el cual es llamado el procedimiento.
     * @return OtpDTO Contiene el codigo OTP generado
     */
    @Override
    public OtpDTO generarOTP(String inCodigoCliente, String canal) {
        OtpDTO otp = new OtpDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        RespuestaDTO respuestaAct = new RespuestaDTO();

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_OTP", "IB_P_GENERAR_OTP", 4, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setInt(1, Integer.parseInt(inCodigoCliente));
            statement.setString(2, canal);
            statement.registerOutParameter(3, OracleTypes.VARCHAR);
            statement.registerOutParameter(4, OracleTypes.INTEGER);
            statement.execute();

            otp.setOvOTP(new String(this.statement.getBytes(3), CHARSET_ORACLE_9));
            otp.setOnCodSalida(String.valueOf(this.statement.getInt(4)));

            respuestaAct = actuacionesDAO.obtenerDescripcionSalidaSP(otp.getOnCodSalida(), canal);

            respuesta.setCodigoSP(respuestaAct.getCodigoSP());
            respuesta.setDescripcionSP(respuestaAct.getDescripcionSP());

            otp.setRespuesta(respuesta);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN generarOTP: ")
                    .append("USR-").append(inCodigoCliente)
                    .append("-CH-").append(canal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log            
        } finally {
            otp.setRespuesta(respuesta);
            this.cerrarConexion(canal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN generarOTP: ")
//                    .append("USR-").append(inCodigoCliente)
//                    .append("-CH-").append(canal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return otp;
    }

    /**
     * Genera un nuevo OTP el cual es asignado al cliente asociado a una tarjeta de debito.
     * Adicionalmente, el OTP es enviado por correo electronico al cliente, solo en caso de que, 
     * tanto el canal, como el cliente esten configurados para dicho envio.
     * @param inCodigoCliente String codigo de cliente al cual se le va a generar el OTP.
     * @param canal String canal Codigo (extendido) del canal desde el cual es llamado el procedimiento.
     * @return OtpDTO Contiene el codigo OTP generado
     */
    @Override
    public OtpDTO generarOTPSinEmail(String inCodigoCliente, String canal) {
        OtpDTO otp = new OtpDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        RespuestaDTO respuestaAct = new RespuestaDTO();

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_OTP", "IB_P_GENERAR_NO_EMAIL_OTP", 4, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setInt(1, Integer.parseInt(inCodigoCliente));
            statement.setString(2, canal);
            statement.registerOutParameter(3, OracleTypes.VARCHAR);
            statement.registerOutParameter(4, OracleTypes.INTEGER);
            statement.execute();

            otp.setOvOTP(new String(this.statement.getBytes(3), CHARSET_ORACLE_9));
            otp.setOnCodSalida(String.valueOf(this.statement.getInt(4)));

            respuestaAct = actuacionesDAO.obtenerDescripcionSalidaSP(otp.getOnCodSalida(), canal);

            respuesta.setCodigoSP(respuestaAct.getCodigoSP());
            respuesta.setDescripcionSP(respuestaAct.getDescripcionSP());

            otp.setRespuesta(respuesta);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN generarOTPSinEmail: ")
                    .append("USR-").append(inCodigoCliente)
                    .append("-CH-").append(canal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log            
        } finally {
            otp.setRespuesta(respuesta);
            this.cerrarConexion(canal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN generarOTPSinEmail: ")
//                    .append("USR-").append(inCodigoCliente)
//                    .append("-CH-").append(canal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        System.out.println("otp :"+otp.getOvOTP());
            return otp;
    }
    
    
    /**
     * Valida un OTP. El OTP es valido solo si coincide con el OTP generado
     * previamente. Nota: Se tomara siempre el ultimo OTP generado.
     *
     * @param inCodigoCliente String codigo de cliente al cual se le va a
     * generar el OTP.
     * @param ivOTP String OTP que va a ser validado.
     * @param ivCodExtCanal String Codigo (extendido) del canal desde el cual es
     * llamado el procedimiento.
     * @return OtpDTO valida el codigo OTP
     */
    @Override
    public OtpDTO validarOTP(String inCodigoCliente, String ivOTP, String ivCodExtCanal) {
        OtpDTO otp = new OtpDTO();
        RespuestaDTO respuesta = new RespuestaDTO();

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, ivCodExtCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_OTP", "IB_P_VALIDAR_OTP", 4, ivCodExtCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setInt(1, Integer.parseInt(inCodigoCliente));
            statement.setString(2, ivOTP);
            statement.setString(3, ivCodExtCanal);
            statement.registerOutParameter(4, OracleTypes.NUMBER);
            this.ejecuto = statement.execute();

            otp.setOnCodSalida(String.valueOf(this.statement.getInt(4)));

            respuesta = actuacionesDAO.obtenerDescripcionSalidaSP(otp.getOnCodSalida(), ivCodExtCanal);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN validarOTP: ").append("USR-").append(inCodigoCliente)
                    .append("-CH-").append(ivCodExtCanal).append("-DT-").append(new Date()).append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            otp.setRespuesta(respuesta);
            this.cerrarConexion(ivCodExtCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN validarOTP: ").append("USR-").append(inCodigoCliente)
//                    .append("-CH-").append(ivCodExtCanal).append("-DT-").append(new Date()).append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return otp;
    }
    
    /* Genera un nuevo OTP el cual es asignado al usuario
     * 
     *
     * @param idUsuario   usuario al cual se le va a generar el OTP
     * @param codigoCanal codigo de canal en el CORE bancario
     * @return OtpDTO con el codigo otp generado y/o respuestaDTO si fue exitoso o no
     */
    @Override
    public OtpDTO generarOTPEnviarCorreoPj(String idUsuario, Integer codigoPlantillaCorreo, String destinatario, String parametrosCorreo, String codigoCanal) {
        OtpDTO otp = new OtpDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        RespuestaDTO respuestaAct = new RespuestaDTO();

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_OTP_PJ", "IB_P_GENERAR_OTP", 7, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setInt(1, Integer.parseInt(idUsuario));
            statement.setString(2, codigoCanal);
            
            //SI ESTOS VALORES SE ENVIAN NULL SOLO GENERA EL OTP 
            //Y NO SE ENVIA CORREO
            statement.setInt(3, codigoPlantillaCorreo == null ? 0 : codigoPlantillaCorreo);
            statement.setString(4, destinatario == null ? "" : destinatario);
            statement.setString(5, parametrosCorreo == null ? "" : parametrosCorreo);
            
            statement.registerOutParameter(6, OracleTypes.VARCHAR);
            statement.registerOutParameter(7, OracleTypes.INTEGER);
            statement.execute();

            otp.setOvOTP(new String(this.statement.getBytes(6), CHARSET_ORACLE_9));
            otp.setOnCodSalida(String.valueOf(this.statement.getInt(7)));

            respuestaAct = actuacionesDAO.obtenerDescripcionSalidaSP(otp.getOnCodSalida(), codigoCanal);

            respuesta.setCodigoSP(respuestaAct.getCodigoSP());
            respuesta.setDescripcionSP(respuestaAct.getDescripcionSP());

            otp.setRespuesta(respuesta);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN generarOTPEnviarCorreoPj: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(),e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log            
        } finally {
            otp.setRespuesta(respuesta);
            this.cerrarConexion(codigoCanal);
        }
        /*
        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
            logger.error( new StringBuilder("EXITO DAO EN generarOTP: ")
                    .append("USR-").append(inCodigoCliente)
                    .append("-CH-").append(canal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
        }
        */
        return otp;
    }

    /**
     * Valida un OTP. El OTP es valido solo si coincide con el OTP generado
     * previamente. Nota: Se tomara siempre el ultimo OTP generado.
     *
     * @param idUsuario codigo del usuario al cual se le va a validar el OTP
     * @param codOTP String OTP que va a ser validado.
     * @param codigoCanal codigo de canal en el CORE bancario
     * @param registroUsuario indica si el OTP a validar es de registro de usuario
     * @return the com.bds.ws.dto.OtpDTO
     */
    @Override
    public OtpDTO validarOTPPj(String idUsuario, String codOTP, String codigoCanal, String registroUsuario) {
        OtpDTO otp = new OtpDTO();
        RespuestaDTO respuesta = new RespuestaDTO();

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_OTP_PJ", "IB_P_VALIDAR_OTP", 7, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setInt(1, Integer.parseInt(idUsuario));
            statement.setString(2, codOTP);
            statement.setString(3, codigoCanal);
            statement.setString(4, registroUsuario); //INDICA SI SE VA A VALIDAR EL OTP DE REGISTRO DE USUARIO
            statement.registerOutParameter(5, OracleTypes.NUMBER);
            statement.registerOutParameter(6, OracleTypes.NUMBER);
            statement.registerOutParameter(7, OracleTypes.NUMBER);
            
            this.ejecuto = statement.execute();
            
            otp.setIntentosRealizados(this.statement.getInt(5));
            otp.setIntentosMaximosPermitidos(this.statement.getInt(6));
            otp.setOnCodSalida(String.valueOf(this.statement.getInt(7)));
            respuesta = actuacionesDAO.obtenerDescripcionSalidaSP(otp.getOnCodSalida(), codigoCanal);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN validarOTPPj: ").append("USR-").append(codigoCanal)
                    .append("-CH-").append(codigoCanal).append("-DT-").append(new Date()).append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            
            logger.error( new StringBuilder("ERROR EN SP: IB_K_OTP_PJ.IB_P_VALIDAR_OTP ")
                    .append("Parametros SP: idUsuario: ").append(idUsuario).append(" codOTP: ").append(codOTP)
                    .append(" codigoCanal: ").append(codigoCanal)
                    .toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            otp.setRespuesta(respuesta);
            this.cerrarConexion(codigoCanal);
        }
        /*
        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
            logger.error( new StringBuilder("EXITO DAO EN validarOTP: ").append("USR-").append(inCodigoCliente)
                    .append("-CH-").append(ivCodExtCanal).append("-DT-").append(new Date()).append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
        }
        */        
        return otp;
    }
}
