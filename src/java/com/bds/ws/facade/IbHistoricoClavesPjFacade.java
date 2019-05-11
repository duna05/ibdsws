/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.model.IbHistoricoClavesPj;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author luis.perez
 */
@Stateless
public class IbHistoricoClavesPjFacade extends AbstractFacade<IbHistoricoClavesPj> {
    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbHistoricoClavesPjFacade() {
        super(IbHistoricoClavesPj.class);
    }

    public List<IbHistoricoClavesPj> consultarClavesPorIdUsuario(BigDecimal idUsuario, int cantidadClaves) {
        List<IbHistoricoClavesPj> ibHistoricoClavesPj = null;
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT e FROM IbHistoricoClavesPj e");
            consulta.append("     WHERE e.idUsuarioPj.id = :idUsuario");
            consulta.append(" ORDER BY  e.fechaCreacion    DESC ");
            
            ibHistoricoClavesPj = (List<IbHistoricoClavesPj>) em.createQuery(consulta.toString())
                    .setParameter("idUsuario", idUsuario)
                    .setMaxResults(cantidadClaves)
                    .getResultList();
        } catch (Exception e) {
            throw e;
        }
        return ibHistoricoClavesPj;
    }
}
