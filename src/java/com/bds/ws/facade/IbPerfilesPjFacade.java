/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.model.IbPerfilesPj;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author luis.perez
 */
@Named("ibPerfilesPjFacade")
@Stateless(name = "wsIbPerfilesPjFacade")
public class IbPerfilesPjFacade extends AbstractFacade<IbPerfilesPj>  {
    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbPerfilesPjFacade() {
        super(IbPerfilesPj.class);
    }
    
    public List<IbPerfilesPj> consultarPerfilesPj(Character estatus) {
        List<IbPerfilesPj> ibPerfilesPj = null;
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT e FROM IbPerfilesPj e");
            consulta.append("     WHERE e.estatus = :estatus");
            
            ibPerfilesPj = (List<IbPerfilesPj>) em.createQuery(consulta.toString())
                    .setParameter("estatus", estatus)
                    .getResultList();
        } catch (Exception e) {
            throw e;
        }
        return ibPerfilesPj;
    }
}
