/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbMensajesDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.IbMensajesDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.exception.DAOException;
import com.bds.ws.facade.IbMensajesFacade;
import com.bds.ws.facade.IbMensajesUsuariosFacade;
import com.bds.ws.util.DAOUtil;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;

/**
 *
 * @author cesar.mujica
 */
@Named("IbMensajesDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class IbMensajesDAOImpl extends DAOUtil implements IbMensajesDAO {

    /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(IbMensajesDAOImpl.class.getName());
    
    @EJB
    IbMensajesFacade mensajesFacade;
    
    @EJB
    IbMensajesUsuariosFacade mensajesUsuariosFacade;

    /**
     * *
     * Metodo que realiza la busqueda un usuario por codigo
     *
     * @param idUsuario codigo del usuario
     * @param nombreCanal nombre del canal
     * @return IbUsuarioDTO
     */
    @Override
    public IbMensajesDTO consultarMensajesUsuarios(String idUsuario, String nombreCanal) {
        IbMensajesDTO mensajes = new IbMensajesDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            
            if (idUsuario == null || idUsuario.isEmpty() || idUsuario.equalsIgnoreCase("")
                    || nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                mensajes.setRespuesta(respuesta);
                throw new DAOException();
            }
            
            mensajes = mensajesFacade.consultarMensajesUsuarios(idUsuario, nombreCanal);
            
            if (!mensajes.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new Exception();
            }
            
        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN consultarMensajesUsuarios: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN consultarMensajesUsuarios: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            mensajes.setRespuesta(respuesta);
        }
//        if (mensajes.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN listadoHistoricoCliente: ")
//                    .append("USR-").append(idUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        mensajes.setRespuesta(respuesta);
        return mensajes;
        
    }
    
    /**
     * *
     * Metodo que realiza la busqueda un usuario por codigo
     *
     * @param idEmpresa   codigo del usuario
     * @param idCanal     id de canal interno en IB
     * @param nombreCanal nombre del canal en el CORE BANARIO
     * @return IbUsuarioDTO
     */
    @Override
    public IbMensajesDTO consultarMensajesEmpresa(String idEmpresa, String idCanal, String nombreCanal) {
        IbMensajesDTO mensajes = new IbMensajesDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            if (idEmpresa == null || idEmpresa.isEmpty() || idEmpresa.equalsIgnoreCase("")
                    || nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                mensajes.setRespuesta(respuesta);
                throw new DAOException();
            }

            mensajes = mensajesFacade.consultarMensajesEmpresa(idEmpresa, nombreCanal);
            if (!mensajes.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new Exception();
            }
        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN consultarMensajesEmpresa: ")
                    .append("-USR-").append(idEmpresa)
                    .append("-EMP-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN consultarMensajesEmpresa: ")
                    .append("-EMP-").append(idEmpresa)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            mensajes.setRespuesta(respuesta);
        }
       
        mensajes.setRespuesta(respuesta);
        return mensajes;

    }
    
    /**
     * *
     * Metodo que consulta la cantidad de mensajes activos(nuevos o leidos) asociados a un usuario
     *
     * @param idUsuario codigo del usuario
     * @param nombreCanal nombre del canal
     * @return IbUsuarioDTO
     */
    @Override
    public UtilDTO cantNuevosMsjsUsuarios (String idUsuario , String nombreCanal ){
        UtilDTO util = new UtilDTO();
        try {
            util = mensajesUsuariosFacade.cantNuevosMsjsUsuarios(idUsuario, nombreCanal);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN cantidadMensajesUsuariosNuevos: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            util.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (util.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN cantidadMensajesUsuariosNuevos: ")
//                    .append("USR-").append(idUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return util;
    }
    
    /**
     * *
     * Metodo que actualiza el estado de un mensaje a leido
     *
     * @param idUsuario codigo del usuario
     * @param idMensaje identificador del mensaje
     * @param nombreCanal nombre del canal
     * @return IbUsuarioDTO
     */
    @Override
    public RespuestaDTO marcarMensajeLeido (String idUsuario , String idMensaje, String nombreCanal ){
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            respuesta = mensajesUsuariosFacade.marcarMensajeLeido(idUsuario, idMensaje, nombreCanal);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN marcarMensajeLeido: ")
                    .append("USR-").append(idUsuario)
                    .append("MSJ-").append(idMensaje)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN marcarMensajeLeido: ")
//                    .append("USR-").append(idUsuario)
//                    .append("MSJ-").append(idMensaje)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return respuesta;
    }
    
    /**
     * *
     * Metodo que consulta el estado de un mensaje nuevo o leido
     *
     * @param idUsuario codigo del usuario
     * @param idMensaje identificador del mensaje
     * @param nombreCanal nombre del canal
     * @return IbUsuarioDTO con parametro estatus y un booleano
     */
    @Override
    public UtilDTO mensajeUsuarioLeido(String idUsuario, String idMensaje, String nombreCanal){
        UtilDTO util = new UtilDTO();
        try {
            util = mensajesUsuariosFacade.mensajeUsuarioLeido(idUsuario, idMensaje, nombreCanal);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN mensajeUsuarioLeido: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            util.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (util.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN mensajeUsuarioLeido: ")
//                    .append("USR-").append(idUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return util;
    
    }

}
