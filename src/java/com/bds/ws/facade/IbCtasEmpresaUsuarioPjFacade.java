/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.model.IbCtasEmpresaUsuarioPj;
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
public class IbCtasEmpresaUsuarioPjFacade extends AbstractFacade<IbCtasEmpresaUsuarioPj> {
    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbCtasEmpresaUsuarioPjFacade() {
        super(IbCtasEmpresaUsuarioPj.class);
    }
    
    public void eliminarIbCtasEmpresaUsuarioPjPorUsuario(BigDecimal idUsuario) {
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" DELETE FROM IbCtasEmpresaUsuarioPj e ");
            consulta.append(" WHERE  e.idUsuarioPj.id = :idUsuario");
            em.createQuery(consulta.toString()).setParameter("idUsuario", idUsuario)
            .executeUpdate(); 
        } catch (Exception e) {
            throw e;
        }
    }
    
    public List<IbCtasEmpresaUsuarioPj> obtenerIbCtasEmpresaUsuarioPj(BigDecimal idUsuario, BigDecimal idEmpresa) {
        List<IbCtasEmpresaUsuarioPj> ctsEmprUsr = null;
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT e FROM IbCtasEmpresaUsuarioPj e ");
            consulta.append(" WHERE  e.idUsuarioPj.id = :idUsuario");
            consulta.append(" AND  e.idEmpresaPj.id = :idEmpresa");
            ctsEmprUsr = em.createQuery(consulta.toString()).setParameter("idUsuario", idUsuario).setParameter("idEmpresa", idEmpresa)
            .getResultList();
        } catch (Exception e) {
            throw e;
        }
        return ctsEmprUsr;
    }
}
