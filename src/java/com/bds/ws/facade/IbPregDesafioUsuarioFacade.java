/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IbPregDesafioUsuarioDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.model.IbPregDesafioUsuario;
import com.bds.ws.util.BDSUtil;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;

/**
 *
 * @author cesar.mujica
 */
@Named("ibPregDesafioUsuario")
@Stateless(name = "wsIbPregDesafioUsuarioFacade")
public class IbPregDesafioUsuarioFacade extends AbstractFacade<IbPregDesafioUsuario> {

    @EJB
    IbUsuariosFacade usuariosFacade;

    @EJB
    IbPreguntasDesafioFacade preguntasDesafioFacade;

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    /*
     *logs del sistema
     */
    private static final Logger logger = Logger.getLogger(IbPregDesafioUsuarioFacade.class.getName());

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbPregDesafioUsuarioFacade() {
        super(IbPregDesafioUsuario.class);
    }

    /**
     * Metodo para obtener el listado de preguntas de desafio por usuario
     *
     * @param idUsuario identificador del usuario Oracle11
     * @param nombreCanal codigo extendido del canal
     * @return lista de relaciones de preguntas definidas para un usuario
     */
    public IbPregDesafioUsuarioDTO listaPreguntasDesafioUsuario(String idUsuario, String nombreCanal) {
        IbPregDesafioUsuarioDTO preguntasDesafioDTO = new IbPregDesafioUsuarioDTO();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        try {

            preguntasDesafioDTO.setIbPregDesafioUsuarios(((List<IbPregDesafioUsuario>) em.createQuery("SELECT i FROM IbPregDesafioUsuario i WHERE i.idUsuario.id = :idUsuario")
                    .setParameter("idUsuario", new BigDecimal(idUsuario))
                    .getResultList()));

            if (preguntasDesafioDTO.getIbPregDesafioUsuarios().isEmpty()) {
                throw new NoResultException();
            }
        } catch (NoResultException e) {
            respuestaDTO.setCodigoSP(BDSUtil.CODIGO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN listaPreguntasDesafioUsuario: ")
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");
            respuestaDTO.setDescripcion(BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA);
            logger.error( new StringBuilder("ERROR JPA EN listaPreguntasDesafioUsuario: ")
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(BDSUtil.CODIGO_EXCEPCION_GENERICA);
            respuestaDTO.setDescripcion(BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA);
        }

        preguntasDesafioDTO.setRespuesta(respuestaDTO);
        return preguntasDesafioDTO;
    }

    /**
     * Metodo para validar una rafaga de preguntas de desafio
     *
     * @param idUsuario id del usuario
     * @param rafaga cadena con las preguntas y respuestas a validar ej: 
     * < codigoPregunta >< separador >< respuesta >< separador >< codigoPregunta >< separador >< respuesta >
     * @param separador separador que utilizara en la rafaga, si este es null se tomará guion "-" como separador
     * @param nombreCanal nombre del canal
     * @return UtilDTO -> 'true' en caso que la respuesta sea corresta 'false'
     * en caso contrario
     */
    public UtilDTO validarPreguntaDD(String idUsuario, String rafaga, String separador, String nombreCanal) {
        UtilDTO utilDTO = new UtilDTO();
        Map resultado = new HashMap();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        long registro = 0;
        IbPregDesafioUsuarioDTO ibPregDesafioUsuarioDTO = new IbPregDesafioUsuarioDTO();
        boolean control = true;
        try {

            ibPregDesafioUsuarioDTO.setIbPregDesafioUsuarios((List<IbPregDesafioUsuario>) em.createQuery("SELECT i FROM IbPregDesafioUsuario i WHERE i.idUsuario.id = :idUsuario")
                    .setParameter("idUsuario", new BigDecimal(idUsuario))
                    .getResultList());

            if (ibPregDesafioUsuarioDTO.getIbPregDesafioUsuarios().isEmpty()) {
                throw new NoResultException();
            } else {

                String[] arrayCadena = rafaga.split("-");
                
                for (int i=0; i<arrayCadena.length; i+=2) {
                    for (IbPregDesafioUsuario pregDesafioUsuarios : ibPregDesafioUsuarioDTO.getIbPregDesafioUsuarios()){
                        if(pregDesafioUsuarios.getIdPreguntaDesafio().getId().toString().equalsIgnoreCase(arrayCadena[i])){
                            if (!pregDesafioUsuarios.getRespuesta().equalsIgnoreCase(arrayCadena[i+1])){
                                logger.error("********** TRAZA PREGUNTAS DE DESAFIO ********** id pregunta: |" 
                                        + pregDesafioUsuarios.getIdPreguntaDesafio().getId().toString() 
                                        + "| respuesta bd: | " + pregDesafioUsuarios.getRespuesta()+ "| " 
                                        + "| respuesta ingresada |"+arrayCadena[i + 1]+"|"
                                        + "| id usuario |"+idUsuario+"|");
                                control = false;
                                i = arrayCadena.length + 1;
                                break;
                            }
                        }
                    }
                }
            }
            
            resultado.put("resultado", control);

            utilDTO.setResulados(resultado);

        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo(BDSUtil.CODIGO_EXCEPCION_GENERICA);
            logger.error( new StringBuilder("ERROR JPA EN validarPreguntaDD: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (NoResultException e) {
            respuestaDTO.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log 
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN validarPreguntaDD: ")//.append("USR-").append(usuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } 
        catch (Exception e) {
            respuestaDTO.setCodigo(BDSUtil.CODIGO_EXCEPCION_GENERICA);
            respuestaDTO.setDescripcion(BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA);
        }

        utilDTO.setRespuesta(respuestaDTO);
        return utilDTO;
    }

    /**
     * Metodo que borra todas las preguntas de desafio de un usuario
     *
     * @param idUsuario id del usuario
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    public RespuestaDTO borrarLotePDDUsuario(String idUsuario, String nombreCanal) {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        int numColAct = 0;
        try {
            numColAct = em.createQuery("DELETE FROM IbPregDesafioUsuario i WHERE i.idUsuario.id = :idUsuario")
                    .setParameter("idUsuario", new BigDecimal(idUsuario))
                    .executeUpdate();
            this.em.setFlushMode(FlushModeType.COMMIT);
            this.em.flush();
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo(BDSUtil.CODIGO_EXCEPCION_GENERICA);
            respuestaDTO.setDescripcion(BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA);
            logger.error( new StringBuilder("ERROR JPA EN borrarLotePDDUsuario: ")
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
     * Metodo que inserta una pregunta de desafio con su respuesta para un
     * usuario
     *
     * @param idUsuario id del usuario
     * @param idPregunta id de la pregunta
     * @param respuesta respuesta de la pregunta
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    public RespuestaDTO insertarPDDUsuario(String idUsuario, String idPregunta, String respuesta, String nombreCanal) {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbPregDesafioUsuario pregDesafioUsuario = new IbPregDesafioUsuario();
        try {

            if (idUsuario != null && idPregunta != null) {
                pregDesafioUsuario.setId(BigDecimal.ZERO);
                pregDesafioUsuario.setIdUsuario(usuariosFacade.find(new BigDecimal(idUsuario)));
                pregDesafioUsuario.setIdPreguntaDesafio(preguntasDesafioFacade.find(new BigDecimal(idPregunta)));
                pregDesafioUsuario.setRespuesta(respuesta);

                respuestaDTO = this.crear(pregDesafioUsuario);
                this.em.setFlushMode(FlushModeType.COMMIT);
                this.em.flush();
            } else {
                throw new IllegalArgumentException();
            }

        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo(BDSUtil.CODIGO_EXCEPCION_GENERICA);
            respuestaDTO.setDescripcion(BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA);
            logger.error( new StringBuilder("ERROR JPA EN insertarPDDUsuario: ")
                    .append("USR-").append(pregDesafioUsuario.getIdUsuario().getId())
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

}
