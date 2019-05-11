/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IbUsuarioDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.model.IbUsuarios;
import com.bds.ws.util.BDSUtil;
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
@Named("ibUsuariosFacade")
@Stateless(name = "wsIbUsuariosFacade")
public class IbUsuariosFacade extends AbstractFacade<IbUsuarios> {

    /*
     *logs del sistema
     */
    private static final Logger logger = Logger.getLogger(IbUsuariosFacade.class.getName());

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbUsuariosFacade() {
        super(IbUsuarios.class);
    }

    /**
     * *
     * Metodo que realiza la busqueda un usuario por codigo
     *
     * @param idUsuario codigo del usuario
     * @param nombreCanal nombre del canal
     * @return IbUsuarioDTO
     */
    public IbUsuarioDTO ibUsuarioPorCodUsuario(String idUsuario, String nombreCanal) {

        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbUsuarioDTO ibUsuarioDTO = new IbUsuarioDTO();

        try {

            ibUsuarioDTO.setUsuario((IbUsuarios) em.createNamedQuery("IbUsuarios.findByCodUsuario")
                    .setParameter("codUsuario", idUsuario)
                    .getSingleResult()
            );

            if (ibUsuarioDTO.getUsuario() == null) {
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuestaDTO.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log  
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN ibUsuarioPorCodUsuario: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN ibUsuarioPorCodUsuario: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }

        ibUsuarioDTO.setRespuesta(respuestaDTO);

        return ibUsuarioDTO;
    }
    /**
     * *
     * Metodo que realiza la busqueda un usuario por id
     *
     * @param idUsuario codigo del usuario
     * @param nombreCanal nombre del canal
     * @return IbUsuarioDTO
     */
    public IbUsuarioDTO ibUsuarioPorIdUsuario(String idUsuario, String nombreCanal) {

        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbUsuarioDTO ibUsuarioDTO = new IbUsuarioDTO();

        try {

            ibUsuarioDTO.setUsuario((IbUsuarios) em.createNamedQuery("IbUsuarios.findById")
                    .setParameter("id", new BigDecimal(idUsuario))
                    .getSingleResult()
            );

            if (ibUsuarioDTO.getUsuario() == null) {
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuestaDTO.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log  
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN ibUsuarioPorIdUsuario: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN ibUsuarioPorIdUsuario: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }

        ibUsuarioDTO.setRespuesta(respuestaDTO);

        return ibUsuarioDTO;
    }

    /**
     * *
     * Metodo que realiza la busqueda un usuario por TDD
     *
     * @param tdd numero de la tdd
     * @param nombreCanal nombre del canal
     * @return IbUsuarioDTO
     */
    public IbUsuarioDTO ibUsuarioPorTDD(String tdd, String nombreCanal) {

        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbUsuarioDTO ibUsuarioDTO = new IbUsuarioDTO();

        try {

            ibUsuarioDTO.setUsuario((IbUsuarios) em.createNamedQuery("IbUsuarios.findByTdd")
                    .setParameter("tdd", tdd)
                    .getSingleResult()
            );

            if (ibUsuarioDTO.getUsuario() == null) {
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuestaDTO.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log   
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN ibUsuarioPorTDD: ")
                    .append("-TDD-").append(this.numeroCuentaFormato(tdd))
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN ibUsuarioPorTDD: ")
                    .append("-TDD-").append(this.numeroCuentaFormato(tdd))
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }

        ibUsuarioDTO.setRespuesta(respuestaDTO);

        return ibUsuarioDTO;
    }

    /**
     * *
     * Metodo que realiza la busqueda un usuario por TDD
     *
     * @param tdd numero de la tdd
     * @param clave contrasenna de acceso encriptada
     * @param nombreCanal nombre del canal
     * @return IbUsuarioDTO
     */
    public IbUsuarioDTO ibUsuarioPorTDDClave(String tdd, String clave, String nombreCanal) {

        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbUsuarioDTO ibUsuarioDTO = new IbUsuarioDTO();

        try {

            ibUsuarioDTO.setUsuario((IbUsuarios) em.createNamedQuery("IbUsuarios.findByTddClave")
                    .setParameter("tdd", tdd)
                    .setParameter("clave", clave)
                    .getSingleResult()
            );

            if (ibUsuarioDTO.getUsuario() == null) {
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuestaDTO.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log    
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN ibUsuarioPorTDDClave: ")
                    .append("-TDD-").append(this.numeroCuentaFormato(tdd))
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN ibUsuarioPorTDDClave: ")
                    .append("-TDD-").append(this.numeroCuentaFormato(tdd))
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }

        ibUsuarioDTO.setRespuesta(respuestaDTO);

        return ibUsuarioDTO;
    }

    /**
     * Metodo para consultar la cantidad de intentos fallidos de preguntas de
     * seguridad
     *
     * @param idUsuario identificador del usuario
     * @param nroTDD nro de TDD del usuario
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    public UtilDTO cantPreguntasSeguridadFallidas(String idUsuario, String nroTDD, String nombreCanal) {
        int cantIntentosFallidos = 0;
        UtilDTO utilDTO = new UtilDTO();
        Map resultados = new HashMap();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        try {

            cantIntentosFallidos = (int) em.createQuery("select c.intentosFallidosPreguntas from IbUsuarios c where c.id = :idUsuario and c.tdd = :nroTdd ")
                    .setParameter("idUsuario", new BigDecimal(idUsuario))
                    .setParameter("nroTdd", nroTDD)
                    .getSingleResult();
            resultados.put("intentosFallidos", cantIntentosFallidos);

            utilDTO.setResulados(resultados);

        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN cantPreguntasSeguridadFallidas: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception ex) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log            
        }

        utilDTO.setRespuesta(respuestaDTO);

        return utilDTO;
    }

    /**
     * Metodo actualiza la cantidad de intentos fallidos de las preguntas de
     * seguridad
     *
     * @param idUsuario identificador del usuario
     * @param cantIntentos cantidad de intentos fallidos a actualizar
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    public RespuestaDTO actualizarCantIntentosFallidosPregSeg(String idUsuario, int cantIntentos, String nombreCanal) {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbUsuarios usuario;
        try {
            usuario = (IbUsuarios) em.createNamedQuery("IbUsuarios.findById")
                    .setParameter("id", new BigDecimal(idUsuario))
                    .getSingleResult();

            if (usuario == null) {
                throw new NoResultException();
            }

            usuario.setIntentosFallidosPreguntas(cantIntentos);
            respuestaDTO = this.actualizar(usuario);

        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo(BDSUtil.CODIGO_EXCEPCION_GENERICA);
            respuestaDTO.setDescripcion(BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA);
            logger.error( new StringBuilder("ERROR JPA EN actualizarCantIntentosFallidosPregSeg: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
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
     * Metodo para consultar la la TDD de un usuario por su Documento de Identidad
     *
     * @param tipoDoc tipo de documento
     * @param nroDoc nro de TDD del usuario
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    public UtilDTO obtenerTDDPorDoc(String tipoDoc, String nroDoc, String nombreCanal){
        String tdd;
        UtilDTO utilDTO = new UtilDTO();
        Map resultados = new HashMap();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        try {

            tdd = (String) em.createQuery("select c.tdd from IbUsuarios c where c.tipoDoc = :tipoDoc and c.documento = :nroDoc ")
                    .setParameter("tipoDoc", tipoDoc.charAt(0))
                    .setParameter("nroDoc", nroDoc)
                    .getSingleResult();
            resultados.put("tdd", tdd);

            utilDTO.setResulados(resultados);

        }catch (NoResultException e) {
            respuestaDTO.setCodigo(CODIGO_SIN_RESULTADOS_JPA);//revisar el log   
            respuestaDTO.setDescripcion(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN obtenerTDDPorDoc: ")
                    .append("TDOC-").append(tipoDoc)
                    .append("-NDOC-").append(nroDoc)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN obtenerTDDPorDoc: ")
                    .append("TDOC-").append(tipoDoc)
                    .append("-NDOC-").append(nroDoc)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception ex) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log            
        }
        utilDTO.setRespuesta(respuestaDTO);
        return utilDTO;
    }

}
