/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;


import com.bds.ws.dto.IbPeriodoClaveDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.model.IbPeriodoClave;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
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
@Named("ibPeriodoClaveFacade")
@Stateless(name = "wsIbPeriodoClaveFacade")
public class IbPeriodoClaveFacade extends AbstractFacade<IbPeriodoClave> {

    /**
     * Log de sistema
     */
    private static final Logger logger = Logger.getLogger(IbPeriodoClaveFacade.class.getName());

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbPeriodoClaveFacade() {
        super(IbPeriodoClave.class);
    }

    /**
     * Metodo que obtiene el listado de periodos de clave
     * @param nombreCanal nombre del canal
     * @return IbPeriodoClaveDTO
     */
    public IbPeriodoClaveDTO obtenerListadoPeriodosClave(String nombreCanal) {
        IbPeriodoClaveDTO periodoClaveDTO = new IbPeriodoClaveDTO();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        try {
            periodoClaveDTO.setPeriodosClave((List<IbPeriodoClave>) em.createNamedQuery("IbPeriodoClave.findAll")
                    .getResultList());
            
            if (periodoClaveDTO.getPeriodosClave().isEmpty()){
                throw new NoResultException();
            }
            
        } catch (NoResultException e) {
            respuestaDTO.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log 
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN obtenerListadoPeriodosClave: ")//.append("USR-").append(usuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN obtenerListadoPeriodosClave: ")//.append("USR-").append(usuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception ex) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log            
        }
        periodoClaveDTO.setRespuesta(respuestaDTO);
        return periodoClaveDTO;
    }

    
}
