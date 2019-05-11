/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IbPregDesafioUsuarioDTO;
import com.bds.ws.dto.IbPregDesafioUsuarioPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.model.IbPregDesafioUsuario;
import com.bds.ws.model.IbPregDesafioUsuarioPj;
import com.bds.ws.model.IbPreguntasDesafio;
import com.bds.ws.model.IbUsuariosPj;
import com.bds.ws.util.BDSUtil;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import static com.bds.ws.util.BDSUtil.TEXTO_SIN_RESULTADOS_JPA;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author luis.perez
 */
@Named("ibPregDesafioUsuarioPjFacade")
@Stateless(name = "wsIbPregDesafioUsuarioPjFacade")
public class IbPregDesafioUsuarioPjFacade extends AbstractFacade<IbPregDesafioUsuarioPj> {
    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbPregDesafioUsuarioPjFacade() {
        super(IbPregDesafioUsuarioPj.class);
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
            numColAct = em.createQuery("DELETE FROM IbPregDesafioUsuarioPj i WHERE i.idUsuarioPj.id = :idUsuario")
                    .setParameter("idUsuario", new BigDecimal(idUsuario))
                    .executeUpdate();
            //this.em.setFlushMode(FlushModeType.COMMIT);
            //this.em.flush();
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
        IbPregDesafioUsuarioPj pregDesafioUsuarioPj = new IbPregDesafioUsuarioPj();
        try {
            if (idUsuario != null && idPregunta != null) {
                pregDesafioUsuarioPj.setId(BigDecimal.ZERO);
                pregDesafioUsuarioPj.setIdUsuarioPj(new IbUsuariosPj(new BigDecimal(idUsuario)));
                pregDesafioUsuarioPj.setIdPreguntaDesafio(new IbPreguntasDesafio(new BigDecimal(idPregunta)));
                pregDesafioUsuarioPj.setRespuesta(respuesta);
                respuestaDTO = this.crearPJ(pregDesafioUsuarioPj);
                //this.em.setFlushMode(FlushModeType.COMMIT);
                //this.em.flush();
            } else {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            respuestaDTO.setCodigo(BDSUtil.CODIGO_EXCEPCION_GENERICA);
            respuestaDTO.setDescripcion(BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA);
        }
        return respuestaDTO;
    }
    
     /**
     * Metodo para obtener el listado de preguntas de desafio por usuario Pj
     *
     * @param idUsuario identificador del usuario Oracle11
     * @param nombreCanal codigo extendido del canal
     * @return lista de relaciones de preguntas definidas para un usuario
     */
    public IbPregDesafioUsuarioPjDTO listaPreguntasDesafioUsuarioPj(String idUsuario, String nombreCanal) {
        IbPregDesafioUsuarioPjDTO preguntasDesafioDTO = new IbPregDesafioUsuarioPjDTO();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        try {

            preguntasDesafioDTO.setPreguntasDesafioUsuarioPj(((List<IbPregDesafioUsuarioPj>) em.createQuery("SELECT i FROM IbPregDesafioUsuarioPj i WHERE i.idUsuarioPj.id = :idUsuarioPj")
                    .setParameter("idUsuarioPj", new BigDecimal(idUsuario))
                    .getResultList()));

            if (preguntasDesafioDTO.getPreguntasDesafioUsuarioPj().isEmpty()) {
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
     * Metodo para validar una rafaga de preguntas de desafio de Pj
     *
     * @param idUsuario id del usuario Pj
     * @param rafaga cadena con las preguntas y respuestas a validar ej: 
     * < codigoPregunta >< separador >< respuesta >< separador >< codigoPregunta >< separador >< respuesta >
     * @param separador separador que utilizara en la rafaga, si este es null se tomará guion "-" como separador
     * @param nombreCanal nombre del canal
     * @return UtilDTO -> 'true' en caso que la respuesta sea corresta 'false'
     * en caso contrario
     */
    public UtilDTO validarPreguntaDDPj(String idUsuario, String rafaga, String separador, String nombreCanal) {
        UtilDTO utilDTO = new UtilDTO();
        Map resultado = new HashMap();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbPregDesafioUsuarioDTO ibPregDesafioUsuarioDTO = new IbPregDesafioUsuarioDTO();
        boolean control = true;
        try {

            ibPregDesafioUsuarioDTO.setIbPregDesafioUsuarios((List<IbPregDesafioUsuario>) em.createQuery("SELECT i FROM IbPregDesafioUsuarioPj i WHERE i.idUsuario.id = :idUsuario")
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
                                control = false;
                                i = arrayCadena.length + 1;
                                break;
                            }
                        }
                    }
                }
                
                
            }
            
            resultado.put(VALIDO, control);

            utilDTO.setResulados(resultado);

        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo(BDSUtil.CODIGO_EXCEPCION_GENERICA);
            logger.error( new StringBuilder("ERROR JPA EN validarPreguntaDDPj: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (NoResultException e) {
            respuestaDTO.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log 
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN validarPreguntaDDPj: ")//.append("USR-").append(usuario)
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
}
