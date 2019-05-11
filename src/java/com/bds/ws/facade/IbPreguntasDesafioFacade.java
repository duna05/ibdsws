/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IbPreguntasDesafioDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.model.IbPreguntasDesafio;
import com.bds.ws.util.BDSUtil;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;

/**
 *
 * @author luis.perez
 */
@Stateless
public class IbPreguntasDesafioFacade extends AbstractFacade<IbPreguntasDesafio> {

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    /*
     *logs del sistema
     */
    private static final Logger logger = Logger.getLogger(IbPreguntasDesafioFacade.class.getName());

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbPreguntasDesafioFacade() {
        super(IbPreguntasDesafio.class);
    }
    
    /**
     * Metodo para obtener el listado de preguntas de desafio banco
     * @param nombreCanal nombre del canal
     * @return IbPreguntasDesafioDTO
     */
    public IbPreguntasDesafioDTO listaPreguntasDesafioBanco(String nombreCanal) {
        IbPreguntasDesafioDTO preguntasDesafioDTO = new IbPreguntasDesafioDTO();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        try {

            preguntasDesafioDTO.setPreguntasDesafio(((List<IbPreguntasDesafio>) em.createNamedQuery("IbPreguntasDesafio.findAllAct")
                    .setParameter("estatus", 'A')
                    .getResultList()));
            
            if (preguntasDesafioDTO.getPreguntasDesafio().isEmpty()){
                throw new NoResultException();
}

        } catch (NoResultException e) {
            respuestaDTO.setCodigo(BDSUtil.CODIGO_SIN_RESULTADOS_JPA);
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN listaPreguntasDesafioBanco: ")
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo(BDSUtil.CODIGO_EXCEPCION_GENERICA);
            logger.error( new StringBuilder("ERROR JPA EN listaPreguntasDesafioBanco: ")
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
   
}
