/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IbCanalDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.model.IbCanal;
import com.bds.ws.util.BDSUtil;
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
@Named("ibCanalFacade")
@Stateless(name = "wsIbCanalFacade")
public class IbCanalFacade extends AbstractFacade<IbCanal> {

    /*
     *logs del sistema
     */
    private static final Logger logger = Logger.getLogger(IbCanalFacade.class.getName());

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbCanalFacade() {
        super(IbCanal.class);
    }

    /**
     * Metodo que realiza la busqueda de un canal por su nombre
     *
     * @param nombreCanal nombre del canal a buscar
     * @return IbCanal
     */
    public IbCanal consultaCanalNombre(String nombreCanal) {
        IbCanal ibCanal = new IbCanal();

        try {
            ibCanal = ((IbCanal) em.createNamedQuery("IbCanal.findByCodigo")
                    .setParameter("codigo", nombreCanal).getSingleResult());
            
            if(ibCanal == null || ibCanal.getId() == null){
                throw new NoResultException();
            }

        } catch (NoResultException | IllegalArgumentException e) {
            logger.error( new StringBuilder("ERROR JPA EN consultaCanalNombre: ")
                    .append("CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
        }
        return ibCanal;
    }

    /**
     * *
     * Metodo que realiza la busqueda de un canal por su id
     *
     * @param idCanal id del canal
     * @return IbCanal
     */
    public IbCanal consultaCanalID(String idCanal) {
        IbCanal ibCanal = new IbCanal();

        try {
            ibCanal = ((IbCanal) em.createNamedQuery("IbCanal.findById")
                    .setParameter("id", new BigDecimal(idCanal))
                    .getSingleResult());
            
            if(ibCanal == null || ibCanal.getId() == null){
                throw new NoResultException();
            }

        } catch (NoResultException | IllegalArgumentException e) {
            /*
            logger.error( new StringBuilder("ERROR JPA EN consultaCanalID: ")
                    .append("CH-").append(idCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            */
        } catch (Exception e) {
        }
        return ibCanal;
    }
    
    /**
     * Metodo para obtener el listado de canales ordenados ascendientemente
     * @param nombreCanal nombre del canal
     * @return IbCanalDTO -> List<IbCanal>
     */
    public IbCanalDTO listaCanales(String nombreCanal) {
        IbCanalDTO ibCanal = new IbCanalDTO();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        try {
            ibCanal.setCanales(((List<IbCanal>) em.createNamedQuery("IbCanal.findAll")
                    .getResultList()));
            
            if(ibCanal.getCanales().isEmpty()){
                throw new NoResultException();
            }
        } catch (NoResultException e) {
            respuestaDTO.setCodigo(BDSUtil.CODIGO_SIN_RESULTADOS_JPA);
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN listaCanales: ")
                    .append("CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo(BDSUtil.CODIGO_EXCEPCION_GENERICA);
            logger.error( new StringBuilder("ERROR JPA EN listaCanales: ")
                    .append("CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(BDSUtil.CODIGO_EXCEPCION_GENERICA);
        }
        
        ibCanal.setRespuesta(respuestaDTO);
        return ibCanal;
    }

}
