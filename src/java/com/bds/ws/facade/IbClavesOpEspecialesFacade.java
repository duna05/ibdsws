/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IbClavesOperacionesEspecialesDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.model.IbClavesOpEspeciales;
import com.bds.ws.model.IbUsuarios;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ledwin.belen
 */
@Named("IbClavesOpEspecialesFacade")
@Stateless(name = "wsIbClavesOpEspecialesFacade")
public class IbClavesOpEspecialesFacade extends AbstractFacade<IbClavesOpEspeciales> {

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbClavesOpEspecialesFacade() {
        super(IbClavesOpEspeciales.class);
    }

    public IbClavesOperacionesEspecialesDTO getClavesOperacionesEspeciales(String idUsuario, String claveOP, String codigoCanal) {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbClavesOpEspeciales ibClavesOpEspeciales = null;
        IbClavesOperacionesEspecialesDTO ibClavesOperacionesEspecialesDTO = new IbClavesOperacionesEspecialesDTO();

        try {

                    ibClavesOpEspeciales = (IbClavesOpEspeciales) em.createQuery("SELECT c FROM IbClavesOpEspeciales c "
                    + "WHERE c.idUsuario = :idUsuario ORDER BY c.id DESC")
                    .setParameter("idUsuario", new IbUsuarios(new BigDecimal(idUsuario)))
                    .getSingleResult();

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

        } finally {
            ibClavesOperacionesEspecialesDTO.setIbClavesOpEspeciales(ibClavesOpEspeciales);
            ibClavesOperacionesEspecialesDTO.setRespuesta(respuestaDTO);
        }
        return ibClavesOperacionesEspecialesDTO;
    }

    public IbClavesOpEspeciales validarExisteClaveOperacionesEspecialesUser(String idUsuario) {       
        IbClavesOpEspeciales ibClavesOpEspeciales = null;
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT e FROM IbClavesOpEspeciales e");
            consulta.append("     WHERE e.idUsuario.id = :idUsuario");

            ibClavesOpEspeciales = (IbClavesOpEspeciales)em.createQuery(consulta.toString())
                    .setParameter("idUsuario", new BigDecimal(idUsuario))
                    .getSingleResult();
        } catch (NoResultException e) {
            //SE HACE EL MANEJO DE LA EXCEPCION DESDE EL METODO DONDE SE LLAMA 
            //VERIFICANDO SI LO QUE SE DEVULEVE ES NULL
        } catch (Exception e) {
            throw e;
        }
        return ibClavesOpEspeciales;
    }
}
