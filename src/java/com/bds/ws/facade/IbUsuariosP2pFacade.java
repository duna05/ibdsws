/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IbUsuariosP2PDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.model.IbUsuariosP2p;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import static com.bds.ws.util.BDSUtil.TEXTO_SIN_RESULTADOS_JPA;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
@Named("IbUsuariosP2pFacade")
@Stateless(name = "wsIbUsuariosP2pFacade")
public class IbUsuariosP2pFacade extends AbstractFacade<IbUsuariosP2p> {

    /**
     * Log de sistema
     */
    private static final Logger logger = Logger.getLogger(IbUsuariosP2pFacade.class.getName());
    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private final String ESTATUS_ACTIVA = "A";

    public IbUsuariosP2pFacade() {
        super(IbUsuariosP2p.class);
    }

    /**
     * *
     * Metodo que realiza la consulta de afiliaciones Activas para el serv P2P
     * por usuario.
     *
     * @param idUsuario id del usuario
     * @param nombreCanal codigo del canal
     * @return IbUsuariosCanalesDTO
     */
    public IbUsuariosP2PDTO consultaUsuarioP2P(String idUsuario, String nombreCanal) {

        RespuestaDTO respuestaDTO = new RespuestaDTO();
        List<IbUsuariosP2p> usuarioP2P = new ArrayList<>();
        IbUsuariosP2PDTO ibUsuariosP2PDto = new IbUsuariosP2PDTO();
        try {

            usuarioP2P = (List<IbUsuariosP2p>) em.createQuery("SELECT c FROM IbUsuariosP2p c "
                    + "WHERE c.idUsuario.id = :idUsuario "
                    + "AND c.estatus = 'A' ")
                    .setParameter("idUsuario", BigDecimal.valueOf(Long.parseLong(idUsuario)))
                    .getResultList();

            if (usuarioP2P == null || usuarioP2P.isEmpty()) {
                throw new NoResultException();
            }

            //---------------------------------------------------------------------------------------
            ibUsuariosP2PDto.setUsuariosP2p(usuarioP2P);

        } catch (NoResultException e) {
            respuestaDTO.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log 
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error(new StringBuilder("ERROR JPA EN consultaUsuarioP2P: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error(new StringBuilder("ERROR JPA EN consultaUsuarioP2P: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
            ibUsuariosP2PDto.setUsuariosP2p(usuarioP2P);

        }
        ibUsuariosP2PDto.setRespuesta(respuestaDTO);
        return ibUsuariosP2PDto;
    }

    /**
     * *
     * Metodo que realiza la validacion de los datos a afiliar al servicio P2P
     *
     * @param nroTelf numero de telf a afiliar
     * @param nroCta numero de cta a afiliar
     * @param idUsuario id del usuario
     * @param nombreCanal codigo del canal
     * @return UtilDTO
     */
    public UtilDTO validarAfiliacionP2P(String nroTelf, String nroCta, String idUsuario, String nombreCanal) {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        boolean afiliacionValida = true;
        UtilDTO util = new UtilDTO();
        List<IbUsuariosP2p> usuarioP2P = new ArrayList<>();
        IbUsuariosP2PDTO ibUsuariosP2PDto = new IbUsuariosP2PDTO();
        Map resultado = new HashMap<>();
        try {
//            usuarioP2P = (List<IbUsuariosP2p>) em.createNamedQuery("IbUsuariosP2p.findByNroTelefono")
//                    .setParameter("nroTelefono", nroTelf)
//                    .setParameter("estatus", ESTATUS_ACTIVA.charAt(0))
//                    .getResultList();

            usuarioP2P = (List<IbUsuariosP2p>) em.createQuery("SELECT c FROM IbUsuariosP2p c "
                    + "WHERE c.nroTelefono = :nroTelefono "
                    + "AND c.estatus = :estatus")
                    .setParameter("nroTelefono", nroTelf)
                    .setParameter("estatus", ESTATUS_ACTIVA.charAt(0))
                    .getResultList();

            if (usuarioP2P != null && !usuarioP2P.isEmpty()) {
                afiliacionValida = false;
                respuestaDTO.setCodigoSP(CODIGO_EXCEPCION_P2P_TELF_DUP);
                util.setResulados(resultado);
            } else {
                usuarioP2P = (List<IbUsuariosP2p>) em.createQuery("SELECT c FROM IbUsuariosP2p c "
                        + "WHERE c.idUsuario.id = :idUsuario "
                        + "AND c.nroCuenta = :nroCuenta "
                        + "AND c.estatus = 'A' ")
                        .setParameter("idUsuario", BigDecimal.valueOf(Long.parseLong(idUsuario)))
                        .setParameter("nroCuenta", nroCta)
                        .getResultList();
                if (usuarioP2P != null && !usuarioP2P.isEmpty()) {
                    afiliacionValida = false;
                    respuestaDTO.setCodigoSP(CODIGO_EXCEPCION_P2P_CTA_DUP);
                    util.setResulados(resultado);
                } else {
                    util.setResulados(resultado);
                }
            }
            resultado.put("valida", afiliacionValida);
            ibUsuariosP2PDto.setUsuariosP2p(usuarioP2P);
        } catch (NoResultException e) {
            respuestaDTO.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log 
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error(new StringBuilder("ERROR JPA EN validarAfiliacionP2P: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error(new StringBuilder("ERROR JPA EN validarAfiliacionP2P: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
            ibUsuariosP2PDto.setUsuariosP2p(usuarioP2P);
        }
        util.setRespuesta(respuestaDTO);
        return util;
    }

    /**
     * *
     * Metodo que realiza la validacion de los datos a editar una afiliacion al
     * servicio P2P
     *
     * @param idAfiliacion identificador de la afiliacion a modificar
     * @param nroTelf numero de telf a afiliar
     * @param nroCta numero de cta a afiliar
     * @param idUsuario id del usuario
     * @param nombreCanal codigo del canal
     * @return UtilDTO
     */
    public UtilDTO validarEdicionAfiliacionP2P(String idAfiliacion, String nroTelf, String nroCta, String idUsuario, String nombreCanal) {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        boolean afiliacionValida = true;
        UtilDTO util = new UtilDTO();
        List<IbUsuariosP2p> usuarioP2P = new ArrayList<>();
        IbUsuariosP2PDTO ibUsuariosP2PDto = new IbUsuariosP2PDTO();
        Map resultado = new HashMap<>();
        try {
            usuarioP2P = (List<IbUsuariosP2p>) em.createQuery("SELECT c FROM IbUsuariosP2p c "
                    + "WHERE c.id <> :id "
                    + "AND c.nroTelefono = :nroTelefono "
                    + "AND c.estatus = 'A' ")
                    .setParameter("id", new BigDecimal(idAfiliacion))
                    .setParameter("nroTelefono", nroTelf)
                    .getResultList();

            if (usuarioP2P != null && !usuarioP2P.isEmpty()) {
                afiliacionValida = false;
                respuestaDTO.setCodigoSP(CODIGO_EXCEPCION_P2P_TELF_DUP);
                util.setResulados(resultado);
            } else {
                usuarioP2P = (List<IbUsuariosP2p>) em.createQuery("SELECT c FROM IbUsuariosP2p c "
                        + "WHERE c.id <> :id "
                        + "AND c.idUsuario.id = :idUsuario "
                        + "AND c.nroCuenta = :nroCuenta "
                        + "AND c.estatus = 'A' ")
                        .setParameter("id", new BigDecimal(idAfiliacion))
                        .setParameter("idUsuario", new BigDecimal(idUsuario))
                        .setParameter("nroCuenta", nroCta)
                        .getResultList();
                if (usuarioP2P != null && !usuarioP2P.isEmpty()) {
                    afiliacionValida = false;
                    respuestaDTO.setCodigoSP(CODIGO_EXCEPCION_P2P_CTA_DUP);
                    util.setResulados(resultado);
                } else {
                    util.setResulados(resultado);
                }
            }
            resultado.put("valida", afiliacionValida);
            ibUsuariosP2PDto.setUsuariosP2p(usuarioP2P);
        } catch (NoResultException e) {
            respuestaDTO.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log 
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error(new StringBuilder("ERROR JPA EN validarEdicionAfiliacionP2P: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error(new StringBuilder("ERROR JPA EN validarEdicionAfiliacionP2P: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
            ibUsuariosP2PDto.setUsuariosP2p(usuarioP2P);
        }
        util.setRespuesta(respuestaDTO);
        return util;
    }

    /**
     * *
     * Metodo que realiza la consulta usuario por número de teléfono.
     *
     * @param nroTelefono
     * @param nombreCanal codigo del canal
     * @return IbUsuariosCanalesDTO
     */
    public IbUsuariosP2PDTO consultaUsuarioP2PxTelefono(String nroTelefono, String nombreCanal) {
        IbUsuariosP2PDTO ibUsuariosP2PDto = new IbUsuariosP2PDTO();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbUsuariosP2p usuarioP2P = null;

        try {

            usuarioP2P = (IbUsuariosP2p) em.createQuery("SELECT c FROM IbUsuariosP2p c "
                    + "WHERE c.nroTelefono = :nroTelefono")
                    .setParameter("nroTelefono", nroTelefono)
                    .getSingleResult();

            //---------------------------------------------------------------------------------------
            ibUsuariosP2PDto.setUsuarioP2p(usuarioP2P);

        } catch (NoResultException e) {
            respuestaDTO.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log 
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error(new StringBuilder("ERROR JPA EN consultaUsuarioP2PxTelefono: ")
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error(new StringBuilder("ERROR JPA EN consultaUsuarioP2PxTelefono: ")
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
            ibUsuariosP2PDto.setUsuarioP2p(usuarioP2P);

        }
        ibUsuariosP2PDto.setRespuesta(respuestaDTO);
        return ibUsuariosP2PDto;
    }

}
