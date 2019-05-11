/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.model.IbSecuenciaAprobacionPj;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author juan.vasquez
 */
@Named("ibSecuenciaAprobacionPjFacade")
@Stateless(name = "wsIbSecuenciaAprobacionPjFacade")
public class IbSecuenciaAprobacionPjFacade extends AbstractFacade<IbSecuenciaAprobacionPj> {
    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public IbSecuenciaAprobacionPjFacade(){
        super(IbSecuenciaAprobacionPj.class);
    }
    
    public List<IbSecuenciaAprobacionPj> buscarAprobacionesMultiples(BigDecimal idServicio,Long idTransaccion) {
        List<IbSecuenciaAprobacionPj> ibSecuenciaAprobacionPjList = null;
        try {    
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT e FROM IbSecuenciaAprobacionPj e ");
            consulta.append(" WHERE e.idTransaccion =:idTransaccion");
            //consulta.append(" AND e.idServicioPj.id =:idServicio");

            ibSecuenciaAprobacionPjList = (List<IbSecuenciaAprobacionPj>) em.createQuery(consulta.toString())
                    //.setParameter("idServicio", idServicio)
                    .setParameter("idTransaccion", idTransaccion)
                    .getResultList();
        } catch (Exception e) {
            throw e;
        }
        return ibSecuenciaAprobacionPjList;
    }
    
    
    
    
    public void saveIbCargaBeneficiariosPj(IbSecuenciaAprobacionPj ibSecuenciaAprobacionPj){
   
        try{
             getEntityManager().persist(ibSecuenciaAprobacionPj);
        }catch (NoResultException e) {
            throw e;
            // Se debe validar en la clase que llama el metodo
        } catch (Exception e) {
            //TODO: definir respuesta incorrecta            
            throw e;
        }
       
    }
    
    
    
    
}
