/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IbClavesOperacionesEspecialesDTO;
import com.bds.ws.dto.RespuestaDTO;
import static com.bds.ws.facade.AbstractFacade.logger;
import com.bds.ws.model.IbHistClavesOpEspeciales;
import com.bds.ws.model.IbUsuarios;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.TEXTO_SIN_RESULTADOS_JPA;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ledwin.belen
 */
@Named("IbHistClavesOpEspecialesFacade")
@Stateless(name = "wsIbHistClavesOpEspecialesFacade")
public class IbHistClavesOpEspecialesFacade extends AbstractFacade<IbHistClavesOpEspeciales> {

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbHistClavesOpEspecialesFacade() {
        super(IbHistClavesOpEspeciales.class);
    }
    
    public IbClavesOperacionesEspecialesDTO getHistClavesOperacionesEspeciales(IbClavesOperacionesEspecialesDTO ibClavesOperacionesEspecialesDTO,String idUsuario, String claveOP, String codigoCanal) {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbHistClavesOpEspeciales ibHistClavesOpEspeciales = new IbHistClavesOpEspeciales();
        List<IbHistClavesOpEspeciales> ibHistClavesOpEspecialesList = null;
        try {

            ibHistClavesOpEspecialesList = (List<IbHistClavesOpEspeciales>) em.createQuery("SELECT c FROM IbHistClavesOpEspeciales c "
                    + "WHERE c.idUsuario = :idUsuario ORDER BY c.id DESC")
                    .setParameter("idUsuario", new IbUsuarios(new BigDecimal(idUsuario)))
                    .setFirstResult(0)
                    .setMaxResults(3)
                    .getResultList();
            
            if (ibHistClavesOpEspecialesList == null || ibHistClavesOpEspecialesList.isEmpty()) {
                throw new NoResultException();
            }
        } catch (NoResultException e) {
            respuestaDTO.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log 
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN getClavesOperacionesEspeciales: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN getClavesOperacionesEspeciales: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log

        }finally{
            ibClavesOperacionesEspecialesDTO.setIbHistClavesOpEspecialesList(ibHistClavesOpEspecialesList);
            ibClavesOperacionesEspecialesDTO.setRespuesta(respuestaDTO);
        }
        return ibClavesOperacionesEspecialesDTO;
    }
    
}
