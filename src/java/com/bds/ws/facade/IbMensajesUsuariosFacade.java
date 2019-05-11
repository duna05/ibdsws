/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.model.IbMensajesUsuarios;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;

/**
 *
 * @author cesar.mujica
 */
@Named("ibMensajesUsuariosFacade")
@Stateless(name = "wsIbMensajesUsuariosFacade")
public class IbMensajesUsuariosFacade extends AbstractFacade<IbMensajesUsuarios> {

    /*
     *logs del sistema
     */
    private static final Logger logger = Logger.getLogger(IbMensajesUsuariosFacade.class.getName());

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbMensajesUsuariosFacade() {
        super(IbMensajesUsuarios.class);
    }

    /**
     * *
     * Metodo que consulta la cantidad de mensajes activos(nuevos o leidos)
     * asociados a un usuario
     *
     * @param idUsuario codigo del usuario
     * @param nombreCanal nombre del canal
     * @return IbUsuarioDTO
     */
    public UtilDTO cantNuevosMsjsUsuarios(String idUsuario, String nombreCanal) {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        Map resultados = new HashMap();

        try {

            int cantMsjs = ((Long) em.createQuery("SELECT Count(i) FROM IbMensajesUsuarios i  WHERE i.idUsuario.id = :idUsuario and i.estatus = 'N'")
                    .setParameter("idUsuario", new BigDecimal(idUsuario))
                    .getSingleResult()).intValue();

            resultados.put("cantMsjs", cantMsjs);
            utilDTO.setResulados(resultados);

        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN cantidadMensajesUsuariosNuevos: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        utilDTO.setRespuesta(respuestaDTO);

        return utilDTO;
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
    public RespuestaDTO marcarMensajeLeido(String idUsuario, String idMensaje, String nombreCanal) {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbMensajesUsuarios msjUsr;

        try {

            msjUsr = (IbMensajesUsuarios) em.createQuery("SELECT i FROM IbMensajesUsuarios i  WHERE i.idUsuario.id = :idUsuario and i.idMensaje.id = :idMensaje")
                    .setParameter("idUsuario", new BigDecimal(idUsuario))
                    .setParameter("idMensaje", new BigDecimal(idMensaje))
                    .getSingleResult();

            if (msjUsr == null || msjUsr.getId() == null) {
                throw new NoResultException();
            } else {
                msjUsr.setEstatus('L');
                respuestaDTO = this.actualizar(msjUsr);
            }

        } catch (NoResultException e) {
            respuestaDTO.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log  
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN marcarMensajeLeido: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN marcarMensajeLeido: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }

        return respuestaDTO;
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
    public UtilDTO mensajeUsuarioLeido(String idUsuario, String idMensaje, String nombreCanal) {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        Map resultados = new HashMap();

        try {

            String estatus = ((Character) em.createQuery("SELECT i.estatus FROM IbMensajesUsuarios i  WHERE i.idUsuario.id = :idUsuario and i.idMensaje.id = :idMensaje")
                    .setParameter("idUsuario", new BigDecimal(idUsuario))
                    .setParameter("idMensaje", new BigDecimal(idMensaje))
                    .getSingleResult()).toString();

            if (estatus != null && !estatus.isEmpty()) {
                if (estatus.equalsIgnoreCase("L")) {
                    resultados.put("estatus", true);
                } else {
                    if (estatus.equalsIgnoreCase("N")) { 
                        resultados.put("estatus", false);
                    } else {
                        //solo se permiten consultar estatus de mensajes leidos o nuevos
                        throw new IllegalArgumentException();
                    }
                }
            } else {
                //solo se permiten consultar estatus de mensajes leidos o nuevos registrados
                throw new IllegalArgumentException();
            }
            utilDTO.setResulados(resultados);

        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN mensajeUsuarioLeido: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        utilDTO.setRespuesta(respuestaDTO);

        return utilDTO;

    }

}
