/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.model.IbUsuariosEventosMedios;
import com.bds.ws.util.BDSUtil;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author juan.faneite
 */
@Named("ibUsuariosEventosMediosFacade")
@Stateless(name = "wsIbUsuariosEventosMediosFacade")
public class IbUsuariosEventosMediosFacade extends AbstractFacade<IbUsuariosEventosMedios> {
    
    @EJB
    IbUsuariosFacade usuariosFacade;
    
    @EJB
    IbEventosNotificacionFacade eventosNotificacionFacade;
    
    @EJB
    IbMediosNotificacionFacade mediosNotificacionFacade;
    
    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;
    
    private static final Logger logger = Logger.getLogger(IbUsuariosEventosMediosFacade.class.getName());

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbUsuariosEventosMediosFacade() {
        super(IbUsuariosEventosMedios.class);
    }
    
    /**
     * Metodo que borra todas las preguntas de desafio de un usuario
     *
     * @param idUsuario id del usuario
     * @param codigoCanal nombre del canal
     * @return RespuestaDTO
     */
    public RespuestaDTO borrarLoteUsrEventMed(String idUsuario, String codigoCanal) {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        int numColAct = 0;
        try {
            numColAct = em.createQuery("DELETE FROM IbUsuariosEventosMedios i WHERE i.idUsuario.id = :idUsuario")
                    .setParameter("idUsuario", new BigDecimal(idUsuario))
                    .executeUpdate();

        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo(BDSUtil.CODIGO_EXCEPCION_GENERICA);
            respuestaDTO.setDescripcion(BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA);
            logger.error( new StringBuilder("ERROR JPA EN borrarLoteUsrEventMed: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(BDSUtil.CODIGO_EXCEPCION_GENERICA);
            respuestaDTO.setDescripcion(BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA);
        }

        return respuestaDTO;
    }

   /**
    * Metodo que inserta un modelo de usuario - evento - medio
    * @param idUsuario id del usuario
    * @param idEvento id del evento
    * @param idMedio id del medio
     * @param montoMin
    * @param codigoCanal codigo del canal
    * @return RespuestaDTO
    */
    public RespuestaDTO insertarUsrEventMed(String idUsuario, String idEvento, String idMedio, String montoMin, String codigoCanal) {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbUsuariosEventosMedios usuariosEventosMedios = new IbUsuariosEventosMedios();
        try {

            if (idUsuario != null && idEvento != null && idMedio != null) {
                usuariosEventosMedios.setId(BigDecimal.ZERO);
                usuariosEventosMedios.setIdUsuario(usuariosFacade.find(new BigDecimal(idUsuario)));
                usuariosEventosMedios.setIdEvento(eventosNotificacionFacade.find(new BigDecimal(idEvento)));
                usuariosEventosMedios.setIdMedio(mediosNotificacionFacade.find(new BigDecimal(idMedio)));
                if(montoMin != null){
                   usuariosEventosMedios.setMontoMin(new BigDecimal(montoMin)); 
                }else{
                    usuariosEventosMedios.setMontoMin(BigDecimal.ZERO);
                }                
                
                respuestaDTO = crear(usuariosEventosMedios);
            } else {
                throw new IllegalArgumentException();
            }

        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo(BDSUtil.CODIGO_EXCEPCION_GENERICA);
            respuestaDTO.setDescripcion(BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA);
            logger.error( new StringBuilder("ERROR JPA EN insertarUsrEventMed: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(BDSUtil.CODIGO_EXCEPCION_GENERICA);
            respuestaDTO.setDescripcion(BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA);
        }

        return respuestaDTO;
    }
    
    /**
     * Método que se encarga de actualizar el monto mínimo establecido para un evento de un medio específico de un usuario
     * @author wilmer.rondon
     * @param idUsuario
     * @param idEvento
     * @param idMedio
     * @param montoMin
     * @param codigoCanal
     * @return
     */ 
    public RespuestaDTO actualizarUsuariosEventosMedios(String idUsuario, String idEvento, String idMedio, String montoMin, String codigoCanal) {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        int updateCount = 0;
        try {

            if (montoMin != null) {
                Query q = em.createQuery("UPDATE IbUsuariosEventosMedios i "
                        + "SET i.montoMin= :montoMin "
                        + "WHERE i.idUsuario.id = :idUsuario "
                        + "and i.idEvento.id = :idEvento "
                        + "and i.idMedio.id = :idMedio ")
                        .setParameter("idUsuario", new BigDecimal(idUsuario))
                        .setParameter("idEvento", new BigDecimal(idEvento))
                        .setParameter("idMedio", new BigDecimal(idMedio));

                q.setParameter("montoMin", new BigDecimal(montoMin));

                updateCount = q.executeUpdate();
            }
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");
            respuestaDTO.setDescripcion(BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA);
            logger.error( new StringBuilder("ERROR JPA EN actualizarUsuariosEventosMedios: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(BDSUtil.CODIGO_EXCEPCION_GENERICA);
            respuestaDTO.setDescripcion(BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA);
        }
        return respuestaDTO;
    }
    
    /**
     * Método que Consulta si ya existe un registro asociado a un usuario de un
     * evento y un medio específico
     *
     * @author wilmer.rondon
     * @param idUsuario
     * @param idEvento
     * @param idMedio
     * @param codigoCanal
     * @return int
     */
    public int consultarUsuariosEventosMedios(String idUsuario, String idEvento, String idMedio, String codigoCanal) {

        int existe = 0;
        try {

            existe = ((Long) em.createQuery("SELECT Count(i) "
                    + "FROM IbUsuariosEventosMedios i  "
                    + "WHERE i.idUsuario.id = :idUsuario "
                    + "and i.idEvento.id = :idEvento "
                    + "and i.idMedio.id = :idMedio ")
                    .setParameter("idUsuario", new BigDecimal(idUsuario))
                    .setParameter("idEvento", new BigDecimal(idEvento))
                    .setParameter("idMedio", new BigDecimal(idMedio))
                    .getSingleResult()).intValue();

        } catch (IllegalArgumentException e) {
            logger.error( new StringBuilder("ERROR JPA EN consultarUsuariosEventosMedios: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR EN consultarUsuariosEventosMedios: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        }
        return existe;
    }
    
}
