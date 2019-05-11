/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IbFideicomisoPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.model.IbEstatusPagosPj;
import com.bds.ws.model.IbFideicomisoDetPj;
import com.bds.ws.model.IbFideicomisoPj;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author luis.perez
 */
@Named("ibFideicomisoPjFacade")
@Stateless(name = "wsIbFideicomisoPjFacade")
public class IbFideicomisoPjFacade extends AbstractFacade<IbFideicomisoPj> {

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @EJB
    private IbEstatusPagosPjFacade ibEstatusPagosPjFacade;

    public IbFideicomisoPjFacade() {
        super(IbFideicomisoPj.class);
    }

    public List<IbFideicomisoPj> listarCabeceraFideicomiso(BigDecimal idUsuario) {
        List<IbFideicomisoPj> cabeceraFideicomiso = null;
        try {
            StringBuilder consulta = new StringBuilder();

            consulta.append(" SELECT e FROM IbFideicomisoPj e");
            consulta.append(" WHERE e.codigoClienteAbanks = :idUsuario ORDER BY e.fechaHoraCarga DESC");

            cabeceraFideicomiso = (List<IbFideicomisoPj>) em.createQuery(consulta.toString())
                    .setParameter("idUsuario", idUsuario)
                    .getResultList();
        } catch (Exception e) {
            throw e;
        }
        return cabeceraFideicomiso;
    }

    public Boolean archivoCargaMasivaExiste(String codigoClienteAbanks, String filename) throws Exception {
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append("SELECT count(i) ");
            consulta.append("  FROM IbFideicomisoPj i ");
            consulta.append(" WHERE i.codigoClienteAbanks = :codigoClienteAbanks ");
            consulta.append("   AND i.nombreArchivo = :nombreArchivo ");
            consulta.append("   AND i.estatus.id > 0 ");//ya esta procesado

            long count = (long) em.createQuery(consulta.toString())
                    .setParameter("nombreArchivo", filename)
                    .setParameter("codigoClienteAbanks", new Long(codigoClienteAbanks))
                    .getSingleResult();
            return (count > 0);
        } catch (Exception ex) {
            //respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log            
            throw ex;
        }
    }

    /**
     * Devuelve el listado autorizado para un usuario
     * @param cdClienteAbanks
     * @param idUsuarioAurorizado
     * @param idServicio
     * @param StatusAConsultar
     * @return
     * @throws Exception 
     */
    public IbFideicomisoPjDTO listarIbCargaFideicomisoAutorizarPj(BigDecimal cdClienteAbanks, Long idUsuarioAurorizado, Long idServicio, String StatusAConsultar) throws Exception {

        IbFideicomisoPjDTO ibFideicomisoPjDTO = new IbFideicomisoPjDTO();
        ibFideicomisoPjDTO.setIbFideicomisoPjsList(new ArrayList<IbFideicomisoPj>());
        ibFideicomisoPjDTO.setRespuestaDTO(new RespuestaDTO());

        List<IbFideicomisoPj> ibFideicomisoPjList = null;

        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT distinct b FROM IbFideicomisoPj  b");
            consulta.append(" INNER JOIN FETCH b.ibFideicomisoDetPjCollection bar");
            consulta.append(" WHERE b.codigoClienteAbanks =:cdClienteAbanks and (bar.estatus.id=1 or bar.estatus.id=2 or bar.estatus.id=3)");
            consulta.append(" and NOT EXISTS (SELECT p FROM IbSecuenciaAprobacionPj p");
            consulta.append(" WHERE p.idTransaccion = bar.id and");
            consulta.append(" p.idServicioPj.id =:idServicioPj and p.idUsuarioPj.id =:idUsuarioAurorizado)");

            ibFideicomisoPjList = (List<IbFideicomisoPj>) em.createQuery(consulta.toString())
                    .setParameter("cdClienteAbanks", cdClienteAbanks)
                    .setParameter("idUsuarioAurorizado", new BigDecimal(idUsuarioAurorizado))
                    .setParameter("idServicioPj", new BigDecimal(idServicio))
                    .getResultList();

        } catch (Exception e) {
            throw e;
        }
        ibFideicomisoPjDTO.setIbFideicomisoPjsList(ibFideicomisoPjList);

        return ibFideicomisoPjDTO;
    }
    
    public IbFideicomisoDetPj actualizarFideicomisoId(BigDecimal idBenediciario, BigDecimal idEstatus) throws Exception {
        IbFideicomisoDetPj ibFideicomisoDetPj = null;
        IbEstatusPagosPj estatus = null;

        try {
            ibFideicomisoDetPj = (IbFideicomisoDetPj) getEntityManager().find(IbFideicomisoDetPj.class, idBenediciario.longValue());
            if(ibFideicomisoDetPj!=null){
                estatus = this.ibEstatusPagosPjFacade.buscarIbEstatusPj(idEstatus);
                ibFideicomisoDetPj.setEstatus(estatus);
                ibFideicomisoDetPj = (IbFideicomisoDetPj) getEntityManager().merge(ibFideicomisoDetPj);
                getEntityManager().flush();
            }
              
        } catch (NoResultException e) {
            // Se debe validar en la clase que llama el metodo
        } catch (Exception e) {
            //TODO: definir respuesta incorrecta            
            throw e;
        }
        return ibFideicomisoDetPj;
    }
    
    
    
    
    public IbFideicomisoPj buscarIbFideicomisoPj(Long idfideicomiso) {
        IbFideicomisoPj ibFideicomisoPj = null;
        try {
            ibFideicomisoPj = (IbFideicomisoPj) getEntityManager().find(IbFideicomisoPj.class, idfideicomiso);
        } catch (NoResultException e) {
            // Se debe validar en la clase que llama el metodo
        } catch (Exception e) {
            //TODO: definir respuesta incorrecta            
            throw e;
        }
        return ibFideicomisoPj;
    }
}
