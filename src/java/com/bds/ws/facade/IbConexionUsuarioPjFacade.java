/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.model.IbConexionUsuarioPj;
import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author luis.perez
 */
@Stateless
public class IbConexionUsuarioPjFacade extends AbstractFacade<IbConexionUsuarioPj> {
    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbConexionUsuarioPjFacade() {
        super(IbConexionUsuarioPj.class);
    }
    
    public IbConexionUsuarioPj consultarConexionPorUsuario(BigDecimal idUsuario, String idSession) {
        IbConexionUsuarioPj ibConexionUsuarioPj = null;
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT e FROM IbConexionUsuarioPj e");
            consulta.append("     WHERE e.idUsuarioPj.id = :idUsuario");
            consulta.append("     AND   e.idSesion       = :idSesion");
            
            ibConexionUsuarioPj = (IbConexionUsuarioPj) em.createQuery(consulta.toString())
                    .setParameter("idUsuario", idUsuario)
                    .setParameter("idSesion", idSession)
                    .getSingleResult();
        } catch (NoResultException e) {
	//SE HACE EL MANEJO DE LA EXEPCION DESDE EL METODO DONDE SE LLAMA 
            //VERIFICANDO SI LO QUE SE DEVULEVE ES NULL
        } catch (Exception e) {
            throw e;
        }
        return ibConexionUsuarioPj;
    }
    
    public IbConexionUsuarioPj consultarConexionPorUsuario(BigDecimal idUsuario) {
        IbConexionUsuarioPj ibConexionUsuarioPj = null;
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT e FROM IbConexionUsuarioPj e");
            consulta.append("     WHERE e.idUsuarioPj.id = :idUsuario");
            
            ibConexionUsuarioPj = (IbConexionUsuarioPj) em.createQuery(consulta.toString())
                    .setParameter("idUsuario", idUsuario)
                    .getSingleResult();
        } catch (NoResultException e) {
	//SE HACE EL MANEJO DE LA EXEPCION DESDE EL METODO DONDE SE LLAMA 
            //VERIFICANDO SI LO QUE SE DEVULEVE ES NULL
        } catch (Exception e) {
            throw e;
        }

        return ibConexionUsuarioPj;
    }
}
