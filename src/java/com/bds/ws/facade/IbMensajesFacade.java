/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IbMensajesDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.model.IbMensajes;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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
@Named("ibMensajesFacade")
@Stateless(name = "wsIbMensajesFacade")
public class IbMensajesFacade extends AbstractFacade<IbMensajes> {
    
    /*
     *logs del sistema
     */
    private static final Logger logger = Logger.getLogger(IbMensajesFacade.class.getName());

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbMensajesFacade() {
        super(IbMensajes.class);
    }
    
    /**
     * *
     * Metodo que realiza la consulta de los mensajes activos (nuevos o leidos) asociados a un usuario
     *
     * @param idUsuario codigo del usuario
     * @param nombreCanal nombre del canal
     * @return IbUsuarioDTO
     */
    public IbMensajesDTO consultarMensajesUsuarios (String idUsuario , String nombreCanal ){
    RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbMensajesDTO mensajes = new IbMensajesDTO();        
        
        try {

            mensajes.setIbMensajes((List<IbMensajes>) em.createQuery("SELECT m FROM IbMensajes m, IbMensajesUsuarios i  WHERE i.idUsuario.id = :idUsuario and i.idMensaje.id = m.id and i.estatus != 'E'")
                    .setParameter("idUsuario",  new BigDecimal(idUsuario))
                    .getResultList());            

            if (mensajes.getIbMensajes() == null || mensajes.getIbMensajes().isEmpty()) {
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuestaDTO.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log     
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN consultarMensajesUsuarios: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN consultarMensajesUsuarios: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        mensajes.setRespuesta(respuestaDTO);
        
        return mensajes;
    }
    
    /**
     * *
     * Metodo que realiza la consulta de los mensajes activos (nuevos o leidos)
     * asociados a un usuario
     *
     * @param idEmpresa codigo del usuario
     * @param nombreCanal nombre del canal
     * @return IbUsuarioDTO
     */
    public IbMensajesDTO consultarMensajesEmpresa(String idEmpresa, String nombreCanal) {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbMensajesDTO mensajes = new IbMensajesDTO();
        StringBuilder consulta = new StringBuilder();
        
        consulta.append(" SELECT m FROM IbMensajes m, IbMensajesEmpresasPj i");
        consulta.append(" WHERE i.idMensaje.id    =  m.id ");
        consulta.append(" AND   i.estatus        != 'E' ");
        consulta.append(" AND   i.idEmpresaPj.id  = :idEmpresa  "); 
        
        try {
            mensajes.setIbMensajes((List<IbMensajes>) 
                    em.createQuery(consulta.toString())
                    .setParameter("idEmpresa", new BigDecimal(idEmpresa)) 
                    .getResultList());

            if (mensajes.getIbMensajes() == null || mensajes.getIbMensajes().isEmpty()) {
                throw new NoResultException();
            }
        } catch (NoResultException e) {
            respuestaDTO.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log     
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN consultarMensajesEmpresa: ")
                    .append("EMP-").append(idEmpresa)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN consultarMensajesEmpresa: ")
                    .append("EMP-").append(idEmpresa)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
        } catch (Exception e) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        mensajes.setRespuesta(respuestaDTO);
        return mensajes;
    }
}
